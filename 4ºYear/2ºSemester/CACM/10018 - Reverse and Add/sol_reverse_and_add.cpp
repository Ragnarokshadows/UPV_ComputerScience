#include <iostream>
#include <cstdio>
#include <sstream>
#include <string>
#include <algorithm>
using namespace std;


bool isPalindrome(string n)
{
    bool sol = true;
    int i, j;
    for (i=0,j=n.size()-1; i<=j && sol; i++, j--) if(n[i]!=n[j]) sol=false;
    return sol;
}

string doSum(string a, string b)
{
    if(a.size() < b.size())
        swap(a, b);

    int j = a.size()-1;
    for(int i=b.size()-1; i>=0; i--, j--)
        a[j]+=(b[i]-'0');

    for(int i=a.size()-1; i>0; i--)
    {
        if(a[i] > '9')
        {
            int d = a[i]-'0';
            a[i-1] = ((a[i-1]-'0') + d/10) + '0';
            a[i] = (d%10)+'0';
        }
    }
    if(a[0] > '9')
    {
        string k;
        k+=a[0];
        a[0] = ((a[0]-'0')%10)+'0';
        k[0] = ((k[0]-'0')/10)+'0';
        a = k+a;
    }
    return a;
}


int main() {
   int cases;
    cin >> cases;
    cin.ignore();
    while(cases--)
    {
        int swaps = 0;
        string number;
        getline(cin, number);

        string reversa, rem, n = number.c_str();

        reversa = n;
        reverse(reversa.begin(), reversa.end());
        n = doSum(n,reversa);
        swaps++;

        while (!isPalindrome(n))
        {
            reversa = n;
            reverse(reversa.begin(), reversa.end());
            n = doSum(n,reversa);
            swaps++;
        } 
        printf("%d ",swaps);
        printf("%s\n",n.c_str());


    }

    return 0;
}




