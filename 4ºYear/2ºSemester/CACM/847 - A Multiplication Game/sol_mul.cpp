#include <iostream>
using namespace std;

int main()
{
    unsigned long n, p;
    bool Stan;

    while (scanf("%lu", &n) != EOF) {

        Stan = true;
        p = 1;

        while (p < n) {
            if (Stan) p *= 9;
            else p *= 2;

            Stan = !Stan;
        }

        if (!Stan) cout << "Stan wins." << endl;
        else cout << "Ollie wins." << endl;
    }

    return 0;
}
