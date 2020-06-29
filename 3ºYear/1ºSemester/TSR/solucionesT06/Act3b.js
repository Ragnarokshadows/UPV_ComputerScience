/****************************************************************/
/* Act3b.js							*/
/* 								*/
/* The solution shown in file Act3.js uses the communication	*/
/* channels provided by the 'cluster' module in order to	*/
/* send messages from the worker processes to the master one.   */
/* It used a line like this (in line 70) in the master:		*/
/*	worker.on('message', handleMessage);			*/
/* ...in order to manage message receptions and a line like	*/
/* this (in line 162) in the worker processes:			*/
/*	process.send(JSON.stringify({id: cluster.worker.id}))	*/
/* Instead of this, the master could filter the regular messages*/
/* to be forwarded to clients.					*/
/* This file uses that second approach, generating a solution	*/
/* that is a bit shorter.					*/
/*								*/
/****************************************************************/

// Modules to be imported.
const cluster = require('cluster')
const fs = require('fs')
const path = require('path')
const zmq = require('zeromq')
const os = require('os')

// Name of DEALER socket.
const ipcName = 'Act2.ipc';
// URL of DEALER socket.
const dlName = 'ipc://'+ipcName;
// Number of active workers.
var curWorkers = 0;

// SIGTERM and SIGKILL handler.
function terminate() {
    // Kill all the worker processes.
    for(let i in cluster.workers) {
	console.log("Sending signal SIGTERM to worker %d.", 
		    cluster.workers[i].process.pid)
	cluster.workers[i].kill('SIGTERM')
    }
    // Remove the IPC socket.
    fs.unlinkSync(ipcName)
    // Terminate the master.
    process.exit(1)
}

function statistics() {
    let totalRequests=0
    console.log("Current workers: %d.", curWorkers)
    console.log("Number of received requests:")
    for (let i in cluster.workers) {
	// Print the number of processed requests.
	console.log("  PID %d. Requests: %d",
		    cluster.workers[i].process.pid,
		    cluster.workers[i].requests)
	// Accumulate on totalRequests.
	totalRequests += cluster.workers[i].requests
	// Clear per-worker requests field.
	cluster.workers[i].requests=0
    }
    // Check whether an existing worker should be killed.
    if (totalRequests < 2 && curWorkers > os.cpus().length) {
	// It should be... Report on it.
	console.log("Scaling in! One worker is eliminated!")
	// Eliminate the first worker. To this end, try to
	// iterate on the current set and, after eliminating
	// one, break the loop.
	for (let i in cluster.workers) {
	    cluster.workers[i].kill();
	    break;
	}
	// Decrease number of workers.
	curWorkers--
    }
    // Check whether a new worker is needed...
    else if (totalRequests > curWorkers*4) {
	// It is! Print a message...
	console.log("Scaling out! One worker is added!")
	// Increase number of workers.
	curWorkers++
	// Add another worker.
	worker = cluster.fork()
	worker.requests=0
    } else {
	console.log("No scaling action is required")
    }
}

// Check whether the intended arguments have been
// provided.
if (process.argv.length < 3) {
    console.log('Usage: node '+path.basename(process.argv[1])+
		' port')
    process.exit(1)
}

// Main program.
if (cluster.isMaster) {
    // Get the number of processors.
    const numCPUs = os.cpus().length
    // The master needs a router/dealer pair of sockets.
    const rt = zmq.socket('router')
    const dl = zmq.socket('dealer')
    // Bind those two sockets.
    rt.bindSync('tcp://127.0.0.1:'+process.argv[2])
    dl.bindSync(dlName)
    // Manage client messages.
    rt.on('message', function(...msg) {
	// All message segments are in the 'msg' array.
	dl.send(msg)
    });
    // Manage the worker messages.
    dl.on('message', function(...msg) {
	// All message segments are in the 'msg' array.
	// Get the important evaluation data from the message.
	m = JSON.parse(msg[2]);
	try {
	    cluster.workers[m.pid].requests++;
	} catch(err) {
	    // Reply from disconnected worker.
	    console.log( "Worker %d has been disconnected!",
			 m.pid )
	}
	// Forward the message to the ROUTER socket.
	rt.send(msg)
    });
    // Manage master termination.
    process.on('SIGTERM', terminate )
    process.on('SIGINT', terminate )
    // Collect and print the statistics every 5 seconds.
    setInterval(statistics, 5000)
    // Create as many workers as processors.
    for(let i=0; i<numCPUs; i++) {
	curWorkers++
	worker = cluster.fork()
	worker.requests = 0
    }
}
else
// Main function of worker processes.
{
    console.log( "Starting worker with PID %d.",process.pid)
    // Create a rep socket.
    const rep = zmq.socket('rep')
    // Connect to DEALER
    rep.connect(dlName)
    rep.on('message', function(data) {
	// Parse incoming message
	let request = JSON.parse(data)
	console.log('Worker %d has received a request for file %s.', 
		    process.pid, request.path)
	// Read file and reply with content
	fs.readFile(request.path, function(err, data) {
	    if (err) {
		console.log('Worker %d has found an error trying to read "%s"', 
			    process.pid, request.path)
		data = ' NOT FOUND'
	    } else {
		console.log('Worker %d is replying request for "%s"', 
			    process.pid, request.path)
	    }
	    rep.send(JSON.stringify({
		// Pass the id number instead of the PID.
		pid: cluster.worker.id ,
		path: request.path ,
		data: data+'' ,
		timestamp: new Date().toString()
	    }))
	})
    })
}
