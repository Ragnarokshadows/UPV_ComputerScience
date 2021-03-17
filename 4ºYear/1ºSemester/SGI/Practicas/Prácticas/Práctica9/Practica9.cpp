#define PROYECTO "Videojuego de conduccion"
#define _CRT_SECURE_NO_WARNINGS
#include <iostream> // Biblioteca de entrada salida
#include <cmath> // Biblioteca matemática de C
#include <freeglut.h> // Biblioteca grafica
#include <../Utilidades.h>
#include <sstream>
#include <ctime>
#include <irrKlang.h>

#pragma comment(lib, "irrKlang.lib")

using namespace irrklang;
using namespace std;

//Booleanos de los distintos modos
bool M_Alam = false, M_Luz = false, M_Niebla = false;
bool M_Dif = false, M_UI = false, M_Ori = false;
bool Vista_Aguila = false, M_Larga = false, M_Car = false;

//Para generar sonido
ISoundEngine* engine;

//Posición de la cámara
double x = 0, z = 0;
static int y = 1;

//Amplitud y periodo de la función sinusoidal
float A = 8, T = 100;

//Array para todas las texturas
GLuint texturas[14];

//Booleanos de los controles
bool arriba = false, abajo = false, der = false, izq = false;
bool der2 = false, izq2 = false;

//Incrementos originales y cambiados
float incVel = 0.05;
float incVel_ori = 0.1;
float incGiro = PI / 90;
float incGiro_ori = 0.25;

//Variables para saber el giro y la velocidad
float giro = 1.10, velocidad = 0;

//Vertices para los quads
GLfloat v0[3] = { 0,0,0 }, v1[3] = { 0,0,0 }, v2[3] = { 0,0,0 }, v3[3] = { 0,0,0 }; 

//Parámetros de la creación de la carretera
GLint anch_Road = 4;
GLint dist_Farola = 10;

//Parámetros material de la carretera
GLfloat roadDif[] = { 0.8, 0.8, 0.8, 1.0 };
GLfloat roadEsp[] = { 0.3, 0.3, 0.3, 1.0 };

//Gestión de coins
int coins = 1;
bool coinTaken = false;

//Giro de las coins continuo
float alfa = 0;

//Gestión de pancartas y farolas
int ind_pancarta = 0;
float ant = 4, ant2 = 4; 

//Array con los nombres de las luces de las farolas
GLfloat farolas[] = { GL_LIGHT2, GL_LIGHT3, GL_LIGHT4, GL_LIGHT5 };

//Funciones para la creación de la carretera siguiendo una función sinusoidal
float func_sin(float x) {
	return A * sin(x * ((2 * PI) / T));
}
float der_func_sin(float x) {
	return ((2 * PI * A) / T) * cos(x * (2 * PI / T));
}

//Función para actualizar el número de monedas recogidas, se hace cada cierto tiempo para tratar de no coger la misma moneda varias veces
void updateCoins(int seg) {
	static int antes = 0;
	int ahora, tiempo;
	ahora = glutGet(GLUT_ELAPSED_TIME); 
	tiempo = ahora - antes;

	if (tiempo > 1000) {
		coinTaken = false;
		antes = ahora;
	}

	glutTimerFunc(1500, updateCoins, 1500);
	glutPostRedisplay();
}

void updateControl(int seg) {
	izq2 = izq;
	der2 = der;

	glutTimerFunc(1500, updateControl, 1500);
	glutPostRedisplay();
}

//Función para ajustar el movimiento del vehículo en base a las teclas pulsadas
void onTimer(int valor) {

	// ANIMACIÓN TEMPORALMENTE COHERENTE - HECHO
	static int antes = 0;
	int ahora;
	double tiempo;
	ahora = glutGet(GLUT_ELAPSED_TIME);
	tiempo = ((double) ahora - antes) / 1000.0;
	x += (double) velocidad * sin(giro) * tiempo;
	z += (double) velocidad * cos(giro) * tiempo;
	antes = ahora;

	alfa += (360.0 / 30) * tiempo;

	// CONTROLES SEGÚN LA PRÁCTICA 6 - HECHO
	if (arriba) {
		if (M_Ori) velocidad += incVel_ori;
		else velocidad += incVel;
	}
	if (abajo && velocidad > 0.0001) {
		if(M_Ori) velocidad -= incVel_ori;
		else velocidad -= incVel;
	} 
	if (izq) {
		if (M_Ori) giro += incGiro_ori;
		else giro += incGiro;
	} 
	if (der) {
		if (M_Ori) giro -= incGiro_ori;
		else giro -= incGiro;
	} 
	if (velocidad > 15) {
		velocidad = 15;
	}
	if (velocidad < 0.05) {
		velocidad = 0;
	}

	arriba = abajo = izq = der = false;

	// TÍTULO CON LA MAGNITUD DE LA VELOCIDAD - HECHO
	stringstream titulo;
	titulo << "Videojuego de conduccion. Velocidad en m/s: " << velocidad << " Monedas: " << (coins - 1);
	glutSetWindowTitle(titulo.str().c_str());
	
	glutTimerFunc(1000/60, onTimer, 1000 / 60);
	glutPostRedisplay();
}

//Función que controla las teclas pulsadas
void onKey(unsigned char letra, int xp, int yp) {
	switch (letra) {
	case 'S':
	case 's':
		if (M_Alam) M_Alam = false; else M_Alam = true;
		break;
	case 'L':
	case 'l':
		if (M_Luz) M_Luz = false; else M_Luz = true;
		break;
	case 'N':
	case 'n':
		if (M_Niebla) M_Niebla = false; else M_Niebla = true;
		break;
	case 27:
		exit(0);
	case 'D':
	case 'd':
		if (!M_Dif) {
			A = 15, T = 70;
			M_Dif = true;
			//Música de fondo
			engine->stopAllSounds();
			engine->play2D("./Wii-Grumble-Volcano.mp3", true);			
		}
		else {
			A = 10, T = 120;
			M_Dif = false;
			//Música de fondo
			engine->stopAllSounds();
			engine->play2D("./Bone-Dry-Dunes.mp3", true);
			
		}
		break;
	case 'C':
	case 'c':
		if (M_UI) M_UI = false; else M_UI = true;
		break;
	case 'O':
	case 'o':
		if (M_Ori) M_Ori = false; else M_Ori = true;
		break;
	case 'W':
	case 'w':
		if (M_Larga) M_Larga = false; else M_Larga = true;
		break;
	case 'E':
	case 'e':
		if (M_Car) M_Car = false; else M_Car = true;
		break;
	case 'A':
	case 'a':
		if (Vista_Aguila) Vista_Aguila = false; else Vista_Aguila = true;
		break;
	case 'P':
	case 'p':
		saveScreenshot((char*)"photo.jpg", 600, 600);
		break;
	}
	glutPostRedisplay();

}

//Función que controla las flechas
void onSpecialKey(int tecla, int xp, int yp) {
	switch (tecla) {
	case GLUT_KEY_UP:
		arriba = true;
		izq2 = false;
		der2 = false;
		break;
	case GLUT_KEY_DOWN:
		abajo = true;
		izq2 = false;
		der2 = false;
		break;
	case GLUT_KEY_LEFT:
		izq = true;
		izq2 = true;
		break;
	case GLUT_KEY_RIGHT:
		der = true;
		der2 = true;
		break;
	}
	glutPostRedisplay();
}

//Dibujo del kart
void kart() {
	glPushMatrix();
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	if (der2) {
		glBindTexture(GL_TEXTURE_2D, texturas[10]);
	}
	else if (izq2) {
		glBindTexture(GL_TEXTURE_2D, texturas[11]);
	}
	else {
		glBindTexture(GL_TEXTURE_2D, texturas[12]);
	}
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	glBegin(GL_QUADS);
	glTexCoord2f(0, 0);
	glVertex3f(-0.5, 0.1, -2);
	glTexCoord2f(1, 0);
	glVertex3f(0.5, 0.1, -2);
	glTexCoord2f(1, 1);
	glVertex3f(0.5, 0.9, -2);
	glTexCoord2f(0, 1);
	glVertex3f(-0.5, 0.9, -2);
	glEnd(); 
	glDisable(GL_BLEND);
	glPopMatrix();
}

//Dibujo de la pelota que se vuelve dorada dependiendo del número de monedas recogidas
void pelota() {
	glPushMatrix();
	glTranslatef(0, -0.75, -2);
	glColor3f(1, 0, 0);

	glRotatef(100 * x, -1, 0, 0);
	if (coins >= 11) {
		glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, ORO);
		glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, AMARILLO);
		glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 5);
		glBindTexture(GL_TEXTURE_2D, texturas[4]);
	}
	else {
		glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, GRISCLARO);
		glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, GRISOSCURO);
		glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 5);
		glBindTexture(GL_TEXTURE_2D, texturas[13]);
	}

	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	if (M_Alam) glutWireSphere(0.2, 30, 30); 
	else glutSolidSphere(0.2, 30, 30);
	glPopMatrix();
}

//Función de iniciación
void init()
{
	engine = createIrrKlangDevice();

	//Música de fondo
	engine->play2D("./Bone-Dry-Dunes.mp3", true);

	// Z-BUFFER ACTIVADO - HECHO
	glEnable(GL_DEPTH_TEST); 

	glClearColor(1.0, 1.0, 1.0, 1.0);

	// ACTIVAMOS EL SOMBREADO - HECHO
	glShadeModel(GL_SMOOTH);

	// ACTIVAMOS LA ILUMINACIÓN - HECHO
	glEnable(GL_LIGHTING);	

	// ACTIVAMOS LAS 5 LUCES DISTINTAS DE LA PRÁCTICA 7 - HECHO
	glEnable(GL_LIGHT0);
	glEnable(GL_LIGHT1);
	glEnable(GL_LIGHT2);
	glEnable(GL_LIGHT3);
	glEnable(GL_LIGHT4);
	glEnable(GL_LIGHT5);
	glEnable(GL_LIGHT6);
	glEnable(GL_LIGHT7);

	// LUZ DIRECCIONAL DE LUNA - HECHO
	GLfloat lunaAmbDif[] = { 0.05,0.05,0.05,1.0 };
	GLfloat lunaEspecular[] = { 0.0,0.0,0.0,1.0 };
	glLightfv(GL_LIGHT0, GL_AMBIENT, lunaAmbDif);
	glLightfv(GL_LIGHT0, GL_DIFFUSE, lunaAmbDif);
	glLightfv(GL_LIGHT0, GL_SPECULAR, lunaEspecular);
	glLightModeli(GL_LIGHT_MODEL_TWO_SIDE, GL_TRUE);

	// LUZ FOCAL DEL VEHICULO - HECHO
	GLfloat faroAmbiente[] = { 0.2,0.2,0.2,1.0 };
	GLfloat faroDifusa[] = { 1.0,1.0,1.0,1.0 };
	GLfloat faroEspecular[] = { 0.3,0.3,0.3,1.0 };
	
	glLightfv(GL_LIGHT1, GL_AMBIENT, faroAmbiente);
	glLightfv(GL_LIGHT1, GL_DIFFUSE, faroDifusa);
	glLightfv(GL_LIGHT1, GL_SPECULAR, faroEspecular);
	glLightf(GL_LIGHT1, GL_SPOT_CUTOFF, 25.0);
	glLightf(GL_LIGHT1, GL_SPOT_EXPONENT, 20.0);

	//Farolas
	GLfloat farolaAmbienteEspecular[] = { 0.0,0.0,0.0,1.0 };
	GLfloat farolasDifusa[] = { 0.5,0.5,0.2,1.0 };
	GLfloat posEms[] = { 0.0, -1.0, 0.0, 1.0 }; 
	glLightfv(GL_LIGHT2, GL_AMBIENT, farolaAmbienteEspecular);
	glLightfv(GL_LIGHT2, GL_DIFFUSE, farolasDifusa);
	glLightfv(GL_LIGHT2, GL_SPECULAR, farolaAmbienteEspecular);
	glLightfv(GL_LIGHT2, GL_SPOT_DIRECTION, posEms);
	glLightf(GL_LIGHT2, GL_SPOT_CUTOFF, 45.0);
	glLightf(GL_LIGHT2, GL_SPOT_EXPONENT, 10.0);

	glLightfv(GL_LIGHT3, GL_AMBIENT, farolaAmbienteEspecular);
	glLightfv(GL_LIGHT3, GL_DIFFUSE, farolasDifusa);
	glLightfv(GL_LIGHT3, GL_SPECULAR, farolaAmbienteEspecular);
	glLightfv(GL_LIGHT3, GL_SPOT_DIRECTION, posEms);
	glLightf(GL_LIGHT3, GL_SPOT_CUTOFF, 45.0);
	glLightf(GL_LIGHT3, GL_SPOT_EXPONENT, 10.0);

	glLightfv(GL_LIGHT4, GL_AMBIENT, farolaAmbienteEspecular);
	glLightfv(GL_LIGHT4, GL_DIFFUSE, farolasDifusa);
	glLightfv(GL_LIGHT4, GL_SPECULAR, farolaAmbienteEspecular);
	glLightfv(GL_LIGHT4, GL_SPOT_DIRECTION, posEms);
	glLightf(GL_LIGHT4, GL_SPOT_CUTOFF, 45.0);
	glLightf(GL_LIGHT4, GL_SPOT_EXPONENT, 10.0);

	glLightfv(GL_LIGHT5, GL_AMBIENT, farolaAmbienteEspecular);
	glLightfv(GL_LIGHT5, GL_DIFFUSE, farolasDifusa);
	glLightfv(GL_LIGHT5, GL_SPECULAR, farolaAmbienteEspecular);
	glLightfv(GL_LIGHT5, GL_SPOT_DIRECTION, posEms);
	glLightf(GL_LIGHT5, GL_SPOT_CUTOFF, 45.0);
	glLightf(GL_LIGHT5, GL_SPOT_EXPONENT, 10.0);

	//Pancartas
	GLfloat pancartaAmbienteEspecular[] = { 0.5,0.5,0.5,1.0 };
	GLfloat pancartaDifusa[] = { 1,1,1,1.0 };

	glLightfv(GL_LIGHT6, GL_AMBIENT, pancartaAmbienteEspecular);
	glLightfv(GL_LIGHT6, GL_DIFFUSE, pancartaDifusa);
	glLightfv(GL_LIGHT6, GL_SPECULAR, pancartaAmbienteEspecular);
	glLightfv(GL_LIGHT6, GL_SPOT_DIRECTION, posEms);
	glLightf(GL_LIGHT6, GL_SPOT_CUTOFF, 45.0);
	glLightf(GL_LIGHT6, GL_SPOT_EXPONENT, 10.0);

	glLightfv(GL_LIGHT7, GL_AMBIENT, pancartaAmbienteEspecular);
	glLightfv(GL_LIGHT7, GL_DIFFUSE, pancartaDifusa);
	glLightfv(GL_LIGHT7, GL_SPECULAR, pancartaAmbienteEspecular);
	glLightfv(GL_LIGHT7, GL_SPOT_DIRECTION, posEms);
	glLightf(GL_LIGHT7, GL_SPOT_CUTOFF, 45.0);
	glLightf(GL_LIGHT7, GL_SPOT_EXPONENT, 10.0);

	// ACTIVAMOS LAS TEXTURAS - HECHO
	glEnable(GL_TEXTURE_2D);

	//Carretera
	glGenTextures(1, &texturas[1]);
	glBindTexture(GL_TEXTURE_2D, texturas[1]);
	loadImageFile((char*)"desertroad.png");
	
	//Desierto seco alrededor
	glGenTextures(1, &texturas[2]);
	glBindTexture(GL_TEXTURE_2D, texturas[2]);
	loadImageFile((char*)"desierto.jpg");
	
	//Cielo despejado
	glGenTextures(1, &texturas[3]);
	glBindTexture(GL_TEXTURE_2D, texturas[3]);
	loadImageFile((char*)"cielo.jpg");

	//Dorado
	glGenTextures(1, &texturas[4]);
	glBindTexture(GL_TEXTURE_2D, texturas[4]);
	loadImageFile((char*)"dorado.jpg");

	//Cielo nocturno con aurora boreal
	glGenTextures(1, &texturas[5]);
	glBindTexture(GL_TEXTURE_2D, texturas[5]);
	loadImageFile((char*)"noche.jpg");

	//Pancartas
	glGenTextures(1, &texturas[6]);
	glBindTexture(GL_TEXTURE_2D, texturas[6]);
	loadImageFile((char*)"pancarta1.jpg");

	glGenTextures(1, &texturas[7]);
	glBindTexture(GL_TEXTURE_2D, texturas[7]);
	loadImageFile((char*)"pancarta2.jpg");

	glGenTextures(1, &texturas[8]);
	glBindTexture(GL_TEXTURE_2D, texturas[8]);
	loadImageFile((char*)"pancarta3.jpg");

	glGenTextures(1, &texturas[9]);
	glBindTexture(GL_TEXTURE_2D, texturas[9]);
	loadImageFile((char*)"pancarta4.jpg");

	//Kart
	glGenTextures(1, &texturas[10]);
	glBindTexture(GL_TEXTURE_2D, texturas[10]);
	loadImageFile((char*)"GiroDerecha.png");

	glGenTextures(1, &texturas[11]);
	glBindTexture(GL_TEXTURE_2D, texturas[11]);
	loadImageFile((char*)"GiroIzquierda.png");

	glGenTextures(1, &texturas[12]);
	glBindTexture(GL_TEXTURE_2D, texturas[12]);
	loadImageFile((char*)"Recto.png");

	//Metal
	glGenTextures(1, &texturas[13]);
	glBindTexture(GL_TEXTURE_2D, texturas[13]);
	loadImageFile((char*)"metal.png");
}

void display()
{
	// Z-BUFFER ACTIVADO - HECHO
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	if (M_Alam) {
		glDisable(GL_LIGHTING);
		glDisable(GL_TEXTURE_2D); 
	}
	else {
		
		if (M_Luz) {
			glDisable(GL_LIGHTING);
			glClearColor(1.0, 1.0, 1.0, 1.0);
		}
		else
		{
			glShadeModel(GL_SMOOTH);
			glEnable(GL_LIGHTING);
			glClearColor(0.0, 0.0, 0.0, 0.0);
		}
		glEnable(GL_TEXTURE_2D);
	}
	if (M_Niebla) {
		glEnable(GL_FOG);
		glFogfv(GL_FOG_COLOR, ORO);
		glFogf(GL_FOG_DENSITY, 0.05);
	}
	else {
		glDisable(GL_FOG);
	}
	

	glMatrixMode(GL_MODELVIEW);

	//Dibujo de pancartas
	GLfloat loop2 = 0;
	GLfloat inicio2 = ant2;
	for (int i = 0; i < 4; i++) {
		inicio2 += 75;
		if (i == 0) loop2 = inicio2;
		float xfarola = inicio2;
		float zfarola = func_sin(xfarola);
		GLfloat posicionLuz1[] = { xfarola,10,zfarola+2,1.0 };
		GLfloat posicionLuz2[] = { xfarola,10,zfarola-2,1.0 };
		glPushMatrix();
		switch (i) {
			case 0: glLightfv(GL_LIGHT6, GL_POSITION, posicionLuz1);
					glLightfv(GL_LIGHT7, GL_POSITION, posicionLuz2);
					glBindTexture(GL_TEXTURE_2D, texturas[(0 + ind_pancarta) % 4 + 6]);
					break;
			case 1: glBindTexture(GL_TEXTURE_2D, texturas[(1 + ind_pancarta) % 4 + 6]); break;
			case 2: glBindTexture(GL_TEXTURE_2D, texturas[(2 + ind_pancarta) % 4 + 6]); break;
			case 3: glBindTexture(GL_TEXTURE_2D, texturas[(3 + ind_pancarta) % 4 + 6]); break;
		}

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		if (M_Alam) {
			glPolygonMode(GL_BACK, GL_LINE);
			glPolygonMode(GL_FRONT, GL_LINE);
		}
		else {
			glPolygonMode(GL_BACK, GL_FILL);
			glPolygonMode(GL_FRONT, GL_FILL);
		}
		glTranslatef(xfarola, 1, zfarola);
		glColor3f(1, 0, 1);
		GLfloat g0[3] = { 1 , 1,-4 }, g1[3] = { 1, 1, +4 }, g2[3] = { 1, 6, +4 }, g3[3] = { 1, 6, -4 };
		quadtex(g0, g1, g2, g3, 0, 1, 0, 1, 50, 50);
		glPopMatrix();
		glPushMatrix();
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_LIGHTING);
		glTranslatef(xfarola + 1, 7, zfarola - 4);
		glColor3f(0,0,0);
		glRotatef(90,1,0,0);
		if (M_Alam) glutWireCylinder(0.1, 10, 30, 30);
		else  glutSolidCylinder(0.1, 10, 30, 30);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_LIGHTING);
		glPopMatrix();
		glPushMatrix();
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_LIGHTING);
		glTranslatef(xfarola + 1, 7, zfarola + 4);
		glColor3f(0, 0, 0);
		glRotatef(90, 1, 0, 0);
		if(M_Alam) glutWireCylinder(0.1, 10, 30, 30);
		else 
		{
			glutSolidCylinder(0.1, 10, 30, 30);
			glEnable(GL_TEXTURE_2D);
			glEnable(GL_LIGHTING);
		}  
		glPopMatrix();
	}
	if (x > loop2) {
		ind_pancarta = (ind_pancarta + 1) % 4;
		ant2 = loop2;
	}

	//Dibujo Carretera dinamica 
	float start_Road = x - 10, vfseno = func_sin(start_Road);
	float derivada = der_func_sin(start_Road);
	GLfloat pre[3] = { start_Road,0,vfseno };
	GLfloat tz[3] = { -derivada,0,1 };
	GLfloat normales[3] = { (1 / sqrt(1 + derivada * derivada)) * tz[0] , 0 ,(1 / sqrt(1 + derivada * derivada)) * tz[2] };
	for (int i = 0; i < 3; i++) {
		v0[i] = pre[i] - (normales[i] * anch_Road);
		v3[i] = pre[i] + (normales[i] * anch_Road);
	}
	for (int i = 1; i < 100; i++) {
		float aux = start_Road + i;
		vfseno = func_sin(aux);
		float derivada = der_func_sin(aux);
		GLfloat pre2[3] = { aux,0,vfseno };
		GLfloat tz[3] = { -derivada,0,1 };
		GLfloat normales2[3] = { (1 / sqrt(1 + derivada * derivada)) * tz[0] , 0 ,(1 / sqrt(1 + derivada * derivada)) * tz[2] };
		for (int i = 0; i < 3; i++) {
			v1[i] = pre2[i] - (normales2[i] * anch_Road);
			v2[i] = pre2[i] + (normales2[i] * anch_Road);
		}
		glPushMatrix();

		if (M_Alam) {
			glPolygonMode(GL_BACK, GL_LINE);
			glPolygonMode(GL_FRONT, GL_LINE);
		}
		else {
			glPolygonMode(GL_BACK, GL_FILL);
			glPolygonMode(GL_FRONT, GL_FILL);
		}
		//Aplicamos la textura
		glBindTexture(GL_TEXTURE_2D, texturas[1]);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glColor3f(0, 1, 0);

		//Material para la carretera
		glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, roadDif);
		glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, roadEsp);
		glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 3);

		quadtex(v2, v1, v0, v3, 0, 1, 0, 1, 50, 50);
		glPopMatrix();
		for (int i = 0; i < 3; i++) {
			v0[i] = v1[i];
			v3[i] = v2[i];
		}
	}
	//Fin dibujo carretera

	//Dibujo farolas y coins
	GLfloat inicio = ant;
	GLfloat loop = 0;
	for (int i = 0; i < 4; i++) {
		inicio += dist_Farola;
		if (i == 0) loop = inicio;
		float xfarola = inicio;
		float zfarola = func_sin(xfarola);
		GLfloat posicionFarola[] = { xfarola,4,zfarola,1.0 };

		glLightfv(farolas[i], GL_POSITION, posicionFarola);

		glPushMatrix();
		glBindTexture(GL_TEXTURE_2D, texturas[4]);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glTranslatef(xfarola, 0.3, zfarola);
		if (abs(xfarola - x) < 0.5 && abs(zfarola - z) < 0.5 && !coinTaken) {

			engine->play2D("./mario-coin.mp3");
			coins++;
			coinTaken = true;
		}
		glColor3f(1, 1, 0);
		glRotatef(20 * alfa, 0, 1, 0);
		if (M_Alam) glutWireCylinder(0.1, 0.1, 100, 100);
		else glutSolidCylinder(0.15, 0.05, 100, 100);
		glPopMatrix();
	}

	if (x > loop) {
		ant = loop;
	}

	//Desierto
	glPushMatrix();
	glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, BLANCO);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, BLANCO);
	glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 4);
	glBindTexture(GL_TEXTURE_2D, texturas[2]);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

	glColor3f(1, 1, 0);

	GLfloat p0[3] = { 100 + x, -0.1, -100 - z }, p1[3] = { 100 + x, -0.1, 100 - z }, p2[3] = { -100 - x, -0.1, 100 + z }, p3[3] = { -100 - x, -0.1, -100 - z };
	quadtex(p0, p1, p2, p3, 0, 10, 0, 10, 10 * 10, 5 * 10);
	glPopMatrix();


	glLoadIdentity();

	if (!Vista_Aguila) {
		glEnable(GL_LIGHT1);
		if(M_Car)
		pelota();
		else {
			glPushMatrix();
			glTranslatef(0, -1, 0);
			kart();
			glPopMatrix();
		}
		
		if (!M_Larga) {
			GLfloat faroAmbiente[] = { 0.2,0.2,0.2,1.0 };
			GLfloat faroDifusa[] = { 1.0,1.0,1.0,1.0 };
			GLfloat faroEspecular[] = { 0.3,0.3,0.3,1.0 };

			glLightfv(GL_LIGHT1, GL_AMBIENT, faroAmbiente);
			glLightfv(GL_LIGHT1, GL_DIFFUSE, faroDifusa);
			glLightfv(GL_LIGHT1, GL_SPECULAR, faroEspecular);
			glLightf(GL_LIGHT1, GL_SPOT_CUTOFF, 25.0);
			glLightf(GL_LIGHT1, GL_SPOT_EXPONENT, 20.0);
			GLfloat pos_luz1[] = { 0.0, 0.7, 0.0, 1.0 };
			GLfloat dir_luz1[] = { 0.0, -0.5, -0.7 };
			glLightfv(GL_LIGHT1, GL_POSITION, pos_luz1);
			glLightfv(GL_LIGHT1, GL_SPOT_DIRECTION, dir_luz1);
		}
		else {
			GLfloat faroAmbiente[] = { 0.5,0.5,0.5,1.0 };
			GLfloat faroDifusa[] = { 1.0,1.0,1.0,1.0 };
			GLfloat faroEspecular[] = { 0.3,0.3,0.3,1.0 };

			glLightfv(GL_LIGHT1, GL_AMBIENT, faroAmbiente);
			glLightfv(GL_LIGHT1, GL_DIFFUSE, faroDifusa);
			glLightfv(GL_LIGHT1, GL_SPECULAR, faroEspecular);
			glLightf(GL_LIGHT1, GL_SPOT_CUTOFF, 25.0);
			glLightf(GL_LIGHT1, GL_SPOT_EXPONENT, 5.0);
			GLfloat pos_luz1[] = { 0.0, 0.7, 0.0, 1.0 };
			GLfloat dir_luz1[] = { 0.0, -0.4, -0.7 };
			glLightfv(GL_LIGHT1, GL_POSITION, pos_luz1);
			glLightfv(GL_LIGHT1, GL_SPOT_DIRECTION, dir_luz1);
		}
		if (M_Alam) {
			glDisable(GL_LIGHTING); //desactivamos las luces
			glDisable(GL_TEXTURE_2D); // desactivamos las texturas
		}
	}
	else {
		
		glDisable(GL_LIGHT1);
	}

	if (M_UI) {
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_LIGHTING);

		glPushMatrix();
		glTranslatef(-0.75, -0.75, -2);
		glScalef(0.25, 0.2 * velocidad, 0);
		if (velocidad <= 5) glColor3f(0, 1, 0);
		else if (velocidad <= 10) glColor3f(1, 1, 0);
		else glColor3f(1, 0, 0);

		if (M_Alam) {
			glutWireCube(1);
		}
		else {
			
			glutSolidCube(1);
			
		}
		glPopMatrix();

		glPushMatrix();
		
		glTranslatef(0.55, -0.55, -2);
		glColor3f(0, 0, 1);
		glRotatef(((giro) * 180 / PI) - 90, 0, 0, 1);
		if (M_Alam) {
			glPushMatrix();
			glTranslatef(0.0, 0.15, 0.0);
			glRotatef(-90, 1, 0, 0);
			glScalef(2, 2, 2);
			glutWireCone(0.03, 0.05, 100, 100);
			glPopMatrix();
			glPushMatrix();
			glRotatef(-90, 1, 0, 0);
			glutWireCylinder(0.01, 0.15, 100, 100);
			glPopMatrix();
		}
		else {
			glPushMatrix();
			glTranslatef(0.0, 0.15, 0.0);
			glRotatef(-90, 1, 0, 0);
			glScalef(2, 2, 2);
			glutSolidCone(0.03, 0.05, 100, 100);
			glPopMatrix();
			glPushMatrix();
			glRotatef(-90, 1, 0, 0);
			glutSolidCylinder(0.01, 0.15, 100, 100);
			glPopMatrix();
		}
		glPopMatrix();

		glEnable(GL_TEXTURE_2D);
		glEnable(GL_LIGHTING);
	}

	// VECTOR LOOK DEFINIDO COMO EN LA PRÁCTICA 6 - HECHO
	if (!Vista_Aguila) {
		gluLookAt(x, y, z, 10 * sin(giro) + x, 1, 10 * cos(giro) + z, 0, 1, 0); // Situo la camara
	}
	else {
		gluLookAt(x, 100, z, 10 * sin(giro) + x, 1, 10 * cos(giro) + z, 0, 1, 0); // Situo la camara
		/** Para el screenshot con los ejes
		glPushMatrix();
		glScalef(40.0, 40.0, 40.0);
		ejes();
		glPopMatrix();
		*/
	}

	if (M_Luz) glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
	else glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);

	//Luz de luna
	GLfloat posicionLuna[] = { 0.0, 10.0, 0.0, 0.0 };
	glLightfv(GL_LIGHT0, GL_POSITION, posicionLuna);

	//Cilindro
	glPushMatrix();
	if (M_Luz) {
		glBindTexture(GL_TEXTURE_2D, texturas[3]);
	}
	else {
		glBindTexture(GL_TEXTURE_2D, texturas[5]);
	}
	float alpha = 2 * PI / 50;
	GLfloat cil0[3] = { 200 * cos(0) + x,100,200 * -sin(0) + z };
	GLfloat cil1[3] = { 200 * cos(0) + x,-55,200 * -sin(0) + z };
	GLfloat cil2[3];
	GLfloat cil3[3];
	for (int i = 1; i <= 50; i++) {
		cil2[0] = 200 * cos(i * alpha) + x;
		cil2[1] = 100;
		cil2[2] = 200 * -sin(i * alpha) + z;
		cil3[0] = 200 * cos(i * alpha) + x;
		cil3[1] = -55;
		cil3[2] = 200 * -sin(i * alpha) + z;

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glColor3f(0, 0, 1);
		quadtex(cil3, cil1, cil0, cil2, (i) / 50.0 + 0.5, (i - 1.0) / 50.0 + 0.5, 0, 1);
		for (int j = 0; j < 3; j++) {
			cil0[j] = cil2[j];
			cil1[j] = cil3[j];
		}
	}
	glPopMatrix();

	glutSwapBuffers();
	glFlush();
}

// VARIACIÓN DE VENTANA - HECHO
void reshape(GLint w, GLint h)
{
	// Toda el area como marco
	glViewport(0, 0, w, h);

	// Razon de aspecto
	float ra = float(w) / float(h);

	// Matriz de la proyeccion
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();

	// CÁMARA PERSPECTIVA DEFINIDA COMO EN LA PRÁCTICA 6 - HECHO
	gluPerspective(45, ra, 1, 250);
}

void main(int argc, char** argv)
{
	FreeImage_Initialise();

	glutInit(&argc, argv);

	// Z-BUFFER ACTIVADO - HECHO
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB | GLUT_DEPTH);

	glutInitWindowSize(600, 600);

	glutCreateWindow(PROYECTO);

	init();

	// CALLBACKS
	glutDisplayFunc(display);
	glutReshapeFunc(reshape);

	//Callback de tecla pulsada
	glutKeyboardFunc(onKey);

	//Callback de las flechas (teclas especiales): arriba, abajo, der e izq
	glutSpecialFunc(onSpecialKey); 
	glutTimerFunc(1000/60, onTimer, 1000 / 60);
	glutTimerFunc(1500, updateCoins, 1500);
	glutTimerFunc(1500, updateControl, 1500);

	//Limpiamos la ventana de comandos por la librería de sonido
	system("CLS");

	// INDICAR AL USUARIO LOS CONTROLES POR LA VENTANA DE COMANDOS - HECHO
	cout << 
		"Flecha izq/der: giro del vehiculo " << endl << 
		"Flecha arriba/abajo: aumento/disminucion de la velocidad " << endl << 
		"S/s: activa/desactiva un modelo simple en alambrico sin luces ni texturas " << endl << 
		"L/l: cambia entre modo diurno/nocturno " << endl << 
		"N/n: cambia el estado de la niebla (on/off) " << endl <<
		"C/c: cambia la visibilidad de elementos solidarios a la camara -HUD- (on/off) " << endl <<
		"O/o: cambia los parametros de giro e incremento de velocidad (on/off) " << endl <<
		"W/w: cambia las luces cortas a largas y viceversa " << endl <<
		"E/e: alterna el vehiculo " << endl <<
		"D/d: alterna el modo de dificultad " << endl <<
		"A/a: proporciona una vista de aguila" << endl <<
		"P/p: guarda una captura de pantalla del videojuego " << endl;

	//Event loop
	glutMainLoop();

	//Liberar Freeimage
	FreeImage_DeInitialise();
}