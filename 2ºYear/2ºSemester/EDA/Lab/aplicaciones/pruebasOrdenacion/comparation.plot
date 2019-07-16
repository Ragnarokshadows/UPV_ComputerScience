set xrange [10000:120000] 
set yrange [0:60]
set xlabel "Size"
set ylabel "Miliseconds"
set key left
set grid

f(x) = a*x*(log(b*x)) + c
g(x) = d*x*(log(e*x)) + f
h(x) = g*x*(log(h*x)) + i
fit f(x) "comparation.out" using 1:2 via a,b,c
fit g(x) "comparation.out" using 1:3 via d,e,f
fit h(x) "comparation.out" using 1:4 via g,h,i

plot "comparation.out" using 1:2 title "MergeSort1" with points, \
 	 "comparation.out" using 1:3 title "MergeSort2" with points, \
	 "comparation.out" using 1:4 title "QuickSort" with points

replot f(x) with lines

replot g(x) with lines

replot h(x) with lines


set term pdf colour enhanced solid
set output "comparation.pdf"
replot
set term x11
set output 

pause -1 "Press ENTER to continue ... "
