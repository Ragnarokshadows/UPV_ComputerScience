const zmq = require('zeromq')
let s = zmq.socket('pull')
s.bind('tcp://*:9999')
s.on('message', (tipo,txt) => {
	console.log('tipo '+tipo +' texto '+txt)
})
