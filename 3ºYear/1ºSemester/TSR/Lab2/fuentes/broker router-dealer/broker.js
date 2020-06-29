const zmq = require('zeromq')
let sc = zmq.socket('router') // frontend
let sw = zmq.socket('dealer') // backed
sc.bind('tcp://*:9998')
sw.bind('tcp://*:9999')
sc.on('message',(...m)=> {sw.send(m)})
sw.on('message',(...m)=> {sc.send(m)})
