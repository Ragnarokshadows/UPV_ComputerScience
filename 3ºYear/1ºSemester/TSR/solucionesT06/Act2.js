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

// SIGTERM and SIGKILL handler.
function terminate() {
    // Kill all the worker processes.
    for(let i in cluster.workers) {
	console.log("Sending signal SIGTERM to worker %d.", cluster.workers[i].process.pid)
	cluster.workers[i].kill('SIGTERM')
    }
    // Remove the IPC socket.
    fs.unlinkSync(ipcName)
    // Terminate the master.
    process.exit(1)
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
    // Get the number of processors/cores.
    const numCPUs = os.cpus().length
    // The master needs a router/dealer pair of sockets.
    const rt = zmq.socket('router')
    const dl = zmq.socket('dealer')
    // Bind those two sockets.
    rt.bindSync('tcp://127.0.0.1:'+process.argv[2])
    dl.bindSync(dlName)
    // Manage client messages.
    rt.on('message', function(...msg) {
	// All message segments have been received in the 'msg'
	// array.
	dl.send(msg)
    })
    // Manage worker messages.
    dl.on('message', function(...msg) {
	// All message segments have been received in the
	// 'msg' array.
	rt.send(msg)
    })
    // Manage master termination.
    process.on('SIGTERM', terminate )
    process.on('SIGINT', terminate )
    // Create as many workers as processors.
    for(let i=0; i<numCPUs; i++) cluster.fork()
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
	// parse incoming message
	let request = JSON.parse(data)
	console.log('Worker %d has received a request for file %s.', 
		    process.pid, request.path)
	// read file and reply with content
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
	})
    })
}


