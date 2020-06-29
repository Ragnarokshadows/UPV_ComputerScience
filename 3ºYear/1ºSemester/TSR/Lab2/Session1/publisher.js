const zmq = require('zeromq')

let pub = zmq.socket('pub')

const PORT = process.argv[2]
const NUM_MESSAGES = Number(process.argv[3])

let topics = []
let index = 1
let times = 1
let send = 1;

for(let i = 4; i < process.argv.length; i++){
    topics.push(process.argv[i])
}

pub.bind('tcp://*:' + PORT)

var myFun = setInterval(emite, 1000) // every second

function emite() {
    let t = topics[0]
    let m = send + ": " + t + " " + times
    pub.send(m)
    console.log(m)

    send++
    
    if(send == NUM_MESSAGES + 1){
        clearInterval(myFun)
    }

    index++

    if(index == topics.length + 1){
        times++
        index = 1
    }
    topics.shift(); topics.push(t) //rotatorio
}
