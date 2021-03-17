/***************************************************
ISGI::Bandeja con Tetera
Roberto Vivo', 2013 (v1.0)
Representa dos bandejas con teteras humeantes usando
encpsulamiento de trasformaciones
Dependencias:
+GLUT
***************************************************/
#define PROYECTO "ISGI::S3E04::Bandejas y teteras"
#include <iostream> // Biblioteca de entrada salida
#include <freeglut.h> // Biblioteca grafica
void tetera()
// Dibuja una tetera humeante
{
	glPushMatrix(); // Se salva el estado
	glutWireTeapot(1.0); // Dibujo de la tetera
	// Burbuja gorda
	glPushMatrix();
	glTranslatef(1.7, 1, 0);
	glScalef(0.1, 0.1, 0.1);
	glutWireSphere(1.0, 10, 10);
	glPopMatrix();
	// Burbuja pequeña
	glPushMatrix();
	glTranslatef(1.9, 1.4, 0);
	glScalef(0.06, 0.06, 0.06);
	glutWireSphere(1.0, 10, 10);
	glPopMatrix();
	glPopMatrix(); // Restaura la modelview
}
void bandejaytetera()
// Dibuja el conjunto bandeja-tetera
{
	glPushMatrix(); // Salva la modelview
	// Dibujo de la bandeja
	glPushMatrix();
	glTranslatef(0, -0.1, 0);
	glScalef(3, 0.2, 3);
	glutWireCube(1.0);
	glPopMatrix();
	// Dibujo de la tetera humeante
	glPushMatrix();
	glTranslatef(0, 0.8, 0);
	tetera();
	glPopMatrix();
	glPopMatrix(); // Restaura la modelview
}
void display()
// Funcion de atencion al dibujo
{
	glClear(GL_COLOR_BUFFER_BIT); // Borra la pantalla
	glMatrixMode(GL_MODELVIEW); // Selecciona la modelview
	glLoadIdentity(); // Carga la matriz identidad
	glRotatef(30, 1, 0, 0); // Inclinacion general
	// Dibujo del conjunto de la derecha
	glPushMatrix();
	glTranslatef(0.4, 0, -0.5);
	glRotatef(180, 0, 1, 0);
	glScalef(0.15, 0.15, 0.15);
	bandejaytetera();
	glPopMatrix();
	// Dibujo del conjunto de la izquierda
	glPushMatrix();
	glTranslatef(-0.4, -0.2, 0.4);
	glScalef(0.2, 0.2, 0.2);
	bandejaytetera();
	glPopMatrix();
	glFlush(); // Finaliza el dibujo
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
	glutInitWindowSize(400, 400); // Tamanyo inicial de la ventana
	glutCreateWindow(PROYECTO); // Creacion de la ventana con su titulo
	std::cout << PROYECTO << " running" << std::endl; // Mensaje por consola
	glutDisplayFunc(display); // Alta de la funcion de atencion a display
	glutReshapeFunc(reshape); // Alta de la funcion de atencion a reshape
	glutMainLoop(); // Puesta en marcha del programa
}