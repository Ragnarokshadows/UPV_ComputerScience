*version 9.1 894962700
u 33
V? 5
M? 2
R? 2
? 3
C? 2
@libraries
@analysis
.DC 0 0 0 0 1 1
+ 0 0 Vi
+ 0 4 0
+ 0 5 5V
+ 0 6 0.01V
.TRAN 1 0 0 0
+0 1n
+1 120n
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
pageloc 1 0 2610 
@status
n 0 118:04:29:21:03:19;1527620599 e 
s 2832 118:04:29:21:03:24;1527620604 e 
*page 1 0 970 720 iA
@ports
port 7 EGND 240 150 h
port 5 EGND 170 160 h
port 6 EGND 110 170 h
port 32 egnd 190 100 h
@parts
part 3 VDC 240 110 h
a 0 x 0:13 0 0 0 hln 100 PKGREF=VDD
a 1 xp 9 0 24 7 hcn 100 REFDES=VDD
a 0 sp 0 0 22 37 hln 100 PART=VDC
a 1 u 13 0 -11 18 hcn 100 DC=5V
part 4 MbreakN3 140 90 h
a 0 u 0 0 0 0 hln 100 L=1u
a 0 u 0 0 0 0 hln 100 W=2u
a 0 a 0:13 0 0 0 hln 100 PKGREF=M1
a 0 ap 9 0 5 10 hln 100 REFDES=M1
a 0 sp 13 0 -18 40 hln 100 MODEL=MbreakN-X
part 26 VPULSE 110 130 h
a 0 x 0:13 0 0 0 hln 100 PKGREF=Vi
a 1 xp 9 0 20 10 hcn 100 REFDES=Vi
a 1 u 0 0 0 0 hcn 100 V1=0V
a 1 u 0 0 0 0 hcn 100 V2=5V
a 1 u 0 0 0 0 hcn 100 TD=10n
a 1 u 0 0 0 0 hcn 100 TR=2n
a 1 u 0 0 0 0 hcn 100 TF=2n
a 1 u 0 0 0 0 hcn 100 PW=40n
a 1 u 0 0 0 0 hcn 100 PER=100n
part 31 C 190 100 v
a 0 sp 0 0 0 10 hlb 100 PART=C
a 0 s 0:13 0 0 0 hln 100 PKGTYPE=CK05
a 0 s 0:13 0 0 0 hln 100 GATE=
a 0 a 0:13 0 0 0 hln 100 PKGREF=C1
a 0 ap 9 0 15 0 hln 100 REFDES=C1
a 0 u 13 0 15 25 hln 100 VALUE=0.1p
part 8 R 170 70 v
a 0 x 0:13 0 0 0 hln 100 PKGREF=RD
a 0 xp 9 0 15 0 hln 100 REFDES=RD
a 0 sp 0 0 0 10 hlb 100 PART=R
a 0 s 0:13 0 0 0 hln 100 PKGTYPE=RC05
a 0 s 0:13 0 0 0 hln 100 GATE=
a 0 u 13 0 15 25 hln 100 VALUE=90k
part 1 titleblk 970 720 h
a 1 s 13 0 350 10 hcn 100 PAGESIZE=A
a 1 s 13 0 180 60 hcn 100 PAGETITLE=
a 1 s 13 0 300 95 hrn 100 PAGENO=1
a 1 s 13 0 340 95 hrn 100 PAGECOUNT=1
part 27 nodeMarker 120 90 h
a 0 s 0 0 0 0 hln 100 PROBEVAR=
a 0 a 0 0 4 22 hlb 100 LABEL=1
part 29 nodeMarker 180 70 h
a 0 s 0 0 0 0 hln 100 PROBEVAR=Vo
a 0 a 0 0 4 22 hlb 100 LABEL=2
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
s 110 90 120 90 19
s 120 90 140 90 28
w 16
a 0 sr 0 0 0 0 hln 100 LABEL=Vo
a 0 up 0:33 0 0 0 hln 100 V=
s 180 70 190 70 30
a 0 sr 3 0 180 68 hcn 100 LABEL=Vo
s 170 70 180 70 15
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
j 140 90
+ p 4 g
+ w 18
j 110 170
+ p 26 -
+ s 6
j 110 130
+ p 26 +
+ w 18
j 120 90
+ p 27 pin1
+ w 18
j 180 70
+ p 29 pin1
+ w 16
j 190 70
+ p 31 2
+ w 16
j 190 100
+ s 32
+ p 31 1
@attributes
a 0 s 0:13 0 0 0 hln 100 PAGETITLE=
a 0 s 0:13 0 0 0 hln 100 PAGENO=1
a 0 s 0:13 0 0 0 hln 100 PAGESIZE=A
a 0 s 0:13 0 0 0 hln 100 PAGECOUNT=1
@graphics
