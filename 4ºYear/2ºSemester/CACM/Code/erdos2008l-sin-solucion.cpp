/*
 * Solución al problema de los números de Erdös.
 * Implementación con listas en base al template vector.
 *
 * Se trabaja con una lista de artículos (lista de objetos de la clase paper),
 * y dentro de cada artículo figura la lista de autores.
 */

#include <cstdio>
#include <climits>
#include <cassert>
#include <string>
#include <map>
#include <vector>

using namespace std;

const string	nombre_de_erdos="Erdos, P.";
const int		infinito=INT_MAX-10;

class paper
{
public:
	vector<int>		autores;
	unsigned int	contador;
	
	paper()
	{
		clear();
	}

	int num_autores()
	{
		return contador;
	}
	void clear()
	{
		contador=0;
	}

	void nuevo_autor( int autor )
	{
		if ( contador < autores.size() ) {
			autores[contador] = autor;
		} else {
			autores.push_back( autor );
		}
		contador++;
	}

	int num_autor( int pos )
	{
		return autores[pos];
	}
};

vector<paper*>	articulos;
vector<int>		contador_erdos;

int main( int argc, char *argv[] )
{
	unsigned int	N, P;
	int				num_casos, ch;
	char			buffer[2048];

	map<string,int>	autores;

	scanf( "%d", &num_casos );

	contador_erdos.push_back(0);

	for( int caso=0; caso < num_casos; caso++ ) {

		scanf( "%u%u", &P, &N );
		while( (ch=getchar()) != EOF  &&  ch != '\n' );

		autores.clear();

		autores[ nombre_de_erdos ] = 0;
		contador_erdos[0] = 0;
		unsigned int num_autores=1;

		for( unsigned int p=0; p < P; p++ ) {

			paper *articulo;
			if ( p < articulos.size() ) {
				articulo = articulos[p];	
			} else {
				articulo = new paper();
				articulos.push_back( articulo );
			}
			articulo->clear();

			int num_comas=0, i=0;

			bool fin_articulo=false;

			while( !fin_articulo  &&  (ch=getchar()) != EOF ) {

				switch( ch ) {

				case ',' :
					if ( num_comas == 1 ) {
						buffer[i] = '\0';
						if ( autores.count(buffer) == 0 ) autores[buffer] = num_autores++;
						articulo->nuevo_autor( autores[buffer] );
						num_comas=0;
						i=0;
					} else {
						buffer[i++] = ch;
						num_comas++;
					}
					break;

				case ':' :
					buffer[i] = '\0';
					if ( autores.count(buffer) == 0 ) autores[buffer] = num_autores++;
					articulo->nuevo_autor( autores[buffer] );
					num_comas=0;
					i=0;
					while( (ch=getchar()) != EOF  &&  ch != '\n' );
					fin_articulo=true;
					break;

				case ' ' :
					if ( i > 0 ) buffer[i++] = ch;
					break;

				case '\r' : break;

				default : buffer[i++] = ch;

				}
			}
		}

		for( unsigned int a=0; a < num_autores; a++ ) {
			if ( a < contador_erdos.size() ) {
				contador_erdos[a]=infinito;
			} else {
				contador_erdos.push_back( infinito );
			}
		}
		contador_erdos[ autores[nombre_de_erdos] ] = 0;

		// AQUÍ DEBE IR EL ALGORITMO QUE OBTIENE LA SOLUCIÓN

		printf( "Scenario %d\n", caso+1 );

		for( unsigned int n=0; n < N; n++ ) {

			int i=0;
			while( (ch=getchar()) != EOF && ch != '\n' )
				if ( ch != '\r' ) buffer[i++] = ch;

			buffer[i] = '\0';

			if ( autores.count(buffer) == 0 ) {
				printf( "%s infinity\n", buffer );
			} else {
				int valor=contador_erdos[autores[buffer]];
				if ( valor == infinito ) {
					printf( "%s infinity\n", buffer );
				} else {
					printf( "%s %d\n", buffer, valor );
				}
			}
		}
	}
	
	return 0;
}
