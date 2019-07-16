set xrange [0:10000] 
set yrange [-20:]
#set xtics 200000
#set ytics 100 
set xlabel "Size"
set ylabel "Microseconds"
set key left
set grid

set term pdf colour enhanced solid
set output "selectionSort.pdf"


f(x) = a*x*x+b*x+c

fit f(x) "selection_sort.out" using 1:2 via a,b,c

plot "selection_sort.out" using 1:2 title "Average case" with points, \
		f(x) with lines

pause -1 "Press ENTER to continue ... "

