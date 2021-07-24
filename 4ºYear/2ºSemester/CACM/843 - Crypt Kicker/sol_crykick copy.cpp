#include <iostream>
#include <map>
#include <vector>
#include <string>
using namespace std; 

#include <string.h>

static map<int, vector<string>> dic;
static map<char, char> relation;
static vector<string> encrypted;

int read_line(char *s) 
{
    int ch, count=0;

    while((ch=getchar()) != '\n' && ch != EOF) { 
        *s++ = ch; 
        ++count; 
    }

    *s = '\0';

    return count;
}

void solve(string res, int ind) 
{
    string word = encrypted.at(ind);
    vector<string> aux = dic[word.length()];
    map<char, char> aux2 = relation;
    string aux3 = res;

    for(vector<string>::iterator it = aux.begin(); it != aux.end(); ++it) {
        res = res + *it;
        solve(res, 1);
    }
}

int main(int argc, char * argv[])
{
    int len_dic, i;
    string word;
    string line;
    string solution = "";
    char *ptr, temp[80];

    cin >> len_dic;
    getline(cin, word);

    for(i=0; i < len_dic; i++) {
        getline(cin, word);
        dic[word.length()].push_back(word);
    }
    while(read_line(temp) > 0) {
        for(i=0, ptr = strtok(temp, " "); ptr != NULL; ptr = strtok(NULL, " "), i++) {
            encrypted.push_back(ptr);
        }
        solve(solution, 0);
    }
    
    return 0;
}
