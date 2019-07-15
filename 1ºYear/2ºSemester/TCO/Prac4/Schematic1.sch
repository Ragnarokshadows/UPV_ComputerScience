*version 9.1 2547178149
u 21
V? 5
M? 3
@libraries
@analysis
.DC 1 0 0 0 0 0
+ 0 0 VGS
+ 0 4 0V
+ 0 5 5V
+ 0 6 0.1V
+ 1 0 VGS
+ 1 4 0V
+ 1 5 5V
+ 1 6 1V
.LIB C:\Users\Stephane\Desktop\UPV\TCO\Prac4\Schematic1.lib
@targets
@attributes
@translators
a 0 u 13 0 0 0 hln 100 PCBOARDS=PCB
a 0 u 13 0 0 0 hln 100 PSPICE=PSPICE
a 0 u 13 0 0 0 hln 100 XILINX=XILINX
@setup
unconnectedPins 0
connectViaLabel 0
connectViaLocalLabels 0
NoStim4ExtIFPortsWarnings 1
AutoGenStim4ExtIFPorts 1
@index
pageloc 1 0 1368 
@status
c 118:04:29:20:05:15;1527617115
n 0 118:04:29:20:05:20;1527617120 e 
s 2832 118:04:29:20:05:24;1527617124 e 
*page 1 0 970 720 iA
@ports
port 6 egnd 230 200 h
port 7 egnd 370 180 h
port 8 egnd 300 180 h
@parts
part 2 vdc 230 160 h
a 0 sp 0 0 22 37 hln 100 PART=vdc
a 0 x 0:13 0 0 0 hln 100 PKGREF=VGS
a 1 xp 9 0 24 7 hcn 100 REFDES=VGS
part 5 vdc 370 140 h
a 1 u 13 0 -11 18 hcn 100 DC=10V
a 0 sp 0 0 22 37 hln 100 PART=vdc
a 0 x 0:13 0 0 0 hln 100 PKGREF=VDS
a 1 xp 9 0 24 7 hcn 100 REFDES=VDS
part 9 MbreakN3 270 140 h
a 0 a 0:13 0 0 0 hln 100 PKGREF=M1
a 0 ap 9 0 5 10 hln 100 REFDES=M1
a 0 sp 13 0 -18 40 hln 100 MODEL=MbreakN-X
a 0 u 0 0 0 0 hln 100 L=1u
a 0 u 0 0 0 0 hln 100 W=2u
part 1 titleblk 970 720 h
a 1 s 13 0 350 10 hcn 100 PAGESIZE=A
a 1 s 13 0 180 60 hcn 100 PAGETITLE=
a 1 s 13 0 340 95 hrn 100 PAGECOUNT=1
a 1 s 13 0 300 95 hrn 100 PAGENO=1
@conn
w 12
a 0 up 0:33 0 0 0 hln 100 V=
s 230 160 270 160 11
a 0 up 33 0 250 159 hct 100 V=
s 270 160 270 140 13
w 16
a 0 up 0:33 0 0 0 hln 100 V=
s 300 120 370 120 15
a 0 up 33 0 335 119 hct 100 V=
s 370 120 370 140 17
w 20
s 300 160 300 180 19
@junction
j 230 200
+ s 6
+ p 2 -
j 370 180
+ s 7
+ p 5 -
j 230 160
+ p 2 +
+ w 12
j 370 140
+ p 5 +
+ w 16
j 300 180
+ s 8
+ w 20
j 270 140
+ p 9 g
+ w 12
j 300 120
+ p 9 d
+ w 16
j 300 160
+ p 9 s
+ w 20
@attributes
a 0 s 0:13 0 0 0 hln 100 PAGETITLE=
a 0 s 0:13 0 0 0 hln 100 PAGENO=1
a 0 s 0:13 0 0 0 hln 100 PAGESIZE=A
a 0 s 0:13 0 0 0 hln 100 PAGECOUNT=1
@graphics
