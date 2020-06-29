const net = require('net')

const LOCAL_PORT = 8000
const LOCAL_IP = '10.236.27.118'
const REMOTE_PORT = Number(process.argv[3])
const REMOTE_IP = process.argv[4]

const CONFIG_PORT = 8001

const server = net.createServer((socket) => {
    socket.on('data', (msg) => {
        const serviceSocket = new net.Socket()
        serviceSocket.connect(parseInt(REMOTE_PORT)), REMOTE_IP, () => {
            serviceSocket.write(msg)
        }
        serviceSocket.on('data', (msg) => {
            socket.write(msg)
        })
    })
}).listen(LOCAL_PORT, LOCAL_IP)
console.log("TCP server accepting connection on port: " + LOCAL_PORT)

const programServer = net.createServer((c) => {
    socket.on('data', (msg) => {
        let parameters = JSON.parse(msg)
        REMOTE_IP = parameters.port
        REMOTE_PORT = parameters.ip
        console.log('The configuration of the proxy changed')
    })
}).listen(CONFIG_PORT, LOCAL_IP)
console.log("TCP server accepting configuration on port: " + CONFIG_PORT)