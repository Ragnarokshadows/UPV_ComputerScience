/***************************************************
ISGI::Pentagono
Roberto Vivo', 2013 (v1.0)
Dibujo de un pentagono de diferentes maneras
Dependencias:
+GLUT
***************************************************/
#define PROYECTO "ISGI::S2E02::Pentagono"
#include <iostream> // Biblioteca de entrada salida
#include <cmath> // Biblioteca matemática de C
#include <freeglut.h> // Biblioteca grafica
void display2()
// Funcion de atencion al dibujo
{
	glClear(GL_COLOR_BUFFER_BIT); // Borra la pantalla
	// Dibujo como POLYGON
	glBegin(GL_POLYGON);
	glVertex3f(0.5 * cos(0 * 2 * 3.1415926 / 5), 0.5 * sin(0 * 2 * 3.1415926 / 5), 0.0);
	glVertex3f(0.5 * cos(1 * 2 * 3.1415926 / 5), 0.5 * sin(1 * 2 * 3.1415926 / 5), 0.0);
	glVertex3f(0.5 * cos(2 * 2 * 3.1415926 / 5), 0.5 * sin(2 * 2 * 3.1415926 / 5), 0.0);
	glVertex3f(0.5 * cos(3 * 2 * 3.1415926 / 5), 0.5 * sin(3 * 2 * 3.1415926 / 5), 0.0);
	glVertex3f(0.5 * cos(4 * 2 * 3.1415926 / 5), 0.5 * sin(4 * 2 * 3.1415926 / 5), 0.0);
	glEnd();
	// Dibujo como TRIANGLE_STRIP
	/*
	glBegin(GL_TRIANGLE_STRIP);
	glVertex3f(0.5*cos(0*2*3.1415926/5), 0.5*sin(0*2*3.1415926/5), 0.0);
	glVertex3f(0.5*cos(1*2*3.1415926/5), 0.5*sin(1*2*3.1415926/5), 0.0);
	glVertex3f(0.5*cos(4*2*3.1415926/5), 0.5*sin(4*2*3.1415926/5), 0.0);
	glVertex3f(0.5*cos(2*2*3.1415926/5), 0.5*sin(2*2*3.1415926/5), 0.0);
	glVertex3f(0.5*cos(3*2*3.1415926/5), 0.5*sin(3*2*3.1415926/5), 0.0);
	glEnd();
	*/
	// Dibujo como TRIANGLE_FAN
	/*
	glBegin(GL_TRIANGLE_FAN);
	glVertex3f(0.5*cos(0*2*3.1415926/5), 0.5*sin(0*2*3.1415926/5), 0.0);
	glVertex3f(0.5*cos(1*2*3.1415926/5), 0.5*sin(1*2*3.1415926/5), 0.0);
	glVertex3f(0.5*cos(2*2*3.1415926/5), 0.5*sin(2*2*3.1415926/5), 0.0);
	glVertex3f(0.5*cos(3*2*3.1415926/5), 0.5*sin(3*2*3.1415926/5), 0.0);
	glVertex3f(0.5*cos(4*2*3.1415926/5), 0.5*sin(4*2*3.1415926/5), 0.0);
	glEnd();
	*/
	glFlush(); // Finaliza el dibujo
}
void reshape2(GLint w, GLint h)
// Funcion de atencion al redimensionamiento
{
}
void S2E02(int argc, char** argv)
// Programa principal
{
	glutInit(&argc, argv); // Inicializacion de GLUT
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB); // Alta de buffers a usar
	glutInitWindowSize(400, 400); // Tamanyo inicial de la ventana
	glutCreateWindow(PROYECTO); // Creacion de la ventana con su titulo
	std::cout << PROYECTO << " running" << std::endl; // Mensaje por consola
	glutDisplayFunc(display2); // Alta de la funcion de atencion a display
	glutReshapeFunc(reshape2); // Alta de la funcion de atencion a reshape
	glutMainLoop(); // Puesta en marcha del programa
}