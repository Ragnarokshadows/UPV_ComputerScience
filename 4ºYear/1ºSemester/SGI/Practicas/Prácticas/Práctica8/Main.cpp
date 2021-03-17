#include <iostream> // Biblioteca de entrada salida
#include <cmath> // Biblioteca matem�tica de C
#include <freeglut.h> // Biblioteca grafica
#include <../Utilidades.h>
#include <sstream>
#define PROYECTO "Circuito texturado"
#define PI 3.1415926
#define tasaFPS 60

static enum { SOLIDO, ALAMBRICO } modo1;
static enum { DIA, NOCHE } modo2;
static float speed = 0, girox = 0, giroz = -1;
static float distx = 0, disty = 1, distz = 0;
static float avance = 0.0;
static float angulo = 180;
static GLuint suelo, cartel1, cartel2, fondo, plano, meta;

void lightmat() {
	//luces
	//glEnable(GL_LIGHTING);

	//luz luna

	GLfloat Al0[] = { 0.05,0.05,0.05 };
	GLfloat Dl0[] = { 0.05,0.05,0.05 };
	GLfloat Sl0[] = { 0.0,0.0,0.0 };

	glEnable(GL_LIGHT0);
	glLightfv(GL_LIGHT0, GL_AMBIENT, Al0);
	glLightfv(GL_LIGHT0, GL_DIFFUSE, Dl0);
	glLightfv(GL_LIGHT0, GL_SPECULAR, Sl0);



	//luz faro vehiculo

	GLfloat Al1[] = { 0.2,0.2,0.2,1.0 };
	GLfloat Dl1[] = { 1.0,1.0,1.0,1.0 };
	GLfloat Sl1[] = { 0.3,0.3,0.3,1.0 };


	glEnable(GL_LIGHT1);
	glLightfv(GL_LIGHT1, GL_AMBIENT, Al1);
	glLightfv(GL_LIGHT1, GL_DIFFUSE, Dl1);
	glLightfv(GL_LIGHT1, GL_SPECULAR, Sl1);
	glLightf(GL_LIGHT1, GL_SPOT_CUTOFF, 25);
	glLightf(GL_LIGHT1, GL_SPOT_EXPONENT, 20.0);

	//farolas

	GLfloat Alx[] = { 0.0,0.0,0.0,1.0 };
	GLfloat Dlx[] = { 0.5,0.5,0.2,1.0 };
	GLfloat Slx[] = { 0.0,0.0,0.0,1.0 };

	glEnable(GL_LIGHT2);
	glLightfv(GL_LIGHT2, GL_AMBIENT, Alx);
	glLightfv(GL_LIGHT2, GL_DIFFUSE, Dlx);
	glLightfv(GL_LIGHT2, GL_SPECULAR, Slx);
	glLightf(GL_LIGHT2, GL_SPOT_CUTOFF, 45);
	glLightf(GL_LIGHT2, GL_SPOT_EXPONENT, 10.0);

	glEnable(GL_LIGHT3);
	glLightfv(GL_LIGHT3, GL_AMBIENT, Alx);
	glLightfv(GL_LIGHT3, GL_DIFFUSE, Dlx);
	glLightfv(GL_LIGHT3, GL_SPECULAR, Slx);
	glLightf(GL_LIGHT3, GL_SPOT_CUTOFF, 45);
	glLightf(GL_LIGHT3, GL_SPOT_EXPONENT, 10.0);

	glEnable(GL_LIGHT4);
	glLightfv(GL_LIGHT4, GL_AMBIENT, Alx);
	glLightfv(GL_LIGHT4, GL_DIFFUSE, Dlx);
	glLightfv(GL_LIGHT4, GL_SPECULAR, Slx);
	glLightf(GL_LIGHT4, GL_SPOT_CUTOFF, 45);
	glLightf(GL_LIGHT4, GL_SPOT_EXPONENT, 10.0);

	glEnable(GL_LIGHT5);
	glLightfv(GL_LIGHT5, GL_AMBIENT, Alx);
	glLightfv(GL_LIGHT5, GL_DIFFUSE, Dlx);
	glLightfv(GL_LIGHT5, GL_SPECULAR, Slx);
	glLightf(GL_LIGHT5, GL_SPOT_CUTOFF, 45);
	glLightf(GL_LIGHT5, GL_SPOT_EXPONENT, 10.0);

	//materiales
	GLfloat Dmx[] = { 0.8,0.8,0.8,1.0 };
	GLfloat Smx[] = { 0.3,0.3,0.3,1.0 };

	//glEnable(GL_COLOR_MATERIAL);//con glColor3fv podemos cambiar el color difuso
	glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, Dmx);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, Smx);
	glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 3);
	glColorMaterial(GL_FRONT_AND_BACK, GL_DIFFUSE);

}

void loadtextures() {

	glGenTextures(1, &suelo);
	glBindTexture(GL_TEXTURE_2D, suelo);
	loadImageFile((char *) "asfalto.jpg");
	glGenTextures(1, &cartel1);
	glBindTexture(GL_TEXTURE_2D, cartel1);
	loadImageFile((char*) "cartel1.jpg");
	glGenTextures(1, &cartel2);
	glBindTexture(GL_TEXTURE_2D, cartel2);
	loadImageFile((char*) "cartel2.png");
	glGenTextures(1, &fondo);
	glBindTexture(GL_TEXTURE_2D, fondo);
	loadImageFile((char*) "background.png");
	glGenTextures(1, &plano);
	glBindTexture(GL_TEXTURE_2D, plano);
	loadImageFile((char*) "plano.jpg");
	glGenTextures(1, &meta);
	glBindTexture(GL_TEXTURE_2D, meta);
	loadImageFile((char*) "meta.png");
	glEnable(GL_TEXTURE_2D);

}

void init()
// Funcion de inicializacion propia
{
	//Tener en cuenta la profundidad logica
	glEnable(GL_DEPTH_TEST);

	lightmat();

	loadtextures();


}

void farolas() {
	GLfloat posicionL2[] = { 0,4,-10,1 };//farola1
	glLightfv(GL_LIGHT2, GL_POSITION, posicionL2);
	GLfloat dir_farola[] = { 0.0, -1.0, 0.0 };
	glLightfv(GL_LIGHT2, GL_SPOT_DIRECTION, dir_farola);
	GLfloat posicionL3[] = { 11,4,-34,1 };//farola2
	glLightfv(GL_LIGHT3, GL_POSITION, posicionL3);
	glLightfv(GL_LIGHT3, GL_SPOT_DIRECTION, dir_farola);
	GLfloat posicionL4[] = { 0,4,10,1 };//farola3
	glLightfv(GL_LIGHT4, GL_POSITION, posicionL4);
	glLightfv(GL_LIGHT4, GL_SPOT_DIRECTION, dir_farola);
	GLfloat posicionL5[] = { 40,4,15,1 };//farola4
	glLightfv(GL_LIGHT5, GL_POSITION, posicionL5);
	glLightfv(GL_LIGHT5, GL_SPOT_DIRECTION, dir_farola);
}

void pista() {
	glPushMatrix();

	glBindTexture(GL_TEXTURE_2D, suelo);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
	glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);

	glNormal3f(0, 1, 0);
	glColor3f(0, 0, 0);
	GLfloat v0[3] = { 2,0,30 }, v1[3] = { -2,0,30 }, v2[3] = { -2,0,-32 }, v3[3] = { 2,0,-30 };
	quad(v1, v0, v3, v2, 30, 30);
	GLfloat v4[3] = { 4,0,-32 }, v5[3] = { 2,0,-36 }, v6[3] = { -2,0,-32 }, v7[3] = { 2,0,-30 };
	quad(v4, v5, v6, v7, 30, 30);
	GLfloat v8[3] = { 4,0,-32 }, v9[3] = { 26,0,-32 }, v10[3] = { 28,0,-36 }, v11[3] = { 2,0,-36 };
	quad(v9, v10, v11, v8, 30, 30);
	GLfloat v12[3] = { 26,0,-32 }, v13[3] = { 28,0,-36 }, v14[3] = { 32,0,-32 }, v15[3] = { 28,0,-30 };
	quad(v13, v12, v15, v14, 30, 30);
	GLfloat v16[3] = { 32,0,-32 }, v17[3] = { 28,0,-30 }, v18[3] = { 28,0,-10 }, v19[3] = { 32,0,-12 };
	quad(v16, v17, v18, v19, 30, 30);
	GLfloat v20[3] = { 32,0,-12 }, v21[3] = { 28,0,-10 }, v22[3] = { 38,0,0 }, v23[3] = { 42,0,-2 };
	quad(v20, v21, v22, v23, 30, 30);
	GLfloat v24[3] = { 38,0,0 }, v25[3] = { 42,0,-2 }, v26[3] = { 42,0,32 }, v27[3] = { 38,0,30 };
	quad(v25, v24, v27, v26, 30, 30);
	GLfloat v28[3] = { 42,0,32 }, v29[3] = { 38,0,30 }, v30[3] = { 36,0,32 }, v31[3] = { 38,0,36 };
	quad(v28, v29, v30, v31, 30, 30);
	GLfloat v32[3] = { 36,0,32 }, v33[3] = { 38,0,36 }, v34[3] = { 2,0,36 }, v35[3] = { 4,0,32 };
	quad(v33, v32, v35, v34, 30, 30);
	GLfloat v36[3] = { 2,0,36 }, v37[3] = { 4,0,32 }, v38[3] = { 2,0,30 }, v39[3] = { -2,0,30 };
	quad(v36, v37, v38, v39, 30, 30);

	glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);

	glPopMatrix();

}

void carteles1() {
	glPushMatrix();
	glBindTexture(GL_TEXTURE_2D, cartel1);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
	glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
	glBegin(GL_QUADS);
	glTexCoord2f(0, 0);
	glVertex3f(-2, 2, -24);
	glTexCoord2f(1, 0);
	glVertex3f(2, 2, -24);
	glTexCoord2f(1, 1);
	glVertex3f(2, 4, -24);
	glTexCoord2f(0, 1);
	glVertex3f(-2, 4, -24);
	glEnd();
	glBegin(GL_QUADS);
	glTexCoord2f(0, 0);
	glVertex3f(-2, 2, 24);
	glTexCoord2f(1, 0);
	glVertex3f(2, 2, 24);
	glTexCoord2f(1, 1);
	glVertex3f(2, 4, 24);
	glTexCoord2f(0, 1);
	glVertex3f(-2, 4, 24);
	glEnd();
	glBegin(GL_QUADS);
	glTexCoord2f(1, 0);
	glVertex3f(38, 2, 15);
	glTexCoord2f(0, 0);
	glVertex3f(42, 2, 15);
	glTexCoord2f(0, 1);
	glVertex3f(42, 4, 15);
	glTexCoord2f(1, 1);
	glVertex3f(38, 4, 15);
	glEnd();
	glPopMatrix();

	glPushMatrix();
	glLineWidth(5);
	glColor3f(0.0, 0.0, 0.0);
	glBegin(GL_LINES);
	glVertex3f(2, 0, -24);
	glVertex3f(2, 4, -24);
	glVertex3f(-2, 0, -24);
	glVertex3f(-2, 4, -24);

	glVertex3f(2, 0, 24);
	glVertex3f(2, 4, 24);
	glVertex3f(-2, 0, 24);
	glVertex3f(-2, 4, 24);

	glVertex3f(38, 0, 15);
	glVertex3f(38, 4, 15);
	glVertex3f(42, 0, 15);
	glVertex3f(42, 4, 15);
	glEnd();
	glPopMatrix();


}

void carteles2() {
	glPushMatrix();
	glBindTexture(GL_TEXTURE_2D, cartel2);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
	glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
	glBegin(GL_QUADS);
	glTexCoord2f(0, 0);
	glVertex3f(15, 2, -36);
	glTexCoord2f(1, 0);
	glVertex3f(15, 2, -32);
	glTexCoord2f(1, 1);
	glVertex3f(15, 4, -32);
	glTexCoord2f(0, 1);
	glVertex3f(15, 4, -36);
	glEnd();

	glBegin(GL_QUADS);
	glTexCoord2f(0, 0);
	glVertex3f(16, 2, 32);
	glTexCoord2f(1, 0);
	glVertex3f(16, 2, 36);
	glTexCoord2f(1, 1);
	glVertex3f(16, 4, 36);
	glTexCoord2f(0, 1);
	glVertex3f(16, 4, 32);
	glEnd();
	glPopMatrix();

	glPushMatrix();
	glLineWidth(5);
	glColor3f(0.0, 0.0, 0.0);
	glBegin(GL_LINES);
	glVertex3f(15, 0, -36);
	glVertex3f(15, 4, -36);
	glVertex3f(15, 0, -32);
	glVertex3f(15, 4, -32);

	glVertex3f(16, 0, 36);
	glVertex3f(16, 4, 36);
	glVertex3f(16, 0, 32);
	glVertex3f(16, 4, 32);
	glEnd();
	glPopMatrix();

}

void background() {

	glPushMatrix();
	glBindTexture(GL_TEXTURE_2D, fondo);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
	glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);

	glBegin(GL_QUADS);
	glTexCoord2f(0, 1);
	glVertex3f(-250, 150, -250);
	glTexCoord2f(1, 1);
	glVertex3f(250, 150, -250);
	glTexCoord2f(1, 0);
	glVertex3f(200, -1, -200);
	glTexCoord2f(0, 0);
	glVertex3f(-200, -1, -200);
	glEnd();

	glBegin(GL_QUADS);
	glTexCoord2f(0, 1);
	glVertex3f(250, 150, -250);
	glTexCoord2f(1, 1);
	glVertex3f(250, 150, 250);
	glTexCoord2f(1, 0);
	glVertex3f(200, -1, 200);
	glTexCoord2f(0, 0);
	glVertex3f(200, -1, -200);
	glEnd();

	glBegin(GL_QUADS);
	glTexCoord2f(0, 1);
	glVertex3f(250, 150, 250);
	glTexCoord2f(1, 1);
	glVertex3f(-250, 150, 250);
	glTexCoord2f(1, 0);
	glVertex3f(-200, -1, 200);
	glTexCoord2f(0, 0);
	glVertex3f(200, -1, 200);
	glEnd();

	glBegin(GL_QUADS);
	glTexCoord2f(0, 1);
	glVertex3f(-250, 150, 250);
	glTexCoord2f(1, 1);
	glVertex3f(-250, 150, -250);
	glTexCoord2f(1, 0);
	glVertex3f(-200, -1, -200);
	glTexCoord2f(0, 0);
	glVertex3f(-200, -1, 200);
	glEnd();

	glPopMatrix();
}

void floor() {
	glPushMatrix();
	glBindTexture(GL_TEXTURE_2D, plano);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
	glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);


	glBegin(GL_QUADS);
	glTexCoord2f(0, 0);
	glVertex3f(-200, -0.01, 200);
	glTexCoord2f(1, 0);
	glVertex3f(200, -0.01, 200);
	glTexCoord2f(1, 1);
	glVertex3f(200, -0.01, -200);
	glTexCoord2f(0, 1);
	glVertex3f(-200, -0.01, -200);
	glEnd();
	glPopMatrix();

}

void startpos() {
	glPushMatrix();
	glBindTexture(GL_TEXTURE_2D, meta);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
	glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
	glBegin(GL_QUADS);
	glTexCoord2f(0, 0);
	glVertex3f(-2, 0.01, 1);
	glTexCoord2f(1, 0);
	glVertex3f(2, 0.01, 1);
	glTexCoord2f(1, 1);
	glVertex3f(2, 0.01, -1);
	glTexCoord2f(0, 1);
	glVertex3f(-2, 0.01, -1);
	glEnd();

	glPopMatrix();



}

void display()
// Funcion de atencion al dibujo
{
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glClearColor(1, 1, 1, 1);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();

	GLfloat posicionL1[] = { 0 ,0.7,0,1 };//luz de los focos del coche
	glLightfv(GL_LIGHT1, GL_POSITION, posicionL1);
	GLfloat dir_light1[] = { 0.0, -0.5, -0.7 };
	glLightfv(GL_LIGHT1, GL_SPOT_DIRECTION, dir_light1);

	//gluLookAt(0, 700, 0, 0, 0, 0, 0, 0, -1);
	gluLookAt(distx, disty, distz, distx + girox, 1, distz + giroz, 0, 1, 0);

	if (modo2 == NOCHE) {
		glClearColor(0, 0, 0, 1);
		glEnable(GL_LIGHTING);
	}
	else {
		glClearColor(1, 1, 1, 1);
		glDisable(GL_LIGHTING);
	}

	if (modo1 == ALAMBRICO) {
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	}
	else {
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}

	pista();

	startpos();

	carteles1();

	carteles2();

	background();

	floor();

	GLfloat posicion[] = { 0.0, 10.0, 0.0, 0.0 };
	glLightfv(GL_LIGHT0, GL_POSITION, posicion);

	farolas();

	glutSwapBuffers();
}

void reshape(GLint w, GLint h)
// Funcion de atencion al redimensionamiento
{
	glViewport(0, 0, w, h);
	float razon = (float)w / h;
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluPerspective(45, razon, 1, 1000);
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
		//Flecha arriba: Aumenta el m�dulo de la velocidad en 0.1 m/sg
		speed += 0.1;
		break;
	case GLUT_KEY_DOWN:
		//Disminuye el m�dulo de la velocidad en 0.1 m/sg
		if (speed > 0.1) speed -= 0.1;
		break;
	case GLUT_KEY_LEFT:
		//Flecha izquierda: El vector director de la velocidad gira � de grado respecto al eje Y
		angulo += 0.8;
		break;
	case GLUT_KEY_RIGHT:
		//Flecha derecha: El vector director de la velocidad gira � de grado respecto al eje Y
		angulo -= 0.8;
		break;
	}
	giroz = cos(angulo * PI / 180);
	girox = sin(angulo * PI / 180);

	stringstream title;
	title << "Circuito texturado SPEED" << speed << "m/s";
	glutSetWindowTitle(title.str().c_str());

	glutPostRedisplay();
}

void onKey(unsigned char tecla, int x, int y)
{
	switch (tecla) {
	case 'a':
		modo1 = ALAMBRICO;
		break;
	case 's':
		modo1 = SOLIDO;
		break;
	case 'd':
		modo2 = DIA;
		break;
	case 'n':
		modo2 = NOCHE;
		break;
	case 27:
		exit(0);
	}
	glutPostRedisplay();
}

void main(int argc, char** argv)
// Programa principal
{
	FreeImage_Initialise();
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
	FreeImage_DeInitialise();
}
