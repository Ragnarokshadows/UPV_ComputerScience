const zmq = require('zeromq')
let s = zmq.socket('pull')
s.bind('tcp://*:9999')
s.on('message', (w,n) => {
	console.log('worker '+w +' resp '+n)
})
