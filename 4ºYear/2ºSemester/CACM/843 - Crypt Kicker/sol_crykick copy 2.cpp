#include <iostream>
#include <string>
#include <vector>
#include <map>
#include <sstream>
using namespace std;

vector<string> dic;
vector<string> encrypted;
map<char,char> replacements;
map<char,char>::iterator it;
bool found;

void decrypt (int n) {
	if (!found) {
		/* Hemos alcanzado el final */
		if (n == encrypted.size()) {
			found = true;
		} else {
			vector<char> used;
			
			for (int i = 0; i < dic.size(); i++) {
				/* Solo tenemos en cuenta las entradas que tienen el mismo tamaño para ir más rápido */
				if (dic[i].size() == encrypted[n].size()) {
					bool check = true;
					for (int j = 0; j < dic[i].size(); j++) {
						if (replacements[encrypted[n][j]] == '*') {
							/* Revisamos que no haya ya un reemplazo con esa letra */
							for(it = replacements.begin(); it != replacements.end() && check; it++) {
								if ((*it).second == dic[i][j]) {
									check = false;
								}
							}
							if (check) {
								used.push_back(encrypted[n][j]);
								replacements[encrypted[n][j]] = dic[i][j];
							}
						} else {
							/* Revisamos que no sea un reemplazo distinto */
							if (replacements[encrypted[n][j]] != dic[i][j]) {
								check = false;
							}
						}
					}
					/* Pasamos al siguiente nivel */
					if (check) { 
						decrypt (n+1);
					}  
					if(!found) {
						/* Limpiamos los reemplazos que hemos utilizado */					
						for (int k = 0; k < used.size(); k++)
							replacements[used[k]] = '*';
						
						used.clear();
					}
				}
			}	
		}
	}
}

int main (void) {
	int len_dic;
	string word, line;
	char a = '*';
	
	cin >> len_dic;
	/* Añdimos las palabras al diccionario */
	while (len_dic--) {
		cin >> word;
		dic.push_back(word);
	}
	
	cin.ignore();
	
	while(getline(cin,line)) {
		found = false;
		replacements.clear();
		encrypted.clear();

		/* Reseteamos el diccionario con los reemplazos */
		for (char i = 'a'; i <= 'z'; i++) replacements.insert(pair<char,char>(i,'*'));
		
		stringstream ss;
		ss << line;
		string e_word;
        
		/* Añdimos las palabras encriptadas al diccionario */
		while (ss >> e_word) {
			encrypted.push_back(e_word);
		}
		
		/* Resolvemos por recursión  */
		decrypt(0);
		
		for (int i = 0; i < line.size(); i++) {
			if (line[i] >= 'a' && line[i] <= 'z')
				cout << replacements[line[i]];
			else
				cout << line[i];
		}
		cout << endl;
	}
	
	return 0;
}