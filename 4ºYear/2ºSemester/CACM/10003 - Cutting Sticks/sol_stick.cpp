#include <iostream>
using namespace std;

int dp[1005][1005]; 

int main()
{
    int cuts, ans, len_stick, i, j, k, l;
    int pos[10005];
    bool finish = false;

    while(!finish)
    {
        scanf("%d",&len_stick);
        

        if(len_stick > 0){
            
            scanf("%d", &cuts);

            for(i=1; i<=cuts; i++) {
                scanf("%d", &pos[i]);
            }

            pos[cuts+1] = len_stick;

            for(i=2; i<=cuts+1; i++) {
                for(j=0; j<=cuts-i+1; j++) {
                    l = j + i;
                    dp[j][l] = 2147483647;

                    for(k=j+1; k<l; k++) {
                        dp[j][l] = min(dp[j][l], dp[j][k] + dp[k][l] + pos[l] - pos[j]);
                    }
                }
            }

            printf("The minimum cutting is %d.\n", dp[0][cuts+1]);
        } else {
            finish = true;
        }
    }

    return 0;
}