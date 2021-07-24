/*
 * Solución al problema de los números de Erdös.
 * Implementación con arrays.
 */


#include <cstdio>
#include <climits>
#include <cassert>
#include <string>
#include <map>

using namespace std;

const string	nombre_de_erdos="Erdos, P.";
const int		infinito=INT_MAX-10;

#define N_P	50000
#define N_A	8000
#define N_A_x_P	130

int articulos[N_P][N_A_x_P];
int contador_erdos[N_A];

char	buffer[2048];

int main( int argc, char *argv[] )
{
	int		num_casos, N, P, ch;

	map<string,int>	autores;

	scanf( "%d", &num_casos );

	for( int caso=0; caso < num_casos; caso++ ) {

		scanf( "%d%d", &P, &N );
		while( (ch=getchar()) != EOF  &&  ch != '\n' );

		assert( P <= N_P );

		autores.clear();

		autores[ nombre_de_erdos ] = 0;
		contador_erdos[0] = 0;
		int num_autores=1;

		for( int p=0; p < P; p++ ) {

			articulos[p][0]=0;
			int num_comas=0, i=0, nap=1;

			bool fin_articulo=false;

			while( !fin_articulo ) {
			
				ch=getchar();

				switch( ch ) {

				case ':' :
					while( (ch=getchar()) != EOF  &&  ch != '\n' );
					fin_articulo=true;
					num_comas=1;

				case ',' :
					if ( num_comas == 1 ) {
						buffer[i] = '\0';
						num_comas=0;
						i=0;

						if ( autores.count(buffer) == 0 )
							autores[buffer] = num_autores++;

						articulos[p][ nap++ ] = autores[buffer];
					} else {
						buffer[i++] = ch;
						num_comas++;
					}
					break;

				case ' ' :
					if ( i > 0 ) buffer[i++] = ch;
					break;

				case '\r' : break;
				case '\n' : break;

				default : buffer[i++] = ch;
				}
			}
			articulos[p][0] = nap-1;
			assert( articulos[p][0] <= N_A_x_P );
		}

		assert( num_autores <= N_A );


		// AQUÍ DEBE EL ALGORITMO QUE CALCULA LA SOLUCIÓN


		printf( "Scenario %d\n", caso+1 );

		for( int n=0; n < N; n++ ) {

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
