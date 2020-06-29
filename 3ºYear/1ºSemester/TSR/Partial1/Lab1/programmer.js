const net = require('net')

let proxyAddress = process.argv[2]
let proxyPort = process.argv[3]
let newAddress = process.argv[4]
let newPort = process.argv[5]

let msg = JSON.stringify({'remote_ip':newAddress, 'remote_port':newPort})

let socket = net.connect({port:proxyPort, address:proxAddress}, 
        () => {
    socket.write(msg)
    socket.end()
})