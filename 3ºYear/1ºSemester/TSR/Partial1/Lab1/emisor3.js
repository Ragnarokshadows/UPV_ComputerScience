const Evento=require("./generadorEventos");

const evento1 = 'e1';
const evento2 = 'e2';
var incremento=0;

const emisor1 = Evento(evento1,'emisor1:   ', incremento);
const emisor2 = Evento(evento2,'emisor2:   ', incremento);

time = 1000;

function visualizar(entidad,evento,dato){
   console.log(entidad,evento+'··> ',dato);
}

emisor1.on(visualizar);
emisor2.on(visualizar);


emisiones();

function emisiones(){
   setTimeout(() => {
      emisor1.emit(1)
      emisor2.emit(1)
      time = Math.floor((Math.random() * 4) + 2)*1000;
      console.log(time/1000 + ' seconds');
      emisiones();  
  },time);
}