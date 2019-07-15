import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ClienteSMTP {

        static void error(String cadena) {
		System.out.println(cadena);
		System.exit(0);
	}

	public static void main(String args[]) {
	try{
		System.setProperty ("line.separator","\r\n");
		Socket s=new Socket("serveis-rdc.redes.upv.es", **completar**);
		System.out.println("Conectado al servidor SMTP de serveis-rdc");
		PrintWriter salida = new PrintWriter(**completar**);
		Scanner entrada=new Scanner(**completar**);
		String respuesta = entrada.nextLine();
		System.out.println(respuesta);
		if (!respuesta.startsWith("**completar**")) {error(respuesta);}

		salida.println("**completar**");
		salida.flush();
		respuesta = entrada.nextLine();
		System.out.println(respuesta);
		if (!respuesta.startsWith("**completar**")) {error(respuesta);}

		salida.println("**completar**");
		salida.flush();
		respuesta = entrada.nextLine();
		System.out.println(respuesta);
		if (!respuesta.startsWith("**completar**")) {error(respuesta);}

		salida.println("**completar**");
		salida.flush();
		respuesta = entrada.nextLine();
		System.out.println(respuesta);
		if (!respuesta.startsWith("**completar**")) {error(respuesta);}

		salida.println("**completar**");
		salida.flush();
		respuesta = entrada.nextLine();
		System.out.println(respuesta);
		if (!respuesta.startsWith("**completar**")) {error(respuesta);}

		// **completar** 
		// Aqui van varios println con todo el correo 
		// electronico incluidas las cabeceras

		salida.flush();
		respuesta = entrada.nextLine();
		System.out.println(respuesta);
		if (!respuesta.startsWith("**completar**")) {error(respuesta);}

		salida.println("QUIT");
		salida.flush();
		respuesta = entrada.nextLine();
		System.out.println(respuesta);
		if (!respuesta.startsWith("**completar**")) {error(respuesta);}

		s.close();
		System.out.println("Desconectado");

	} catch (UnknownHostException e) {
		System.out.println("Host desconocido");
		System.out.println(e);
	} catch (IOException e) {
		System.out.println("No se puede conectar");
		System.out.println(e);
	}
	}
}
