/****************************************************************/
/*								*/
/* Act3.js							*/
/* 								*/
/****************************************************************/

// Modules to be imported.
const cluster = require('cluster')
const fs = require('fs')
const path = require('path')
const zmq = require('zeromq')
const os = require('os')

// Name of DEALER socket.
const ipcName = 'Act2.ipc'
// URL of DEALER socket.
const dlName = 'ipc://'+ipcName
// Number of active workers.
let curWorkers = 0

// SIGTERM and SIGKILL handler.
function terminate() {
    // Kill all worker processes.
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

function handleMessage(msg) {
    m = JSON.parse(msg)
    // Increase the number of processed requests.
    cluster.workers[m.id].requests++
}
    
function statistics() {
    var totalRequests=0
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
	console.log("Scaling in! One worker is eliminated!");
	// Eliminate the first worker. To this end, try to
	// iterate on the current set and, after eliminating
	// one, break the loop.
	for (let i in cluster.workers) {
	    cluster.workers[i].kill()
	    break
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
	// Set a message handler for it.
	worker.on('message', handleMessage)
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
    rt.bindSync('tcp://127.0.0.1:'+process.argv[2]);
    dl.bindSync(dlName);
    // Manage the client messages.
    rt.on('message', function(...msg) {
	// All message segments are in the 'msg' array.
	dl.send(msg);
    });
    // Manage worker messages.
    dl.on('message', function(...msg) {
	// All message segments are in the 'msg' array.
	rt.send(msg);
    });
    // Manage master termination.
    process.on('SIGTERM', terminate )
    process.on('SIGINT', terminate )
    // Collect and print the statistics every 5 seconds.
    setInterval(statistics, 5000)
    // Create as many workers as processors.
    for(var i=0; i<numCPUs; i++) {
	curWorkers++
	cluster.fork()
    }
    // Set message handlers for the current workers.
    for(var i in cluster.workers) {
	cluster.workers[i].on('message', handleMessage)
	cluster.workers[i].requests=0
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
	var request = JSON.parse(data)
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
		pid: process.pid ,
		path: request.path ,
		data: data+'' ,
		timestamp: new Date().toString()
	    }))
	    try {
		// Send a message to the master, reporting this
		// worker id.
		process.send(JSON.stringify({id: cluster.worker.id}))
	    } catch (err) {
		console.log("Worker %d is being disconnected by the master!!", cluster.worker.id)
	    }
	})
    })
}
