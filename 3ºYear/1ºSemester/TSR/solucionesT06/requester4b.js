// requester4b.js
// This is another requester program that can be used in Activity 4.
// Instead of relying on a delay argument to be received from the
// command line, this program sends the next request as soon as
// possible; i.e., once the current answer has been received.

const path=require('path')

if (process.argv.length < 3) {
    console.log('usage: node %s port',
	       path.basename(process.argv[1]))
    process.exit(0)
}

const filenames = ['Act2.js','Act3.js','Act3b.js','Act4.js','doesNotExist']
const
zmq = require('zeromq'),
req = zmq.socket('req'),
endpoint = 'tcp://localhost:'+process.argv[2]

req.connect(endpoint)

let counter=0
// Send request for content.
function request() {
    counter++
    let requestedFile = filenames[counter%filenames.length]
 //   console.log('Sending request for ' + requestedFile)
    req.send( JSON.stringify({path:requestedFile}) )
}

// Handle replies.
req.on('message', function(data) {
    var reply = JSON.parse(data)
    , nf = ' NOT FOUND'
    , err = reply.error ? nf : ''
    // Print a reporting message.
    console.log('Received reply:',
		'file:', reply.path + err,
		'from worker:', reply.pid,
		'at:', reply.timestamp)
    // Send the next request.
    request()
})

// Send the first request.
request()
