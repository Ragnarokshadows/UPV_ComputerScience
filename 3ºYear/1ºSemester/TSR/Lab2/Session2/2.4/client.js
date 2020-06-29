const zmq = require('zeromq')
let req = zmq.socket('req');

const URL_FRONTEND = process.argv[2]
const NICK_CLIENT = process.argv[3]
const TXT_REQUEST = process.argv[4]

let replies = 0;

req.identity= NICK_CLIENT
req.connect(URL_FRONTEND)
req.on('message', (msg)=> {
	replies++;
	console.log('Resp: '+msg)
	if(replies == 10){
		process.exit(0);
	}
})
for(let i = 0; i <= 10; i++){
    req.send(TXT_REQUEST)
}

