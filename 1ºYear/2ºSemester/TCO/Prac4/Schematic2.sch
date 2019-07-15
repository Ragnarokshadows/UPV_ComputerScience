*version 9.1 267191052
u 26
M? 2
R? 4
V? 3
@libraries
@analysis
.LIB C:\Users\Stephane\Desktop\UPV\TCO\Prac4\Schematic2.lib
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
pageloc 1 0 2110 
@status
n 0 118:04:29:20:15:27;1527617727 e 
s 0 118:04:29:20:15:31;1527617731 e 
*page 1 0 970 720 iA
@ports
port 6 EGND 200 230 h
port 7 EGND 320 180 h
port 8 EGND 410 190 h
@parts
part 4 R 320 130 v
a 0 x 0:13 0 0 0 hln 100 PKGREF=RD
a 0 xp 9 0 15 0 hln 100 REFDES=RD
a 0 sp 0 0 0 10 hlb 100 PART=R
a 0 s 0:13 0 0 0 hln 100 PKGTYPE=RC05
a 0 s 0:13 0 0 0 hln 100 GATE=
part 3 R 200 110 v
a 0 u 13 0 15 25 hln 100 VALUE=6k
a 0 sp 0 0 0 10 hlb 100 PART=R
a 0 s 0:13 0 0 0 hln 100 PKGTYPE=RC05
a 0 s 0:13 0 0 0 hln 100 GATE=
a 0 a 0:13 0 0 0 hln 100 PKGREF=R1
a 0 ap 9 0 15 0 hln 100 REFDES=R1
part 5 R 200 230 v
a 0 x 0:13 0 0 0 hln 100 PKGREF=R2
a 0 xp 9 0 15 0 hln 100 REFDES=R2
a 0 sp 0 0 0 10 hlb 100 PART=R
a 0 s 0:13 0 0 0 hln 100 PKGTYPE=RC05
a 0 s 0:13 0 0 0 hln 100 GATE=
a 0 u 13 0 15 25 hln 100 VALUE=4k
part 9 VDC 410 150 h
a 0 x 0:13 0 0 0 hln 100 PKGREF=VDD
a 1 xp 9 0 24 7 hcn 100 REFDES=VDD
a 0 sp 0 0 22 37 hln 100 PART=VDC
a 1 u 13 0 -11 18 hcn 100 DC=10V
part 2 MbreakN3 290 160 h
a 0 a 0:13 0 0 0 hln 100 PKGREF=M1
a 0 ap 9 0 5 10 hln 100 REFDES=M1
a 0 u 0 0 0 0 hln 100 L=1u
a 0 u 0 0 0 0 hln 100 W=2u
a 0 sp 13 0 -18 40 hln 100 MODEL=MbreakN-X
part 1 titleblk 970 720 h
a 1 s 13 0 350 10 hcn 100 PAGESIZE=A
a 1 s 13 0 180 60 hcn 100 PAGETITLE=
a 1 s 13 0 300 95 hrn 100 PAGENO=1
a 1 s 13 0 340 95 hrn 100 PAGECOUNT=1
@conn
w 12
a 0 up 0:33 0 0 0 hln 100 V=
s 200 70 320 70 11
a 0 up 33 0 260 69 hct 100 V=
s 410 70 410 150 13
s 320 70 410 70 17
s 320 90 320 70 15
w 19
a 0 up 0:33 0 0 0 hln 100 V=
s 290 160 200 160 18
a 0 up 33 0 245 159 hct 100 V=
s 200 160 200 110 20
s 200 190 200 160 22
w 25
a 0 up 0:33 0 0 0 hln 100 V=
s 320 130 320 140 24
a 0 up 33 0 322 135 hlt 100 V=
@junction
j 200 230
+ s 6
+ p 5 1
j 410 190
+ p 9 -
+ s 8
j 200 70
+ p 3 2
+ w 12
j 410 150
+ p 9 +
+ w 12
j 320 90
+ p 4 2
+ w 12
j 320 70
+ w 12
+ w 12
j 200 110
+ p 3 1
+ w 19
j 200 190
+ p 5 2
+ w 19
j 200 160
+ w 19
+ w 19
j 320 130
+ p 4 1
+ w 25
j 320 180
+ s 7
+ p 2 s
j 290 160
+ p 2 g
+ w 19
j 320 140
+ p 2 d
+ w 25
@attributes
a 0 s 0:13 0 0 0 hln 100 PAGETITLE=
a 0 s 0:13 0 0 0 hln 100 PAGENO=1
a 0 s 0:13 0 0 0 hln 100 PAGESIZE=A
a 0 s 0:13 0 0 0 hln 100 PAGECOUNT=1
@graphics
