const EventEmitter = require('events').EventEmitter;

const emisor=new EventEmitter();

function Evento(evento,entidadEmisora,valor){
   return {
	   emit:function(incr){
                               valor += incr;
                               emisor.emit(evento,entidadEmisora,evento,valor);
                 },
                on:function(listener){
		   emisor.on(evento,listener);
	   }
   }
}
module.exports=Evento;