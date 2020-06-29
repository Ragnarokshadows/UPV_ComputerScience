// Modules to be imported.
var cluster = require('cluster');
var fs = require('fs');
var path = require('path');
var zmq = require('zeromq');
var os = require('os');

// Name of DEALER socket.
const ipcName = 'Act2.ipc';
// URL of DEALER socket.
const dlName = 'ipc://'+ipcName;
// Number of active workers.
var curWorkers = 0;
// Per interval file requests.
var intervalFileReq = [];
// Accumulated file requests.
var allFileReq = [];
// Accumulated number of errors.
var numErrors = 0;

// SIGTERM and SIGKILL handler.
function terminate() {
    // Kill all the worker processes.
    for(var i in cluster.workers) {
	console.log("Sending signal SIGTERM to worker %d.", 
		    cluster.workers[i].process.pid);
	cluster.workers[i].kill('SIGTERM');
    }
    // Remove the IPC socket.
    fs.unlinkSync(ipcName);
    // Terminate the master.
    process.exit(1);
}

function statistics() {
    var totalRequests=0;
    console.log("Current workers: %d.", curWorkers);
    console.log("Number of received requests:");
    for (var i in cluster.workers) {
	// Print the number of processed requests.
	console.log("  PID %d. Interval requests: %d. All requests: %d.",
		    cluster.workers[i].process.pid,
		    cluster.workers[i].requests,
		    cluster.workers[i].allReqs);
	// Accumulate on totalRequests.
	totalRequests += cluster.workers[i].requests;
	// Clear per-worker requests field.
	cluster.workers[i].requests=0;
    }
    // Check whether an existing worker should be killed.
    if (totalRequests < 2 && curWorkers > os.cpus().length) {
	// It should be... Report on it.
	console.log("Scaling in! One worker is eliminated!");
	// Disconnect the first worker. To this end, try to
	// iterate on the current set and, after disconnecting
	// one, break the loop.
	for (var i in cluster.workers) {
	    cluster.workers[i].kill();
	    break;
	}
	// Decrease number of workers.
	curWorkers--;
    }
    // Check whether a new worker is needed...
    else if (totalRequests > curWorkers*4) {
	// It is! Print a message...
	console.log("Scaling out! One worker is added!");
	// Increase number of workers.
	curWorkers++;
	// Add another worker.
	worker = cluster.fork();
	// Clear its request counters.
	worker.requests=0;
	worker.allReqs=0;
    } else {
	console.log("No scaling action is required");
    }
    // Print the collected data for files.
    for (var i in allFileReq) {
	console.log( 'File "%s" -> Interval requests: %d ' +
		     'All requests: %d.', i, intervalFileReq[i],
		     allFileReq[i] );
	// Clear the interval file request counter.
	intervalFileReq[i]=0;
    }
    // Print the number of requests that have ended in error.
    console.log( 'Number of requests that generated an error: %d.',
		 numErrors );
}

// Check whether the intended arguments have been
// provided.
if (process.argv.length < 3) {
    console.log('Usage: node '+path.basename(process.argv[1])+
		' port');
    process.exit(1);
}

// Main program.
if (cluster.isMaster) {
    // Get the number of processors.
    var numCPUs = os.cpus().length;
    // The master needs a router/dealer pair of sockets.
    var rt = zmq.socket('router');
    var dl = zmq.socket('dealer');
    // Bind those two sockets.
    rt.bindSync('tcp://127.0.0.1:'+process.argv[2]);
    dl.bindSync(dlName);
    // Manage client messages.
    rt.on('message', function() {
	// All message segments have been received as a list
	// of arguments. They should be inserted in an array
	// in order to send them. "msg" is such array.
	msg = Array.prototype.slice.call(arguments);
	dl.send(msg);
    });
    // Manage worker messages.
    dl.on('message', function() {
	// All message segments have been received as a list
	// of arguments. They should be inserted in an array
	// in order to send them. "msg" is such array.
	msg = Array.prototype.slice.call(arguments);
	// Get the important evaluation data from the message.
	m = JSON.parse(msg[2]);
	// Update the file counters.
	if (m.error) {
	    numErrors++;
	} else {
	    // Increase the number of times this file has been
	    // requested in the current interval.
	    if (!intervalFileReq[m.path])
		intervalFileReq[m.path]=0;
	    intervalFileReq[m.path]++;
	    // Increase the total number of times this file has
	    // been requested.
	    if (!allFileReq[m.path])
		allFileReq[m.path]=0;
	    allFileReq[m.path]++;
	}
	try {
	    cluster.workers[m.pid].requests++;
	    cluster.workers[m.pid].allReqs++;
	} catch(err) {
	    // Reply from disconnected worker.
	    console.log( "Worker %d has been disconnected!",
			 m.pid );
	}
	// Forward the message to the ROUTER socket.
	rt.send(msg);
    });
    // Manage master termination.
    process.on('SIGTERM', terminate );
    process.on('SIGINT', terminate );
    // Collect and print the statistics every 5 seconds.
    setInterval(statistics, 5000);
    // Create as many workers as processors.
    for(var i=0; i<numCPUs; i++) {
	// Increase the current number of workers.
	curWorkers++;
	// Generate the new worker.
	worker = cluster.fork();
	// Clear its request counters.
	worker.requests = 0;
	worker.allReqs = 0;
    }
}
else
// Main function of worker processes.
{
    console.log( "Starting worker with PID %d.",process.pid);
    // Create a rep socket.
    var rep = zmq.socket('rep');
    // Connect to DEALER
    rep.connect(dlName);
    rep.on('message', function(data) {
	// parse incoming message
	var request = JSON.parse(data)
/*	console.log('Worker %d has received a request for file %s.', 
		    process.pid, request.path)
*/	// read file and reply with content
	fs.readFile(request.path, function(err, data) {
	    if (err) {
/*		console.log('Worker %d has found an error trying to read "%s"', 
			    process.pid, request.path);
*/		data = ' NOT FOUND';
		
	    } else {
/*		console.log('Worker %d is replying request for "%s"', 
			    process.pid, request.path)
*/	    }
	    rep.send(JSON.stringify({
		// Pass the id number instead of the PID.
		pid: cluster.worker.id ,
		path: request.path ,
		// Although files shouldn't be large, it is not 
		// reasonable to compare all file contents with a
		// given string (' NOT FOUND'). So, use a boolean
		// to this end.
		error: (err ? true:false),
		data: data+'' ,
		timestamp: new Date().toString()
	    }));
	})
    })
}
