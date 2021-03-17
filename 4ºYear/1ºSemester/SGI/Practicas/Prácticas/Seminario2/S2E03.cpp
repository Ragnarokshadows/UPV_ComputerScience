/***************************************************
ISGI::Pentagono coloreado
Roberto Vivo', 2013 (v1.0)
Dibujo de un pentagono con vertices coloreados
Dependencias:
+GLUT
***************************************************/
#define PROYECTO "ISGI::S2E03::Pentagono Colores"
#include <iostream> // Biblioteca de entrada salida
#include <cmath> // Biblioteca matemática de C
#include <freeglut.h> // Biblioteca grafica
void display3()
// Funcion de atencion al dibujo
{
	glClear(GL_COLOR_BUFFER_BIT); // Borra la pantalla
	// Dibujo como POLYGON con los vertices coloreados
	glBegin(GL_POLYGON);
	glColor3f(1.0, 0.0, 0.0);
	glVertex3f(0.5 * cos(0 * 2 * 3.1415926 / 5), 0.5 * sin(0 * 2 * 3.1415926 / 5), 0.0);
	glColor3f(0.0, 1.0, 0.0);
	glVertex3f(0.5 * cos(1 * 2 * 3.1415926 / 5), 0.5 * sin(1 * 2 * 3.1415926 / 5), 0.0);
	glColor3f(0.0, 0.0, 1.0);
	glVertex3f(0.5 * cos(2 * 2 * 3.1415926 / 5), 0.5 * sin(2 * 2 * 3.1415926 / 5), 0.0);
	glColor3f(1.0, 1.0, 0.0);
	glVertex3f(0.5 * cos(3 * 2 * 3.1415926 / 5), 0.5 * sin(3 * 2 * 3.1415926 / 5), 0.0);
	glColor3f(0.0, 1.0, 1.0);
	glVertex3f(0.5 * cos(4 * 2 * 3.1415926 / 5), 0.5 * sin(4 * 2 * 3.1415926 / 5), 0.0);
	glEnd();
	glFlush(); // Finaliza el dibujo
}
void reshape3(GLint w, GLint h)
// Funcion de atencion al redimensionamiento
{
}
void S2E03(int argc, char** argv)
// Programa principal
{
	glutInit(&argc, argv); // Inicializacion de GLUT
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB); // Alta de buffers a usar
	glutInitWindowSize(400, 400); // Tamanyo inicial de la ventana
	glutCreateWindow(PROYECTO); // Creacion de la ventana con su titulo
	std::cout << PROYECTO << " running" << std::endl; // Mensaje por consola
	glutDisplayFunc(display3); // Alta de la funcion de atencion a display
	glutReshapeFunc(reshape3); // Alta de la funcion de atencion a reshape
	glutMainLoop(); // Puesta en marcha del programa
}