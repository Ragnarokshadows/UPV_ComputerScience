*version 9.1 598435174
u 77
M? 7
V? 4
@libraries
@analysis
.LIB C:\Users\Stephane\Desktop\UPV\TCO\Prac7\Schematic1.lib
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
pageloc 1 0 3014 
@status
n 0 118:04:29:22:03:52;1527624232 e 
s 2832 118:04:29:22:03:57;1527624237 e 
*page 1 0 970 720 iA
@ports
port 9 EGND 340 140 h
port 11 EGND 130 290 h
port 12 EGND 100 180 h
port 10 EGND 230 270 h
@parts
part 6 VDC 340 100 h
a 1 u 13 0 -11 18 hcn 100 DC=5V
a 0 sp 0 0 22 37 hln 100 PART=VDC
a 0 x 0:13 0 0 0 hln 100 PKGREF=VDD
a 1 xp 9 0 24 7 hcn 100 REFDES=VDD
part 7 VDC 100 140 h
a 0 x 0:13 0 0 0 hln 100 PKGREF=VA
a 1 xp 9 0 24 7 hcn 100 REFDES=VA
a 0 sp 0 0 22 37 hln 100 PART=VDC
part 8 VDC 130 250 h
a 0 x 0:13 0 0 0 hln 100 PKGREF=VB
a 1 xp 9 0 24 7 hcn 100 REFDES=VB
a 0 sp 0 0 22 37 hln 100 PART=VDC
part 76 MbreakP 160 60 h
a 0 sp 13 0 -12 40 hln 100 MODEL=MbreakP-X
a 0 a 0:13 0 0 0 hln 100 PKGREF=M6
a 0 ap 9 0 5 10 hln 100 REFDES=M6
a 0 u 0 0 0 0 hln 100 L=1u
a 0 u 0 0 0 0 hln 100 W=1u
part 5 MbreakP 280 60 H
a 0 a 0:13 0 0 0 hln 100 PKGREF=M4
a 0 ap 9 0 5 10 hln 100 REFDES=M4
a 0 sp 13 0 -12 40 hln 100 MODEL=MbreakP-X
a 0 u 0 0 0 0 hln 100 L=1u
a 0 u 0 0 0 0 hln 100 W=1u
part 2 MbreakN 200 140 h
a 0 a 0:13 0 0 0 hln 100 PKGREF=M1
a 0 ap 9 0 5 10 hln 100 REFDES=M1
a 0 sp 13 0 -12 40 hln 100 MODEL=MbreakN-X
a 0 u 0 0 0 0 hln 100 L=1u
a 0 u 0 0 0 0 hln 100 W=1u
part 75 MbreakN 200 230 h
a 0 sp 13 0 -12 40 hln 100 MODEL=MbreakN-X
a 0 a 0:13 0 0 0 hln 100 PKGREF=M5
a 0 ap 9 0 5 10 hln 100 REFDES=M5
a 0 u 0 0 0 0 hln 100 L=1u
a 0 u 0 0 0 0 hln 100 W=1u
part 1 titleblk 970 720 h
a 1 s 13 0 350 10 hcn 100 PAGESIZE=A
a 1 s 13 0 180 60 hcn 100 PAGETITLE=
a 1 s 13 0 300 95 hrn 100 PAGENO=1
a 1 s 13 0 340 95 hrn 100 PAGECOUNT=1
@conn
w 57
s 230 270 230 250 56
s 240 160 240 250 54
s 230 250 240 250 58
w 61
s 230 210 230 160 60
w 63
s 130 250 130 230 62
s 130 230 170 230 64
s 170 230 200 230 68
s 170 230 170 300 69
s 170 300 280 300 71
s 280 300 280 60 73
w 23
s 190 40 190 20 22
s 190 20 200 20 24
s 350 20 350 100 26
s 350 100 340 100 28
s 250 20 350 20 32
s 250 20 250 40 30
s 240 20 250 20 35
s 240 80 240 20 33
s 200 20 240 20 38
s 200 80 200 20 36
w 42
s 190 80 190 90 41
s 190 90 220 90 43
s 250 90 250 80 45
s 220 90 250 90 49
s 220 90 220 120 47
s 220 120 230 120 50
w 14
s 100 140 100 130 13
s 100 60 160 60 15
s 100 130 100 60 19
s 100 130 200 130 17
s 200 130 200 140 20
@junction
j 340 140
+ s 9
+ p 6 -
j 100 130
+ w 14
+ w 14
j 200 140
+ p 2 g
+ w 14
j 340 100
+ p 6 +
+ w 23
j 250 20
+ w 23
+ w 23
j 240 20
+ w 23
+ w 23
j 200 20
+ w 23
+ w 23
j 220 90
+ w 42
+ w 42
j 230 120
+ p 2 d
+ w 42
j 230 270
+ s 10
+ w 57
j 240 160
+ p 2 b
+ w 57
j 230 160
+ p 2 s
+ w 61
j 170 230
+ w 63
+ w 63
j 100 180
+ s 12
+ p 7 -
j 100 140
+ p 7 +
+ w 14
j 130 290
+ s 11
+ p 8 -
j 130 250
+ p 8 +
+ w 63
j 230 250
+ p 75 s
+ w 57
j 240 250
+ p 75 b
+ w 57
j 230 210
+ p 75 d
+ w 61
j 200 230
+ p 75 g
+ w 63
j 250 40
+ p 5 d
+ w 23
j 240 80
+ p 5 b
+ w 23
j 250 80
+ p 5 s
+ w 42
j 280 60
+ p 5 g
+ w 63
j 190 40
+ p 76 d
+ w 23
j 200 80
+ p 76 b
+ w 23
j 190 80
+ p 76 s
+ w 42
j 160 60
+ p 76 g
+ w 14
@attributes
a 0 s 0:13 0 0 0 hln 100 PAGETITLE=
a 0 s 0:13 0 0 0 hln 100 PAGENO=1
a 0 s 0:13 0 0 0 hln 100 PAGESIZE=A
a 0 s 0:13 0 0 0 hln 100 PAGECOUNT=1
@graphics
