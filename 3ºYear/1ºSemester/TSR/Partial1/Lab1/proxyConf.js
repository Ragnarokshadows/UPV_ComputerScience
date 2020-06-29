const net = require('net')

const LOCAL_PORT = 8000
const LOCAL_IP = '10.236.27.118'
const REMOTE_PORT = Number(process.argv[3])
const REMOTE_IP = process.argv[4]

const server = net.createServer((socket) => {
    const serviceSocket = new net.Socket()
    serviceSocket.connect(parseInt(REMOTE_PORT)), REMOTE_IP, () => {
        socket.on('data', (msg) => {
            serviceSocket.write(msg)
        })
    }
    serviceSocket.on('data', (msg) => {
        socket.write(msg)
    })
}).listen(LOCAL_PORT, LOCAL_IP)
console.log("TCP server accepting connection on port: " + LOCAL_PORT)