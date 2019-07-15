*version 9.1 784771968
u 26
V? 4
M? 2
R? 2
@libraries
@analysis
.DC 1 0 0 0 1 1
+ 0 0 Vi
+ 0 4 0
+ 0 5 5V
+ 0 6 0.01V
.LIB C:\Users\Stephane\Desktop\UPV\TCO\Prac5\Schematic1.lib
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
pageloc 1 0 1860 
@status
n 0 118:04:29:20:51:39;1527619899 e 
s 0 118:04:29:20:53:12;1527619992 e 
c 118:04:29:20:55:52;1527620152
*page 1 0 970 720 iA
@ports
port 6 EGND 110 170 h
port 7 EGND 240 150 h
port 5 EGND 170 160 h
@parts
part 2 VDC 110 130 h
a 0 x 0:13 0 0 0 hln 100 PKGREF=Vi
a 1 xp 9 0 24 7 hcn 100 REFDES=Vi
a 0 sp 0 0 22 37 hln 100 PART=VDC
part 3 VDC 240 110 h
a 0 x 0:13 0 0 0 hln 100 PKGREF=VDD
a 1 xp 9 0 24 7 hcn 100 REFDES=VDD
a 0 sp 0 0 22 37 hln 100 PART=VDC
a 1 u 13 0 -11 18 hcn 100 DC=5V
part 4 MbreakN3 140 90 h
a 0 a 0:13 0 0 0 hln 100 PKGREF=M1
a 0 ap 9 0 5 10 hln 100 REFDES=M1
a 0 sp 13 0 -18 40 hln 100 MODEL=MbreakN-X
a 0 u 0 0 0 0 hln 100 L=1u
a 0 u 0 0 0 0 hln 100 W=2u
part 8 R 170 70 v
a 0 x 0:13 0 0 0 hln 100 PKGREF=RD
a 0 xp 9 0 15 0 hln 100 REFDES=RD
a 0 sp 0 0 0 10 hlb 100 PART=R
a 0 s 0:13 0 0 0 hln 100 PKGTYPE=RC05
a 0 s 0:13 0 0 0 hln 100 GATE=
a 0 u 13 0 15 25 hln 100 VALUE=200k
part 1 titleblk 970 720 h
a 1 s 13 0 350 10 hcn 100 PAGESIZE=A
a 1 s 13 0 180 60 hcn 100 PAGETITLE=
a 1 s 13 0 300 95 hrn 100 PAGENO=1
a 1 s 13 0 340 95 hrn 100 PAGECOUNT=1
@conn
w 10
a 0 up 0:33 0 0 0 hln 100 V=
s 170 30 240 30 9
s 240 30 240 110 11
a 0 up 33 0 242 70 hlt 100 V=
w 14
a 0 up 0:33 0 0 0 hln 100 V=
s 170 160 170 110 13
a 0 up 33 0 172 135 hlt 100 V=
w 18
a 0 up 0:33 0 0 0 hln 100 V=
s 110 130 110 90 17
a 0 up 33 0 112 110 hlt 100 V=
s 110 90 140 90 19
w 16
a 0 sr 0 0 0 0 hln 100 LABEL=Vo
a 0 up 0:33 0 0 0 hln 100 V=
s 170 70 190 70 15
a 0 sr 3 0 180 68 hcn 100 LABEL=Vo
a 0 up 33 0 180 69 hct 100 V=
@junction
j 240 150
+ s 7
+ p 3 -
j 170 70
+ p 8 1
+ p 4 d
j 170 30
+ p 8 2
+ w 10
j 240 110
+ p 3 +
+ w 10
j 170 110
+ p 4 s
+ w 14
j 170 160
+ s 5
+ w 14
j 170 70
+ p 8 1
+ w 16
j 170 70
+ p 4 d
+ w 16
j 110 170
+ s 6
+ p 2 -
j 110 130
+ p 2 +
+ w 18
j 140 90
+ p 4 g
+ w 18
@attributes
a 0 s 0:13 0 0 0 hln 100 PAGETITLE=
a 0 s 0:13 0 0 0 hln 100 PAGENO=1
a 0 s 0:13 0 0 0 hln 100 PAGESIZE=A
a 0 s 0:13 0 0 0 hln 100 PAGECOUNT=1
@graphics
