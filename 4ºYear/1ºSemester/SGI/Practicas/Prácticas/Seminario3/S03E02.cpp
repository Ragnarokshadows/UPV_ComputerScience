/***************************************************
ISGI::Giro y Traslacion
Roberto Vivo', 2013 (v1.0)
Gira y Traslada un cubo usando la composicion de OpenGL
Dependencias:
+GLUT
***************************************************/
#define PROYECTO "ISGI::S3E02::Giro+Traslacion Cubo"
#include <iostream> // Biblioteca de entrada salida
#include <freeglut.h> // Biblioteca grafica
void display2()
// Funcion de atencion al dibujo
{
	glClear(GL_COLOR_BUFFER_BIT); // Borra la pantalla
	glMatrixMode(GL_MODELVIEW); // Selecciona la modelview
	glLoadIdentity(); // Carga la matriz identidad
	glTranslatef(0.5, 0.0, 0.0); // modelview=I*T
	glRotatef(45, 0, 0, 1); // modelview=I*T*R
	glColor3f(1, 1, 1); // Dibuja en color blanco
	glutWireCube(1.0); // Dibuja el cubo
	glFlush(); // Finaliza el dibujo
}
void reshape2(GLint w, GLint h)
// Funcion de atencion al redimensionamiento
{
}
void s03e02(int argc, char** argv)
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