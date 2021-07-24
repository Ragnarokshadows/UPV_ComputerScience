#include <stdio.h>
#include <string.h>

pair<string, string> splitByFirstColon(string str) {
  for (int i = 0; i < str.size(); i++) {
    if (str[i] == ':') {
      return pair<string, string>(str.substr(0, i), str.substr(i + 2 , str.size()));
    }
  }
}

vector<string> splitByEvenComma(string str) {
  vector<string> result;
  int count = 0;
  int index = 0;
  for (int i = 0; i < str.size(); i++) {
    if (str[i] == ',') {
      count++;
      if (count % 2 == 0) {
        result.push_back(str.substr(index, i - index));
        index = i + 2;
      }
    }
  }
  result.push_back(str.substr(index, str.size()));
  return result;
}

int main() {
  int k;
  cin >> k;
  cin.ignore();
  for (int i = 1; i <= k; i++) {
    int p, n;
    cin >> p >> n;
    cin.ignore();
    string paper;
    map<string, vector<string> > adja;
    map<string, bool> visited;
    map<string, int> enumber;
    while(p-- && getline(cin, paper)) {
      pair<string, string> parts = splitByFirstColon(paper);
      vector<string> names = splitByEvenComma(parts.first);
      for (auto orig : names) {
        for (auto dest : names) {
          if (orig != dest) {
            if (adja.find(orig) != adja.end()) {
              adja[orig].push_back(dest);
            } else {
              adja[orig] = vector<string>();
              adja[orig].push_back(dest);
            }
          }
        }
      }
    }

    queue<string> bfs;
    bfs.push("Erdos, P.");
    int toBeVisited = 1;
    int dist = 0;
    while(!bfs.empty()) {
      int nextToBeVisited = 0;
      while(toBeVisited--) {
        string curr = bfs.front();
        bfs.pop();
        if (!visited[curr]) { 
          visited[curr] = true;
          enumber[curr] = dist;
          nextToBeVisited += adja[curr].size();
          for (auto str : adja[curr]) {
            bfs.push(str);
          }
        }
      }
      toBeVisited = nextToBeVisited;
      dist++;
    }

    string name;
    cout << "Scenario" << " " << i << endl;
    while(n-- && getline(cin, name)) {
      if (enumber[name] == 0) {
        cout << name << " " << "infinity" << endl;
      } else {
        cout << name << " " << enumber[name] << endl;
      }
    }
  }
  return 0;
}