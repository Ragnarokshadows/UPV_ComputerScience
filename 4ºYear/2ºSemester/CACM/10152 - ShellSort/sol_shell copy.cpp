#include <iostream>
#include <vector>
#include <map>
#include <string>
using namespace std;

int main() {
  int k;
  cin >> k;
  while (k--) {
    int n;
    cin >> n;
    cin.ignore();
    map<string, int> original;
    vector<string> desired;
    for (int i = 0; i < n; i++) {
      string name;
      getline(cin, name);
      original[name] = i;
    }
    for (int i = 0; i < n; i++) {
      string name;
      getline(cin, name);
      desired.push_back(name);
    }

    vector<int> indexes;
    for (auto name : desired) {
      indexes.push_back(original[name]);
    }
    int i;
    for (i = indexes.size() - 1; i >= 1; i--) {
      if (indexes[i] < indexes[i-1]) break;
    }

    i--;
    while (i >= 0) {
      cout << desired[i] << endl;
      i--;
    }
    cout << endl;
    
  }
  return 0;
}
