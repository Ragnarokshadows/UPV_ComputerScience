// req2.js
// It is a client for the servers to be written in Activity 2.
// In this variant a reply summary is written to standard error.
// Besides, the received file is shown in standard output. Thus,
// stdout may be redirected to a file, getting in this way a copy
// of the downloaded file.
if (process.argv.length != 4) {
    console.log('usage: node zmq-requester port filename')
    process.exit(0)
}

const
zmq = require('zeromq'),
req = zmq.socket('req'),
endpoint = 'tcp://localhost:'+process.argv[2],
filename = process.argv[3];

req.connect(endpoint);

// send request for content
console.log('Sending request for ' + filename)
req.send( JSON.stringify({path:filename}) )

// handle replies from responder
req.on('message', function(data) {
    let reply = JSON.parse(data)
    , nf = ' NOT FOUND'
    , err = ( reply.data == nf ) ? nf : ''
    console.error('Received reply:',
		'file:', reply.path + err,
		'from worker:', reply.pid,
		'at:', reply.timestamp)
    console.log(reply.data+'')
    req.close()
    process.exit(0)
})
