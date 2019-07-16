set xrange [0:10000] 
set yrange [0:]
#set xtics 200000
#set ytics 100 
set xlabel "Size"
set ylabel "Microseconds"
set key left
set grid

set term pdf colour enhanced solid
set output "insertionSort.pdf"

f(x) = a*x+b
g(x) = c*x*x+d*x+e
h(x) = f*x*x+g*x+h
fit f(x) "insertion_sort.out" using 1:3 via a,b
fit g(x) "insertion_sort.out" using 1:2 via c,d,e
fit h(x) "insertion_sort.out" using 1:4 via f,g,h

plot "insertion_sort.out" using 1:3 title "Best case" with points, \
     "insertion_sort.out" using 1:2 title "Worst case" with points, \
     "insertion_sort.out" using 1:4 title "Average case" with points, \
     f(x) with lines, g(x) with lines, h(x) with lines


pause -1 "Press ENTER to continue ... "
