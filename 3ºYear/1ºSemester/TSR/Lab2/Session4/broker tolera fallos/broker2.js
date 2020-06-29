// This is an alternative version of ftbroker.js, but this
// file (broker2.js) doesn't discard answers from "failed" workers. They
// should be discarded.
const zmq = require('zeromq')
const ansInterval = 2000
let who=[], req=[], workers=[], tout={}
let sc = zmq.socket('router') // frontend
let sw = zmq.socket('router') // backend
sc.bindSync('tcp://*:9998')
sw.bindSync('tcp://*:9999')

function resend(c,m) {
	return function() {
		if (workers.length==0) {
			who.push(c); req.push(m)
		} else {
			sendToW(workers.shift(),c,m)
		}
	}
}
function sendToW(w,c,m) {
	sw.send([w,'',c,'',m])
	tout[w]=setTimeout(resend(c,m), ansInterval)
}

sc.on('message',(c,sep,m)=> {
	if (workers.length==0) { 
		who.push(c); req.push(m)
	} else {
		sendToW(workers.shift(),c,m)
	}
})
sw.on('message',(w,sep,c,sep2,r)=> {
	if (c!='') {
		clearTimeout(tout[w]); delete tout[w]
	}
	if (who.length==0) {
		workers.push(w)
	} else {
		sendToW(w,who.shift(),req.shift())
	}
	sc.send([c,'',r])
})
