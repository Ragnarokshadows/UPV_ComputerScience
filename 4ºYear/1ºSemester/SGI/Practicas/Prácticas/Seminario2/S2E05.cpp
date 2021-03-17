/***************************************************
ISGI::Pentagono como display list
Roberto Vivo', 2013 (v1.0)
Dibujo de un pentagono con vertices y aristas diferenciados
como display list
Dependencias:
+GLUT
***************************************************/
#define PROYECTO "ISGI::S2E05::Pentagono Display List"
#include <iostream> // Biblioteca de entrada salida
#include <cmath> // Biblioteca matemática de C
#include <freeglut.h> // Biblioteca grafica
static GLuint pentagono; // Identificador del objeto
void init5()
// Funcion de inicializacion propia
{
	pentagono = glGenLists(1); // Obtiene el identificador de la lista
	glNewList(pentagono, GL_COMPILE); // Abre la lista
	// Dibuja el pentagono en la lista
	glBegin(GL_POLYGON);
	glVertex3f(0.5 * cos(0 * 2 * 3.1415926 / 5), 0.5 * sin(0 * 2 * 3.1415926 / 5), 0.0);
	glVertex3f(0.5 * cos(1 * 2 * 3.1415926 / 5), 0.5 * sin(1 * 2 * 3.1415926 / 5), 0.0);
	glVertex3f(0.5 * cos(2 * 2 * 3.1415926 / 5), 0.5 * sin(2 * 2 * 3.1415926 / 5), 0.0);
	glVertex3f(0.5 * cos(3 * 2 * 3.1415926 / 5), 0.5 * sin(3 * 2 * 3.1415926 / 5), 0.0);
	glVertex3f(0.5 * cos(4 * 2 * 3.1415926 / 5), 0.5 * sin(4 * 2 * 3.1415926 / 5), 0.0);
	glEnd();
	glEndList(); // Cierra la lista
}
void display5()
// Funcion de atencion al dibujo
{
	glClear(GL_COLOR_BUFFER_BIT); // Borra la pantalla
	// Dibujo como POLYGON relleno
	glColor3f(1.0, 0.0, 0.0);
	glCallList(pentagono);
	// Dibujo como POLYGON solo aristas
	glPolygonMode(GL_FRONT, GL_LINE);
	glLineWidth(2.0);
	glColor3f(0.0, 0.0, 1.0);
	glCallList(pentagono);
	// Dibujo como POLYGON solo vertices
	glPolygonMode(GL_FRONT, GL_POINT);
	glPointSize(10.0);
	glColor3f(0.0, 1.0, 0.0);
	glCallList(pentagono);
	glFlush(); // Finaliza el dibujo
}
void reshape5(GLint w, GLint h)
// Funcion de atencion al redimensionamiento
{
}
void S2E05(int argc, char** argv)
// Programa principal
{
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB); // Alta de buffers a usar
	glutInitWindowSize(400, 400); // Tamanyo inicial de la ventana
	glutCreateWindow(PROYECTO); // Creacion de la ventana con su titulo
	init5(); // Inicializacion propia. IMPORTANTE SITUAR AQUI
	std::cout << PROYECTO << " running" << std::endl; // Mensaje por consola
	glutDisplayFunc(display5); // Alta de la funcion de atencion a display
	glutReshapeFunc(reshape5); // Alta de la funcion de atencion a reshape
	glutMainLoop(); // Puesta en marcha del programa
}