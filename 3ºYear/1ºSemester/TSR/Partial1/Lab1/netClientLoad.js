const net = require('net');

let serverIP = process.argv[2]
let localIP = process.argv[3]
    const client = net.connect({
        port: 8000,
        host: serverIP
    },
    function() { //connect listener
    console.log('client connected');
    client.write(localIP);
});
client.on('data',
    function(data) {
        console.log(data.toString());
        client.end();
});
client.on('end',
    function() {
        console.log('client disconnected');
        process.exit(1);
});
