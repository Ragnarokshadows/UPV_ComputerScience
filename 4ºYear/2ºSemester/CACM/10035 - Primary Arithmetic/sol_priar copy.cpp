#include <string>
#include <iostream>
using namespace std; 

int main( int argc, char * argv[] )
{
    int finish = 0, n_case = 1, i, carries, aux;
    string s1, s2;
    int maxlength, minlength;
    int len1, len2;
    int carry [10];

    while(!finish) {
        cin >> s1;
        cin >> s2;
        s1 = string(s1.rbegin(),s1.rend()); 
        s2 = string(s2.rbegin(),s2.rend()); 

        if(!((s1.compare("0") == 0) && (s1.compare("0") == 0))) {
            len1 = s1.length();
            len2 = s2.length(); 
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
                int n = (int)(s1[i] - '0') + (int)(s2[i] - '0') >= 10;
                //printf("%d \n",n);
                if(i == 0 && n) {
                    carries++;
                    carry[0] = 1;
                }
                else if((int)(s1[i] - '0') + (int)(s2[i] - '0') + carry[i-1] >= 10) {
                    carries++;
                    carry[i] = 1;
                }
            }
            if(minlength != maxlength) {
                aux = minlength;
                if(len2 == maxlength) {
                    while(aux != maxlength) {
                        if((int)(s2[aux] - '0') + carry[aux - 1] >= 10) {
                            carry[aux] = 1;
                            carries++;
                        }
                        aux++;
                    }
                }
                if(len1 == maxlength) {
                    while(aux != maxlength) {
                        if((int)(s1[aux] - '0') + carry[aux - 1] >= 10) {
                            carry[aux] = 1;
                            carries++;
                        }
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
