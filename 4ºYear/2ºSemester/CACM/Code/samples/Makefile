
all: sorting.exe  sorting_stl.exe check_gcd.exe

check_gcd.exe: greatestCommonDivisor.o check_gcd.o
	g++ -Wall -o $@ $+

clean:
	rm -f *.exe *.o *.a *.so


%.o: %.c
	gcc -Wall -c $?

%.exe: %.c
	gcc -Wall -o $@ $?

%.o: %.cpp
	g++ -Wall -c $?

%.exe: %.cpp
	g++ -Wall -o $@ $?
