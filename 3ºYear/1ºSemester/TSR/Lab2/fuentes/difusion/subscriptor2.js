const zmq = require('zeromq')
let sub = zmq.socket('sub')
sub.connect('tcp://127.0.0.1:9999')
sub.subscribe('dos')
sub.on('message', (m) => 
	{console.log('2',m+'')})
