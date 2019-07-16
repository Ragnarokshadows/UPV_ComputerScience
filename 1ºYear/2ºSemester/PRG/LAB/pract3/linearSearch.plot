set xrange [0:110000] 
set yrange [-20:]
#set xtics 200000
#set ytics 100 
set xlabel "Size"
set ylabel "Microseconds"
set key left
set grid

set term pdf colour enhanced solid
set output "linearSearch.pdf"

f(x) = a*x+b
g(x) = c*x+d
fit f(x) "linearSearch.out" using 1:2 via a,b
fit g(x) "linearSearch.out" using 1:4 via c,d

plot "linearSearch.out" using 1:3 title "Best case" with points, \
     "linearSearch.out" using 1:2 title "Worst case" with points, \
     "linearSearch.out" using 1:4 title "Average case" with points, \
     f(x) with lines, g(x) with lines


pause -1 "Press ENTER to continue ... "
