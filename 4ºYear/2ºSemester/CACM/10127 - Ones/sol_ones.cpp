#include <iostream>
#include <cstdio>
#include <sstream>
#include <string>
#include <algorithm>
using namespace std;


bool isLastOne(int n)
{
    return ((n%10) == 1);
}


int main() {
    string line;
    int num, aux, digits = 0;
    while(getline(cin, line)) 
    {
        stringstream sinput(line);
        sinput >> num;
        aux = num;

        while (aux > 0)
        {
            if (isLastOne(aux))
            {
                digits++;
                aux = aux/10;
            } else {
                aux += num;
            }
        }
        printf("%d\n", digits);
        digits = 0;
    }
    return 0;
}




