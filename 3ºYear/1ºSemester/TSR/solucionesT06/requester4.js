// requester4.js
// This is the requester program to be used in Activity 4.

const path=require('path')

if (process.argv.length < 4) {
    console.log('usage: node %s port delay',
	       path.basename(process.argv[1]))
    process.exit(0)
}

const filenames = ['Act2.js','Act3.js','Act3b.js','Act4.js','doesNotExist']
const
zmq = require('zeromq'),
req = zmq.socket('req'),
endpoint = 'tcp://localhost:'+process.argv[2],
delay = process.argv[3] || 150;

req.connect(endpoint);

let counter=0;
// send request for content
function request() {
    counter++;
    var requestedFile = filenames[counter%filenames.length]
 //   console.log('Sending request for ' + requestedFile)
    req.send( JSON.stringify({path:requestedFile}) )
}

// Request a file periodically.
setInterval(request,delay);

// Handle replies.
req.on('message', function(data) {
    let reply = JSON.parse(data)
    , nf = ' NOT FOUND'
    , err = reply.error ? nf : ''
    console.log('Received reply:',
		'file:', reply.path + err,
		'from worker:', reply.pid,
		'at:', reply.timestamp)
})
