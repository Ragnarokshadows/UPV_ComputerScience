const zmq = require('zeromq')
let s = zmq.socket('push')
s.connect('tcp://127.0.0.1:9999')
s.send(['ejemplo','multisegmento'])
