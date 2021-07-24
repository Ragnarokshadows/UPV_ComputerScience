#include <stdio.h>
#include <stdlib.h>

int main()
{
    int n_cases, count, pc;

    scanf("%d ", &n_cases);

    while (n_cases--)
    {
        int reg[10] = {};
        int mem[1000] = {};
        char cmd[1000][5] = {};

        count = 0;
        pc = 0;

        while (gets(cmd[pc]))
        {
            if (cmd[pc][0] == '\0')
                break;
            mem[pc] = atoi(cmd[pc]);
            pc++;
        }
        pc = 0;
        while (1)
        {
            int d = mem[pc] % 100 / 10;
            int n = mem[pc] % 10;
            int f = mem[pc] / 100;
            pc++;
            count++;
            
            if (f == 1)
                break;

            switch (f) {
            case 2:
                reg[d] = n;
                break;
            case 3:
                reg[d] += n;
                reg[d] %= 1000;
                break;
            case 4:
                reg[d] *= n;
                reg[d] %= 1000;
                break;
            case 5:
                reg[d] = reg[n];
                break;
            case 6:
                reg[d] += reg[n];
                reg[d] %= 1000;
                break;
            case 7:
                reg[d] *= reg[n];
                reg[d] %= 1000;
                break;
            case 8:
                reg[d] = mem[reg[n]];
                break;
            case 9:
                mem[reg[n]] = reg[d];
                break;
            case 0:
                if (reg[n] != 0)
                    pc = reg[d];
                break;
            }
        }

        printf("%d\n", count);

        if (n_cases)
            printf("\n");
    }
    return 0;
}
