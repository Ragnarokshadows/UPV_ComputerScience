const zmq = require('zeromq')
let cli=[], req=[], workers=[]
let sc = zmq.socket('router') // frontend
let sw = zmq.socket('router') // backend
sc.bind('tcp://*:9998')
sw.bind('tcp://*:9999')
sc.on('message',(c,sep,m)=> {
	if (workers.length==0) { 
		cli.push(c); req.push(m)
	} else {
		sw.send([workers.shift(),'',c,'',m])
	}
})
sw.on('message',(w,sep,c,sep2,r)=> {
    if (c=='') {workers.push(w); return}
	if (cli.length>0) { 
		sw.send([w,'',
			cli.shift(),'',req.shift()])
	} else {
		workers.push(w)
	}
	sc.send([c,'',r])
})
