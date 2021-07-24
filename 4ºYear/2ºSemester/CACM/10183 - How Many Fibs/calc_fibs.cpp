#include <iostream>
#include <fstream>
#include <cstdio>
#include <sstream>
#include <vector>
#include <cmath>

using namespace std;

std::string intToString(int s)
{
 std::string toReturn = "";
 while (s > 0)
 {
 char v = s % 10 + 48;
 toReturn = v + toReturn;
 s = (s - (s % 10)) / 10;
 }
 return toReturn;
}
int stringToInt(std::string s)
{
 int v = 0;
 for (int n = 0; n < s.length(); n++)
 {
 if (s[n] >= 48 && s[n] < 58) v = (v * 10) + s[n] - 48;
 else break;
 }
 return v;
}
std::string add(std::string a, std::string b)
{
 std::vector<char> aList;
 int n;
 for (n = 0; n < b.length(); n++) aList.push_back(b[n] - 48);
 int offset = b.length() - a.length();
 for (n = 0; n < a.length(); n++) aList[n + offset] += a[n] - 48;
 for (n = aList.size() - 1; n > 0; n--)
 {
 aList[n - 1] += (aList[n] - aList[n] % 10) / 10;
 aList[n] = aList[n] % 10;
 }
 std::string toReturn = intToString(aList[n]);
 for (n = 1; n < aList.size(); n++) toReturn += 48 + aList[n];
 return toReturn;
}
int main(int argc, char* argv[])
{

 std::cout << "Going to calculate all fibonacci under 100 digits." << std::endl;

 std::string a = "1", b = "1", c;
 std::ofstream file;
 file.open("fibs.txt");
 file << "1";
 while (c.size() <= 100)
 {
 file << std::endl << b;
 c = add(a,b);
 a = b;
 b = c;
 }

 std::cout << "Calculated values and written to file";
 file.close();

 return 0;

}

