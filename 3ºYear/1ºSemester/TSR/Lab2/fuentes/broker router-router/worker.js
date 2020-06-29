const zmq = require('zeromq')
let req = zmq.socket('req')
req.identity='Worker1'+process.pid
req.connect('tcp://localhost:9999')
req.on('message', (c,sep,msg)=> {
	setTimeout(()=> {
		req.send([c,'','resp'])
	}, 1000)
})
req.send(['','',''])
