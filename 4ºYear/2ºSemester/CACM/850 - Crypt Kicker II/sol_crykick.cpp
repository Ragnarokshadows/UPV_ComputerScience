#include <stdio.h>
#include <string.h>
#include <iostream>
using namespace std;

#define KEY "the quick brown fox jumps over the lazy dog"
#define LEN_KEY 43

int n, len[110], char_ori[126], char_cryp[126];
char line_crypted[110][85], dic_char[126];

int get()
{
    int i = 1;
    
    gets(line_crypted[i]);
    len[i] = strlen(line_crypted[i]);

    while (line_crypted[i][0] != '\0') {
        i++;
        gets(line_crypted[i]);
        len[i] = strlen(line_crypted[i]);
    }
    
    return i - 1;
}

int find()
{
    int t, i;

    for (t = 1; t <= n; t++) {
        if (len[t] == LEN_KEY && line_crypted[t][3] == ' ') {
            for (i = 32; i <= 125; i++) {
                char_ori[i] = char_cryp[i] = 0;
            }

            for (i = 0; i < LEN_KEY; i++) {
                if (!char_cryp[line_crypted[t][i]] && !char_ori[KEY[i]]) {
                    dic_char[line_crypted[t][i]] = KEY[i];
                    char_cryp[line_crypted[t][i]] = char_ori[KEY[i]] = 1;
                } else if (dic_char[line_crypted[t][i]] != KEY[i]) break;
            }

            if (i == LEN_KEY) return t;
        }
    }

    return 0;
}

void custom_print()
{
    int t, i;

    for (t = 1; t <= n; t++) {
        for (i = 0; i < len[t]; i++)
            printf("%c", dic_char[line_crypted[t][i]]);
        printf("\n");
    }
}

int main()
{
    int n_cases, k;

    scanf("%d", &n_cases);
    getchar();
    getchar();

    while (n_cases > 0) {
        n = get();
        k = find();

        if (k == 0) printf("No solution.\n");
        else custom_print();

        if (n_cases != 1) printf("\n");

        n_cases--;
    }

    return 0;
}
