const zmq = require('zeromq')
let req = zmq.socket('req');

const URL_FRONTEND = process.argv[2]
const NICK_CLIENT = process.argv[3]
const TXT_REQUEST = process.argv[4]

req.identity= NICK_CLIENT
req.connect(URL_FRONTEND)
req.on('message', (msg)=> {
	console.log('resp: '+msg)
	process.exit(0);
})
req.send(TXT_REQUEST)
