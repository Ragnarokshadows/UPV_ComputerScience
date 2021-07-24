#include <string>
#include <map>
#include <iostream>
#include <sstream>
#include<map>
using namespace std; 

map<string, map<int, int>> m;
map<string, map<int, int>>::iterator it;
map<int, int>::iterator it2, it3;
int fee [24];

int getTime( const string& buf ){
	int mon, day, hr, min;
	sscanf( buf.c_str(), "%d:%d:%d:%d", &mon, &day, &hr, &min );
	return day * 24 * 60 + hr * 60 + min;
}

int ABS(int x){ return x < 0 ? -x : x; }

double Price( map<int, int>& n ){
	int ans = 0;
	for( it2 = n.begin(); it2 != n.end(); ++it2 ){
		if( it2->second >= 0 ){
			if( it2 == n.begin() ) continue;
			it3 = it2;
			--it3;
			if( it3->second < 0 ){
				int prev = -it3->second - 1;
				int km = it2->second - prev;
				int pr = fee[(it3->first/60)%24];
				ans += 100 + ABS(km * pr);
			}
		}
	}
	return ans == 0 ? 0.00 : (double)(ans+200)/100.0;
}

int main(int argc, char * argv[])
{
    int n_cases, i, f, n_photo;
    
    string line;

    cin >> n_cases;

    while(n_cases--) {
        for(i = 0; i < 24; i++) {
            cin >> fee[i];
        }
        cin.ignore();
        while(getline(cin, line) && !line.empty()) {
            istringstream in(line);
			in >> line;
			map<int, int>& n = m[line];
			in >> line;
			int tm = getTime(line);
			in >> line;
			int x;
			in >> x;
			n[tm] = ( line[1] == 'n' ? -x - 1 : x );
        }
        for( it = m.begin(); it != m.end(); ++it ){
			double price = Price( it->second );
			if( price == 0.00 ) continue;
			cout << it->first << " $" << Price( it->second ) << endl;
		}
		if(n_cases) cout << endl;
    }

    return 0;
}
