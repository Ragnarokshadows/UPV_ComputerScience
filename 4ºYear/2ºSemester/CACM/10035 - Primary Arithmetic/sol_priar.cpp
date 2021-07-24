#include <string>
#include <iostream>
using namespace std; 

int main( int argc, char * argv[] )
{
    int finish = 0, n_case = 1, i, carries = 0, aux;
    int n, m;
    int maxlength, minlength;
    int len1, len2;
    int carry [10];

    while(!finish) {
        cin >> n;
        cin >> m;

        if(n || m) {
            len1 = to_string(n).length();
            len2 = to_string(m).length();
            carries = 0;

            if(len1 >= len2) {
                maxlength = len1;
                minlength = len2;
            } else {
                maxlength = len2;
                minlength = len1;
            }
            
            for(i = 0; i < maxlength; i++) carry[i] = 0;
            
            for(i = 0; i < minlength; i++) {
                if(i == 0) {
                    if(n%10 + m%10 >= 10){
                        carries++;
                        carry[0] = 1;
                    }
                }
                else if(n%10 + m%10 + carry[i-1] >= 10) {
                    carries++;
                    carry[i] = 1;
                }
                n = n/10;
                m = m/10;
            }
            if(minlength != maxlength) {
                aux = minlength;
                if(len2 == maxlength) {
                    while(aux != maxlength) {
                        if(m%10 + carry[aux - 1] >= 10) {
                            carry[aux] = 1;
                            carries++;
                        }
                        m = m/10;
                        aux++;
                    }
                }
                if(len1 == maxlength) {
                    while(aux != maxlength) {
                        if(n%10 + carry[aux - 1] >= 10) {
                            carry[aux] = 1;
                            carries++;
                        }
                        n = n/10;
                        aux++;
                    }
                }
                
            }
            if(carries == 0) {
                printf("No carry operation.");
            }
            else if(carries == 1) {
                printf("%d carry operation.", carries);
            }
            else{
                printf("%d carry operations.", carries);
            }
            if(n_case > 0) {
                printf("\n");
            }
        }
        else {
            finish = 1;
        } 
        ++n_case;
    }

    return 0;
}
