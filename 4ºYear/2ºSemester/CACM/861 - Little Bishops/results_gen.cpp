#include <iostream>
#include <vector>
using namespace std;

int ans;
int n,k;

void dfs(int row,int col,int count,vector<bool>& diag1,vector<bool>& diag2){
    if(count == k) {
        ans++;
        return;
    } else if(row == n) {
        return;
    } else if(col == n)
        return dfs(row+1,0,count,diag1,diag2);

    if(!diag1[row-col+(n-1)] && !diag2[row+col]){
        diag1[row-col+(n-1)] = diag2[row+col] = true;
        dfs(row,col+1,count+1,diag1,diag2);
        diag1[row-col+(n-1)] = diag2[row+col] = false;
    }

    dfs(row,col+1,count,diag1,diag2);
}

int main()
{
    int top;
    cout << "{" << endl;
    for(n = 1; n <= 8; n++) {
        top = n*n;
        cout << "{";
        for(k = 0; k <= top; k++) {
            ans = 0;

            vector<bool> diag1(n);
            vector<bool> diag2(2*n);

            if(k <= 2*n) dfs(0,0,0,diag1,diag2);
            
            if(k == top && n == 8) cout << ans << "}" << endl;
            else if(k == top) cout << ans << "}," << endl;
            else cout << ans << ", ";            
        }
    }
    cout << "}";

    return 0;
}
