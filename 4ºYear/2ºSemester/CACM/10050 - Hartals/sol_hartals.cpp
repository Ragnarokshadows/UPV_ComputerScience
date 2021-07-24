#include <iostream>
#include <vector>

using namespace std;

int main( int argc, char * argv[] )
{
    int n_cases, N, P, n_strikes, hartal, day;
    cin >> n_cases;

    while(n_cases > 0) {
        n_strikes = 0;
        cin >> N >> P;

        vector<bool> strikes(N + 1);

        for (int i = 0; i < P; i++) {
            cin >> hartal;

            for (int j = 1; j <= N; j++) {
                day = j % 7;

                if (day != 6 && day != 0 && j % hartal == 0 && !strikes[j]) {
                    n_strikes++;
                    strikes[j] = true;
                }
            }
        }
        cout << n_strikes << endl;
        n_cases--;
    }
    return 0;
}