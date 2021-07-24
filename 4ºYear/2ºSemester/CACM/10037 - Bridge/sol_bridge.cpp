#include <iostream>
#include <queue>
#include <algorithm>
#include <sstream>

using namespace std;

template<typename ForwardIterator> void counting_sort(ForwardIterator begin, ForwardIterator end) {
    auto min_max = std::minmax_element(begin, end);
    if (min_max.first == min_max.second) {  
        return;
    }
    auto min = *min_max.first;
    auto max = *min_max.second;
    std::vector<unsigned> count((max - min) + 1, 0u);
    for (auto i = begin; i != end; ++i) {
        ++count[*i - min];
    }
    for (auto i = min; i <= max; ++i) {
        for (auto j = 0; j < count[i - min]; ++j) {
        *begin++ = i;
        }
    }
}

int main() {
    int cases, n, i, speed, t_total, found = 0, first, second, length, last, penultime;
    stringstream ss;
    cin >> cases;

    while(cases--) {
        cin >> n;

        deque<int> speeds;
        for(i = 0; i < n; i++) {
            cin >> speed;
            speeds.push_back(speed);
        }
        
        counting_sort(speeds.begin(), speeds.end());

        ss.str(string());
        t_total = 0;
        found = 0;

        while (!found) {
            first = speeds[0];
            second = speeds[1];
            length = speeds.size();

            /* Caso trivial: solo hay una persona */
            if (length == 1) {
                t_total += first;
                ss << first << endl;
                found = 1;
            }
            /* Caso trivial: solo hay dos personas */
            else if (length == 2) {
                t_total += second;
                ss << first << " " << second << endl;
                found = 1;
            }
            /* Caso trivial: solo hay tres personas */
            else if (length == 3) {
                t_total += second + first + speeds[2];
                ss << first << " " << second << endl;
                ss << first << endl;
                ss << first << " " << speeds[2] << endl;
                found = 1;
            } 
            /* Caso general: solo hay tres personas */
            else {
                last = speeds.back();
                speeds.pop_back();
                penultime = speeds.back();
                speeds.pop_back();

                if (first + 3 * second + last < 2 * first + second + penultime + last) {
                    t_total += second + first + last + second;
                    ss << first << " " << second << endl;
                    ss << first << endl;
                    ss << penultime << " " << last << endl;
                    ss << second << endl;
                } else {
                    t_total += last + first + penultime + first;
                    ss << first << " " << last << endl;
                    ss << first << endl;
                    ss << first << " " << penultime << endl;
                    ss << first << endl;
                }
            }
        }
        cout << t_total << endl;
        cout << ss.str();

        if (cases) {
            cout << endl;
        }
    }

    return 0;
}
