const zmq = require('zeromq')
let s = zmq.socket('req')
s.connect('tcp://127.0.0.1:9998')
s.send('uno')
s.on('message', (msg) => {
  console.log('Recibido: '+msg)
  s.close()
})
