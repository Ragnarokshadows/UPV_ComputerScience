const zmq = require('zeromq')
let pub = zmq.socket('pub')
let msg = ['uno', 'dos', 'tres']
pub.bind('tcp://*:9999')
function emite() {
	let m=msg[0]
	pub.send(m)
	msg.shift(); msg.push(m) // rotatorio
}
setInterval(emite,1000) // every second
