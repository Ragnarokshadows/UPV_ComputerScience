const zmq = require('zeromq')
let s = zmq.socket('rep')
s.bind('tcp://*:9999')
s.on('message', (nom) => {
  console.log('Nombre: '+nom)
  s.send('Hola, '+nom)
})
