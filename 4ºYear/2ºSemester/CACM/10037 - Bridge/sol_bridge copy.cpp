#include <iostream>
#include <queue>
#include <algorithm>
#include <sstream>

using namespace std;

int main() {
  int k;
  cin >> k;
  while (k--) {
    int n;
    cin >> n;
    deque<int> speeds;
    while (n--) {
      int speed;
      cin >> speed;
      speeds.push_back(speed);
    }
    sort(speeds.begin(), speeds.end());

    stringstream ss;
    int totalTime = 0;
    while (true) {
      int a = speeds[0];
      if (speeds.size() == 1) {
        totalTime += a;
        ss << a << endl;
        break;
      }
      
      int b = speeds[1];
      if (speeds.size() == 2) {
        totalTime += b;
        ss << a << " " << b << endl;
        break;
      }

      if (speeds.size() == 3) {
       totalTime += a + b + speeds[2];
       ss << a << " " << b << endl;
       ss << a << endl;
       ss << a << " " << speeds[2] << endl;
       break;
      } 

      int d = speeds.back();
      speeds.pop_back();
      int c = speeds.back();
      speeds.pop_back();
      if (a + 3*b + d < 2*a + b + c + d) {
        totalTime += b + a + d + b;
        ss << a << " " << b << endl;
        ss << a << endl;
        ss << c << " " << d << endl;
        ss << b << endl;
      } else {
        totalTime += d + a + c + a;
        ss << a << " " << d << endl;
        ss << a << endl;
        ss << a << " " << c << endl;
        ss << a << endl;
      }
    }
    cout << totalTime << endl;
    cout << ss.str();
    if (k) {
      cout << endl;
    }
  }
}
