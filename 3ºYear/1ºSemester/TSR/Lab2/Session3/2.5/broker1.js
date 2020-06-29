const zmq = require('zeromq')

let cli=[], req=[]

let sc = zmq.socket('router') // frontend
let sb = zmq.socket('dealer') // broker2

let freeWorkers = 0;

sc.bind('tcp://*:9998')
sb.bind('tcp://*:9997')

sc.on('message',(c,sep,m)=> {

	cli.push(c) //We queue the client
	req.push(m) //We queue the message
	console.log("BROKER 1 => QUEUED CLI: " + c + " / MSG: " + m)

	while(freeWorkers > 0 && cli.length > 0){ //While there are free workers and client messages
		freeWorkers--; //We reduce the number of free workers
		let client = cli.shift(); let request = req.shift(); //We take the client and its message
		sb.send([client,'',request]) //We send it to the broker 2
		console.log("\t FWD TO B2==> CLI: " + client + " / MSG: " + request)
	}
})

sb.on('message', (sep, c,sep2,r)=> {

	freeWorkers++; //We increase the number of free workers since we got a message

	console.log("BROKER 1 => FWD TO C: " + c + " / REP: " + r + "(numFree = " + freeWorkers + ")")

	while(freeWorkers > 0 && cli.length > 0){ //While there are free workers and there are client messages
		freeWorkers--; //We reduce the number of free workers
		let client = cli.shift(); let request = req.shift(); //We take the client and its message
		sb.send([client,"",request]) //We send it to the broker 2
		console.log("\t FWD TO B2==> CLI: " + client + " / MSG: " + request)
	}

	if(c!=''){ //If it is a response, we send it
		sc.send([c,'',r])
	}
})

/*
node worker.js tcp://localhost:9999 W1 Worker1REPLY &
node worker.js tcp://localhost:9999 W2 Worker2REPLY

node client.js tcp://localhost:9998 C1 Client1Req &
node client.js tcp://localhost:9998 C2 Client2Req &
node client.js tcp://localhost:9998 C3 Client3Req &
node client.js tcp://localhost:9998 C4 Client4Req &
node client.js tcp://localhost:9998 C5 Client5Req &
node client.js tcp://localhost:9998 C6 Client6Req &
node client.js tcp://localhost:9998 C7 Client7Req &
node client.js tcp://localhost:9998 C8 Client8Req
*/
