#include <vector>
#include <sstream>
#include <iostream>
#include <algorithm>

using namespace std;

int compare(vector<int> &bn, vector<char> &bs, vector<int> &wn, vector<char> &ws)
{
    sort(bn.begin(), bn.end());
    sort(bs.begin(), bs.end());
    sort(wn.begin(), wn.end());
    sort(ws.begin(), ws.end());

    int bMax = 0;
    int wMax = 0;

    /* Straight Flush */
    if (bn[0] == bn[4] - 4 && bs[0] == bs[4]) {
        bMax = bn[4];
    } 
    if (wn[0] == wn[4] - 4 && ws[0] == ws[4]) {
        wMax = wn[4];
    }
    if (bMax || wMax) {
        return bMax - wMax;
    }

    /* Four of a Kind */
    if (bn[0] == bn[3] || bn[1] == bn[4]) {
        bMax = bn[2];
    }
    if (wn[0] == wn[3] || wn[1] == wn[4]) {
        wMax = wn[2];
    }
    if (bMax || wMax) {
        return bMax - wMax;
    }

    /* Full House */
    if (bn[0] == bn[2] && bn[3] == bn[4]) {
        bMax = bn[0];
    } 
    if (bn[0] == bn[1] && bn[2] == bn[4]) {
        bMax = bn[2];
    }
    if (wn[0] == wn[2] && wn[3] == wn[4]) {
        wMax = wn[0];
    }
    if (wn[0] == wn[1] && wn[2] == wn[4]) {
        wMax = wn[2];
    }
    if (bMax || wMax) {
        return bMax - wMax;
    }

    /* Flush */
    if (bs[0] == bs[4]) {
        bMax = bn[4];
    }
    if (ws[0] == ws[4]) {
        wMax = wn[4];
    }
    if (bMax > wMax) {
        return 1;
    } else if (wMax > bMax)  {
        return -1;
    } else if (bMax && wMax) {
        int i = 4;
        while (bn[i] == wn[i] && i > 0) {
            i--;
        }
        return bn[i] - wn[i];
    }

    /* Straight */
    if (bn[0] == bn[4] - 4) {
        bMax = bn[4];
    }
    if (wn[0] == wn[4] - 4) {
        wMax = wn[4];
    }
    if (bMax || wMax) {
        return bMax - wMax;
    }

    /* Three of a Kind */
    for (int i = 0; i <= 2; i++) {
        if (bn[i] == bn[i + 2]) {
            bMax = max(bMax, bn[i]);
        }
    }

    for (int i = 0; i <= 2; i++) {
        if (wn[i] == wn[i + 2]) {
            wMax = max(wMax, wn[i]);
        }
    }

    if (bMax || wMax) {
        return bMax - wMax;
    }

    /* Two Pairs */
    int bHighestPair = 0;
    int bLowestPair = 0;
    int bRem = 0;
    int wHighestPair = 0;
    int wLowestPair = 0;
    int wRem = 0;

    if (bn[0] == bn[1] && bn[2] == bn[3]) {
        bHighestPair = max(bn[0], bn[2]);
        bLowestPair = min(bn[0], bn[2]);
        bRem = bn[4];
    }
    if (bn[1] == bn[2] && bn[3] == bn[4]) {
        bHighestPair = max(bn[1], bn[3]);
        bLowestPair = min(bn[1], bn[3]);
        bRem = bn[0];
    }
    if (bn[0] == bn[1] && bn[3] == bn[4]) {
        bHighestPair = max(bn[0], bn[3]);
        bLowestPair = min(bn[0], bn[3]);
        bRem = bn[2];
    }
    if (wn[0] == wn[1] && wn[2] == wn[3]) {
        wHighestPair = max(wn[0], wn[2]);
        wLowestPair = min(wn[0], wn[2]);
        wRem = wn[4];
    }
    if (wn[1] == wn[2] && wn[3] == wn[4]) {
        wHighestPair = max(wn[1], wn[3]);
        wLowestPair = min(wn[1], wn[3]);
        wRem = wn[0];
    }
    if (wn[0] == wn[1] && wn[3] == wn[4]) {
        wHighestPair = max(wn[0], wn[3]);
        wLowestPair = min(wn[0], wn[3]);
        wRem = wn[2];
    }
    if (bHighestPair > wHighestPair) {
        return 1;
    } else if (bHighestPair < wHighestPair) {
        return -1;
    } else if (bHighestPair && wHighestPair) {
        if (bLowestPair > wLowestPair) {
            return 1;
        } else if (bLowestPair < wLowestPair) {
            return -1;
        } else {
            return bRem - wRem;
        }
    }

    /* Pair */
    int a = 0;
    int b = 0;
    int c = 0;
    int x = 0;
    int y = 0;
    int z = 0;

    if (bn[0] == bn[1]) {
        bMax = bn[0];
        c = bn[2];
        b = bn[3];
        a = bn[4];
    }
    if (bn[1] == bn[2]) {
        bMax = bn[1];
        c = bn[0];
        b = bn[3];
        a = bn[4];
    }
    if (bn[2] == bn[3]) {
        bMax = bn[2];
        c = bn[0];
        b = bn[1];
        a = bn[4];
    }
    if (bn[3] == bn[4]) {
        bMax = bn[3];
        c = bn[0];
        b = bn[1];
        a = bn[2];
    }
    if (wn[0] == wn[1]) {
        wMax = wn[0];
        z = wn[2];
        y = wn[3];
        x = wn[4];
    }
    if (wn[1] == wn[2]) {
        wMax = wn[1];
        z = wn[0];
        y = wn[3];
        x = wn[4];
    }
    if (wn[2] == wn[3]) {
        wMax = wn[2];
        z = wn[0];
        y = wn[1];
        x = wn[4];
    }
    if (wn[3] == wn[4]) {
        wMax = wn[3];
        z = wn[0];
        y = wn[1];
        x = wn[2];
    }
    if (bMax > wMax) {
        return 1;
    } else if (bMax < wMax) {
        return -1;
    } else if (bMax && wMax) {
        if (a > x) {
            return 1;
        } else if (a < x) {
            return -1;
        } else {
            if (b > y) {
                return 1;
            } else if (b < y) {
                return -1;
            } else {
                return c - z;
            }
        }
    }

    /* High Card */
    int i = 4;
    while (bn[i] == wn[i] && i > 0) {
        i--;
    }
    return bn[i] - wn[i];
}

void push_backPoker(vector<int> &vec, char chr)
{
    if (chr == 'T') {
        vec.push_back(10);
    } else if (chr == 'J') {
        vec.push_back(11);
    } else if (chr == 'Q') {
        vec.push_back(12);
    } else if (chr == 'K') {
        vec.push_back(13);
    } else if (chr == 'A') {
        vec.push_back(14);
    } else {
        vec.push_back(chr - '0');
    }
}

int main()
{
    string hands;
    string tmp;

    while (getline(cin, hands)) {
        vector<int> bn;
        vector<char> bs;
        vector<int> wn;
        vector<char> ws;
        stringstream ss(hands);

        for (int i = 0; i < 5; i++) {
            ss >> tmp;
            push_backPoker(bn, tmp[0]);
            bs.push_back(tmp[1]);
        }

        for (int i = 0; i < 5; i++) {
            ss >> tmp;
            push_backPoker(wn, tmp[0]);
            ws.push_back(tmp[1]);
        }

        int result = compare(bn, bs, wn, ws);

        if (result > 0) {
            cout << "Black wins." << endl;
        } else if (result < 0) {
            cout << "White wins." << endl;
        } else {
            cout << "Tie." << endl;
        }
    }
    return 0;
}
