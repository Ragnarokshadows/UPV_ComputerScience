/***************************************************
ISGI::Octaedro como Vertex Array
Roberto Vivo', 2013 (v1.0)
Dibujo de un octaedro con vertices coloreados usando
arrays de vertices, colores e indices
Dependencias:
+GLUT
***************************************************/
#define PROYECTO "ISGI::S2E06::Octaedro Vertex Array"
#include <iostream> // Biblioteca de entrada salida
#include <cmath> // Biblioteca matemática de C
#include <glut.h> // Biblioteca grafica
// Vector de coordenadas 6x3 de los vertices del octaedro
static const GLfloat vertices[18] = { 1,0,0, 0,1,0, 0,0,1, -1,0,0, 0,-1,0, 0,0,-1 };
// Vector de colores 6x3 de los vertices del octaedro
static const GLfloat colores[18] = { 1,0,0, 0,1,0, 0,0,1, 1,1,0, 0,1,1, 1,0,1 };
// Vector de indices de los triángulos (antihorario) que forman el octaedro 8x3
static const GLuint indices[24] = { 1,2,0, 1,0,5, 1,5,3, 1,3,2, 4,2,0, 4,0,5, 4,5,3, 4,3,2 };
void init()
// Funcion de inicializacion propia
{
	glEnableClientState(GL_VERTEX_ARRAY); // Activa el uso del array de vertices
	glVertexPointer(3, GL_FLOAT, 0, vertices); // Carga el array de vertices
}
void display()
// Funcion de atencion al dibujo
{
	glClear(GL_COLOR_BUFFER_BIT); // Borra la pantalla
	glEnableClientState(GL_COLOR_ARRAY); // Activa el uso del array de colores
	glColorPointer(3, GL_FLOAT, 0, colores); // Carga el array de colores
	glPolygonMode(GL_FRONT_AND_BACK, GL_FILL); // Las caras del poligono rellenas
	// Dibujo del octaedro como 8 triangulos coloreados
	glDrawElements(GL_TRIANGLES, 24, GL_UNSIGNED_INT, indices);
	glDisableClientState(GL_COLOR_ARRAY); // Desactiva el array de colores
	glColor3f(1, 1, 1); // Fija el color a blaco
	glPolygonMode(GL_FRONT_AND_BACK, GL_LINE); // Las caras del poligono en alambrico
	// Dibujo del octaedro como 8 triangulos en alambrico
	glDrawElements(GL_TRIANGLES, 24, GL_UNSIGNED_INT, indices);
	glFlush(); // Finaliza el dibujo
}
void reshape(GLint w, GLint h)
// Funcion de atencion al redimensionamiento
{
}
void main(int argc, char** argv)
// Programa principal
{
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB); // Alta de buffers a usar
	glutInitWindowSize(400, 400); // Tamanyo inicial de la ventana
	glutCreateWindow(PROYECTO); // Creacion de la ventana con su titulo
	init(); // Inicializacion propia. IMPORTANTE SITUAR AQUI
	std::cout << PROYECTO << " running" << std::endl; // Mensaje por consola
	glutDisplayFunc(display); // Alta de la funcion de atencion a display
	glutReshapeFunc(reshape); // Alta de la funcion de atencion a reshape
	glutMainLoop(); // Puesta en marcha del programa
}