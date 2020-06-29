const zmq = require('zeromq')
const ansInterval = 2000     // answer timeout. If exceeded, worker failed

let who=[], req=[] 	     // pending request (client,message)
let workers=[], failed={}    // available & failed workers
let tout={} 		     // timeouts for attended requests

let sc = zmq.socket('router') // frontend
let sw = zmq.socket('router') // backend
sc.bind("tcp://*:9998", (err)=>{
   console.log(err?"sc binding error":"accepting client requests")
})
sw.bind("tcp://*:9999", (err)=>{
   console.log(err?"sw binding error":"accepting worker requests")
})

function dispatch(c,m) {
  if (workers.length)               // if available workers,
      sendToW(workers.shift(),c,m)  //    send request to first worker
  else {                            // no available workers
      who.push(c); req.push(m)      //    set as pending
  }  	
}
function resend(w,c,m) {
	return function() {         // ansInterval finished and no response
		failed[w]=true      // Worker w has failed
		dispatch(c,m)
	}
}
function sendToW(w,c,m) {
	sw.send([w,'',c,'',m])
	tout[w]=setTimeout(resend(w,c,m), ansInterval) }

sc.on('message', (c,sep,m) => dispatch(c,m))

sw.on('message', (w,sep,c,sep2,r) => {
     if (failed[w]) return 	    // ignore msg from failed worker
     if (tout[w]) { 		    // ans received in-time
     	clearTimeout(tout[w])  	    //    cancel timeout
     	delete tout[w]
     }
     if (c) sc.send([c,'',r]) 	    // If it was a response, send resp to client
     if (who.length) 		    // If there are pending requests,
     	sendToW(w,who.shift(),req.shift()) //    process first pending req
     else
     	workers.push(w) 	    // add as available worker
})

