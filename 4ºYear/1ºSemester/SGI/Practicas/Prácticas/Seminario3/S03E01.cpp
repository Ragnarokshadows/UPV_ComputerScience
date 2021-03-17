/***************************************************
ISGI::Traslacion Cubo
Roberto Vivo', 2013 (v1.0)
Traslada un cubo usando una matriz construida al efecto
Dependencias:
+GLUT
***************************************************/
#define PROYECTO "ISGI::S3E01::Traslacion Cubo"
#include <iostream> // Biblioteca de entrada salida
#include <freeglut.h> // Biblioteca grafica
void display1()
// Funcion de atencion al dibujo
{
	// Matriz de traslacion por columnas
	static const GLfloat traslacion[] = { 1,0,0,0, 0,1,0,0, 0,0,1,0, 0.5,0.6,0,1 };
	glClear(GL_COLOR_BUFFER_BIT); // Borra la pantalla
	glMatrixMode(GL_MODELVIEW); // Selecciona la modelview
	glLoadMatrixf(traslacion); // Carga la matriz sobreescribiendo
	glColor3f(1, 1, 1); // Dibuja en color blanco
	glutWireCube(1.0); // Dibuja el cubo
	glFlush(); // Finaliza el dibujo
}
void reshape1(GLint w, GLint h)
// Funcion de atencion al redimensionamiento
{
}
void s03e01(int argc, char** argv)
// Programa principal
{
	glutInit(&argc, argv); // Inicializacion de GLUT
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB); // Alta de buffers a usar
	glutInitWindowSize(400, 400); // Tamanyo inicial de la ventana
	glutCreateWindow(PROYECTO); // Creacion de la ventana con su titulo
	std::cout << PROYECTO << " running" << std::endl; // Mensaje por consola
	glutDisplayFunc(display1); // Alta de la funcion de atencion a display
	glutReshapeFunc(reshape1); // Alta de la funcion de atencion a reshape
	glutMainLoop(); // Puesta en marcha del programa
}