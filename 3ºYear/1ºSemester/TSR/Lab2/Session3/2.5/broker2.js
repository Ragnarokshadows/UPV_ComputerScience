const zmq = require('zeromq')

let workers=[]

let sb = zmq.socket('dealer') // broker1
let sw = zmq.socket('router') // backend

sb.connect('tcp://127.0.0.1:9997')
sw.bind('tcp://*:9999')

sb.on('message',(c,sep,m)=> {

	if(workers.length == 0){ //If there are no workers available, we drop the packet
		console.log("NO WORKER AVAILABLE. PACKET DROPEED: ["+c+","+sep+","+m+"]"); 
		return;
	}
	
	let worker = workers.shift() //We take a worker
	sw.send([worker, '', c, '', m]) //We send the message to the worker
	console.log("BROKER 2 ==> FROM CLI: " + c + " / TO WOR: " + worker + " / CONTENT: " + m + "(freeWorkers=" + workers.length + ")")
})

sw.on('message',(w,sep,c,sep2,r)=> {

	workers.push(w) //We add the worker to the list of available workers
	sb.send(['',c,'',r]) //We send the message to broker 1
	console.log("BROKER 2 ==> FROM WOR: " + w + " / TO: CLI: " + c + " / CONTENT: " + r)
})