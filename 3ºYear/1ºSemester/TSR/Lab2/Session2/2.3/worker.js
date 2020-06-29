const zmq = require('zeromq')
let req = zmq.socket('req')

const URL_BACKEND = process.argv[2]
const NICK_WORKER = process.argv[3]
const TXT_REPLY = process.argv[4]

req.identity= NICK_WORKER
req.connect(URL_BACKEND)
req.on('message', (c,sep,msg)=> {
	console.log('received request: ', msg.toString());
	setTimeout(()=> {
		req.send([c,'',TXT_REPLY])
	}, 1000)
})
req.send(['','',''])
