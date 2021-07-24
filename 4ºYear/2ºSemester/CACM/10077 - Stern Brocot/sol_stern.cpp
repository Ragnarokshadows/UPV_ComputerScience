#include <iostream>
#include <cstdio>
#include <sstream>
#include <string>
#include <algorithm>
using namespace std;


class fraccion
{
public:
    unsigned int numerador;
    unsigned int denominador;

    fraccion(int n, int m)
	{
		numerador = n;
        denominador = m;
	}
    void cambiarA(fraccion f)
	{
		numerador = f.numerador;
        denominador = f.denominador;
	}
    fraccion sumarA(fraccion b)
	{
		return fraccion(numerador + b.numerador, denominador + b.denominador);
	}
    int compare(fraccion f)
    {
        int t1 = numerador * f.denominador;
        int t2 = denominador * f.numerador;

        if(t1==t2) return 0;
        else if (t1>t2) return 1;
        else return -1;
    }

};

void process(int n, int m)
{
    fraccion NM = fraccion(n,m);
    fraccion L = fraccion(0,1);
    fraccion R = fraccion(1,0);
    fraccion C = fraccion(1,1);
    fraccion aux = fraccion(0,0);
    while(NM.compare(C)!=0)
    {
        if (NM.compare(C)>0)
        {
            printf("R");
            L.cambiarA(C);
            C.cambiarA( C.sumarA(R));
        }else{
            printf("L");
            R.cambiarA(C);
            C.cambiarA(C.sumarA(L));
        }
    }
    printf("\n");
}

int main() {
    string line, line2;
    int n,m;
    while(getline(cin, line) && getline(cin, line2)) 
    {
        stringstream sinput(line);
        sinput >> n;
        sinput >> m;
        process(n,m);

        stringstream sinput2(line2);
        sinput2 >> n;
        sinput2 >> m;
        process(n,m);
        
    }
    return 0;
}




