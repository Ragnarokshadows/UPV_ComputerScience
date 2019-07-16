set xrange [10000:120000] 
set yrange [0:300]
set xlabel "Size"
set ylabel "Miliseconds"
set key left
set grid

f(x) = a*x*(log(b*x)) + c
g(x) = d*x*(log(e*x)) + f
h(x) = g*x*(log(h*x)) + i
fit f(x) "comparation2.out" using 1:2 via a,b,c
fit g(x) "comparation2.out" using 1:3 via d,e,f
fit h(x) "comparation2.out" using 1:4 via g,h,i

plot "comparation2.out" using 1:2 title "MergeSort1" with lines
replot "comparation2.out" using 1:3 title "MergeSort2" with lines
replot "comparation2.out" using 1:4 title "QuickSort" with lines

set term pdf colour enhanced solid
set output "comparation2.pdf"
replot
set term x11
set output 

pause -1 "Press ENTER to continue ... "
