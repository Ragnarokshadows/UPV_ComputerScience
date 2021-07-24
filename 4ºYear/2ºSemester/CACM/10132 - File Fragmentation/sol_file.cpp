#include <stdio.h>
#include <string.h>
#include <iostream>
#include <algorithm>
using namespace std;

string s[1000];
string a, b;
int length, n;
char ss[1000];

bool cmp(string a, string b)
{
    return a.size() < b.size();
}

bool judge()
{
    for (int i = n - 1; i >= 0; --i)
    {
        if ((s[i].size() + s[1].size()) != length)
            continue;

        b = s[1] + s[i];

        if (b == a) return true;

        b = s[i] + s[1];

        if (b == a) return true;
    }
    return false;
}

int main( int argc, char * argv[] )
{
    int n_cases;
    int MIN, MAX;

    scanf("%d\n", &n_cases);

    while (n_cases--)
    {
        n = 0;
        while (gets(ss) && ss[0])
        {
            s[n] = ss;
            n++;
        }

        sort(s, s + n, cmp);

        MIN = s[0].size();
        MAX = s[n - 1].size();
        length = MIN + MAX;
        
        for (int i = n - 1; i >= 0 && s[i].size() == MAX; --i)
        {
            a = s[0] + s[i];
            if (judge())
                break;
            a = s[i] + s[0];
            if (judge())
                break;
        }

        cout << a << endl;
        
        if (n_cases) {
            cout << endl;
        }
    }
    return 0;
}