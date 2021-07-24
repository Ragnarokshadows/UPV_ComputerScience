#include <iostream>
#include <vector>

using namespace std;

int main()
{
    int n_cases, n, m, i, j, k;
    char c;
    string word;
    bool found;

    int len;
    int right;
    int left;
    int down;
    int up;
    int leftUp;
    int leftDown;
    int rightDown;
    int rightUp;

    cin >> n_cases;

    while (n_cases--)
    {

        cin >> n >> m;
        vector<vector<char>> matrix(n + 55, vector<char>(m + 55));

        /* Leemos todo el tablero de letras */
        for (i = 1; i <= n; i++)
        {
            for (j = 1; j <= m; j++)
            {
                cin >> c;
                matrix[i][j] = tolower(c);
            }
        }

        cin >> k;

        while (k--)
        {

            cin >> word;

            for (i = 0; i < word.size(); i++)
            {
                word[i] = tolower(word[i]);
            }

            found = false;

            for (i = 1; i <= n && !found; i++)
            {
                for (j = 1; j <= m && !found; j++)
                {
                    if (matrix[i][j] == word[0])
                    {
                        len = word.size();
                        right = 1;
                        left = 1;
                        down = 1;
                        up = 1;
                        leftUp = 1;
                        leftDown = 1;
                        rightDown = 1;
                        rightUp = 1;

                        for (int k = 1; k < len; k++)
                        {
                            if (i - k >= 0 && matrix[i - k][j + k] == word[k])
                            {
                                rightUp++;
                            }
                            if (j - k >= 0 && matrix[i + k][j - k] == word[k])
                            {
                                leftDown++;
                            }
                            if (matrix[i][j + k] == word[k])
                            {
                                right++;
                            }
                            if (j - k >= 0 && matrix[i][j - k] == word[k])
                            {
                                left++;
                            }
                            if (i - k >= 0 && matrix[i - k][j] == word[k])
                            {
                                up++;
                            }
                            if (matrix[i + k][j] == word[k])
                            {
                                down++;
                            }
                            if (matrix[i + k][j + k] == word[k])
                            {
                                rightDown++;
                            }
                            if (i - k >= 0 && j - k >= 0 && matrix[i - k][j - k] == word[k])
                            {
                                leftUp++;
                            }
                        }
                        if (rightUp == len || leftDown == len || up == len || leftUp == len || left == len || right == len || down == len || rightDown == len)
                        {
                            found = true;
                            cout << i << " " << j << endl;
                        }
                    }
                }
            }
        }
        if (n_cases)
        {
            cout << endl;
        }
    }
    return 0;
}
