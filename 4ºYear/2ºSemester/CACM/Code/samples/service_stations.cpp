
#include <cstdio>

#include <vector>

// THIS VERSION IS NOT CORRECT!

class Stations
{
private:
    static constexpr int N = 35;
public:

    int edges[N + 1][N + 1];
    int reached[N + 1][N + 1];
    int degree[N + 1];
    int station[N + 1];
    int n;
    int number;

    Stations(int n) : n(n)
    {
        for (int i=0; i <= n; i++) {
            for (int j=0; j <= n; j++) {
                edges[i][j] = 0;
                reached[i][j] = 0;
            }
            degree[i] = 0;
            station[i] = 0;
        }
    }

    void read_edges(int m)
    {
        while (--m >= 0) {
            int i, j;
            scanf("%d%d", &i, &j);
            //printf("%d %d\n", i, j);
            edges[i][j] = edges[j][i] = 1;
            degree[i]++;
            degree[j]++;
        }
    }

    int minimum()
    {
        number = n;
        backtracking(1);

        return number;
    }

    void backtracking(int k)
    {
        if (k <= number) {

            int count = 0;
            for (int i = 1; i <= n; i++)
                if (reached[k][i] > 0) count++;

            if (count == n) number = (k < number) ? k : number;

            if (k < number) {

                int max_degree = 0;
                for (int i = 1; i <= n; i++) {
                    if (0 == reached[k - 1][i] && degree[i] > max_degree)
                        max_degree = degree[i];
                }

                if (max_degree == 0) {

                    for (int i = 1; i <= n; i++) {
                        if (! reached[k-1][i]) {
                            station[i] = 1;
                            k++;
                        }
                    }
                    backtracking(k + 1);

                } else {

                    for (int i = 1; i <= n; i++) {

                        if (0 == reached[k - 1][i] && degree[i] == max_degree) {
                            station[i] = 1;
                            for (int j = 1; j <= n; j++) {
                                if (edges[i][j]) {
                                    reached[k][j] = reached[k - 1][j] + 1;
                                }
                            }
                            reached[k][i] = reached[k - 1][i] + 1;

                            backtracking(k + 1);

                            for (int j = 1; j <= n; j++) reached[k][j] = 0;
                            station[i] = 0;
                        }
                    }
                }
            }
        }
    }
};


int main(int argc, char * argv[])
{
    int n, m;

    while(scanf("%d%d", &n, &m) == 2) {
        
        if (n == 0 && m == 0) break;

        Stations sts(n);
        sts.read_edges(m);

        printf("%d\n", sts.minimum());
    }

    return EXIT_SUCCESS;
}
