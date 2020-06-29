const zmq = require('zeromq')
let s = zmq.socket('rep')
s.bind('tcp://*:9998')
s.on('message', (nom) => {
  console.log('Serv1, '+nom)
  s.send('Hola, '+nom)
})
