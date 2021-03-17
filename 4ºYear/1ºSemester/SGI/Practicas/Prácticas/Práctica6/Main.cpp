#define PROYECTO "Interfaz de conducción"
#include <iostream> // Biblioteca de entrada salida
#include <cmath> // Biblioteca matemática de C
#include <freeglut.h> // Biblioteca grafica
#include <../Utilidades.h>
#include <sstream>
#define PI 3.1415926
#define tasaFPS 60

static enum { SOLIDO, ALAMBRICO } modo;
static float speed = 0, girox = 0, giroz = -1;
static float distx = 0, disty = 1, distz = 0;
static float avance = 0.0;
static float angulo = 180;
static char buffer[50];



void init()
// Funcion de inicializacion propia
{

	//Tener en cuenta la profundidad logica
	glEnable(GL_DEPTH_TEST);
}

void display()
// Funcion de atencion al dibujo
{
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glClearColor(1, 1, 1, 1);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	//gluLookAt(0, 150, 0, 0, 0, 0, 0, 0, -1);
	gluLookAt(distx, disty, distz, distx + girox, 1, distz + giroz, 0, 1, 0);

	if (modo == ALAMBRICO) {
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	}
	else {
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}



	glColor3f(0, 0, 0);
	GLfloat v0[3] = { 2,0,30 }, v1[3] = { -2,0,30 }, v2[3] = { -2,0,-32 }, v3[3] = { 2,0,-30 };
	quad(v0, v1, v2, v3, 10, 5);
	GLfloat v4[3] = { 4,0,-32 }, v5[3] = { 2,0,-36 }, v6[3] = { -2,0,-32 }, v7[3] = { 2,0,-30 };
	quad(v4, v5, v6, v7, 10, 5);
	GLfloat v8[3] = { 4,0,-32 }, v9[3] = { 26,0,-32 }, v10[3] = { 28,0,-36 }, v11[3] = { 2,0,-36 };
	quad(v8, v9, v10, v11, 10, 5);
	GLfloat v12[3] = { 26,0,-32 }, v13[3] = { 28,0,-36 }, v14[3] = { 32,0,-32 }, v15[3] = { 28,0,-30 };
	quad(v12, v13, v14, v15, 10, 5);
	GLfloat v16[3] = { 32,0,-32 }, v17[3] = { 28,0,-30 }, v18[3] = { 28,0,-10 }, v19[3] = { 32,0,-12 };
	quad(v16, v17, v18, v19, 10, 5);
	GLfloat v20[3] = { 32,0,-12 }, v21[3] = { 28,0,-10 }, v22[3] = { 38,0,0 }, v23[3] = { 42,0,-2 };
	quad(v20, v21, v22, v23, 10, 5);
	GLfloat v24[3] = { 38,0,0 }, v25[3] = { 42,0,-2 }, v26[3] = { 42,0,32 }, v27[3] = { 38,0,30 };
	quad(v24, v25, v26, v27, 10, 5);
	GLfloat v28[3] = { 42,0,32 }, v29[3] = { 38,0,30 }, v30[3] = { 36,0,32 }, v31[3] = { 38,0,36 };
	quad(v28, v29, v30, v31, 10, 5);
	GLfloat v32[3] = { 36,0,32 }, v33[3] = { 38,0,36 }, v34[3] = { 2,0,36 }, v35[3] = { 4,0,32 };
	quad(v32, v33, v34, v35, 10, 5);
	GLfloat v36[3] = { 2,0,36 }, v37[3] = { 4,0,32 }, v38[3] = { 2,0,30 }, v39[3] = { -2,0,30 };
	quad(v36, v37, v38, v39, 10, 5);



	glutSwapBuffers();
}

void reshape(GLint w, GLint h)
// Funcion de atencion al redimensionamiento
{
	glViewport(0, 0, w, h);
	float razon = (float)w / h;
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluPerspective(45, razon, 1, 200);
}

void update()
{

	static int antes = glutGet(GLUT_ELAPSED_TIME);
	int ahora = glutGet(GLUT_ELAPSED_TIME);
	float tiempo_transcurrido = (ahora - antes) / 1000.0f;
	avance = tiempo_transcurrido * speed;
	antes = ahora;

	distx += avance * girox;
	distz += avance * giroz;



	glutPostRedisplay();
}

void onTimer(int tiempo)
{
	// Sirve para re-arrancar el reloj de cuenta atras
	glutTimerFunc(tiempo, onTimer, tiempo);
	update();
}

void onArrow(int tecla, int x, int y)
{
	switch (tecla) {
	case GLUT_KEY_UP:
		//Flecha arriba: Aumenta el módulo de la velocidad en 0.1 m/sg
		speed += 0.1;
		break;
	case GLUT_KEY_DOWN:
		//Disminuye el módulo de la velocidad en 0.1 m/sg
		if (speed > 0.1) speed -= 0.1;
		break;
	case GLUT_KEY_LEFT:
		//Flecha izquierda: El vector director de la velocidad gira ¼ de grado respecto al eje Y
		angulo += 0.25;
		break;
	case GLUT_KEY_RIGHT:
		//Flecha derecha: El vector director de la velocidad gira ¼ de grado respecto al eje Y
		angulo -= 0.25;
		break;
	}
	giroz = cos(angulo * PI / 180);
	girox = sin(angulo * PI / 180);

	stringstream title;
	title << "Interfaz de conducción " << speed << "m/s";
	glutSetWindowTitle(title.str().c_str());

	glutPostRedisplay();
}

void onKey(unsigned char tecla, int x, int y)
{
	switch (tecla) {
	case 'a':
		modo = ALAMBRICO;
		break;
	case 's':
		modo = SOLIDO;
		break;
	case 27:
		exit(0);
	}
	glutPostRedisplay();
}

void main(int argc, char** argv)
// Programa principal
{
	glutInit(&argc, argv); // Inicializacion de GLUT
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH); // Alta de buffers a usar
	glutInitWindowSize(1280, 720); // Tamanyo inicial de la ventana
	glutCreateWindow(PROYECTO); // Creacion de la ventana con su titulo
	init(); // Inicializacion propia. IMPORTANTE SITUAR AQUI
	std::cout << PROYECTO << " running" << std::endl; // Mensaje por consola

	glutDisplayFunc(display); // Alta de la funcion de atencion a display
	glutReshapeFunc(reshape); // Alta de la funcion de atencion a reshape
	glutTimerFunc(1000 / tasaFPS, onTimer, 1000 / tasaFPS);
	glutSpecialFunc(onArrow);
	glutKeyboardFunc(onKey);
	glutMainLoop(); // Puesta en marcha del programa
}