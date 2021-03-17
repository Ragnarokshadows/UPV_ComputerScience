#define PROYECTO "Reloj analogico" //NOMBRE DEL PROYECTO - HECHO

#ifdef _MSC_VER
#define _CRT_SECURE_NO_WARNINGS
#endif

#define _USE_MATH_DEFINES 

#include <iostream>
#include <sstream>
#include <cmath>
#include <ctime>
#include "freeglut.h"

using namespace std;

static GLuint circunferenciaHora, circunferenciaMinuto, centro, horas, minutero, secundero, marcaHora, marcaMinuto, estrella, figura;

//Posiciones de la camara perspectiva
static float x = 0;
static float y = 0;
static float z = 7;
static float radio = 1;

//Colores para la figura del centro del reloj
float rojo = 0.0, verde = 0.0, azul = 0.0;

//Ángulos de giro
float alfa = 0, alfaH, alfaM, alfaS;

void init()
{
	glClearColor(1.0, 1.0, 1.0, 1); //COLOR DE FONDO - HECHO
	glEnable(GL_DEPTH_TEST); //ACTIVAR EL Z-BUFFER - HECHO

	//Estrella de David
	estrella = glGenLists(1); 
	glNewList(estrella, GL_COMPILE); 
	glPushAttrib(GL_CURRENT_BIT);
	glPushMatrix(); 
	glBegin(GL_TRIANGLE_STRIP);
	glVertex3f(1.0 * cos(0 * (2 * M_PI / 6) + (M_PI / 2)), 1.0 * sin(0 * (2 * M_PI / 6) + (M_PI / 2)), 0.0);
	glVertex3f(0.7 * cos(0 * (2 * M_PI / 6) + (M_PI / 2)), 0.7 * sin(0 * (2 * M_PI / 6) + (M_PI / 2)), 0.0);
	glVertex3f(1.0 * cos(2 * (2 * M_PI / 6) + (M_PI / 2)), 1.0 * sin(2 * (2 * M_PI / 6) + (M_PI / 2)), 0.0);
	glVertex3f(0.7 * cos(2 * (2 * M_PI / 6) + (M_PI / 2)), 0.7 * sin(2 * (2 * M_PI / 6) + (M_PI / 2)), 0.0);
	glVertex3f(1.0 * cos(4 * (2 * M_PI / 6) + (M_PI / 2)), 1.0 * sin(4 * (2 * M_PI / 6) + (M_PI / 2)), 0.0);
	glVertex3f(0.7 * cos(4 * (2 * M_PI / 6) + (M_PI / 2)), 0.7 * sin(4 * (2 * M_PI / 6) + (M_PI / 2)), 0.0);
	glVertex3f(1.0 * cos(0 * (2 * M_PI / 6) + (M_PI / 2)), 1.0 * sin(0 * (2 * M_PI / 6) + (M_PI / 2)), 0.0);
	glVertex3f(0.7 * cos(0 * (2 * M_PI / 6) + (M_PI / 2)), 0.7 * sin(0 * (2 * M_PI / 6) + (M_PI / 2)), 0.0);
	glEnd();
	glBegin(GL_TRIANGLE_STRIP);
	glVertex3f(1.0 * cos(1 * (2 * M_PI / 6) + (M_PI / 2)), 1.0 * sin(1 * (2 * M_PI / 6) + (M_PI / 2)), 0.0);
	glVertex3f(0.7 * cos(1 * (2 * M_PI / 6) + (M_PI / 2)), 0.7 * sin(1 * (2 * M_PI / 6) + (M_PI / 2)), 0.0);
	glVertex3f(1.0 * cos(3 * (2 * M_PI / 6) + (M_PI / 2)), 1.0 * sin(3 * (2 * M_PI / 6) + (M_PI / 2)), 0.0);
	glVertex3f(0.7 * cos(3 * (2 * M_PI / 6) + (M_PI / 2)), 0.7 * sin(3 * (2 * M_PI / 6) + (M_PI / 2)), 0.0);
	glVertex3f(1.0 * cos(5 * (2 * M_PI / 6) + (M_PI / 2)), 1.0 * sin(5 * (2 * M_PI / 6) + (M_PI / 2)), 0.0);
	glVertex3f(0.7 * cos(5 * (2 * M_PI / 6) + (M_PI / 2)), 0.7 * sin(5 * (2 * M_PI / 6) + (M_PI / 2)), 0.0);
	glVertex3f(1.0 * cos(1 * (2 * M_PI / 6) + (M_PI / 2)), 1.0 * sin(1 * (2 * M_PI / 6) + (M_PI / 2)), 0.0);
	glVertex3f(0.7 * cos(1 * (2 * M_PI / 6) + (M_PI / 2)), 0.7 * sin(1 * (2 * M_PI / 6) + (M_PI / 2)), 0.0);
	glEnd();
	glPopMatrix();
	glPopAttrib();
	glEndList(); 

	//Figura de la práctica 4
	figura = glGenLists(1); 
	glNewList(figura, GL_COMPILE); 
	glPushAttrib(GL_CURRENT_BIT);
	glPushMatrix(); 
	for (int i = 0; i < 6; i++) {
		glRotatef(30, 0, 1, 0);
		glCallList(estrella);
	}
	glPopMatrix();
	glPopAttrib();
	glEndList();

	//Centro con la figura y la esfera
	centro = glGenLists(1); 
	glNewList(centro, GL_COMPILE); 
	glPushAttrib(GL_CURRENT_BIT);
	glPushMatrix(); 
	glRotatef(45, 0, 0, 1);
	glScalef(0.2, 0.2, 0.2);
	glCallList(figura);
	glutWireSphere(1, 20, 20);
	glPopMatrix();
	glPopAttrib();
	glEndList();

	//Manecillas de las horas. Un cilindro con un cono en la punta.
	horas = glGenLists(1);
	glNewList(horas, GL_COMPILE);
	glPushAttrib(GL_CURRENT_BIT);
	glPushMatrix();
	glColor3f(0.41, 0.0, 0.35); //Morado
	glTranslatef(0.0, 0.4, 0.0); //Trasladamos el cono 0.4 en el eje de las y
	glRotatef(-90, 1, 0, 0); //Rotamos 90 º sobre el eje de las x.
	glutSolidCone(0.05, 0.15, 100, 100); //Dibujamos cono con base de radio 0.05 y altura 0,15
	glPopMatrix();
	glPushMatrix();
	glColor3f(0.41, 0.0, 0.35); //Morado
	glTranslatef(0.0, 0.3, 0.0); //Trasladamos el cilindro 0.3 en el eje de las y
	glRotatef(-90, 1, 0, 0); //Rotamos 90 º sobre el eje de las x.
	glutSolidCylinder(0.02, 0.15, 100, 100); //Dibujamos cilindro con base de radio 0.02 y altura 0,15
	glPopMatrix();
	glPopAttrib();
	glEndList();

	//Manecilla de los minutos. Un cilindro con un cono en la punta más grande que el anterior.
	minutero = glGenLists(1);
	glNewList(minutero, GL_COMPILE);
	glPushAttrib(GL_CURRENT_BIT);
	glPushMatrix();
	glColor3f(0.3, 0.3, 1.0); //Azul
	glTranslatef(0.0, 0.5, 0.0); //Trasladamos el cono 0.5 en el eje de las y
	glRotatef(-90, 1, 0, 0);
	glutSolidCone(0.05, 0.30, 100, 100);
	glPopMatrix();
	glPushMatrix();
	glColor3f(0.3, 0.3, 1.0); //Azul
	glTranslatef(0.0, 0.3, 0.0); //Trasladamos el cilindro 0.3 en el eje de las y
	glRotatef(-90, 1, 0, 0); //Rotamos 90 º sobre el eje de las x.
	glutSolidCylinder(0.02, 0.3, 100, 100); //Dibujamos cilindro con base de radio 0.02 y altura 0,3
	glPopMatrix();
	glPopAttrib();
	glEndList();

	//Manecilla de los segundos. Un cilindro con un cono en la punta más estrecho que el anterior.
	secundero = glGenLists(1);
	glNewList(secundero, GL_COMPILE);
	glPushAttrib(GL_CURRENT_BIT);
	glPushMatrix();
	glColor3f(0.0, 0.0, 0.0);
	glTranslatef(0.0, 0.85, 0.0);
	glRotatef(-90, 1, 0, 0);
	glutSolidCone(0.01, 0.05, 100, 100);
	glPopMatrix();
	glPushMatrix();
	glColor3f(0.0, 0.0, 0.0);
	glRotatef(-90, 1, 0, 0);
	glutSolidCylinder(0.01, 0.85, 100, 100);
	glPopMatrix();
	glPopAttrib();
	glEndList();

	//Creacion de las marcas horarias
	marcaHora = glGenLists(1);
	glNewList(marcaHora, GL_COMPILE);
	glPushAttrib(GL_CURRENT_BIT);
	glPushMatrix();
	glTranslatef(0.0, 0.85, 0.0);
	glRotatef(-90, 1, 0, 0);
	glutSolidCone(0.01, 0.1, 100, 100);
	glPopMatrix();
	glPopAttrib();
	glEndList();

	//Creamos la circunferencia con las 12 horas del reloj, siendo (3, 6, 9, 12) de color rojo y las otras naranja
	circunferenciaHora = glGenLists(1);
	glNewList(circunferenciaHora, GL_COMPILE);
	glPushAttrib(GL_CURRENT_BIT);
	for (auto i = 0; i < 12; i++) {
		float angle = i * 360 / 12;

		glPushMatrix();
		if (i == 0 || i == 3 || i == 6 || i == 9) {
			glColor3f(1.0, 0.0, 0.0); //Rojo
		} else {
			glColor3f(1.0, 0.5, 0.0); //Naranja
		}
		glRotatef(angle, 0, 0, 1); //Distribucion en la circunferencia
		if (i == 0 || i == 3 || i == 6 || i == 9) {
			glScalef(5, 1, 5);
		} else {
			glScalef(3, 1, 3);
		}
		glCallList(marcaHora);
		glPopMatrix();
	}
	glPopAttrib();
	glEndList();

	//Creacion de las marcas de los minutos
	marcaMinuto = glGenLists(1);
	glNewList(marcaMinuto, GL_COMPILE);
	glPushAttrib(GL_CURRENT_BIT);
	glPushMatrix();
	//glColor3f(0.0, 0.0, 0.0);
	glTranslatef(0.0, 0.90, 0.0);
	glRotatef(90, 1, 0, 0); //Cono apunta hacia abajo
	glutSolidCone(0.01, 0.10, 100, 100);
	glPopMatrix();
	glPopAttrib();
	glEndList();

	//Creamos la circunferencia con los 60 minutos del reloj
	circunferenciaMinuto = glGenLists(1);
	glNewList(circunferenciaMinuto, GL_COMPILE);
	glPushAttrib(GL_CURRENT_BIT);
	for (auto i = 0; i < 60; i++) {
		float angle = i * 360 / 60;
		if (i % 5 != 0) { //Quitamos las marcas que coinciden con las de las horas
			glPushMatrix();
			if (i % 2 == 0) {
				glColor3f(0.0, 1.0, 0.0); //Verde
			}
			else {
				glColor3f(0.36, 0.25, 0.20); //Marrón
			}
			glRotatef(angle, 0, 0, 1); //Distribucion en la circunferencia
			glCallList(marcaMinuto);
			glPopMatrix();
		}
	}
	glPopAttrib();
	glEndList();
}

void display()
{
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Borra la pantalla
	glMatrixMode(GL_MODELVIEW); // Seleccionamos la matriz que almacena el modelo y la camara
	glLoadIdentity();

	//CÁMARA PERSPECTIVA, FUERA DE LA ESFEREA UNIDAD, MIRANDO AL ORIGEN, VERTICAL EN EL EJE Y - HECHO
	gluLookAt(x, y, z, 0, 0, 0, 0, 1, 0); 

	//Se van alterando los colores de la figura del centro
	rojo = rojo + 0.1; if (rojo > 1.0) rojo = 0.0;
	verde = verde + 0.05; if (verde > 1.0) verde = 0.0;
	azul = azul + 0.15; if (azul > 1.0) azul = 0.0;

	//UN OBJETO DEBE MOVERSE CONTINUAMENTE - HECHO
	glPushMatrix();
	glColor3f(rojo, verde, azul);
	glRotatef(alfa, 1, 1, 1);
	glCallList(centro);
	glPopMatrix();

	//UN OBJETO DEBE CAMBIAR CADA HORA - HECHO
	glPushMatrix();
	glRotatef(-alfaH, 0, 0, 1);
	glCallList(horas);
	glPopMatrix();

	//UN OBJETO DEBE CAMBIAR CADA MINUTO - HECHO
	glPushMatrix();
	glRotatef(-alfaM, 0, 0, 1);
	glCallList(minutero);
	glPopMatrix();

	//UN OBJETO DEBE CAMBIAR CADA SEGUNDO - HECHO
	glPushMatrix();
	glRotatef(-alfaS, 0, 0, 1);
	glCallList(secundero);
	glPopMatrix();

	//UN OBJETO DEBE MOVERSE CONTINUAMENTE - HECHO
	glPushMatrix();
	glRotatef(alfa, 1, 1, 0);
	glCallList(circunferenciaHora);
	glPopMatrix();

	//UN OBJETO DEBE MOVERSE CONTINUAMENTE - HECHO
	glPushMatrix();
	glRotatef(alfa, -1, 1, 0);
	glCallList(circunferenciaMinuto);
	glPopMatrix();


	//ESFERA UNIDAD, SÓLO PARA VER SI TODO QUEDA DENTRO - HECHO
	/*
	glPushMatrix();
	glColor3f(0.36, 0.25, 0.20); 
	glRotatef(45, 1, 1, 0);
	glutWireSphere(1, 100, 100);
	glPopMatrix();
	*/

	//SE DEBE USAR DOBLE BUFFER - HECHO
	glutSwapBuffers(); 
}

void reshape(GLint w, GLint h)
{
	glViewport(0, 0, w, h);

	float ra = float(w) / float(h);

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();

	float distancia = sqrt(x*x + y*y + z*z);
	float angulo = (asin(1 / (distancia)) * 2.0) * 180 / M_PI;

	//LA ESFERA UNIDAD TIENE QUE TOCAR LOS LÍMITES SUPERIOR E INFERIOR - HECHO
	gluPerspective(angulo, ra, 1, 10);
}

void update() 
{
	//ANIMACIÓN TEMPORALMENTE COHERENTE - HECHO

	//Velocidad de cambio continuo
	static const float vel = 0.1;

	time_t now = time(NULL);
	tm* time = localtime(&now);

	static int t_anterior = glutGet(GLUT_ELAPSED_TIME);

	int t_actual = glutGet(GLUT_ELAPSED_TIME);
	
	float t_transcurrido = ((double)t_actual - (double)t_anterior) / 1000.0;

	t_anterior = t_actual;

	alfa += (vel * 360.0) * t_transcurrido;
	alfaH = 360 / 12.0 * (time->tm_hour % 12);
	alfaM = 360 / 60.0 * time->tm_min;
	alfaS = 360 / 60.0 * time->tm_sec;

	glutPostRedisplay();
}

void onTimer(int tiempo) 
{
	glutTimerFunc(tiempo, onTimer, tiempo);

	update();
}

void main(int argc, char** argv)
{
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
	glutInitWindowSize(600, 600); //CUALQUIER TIPO DE DIMESIONES DE DIBUJO - HECHO

	glutCreateWindow(PROYECTO);
	init();
	cout << PROYECTO << " en ejecucion" << endl;

	glutDisplayFunc(display);
	glutReshapeFunc(reshape);
	glutTimerFunc(1000 / 60, onTimer, 1000 / 60); // 60 FPS

	glutMainLoop();
}