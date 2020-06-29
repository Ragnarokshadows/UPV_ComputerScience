const zmq = require('zeromq')
let cli=[], req=[], workers=[]
let sc = zmq.socket('router') // frontend
let sw = zmq.socket('router') // backend

const PORT_FRONTEND = Number(process.argv[2])
const PORT_BACKEND = Number(process.argv[3])

let reply_workers = 0;
let requests_clients = 0;

sc.bind('tcp://*:' + PORT_FRONTEND)
sw.bind('tcp://*:' + PORT_BACKEND)
sc.on('message',(c,sep,m)=> {
	requests_clients++;
	if (workers.length==0) { 
		cli.push(c); req.push(m)
	} else {
		sw.send([workers.shift(),'',c,'',m])
	}
})
sw.on('message',(w,sep,c,sep2,r)=> {
	reply_workers++;
    if (c=='') {workers.push(w); return}
	if (cli.length>0) { 
		sw.send([w,'',
			cli.shift(),'',req.shift()])
	} else {
		workers.push(w)
	}
	r = r + ' ' + reply_workers
	sc.send([c,'',r])
})

function statistics(){
	console.log("Total amount of requests: " + requests_clients);
	console.log("Total amount of replies: " + reply_workers);
}
setInterval(statistics, 5000)
