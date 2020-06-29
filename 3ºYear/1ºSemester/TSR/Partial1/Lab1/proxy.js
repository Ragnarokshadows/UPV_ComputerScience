const net = require('net');

const LOCAL_PORT  = 8000;
const LOCAL_IP  = '10.236.27.118';
const REMOTE_PORT = 80;
const REMOTE_IP = '158.42.4.23'; // www.upv.es

const server = net.createServer(function (socket) {
      const serviceSocket = new net.Socket();
      serviceSocket.connect(parseInt(REMOTE_PORT),   
         REMOTE_IP, function () {
          socket.on('data', function (msg) {
               serviceSocket.write(msg);
          });
          serviceSocket.on('data', function (data) {
             socket.write(data);
          });
      });
}).listen(LOCAL_PORT, LOCAL_IP);
console.log("TCP server accepting connection on port: " + LOCAL_PORT);