*version 9.1 901798489
u 80
M? 7
V? 2
@libraries
@analysis
.TRAN 1 0 0 0
+0 0ns
+1 1u
.LIB C:\Users\Stephane\Desktop\UPV\TCO\Prac7\Schematic2.lib
.STMLIB Schematic2.stl
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
pageloc 1 0 4059 
@status
n 0 118:04:29:22:22:39;1527625359 e 
s 2832 118:04:29:22:22:44;1527625364 e 
*page 1 0 970 720 iA
@ports
port 3 IF_IN 240 50 H
a 1 xr 3 0 19 8 hcn 100 LABEL=C
port 4 IF_IN 240 100 H
a 1 xr 3 0 19 8 hcn 100 LABEL=B
port 2 IF_IN 120 110 h
a 1 xr 3 0 19 8 hcn 100 LABEL=A
port 15 EGND 350 90 h
port 6 IF_IN 110 250 h
a 1 xr 3 0 19 8 hcn 100 LABEL=B
a 0 s 0:13 0 0 0 hln 100 STIMULUS=B
port 7 IF_IN 280 250 h
a 1 xr 3 0 19 8 hcn 100 LABEL=C
a 0 s 0:13 0 0 0 hln 100 STIMULUS=C
port 5 IF_IN 160 180 h
a 1 xr 3 0 19 8 hcn 100 LABEL=A
a 0 s 0:13 0 0 0 hln 100 STIMULUS=A
port 16 EGND 170 310 h
port 8 IF_OUT 210 160 h
a 1 xr 3 0 31 8 hcn 100 LABEL=F
@parts
part 17 MbreakN 110 250 h
a 0 x 0:13 0 0 0 hln 100 PKGREF=M2
a 0 xp 9 0 5 10 hln 100 REFDES=M2
a 0 sp 13 0 -12 40 hln 100 MODEL=MbreakN-X
a 0 u 0 0 0 0 hln 100 L=1u
a 0 u 0 0 0 0 hln 100 W=1u
part 9 MbreakP 120 110 h
a 0 sp 13 0 -12 40 hln 100 MODEL=MbreakP-X
a 0 u 0 0 0 0 hln 100 L=1u
a 0 u 0 0 0 0 hln 100 W=1u
a 0 x 0:13 0 0 0 hln 100 PKGREF=M4
a 0 xp 9 0 5 10 hln 100 REFDES=M4
part 10 MbreakP 240 50 H
a 0 sp 13 0 -12 40 hln 100 MODEL=MbreakP-X
a 0 u 0 0 0 0 hln 100 L=1u
a 0 u 0 0 0 0 hln 100 W=1u
a 0 x 0:13 0 0 0 hln 100 PKGREF=M5
a 0 xp 9 0 5 10 hln 100 REFDES=M5
part 11 MbreakP 240 100 H
a 0 sp 13 0 -12 40 hln 100 MODEL=MbreakP-X
a 0 u 0 0 0 0 hln 100 L=1u
a 0 u 0 0 0 0 hln 100 W=1u
a 0 x 0:13 0 0 0 hln 100 PKGREF=M6
a 0 xp 9 0 5 10 hln 100 REFDES=M6
part 18 MbreakN 280 250 h
a 0 x 0:13 0 0 0 hln 100 PKGREF=M3
a 0 xp 9 0 5 10 hln 100 REFDES=M3
a 0 sp 13 0 -12 40 hln 100 MODEL=MbreakN-X
a 0 u 0 0 0 0 hln 100 L=1u
a 0 u 0 0 0 0 hln 100 W=1u
part 19 MbreakN 160 180 h
a 0 x 0:13 0 0 0 hln 100 PKGREF=M1
a 0 xp 9 0 5 10 hln 100 REFDES=M1
a 0 sp 13 0 -12 40 hln 100 MODEL=MbreakN-X
a 0 u 0 0 0 0 hln 100 L=1u
a 0 u 0 0 0 0 hln 100 W=1u
part 14 VDC 350 50 h
a 1 u 13 0 -11 18 hcn 100 DC=5V
a 0 sp 0 0 22 37 hln 100 PART=VDC
a 0 x 0:13 0 0 0 hln 100 PKGREF=VDD
a 1 xp 9 0 24 7 hcn 100 REFDES=VDD
part 1 titleblk 970 720 h
a 1 s 13 0 350 10 hcn 100 PAGESIZE=A
a 1 s 13 0 180 60 hcn 100 PAGETITLE=
a 1 s 13 0 300 95 hrn 100 PAGENO=1
a 1 s 13 0 340 95 hrn 100 PAGECOUNT=1
@conn
w 21
a 0 up 0:33 0 0 0 hln 100 V=
s 140 230 190 230 20
s 190 230 190 200 22
s 190 230 310 230 24
a 0 up 33 0 250 229 hct 100 V=
w 27
a 0 up 0:33 0 0 0 hln 100 V=
s 320 270 310 270 26
s 310 270 170 270 28
s 170 270 170 290 30
s 140 270 150 270 32
s 150 270 170 270 34
s 170 290 170 310 37
s 170 290 330 290 35
a 0 up 33 0 250 289 hct 100 V=
s 330 290 330 200 38
s 330 200 200 200 40
w 52
a 0 up 0:33 0 0 0 hln 100 V=
s 200 120 200 70 51
s 200 70 200 20 56
s 200 20 210 20 58
s 350 20 350 50 60
s 210 20 350 20 64
a 0 up 33 0 280 19 hct 100 V=
s 210 30 210 20 62
s 200 20 170 20 65
s 150 20 150 90 67
s 170 20 150 20 71
s 170 20 170 130 69
s 170 130 160 130 72
w 75
a 0 up 0:33 0 0 0 hln 100 V=
s 150 130 150 160 74
s 190 160 200 160 42
s 200 160 210 160 46
s 200 160 200 140 44
s 200 140 210 140 47
s 210 140 210 120 49
s 150 160 190 160 76
a 0 up 33 0 170 159 hct 100 V=
w 79
a 0 up 0:33 0 0 0 hln 100 V=
s 210 80 210 70 78
a 0 up 33 0 212 75 hlt 100 V=
@junction
j 120 110
+ p 9 g
+ s 2
j 240 50
+ p 10 g
+ s 3
j 240 100
+ p 11 g
+ s 4
j 350 90
+ s 15
+ p 14 -
j 110 250
+ p 17 g
+ s 6
j 280 250
+ p 18 g
+ s 7
j 140 230
+ p 17 d
+ w 21
j 310 230
+ p 18 d
+ w 21
j 190 230
+ w 21
+ w 21
j 310 270
+ p 18 s
+ w 27
j 320 270
+ p 18 b
+ w 27
j 170 310
+ s 16
+ w 27
j 140 270
+ p 17 s
+ w 27
j 150 270
+ p 17 b
+ w 27
j 170 270
+ w 27
+ w 27
j 170 290
+ w 27
+ w 27
j 200 160
+ w 75
+ w 75
j 200 70
+ p 10 b
+ w 52
j 200 120
+ p 11 b
+ w 52
j 350 50
+ p 14 +
+ w 52
j 210 30
+ p 10 d
+ w 52
j 210 20
+ w 52
+ w 52
j 200 20
+ w 52
+ w 52
j 150 90
+ p 9 d
+ w 52
j 170 20
+ w 52
+ w 52
j 160 130
+ p 9 b
+ w 52
j 150 130
+ p 9 s
+ w 75
j 210 160
+ s 8
+ w 75
j 210 120
+ p 11 s
+ w 75
j 210 70
+ p 10 s
+ w 79
j 210 80
+ p 11 d
+ w 79
j 160 180
+ p 19 g
+ s 5
j 190 200
+ p 19 s
+ w 21
j 200 200
+ p 19 b
+ w 27
j 190 160
+ p 19 d
+ w 75
@attributes
a 0 s 0:13 0 0 0 hln 100 PAGETITLE=
a 0 s 0:13 0 0 0 hln 100 PAGENO=1
a 0 s 0:13 0 0 0 hln 100 PAGESIZE=A
a 0 s 0:13 0 0 0 hln 100 PAGECOUNT=1
@graphics
