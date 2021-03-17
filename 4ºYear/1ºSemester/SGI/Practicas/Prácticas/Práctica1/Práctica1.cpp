/***************************************************
ISGI::Hola Mundo
Roberto Vivo', 2012 (v1.0)
Esqueleto basico de un programa en OpenGL
Dependencias:
+GLUT
***************************************************/
#define PROYECTO "Primer programa en OpenGL"
#include <iostream> // Biblioteca de entrada salida
#include <freeglut.h> // Biblioteca grafica
void display()
// Funcion de atencion al dibujo
{
	glClearColor(0.0, 0.0, 0.3, 1.0);
	glClear(GL_COLOR_BUFFER_BIT);
	glFlush();
}
void reshape(GLint w, GLint h)
// Funcion de atencion al redimensionamiento
{
}
void main(int argc, char** argv)
// Programa principal
{
	glutInit(&argc, argv); // Inicializacion de GLUT
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB); // Alta de buffers a usar
	glutInitWindowSize(500, 400); // Tamanyo inicial de la ventana
	glutInitWindowPosition(50, 600);
	glutCreateWindow(PROYECTO); // Creacion de la ventana con su titulo
	std::cout << PROYECTO << " running" << std::endl; // Mensaje por consola
	glutDisplayFunc(display); // Alta de la funcion de atencion a display
	glutReshapeFunc(reshape); // Alta de la funcion de atencion a reshape
	glutMainLoop(); // Puesta en marcha del programa
}