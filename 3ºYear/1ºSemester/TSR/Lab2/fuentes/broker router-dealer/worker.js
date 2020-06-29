const zmq = require('zeromq')
let rep = zmq.socket('rep');
rep.connect('tcp://localhost:9999')
rep.on('message', (msg)=> {
	setTimeout(()=> {
		rep.send('resp')
	}, 1000)
})
