/*
 * Solución al problema de los números de Erdös.
 * Implementación con grafos.
 *
 * La classe autor es el nodo, los arcos el vector de coautores.
 */


#include <cstdio>
#include <climits>
#include <cassert>
#include <string>
#include <map>
#include <vector>
#include <queue>

using namespace std;

const string	nombre_de_erdos="Erdos, P.";
const int		infinito= 1 << 28;

class autor
{
public:
	vector<int>		coautores;
	unsigned int	contador;
	unsigned int	numero_erdos;

	autor()
	{
		clear();
	}

	void clear()
	{
		contador = 0;
		numero_erdos = infinito;
	}

	void set_numero_erdos( int valor )
	{
		numero_erdos = valor;
	}
	int get_numero_erdos()
	{
		return numero_erdos;
	}

	int num_coautores()
	{
		return contador;
	}

	void nuevo_coautor( int autor )
	{
		if ( contador < coautores.size() ) {
			coautores[contador] = autor;
		} else {
			coautores.push_back( autor );
		}
		contador++;
	}

	int num_coautor( int pos )
	{
		return coautores[pos];
	}
};

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

vector<autor*>	lista_autores;

int main( int argc, char *argv[] )
{
	unsigned int	N, P;
	int				num_casos, ch;
	char			buffer[2048];

	map<string,int>	autores;

	scanf( "%d", &num_casos );

	lista_autores.push_back( new autor() );

	for( int caso=0; caso < num_casos; caso++ ) {

		scanf( "%u%u", &P, &N );
		while( (ch=getchar()) != EOF  &&  ch != '\n' );

		autores.clear();

		autores[ nombre_de_erdos ] = 0;
		lista_autores[0]->clear();
		unsigned int num_autores=1;

		paper	articulo;

		for( unsigned int p=0; p < P; p++ ) {

			articulo.clear();

			int num_comas=0, i=0;

			bool fin_articulo=false;

			while( !fin_articulo  &&  (ch=getchar()) != EOF ) {

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

						if ( autores.count(buffer) == 0 ) {
							autor *a;
							if ( num_autores < lista_autores.size() ) {
								a = lista_autores[num_autores];
								a->clear();
							} else {
								a = new autor();
								lista_autores.push_back( a );
							}
							autores[buffer] = num_autores++;
						}
						int an = autores[buffer];
						for( int k=0; k < articulo.num_autores(); k++ ) {
							int ak = articulo.num_autor(k);
							lista_autores[an]->nuevo_coautor(ak);
							lista_autores[ak]->nuevo_coautor(an);
						}
						articulo.nuevo_autor( autores[buffer] );
					} else {
						buffer[i++] = ch;
						num_comas++;
					}
					break;

				case ' ' :
					if ( i > 0 ) buffer[i++] = ch;
					break;

				case '\r' : break;

				default : buffer[i++] = ch;

				}
			}
		}

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
				int valor=lista_autores[autores[buffer]]->get_numero_erdos();
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
