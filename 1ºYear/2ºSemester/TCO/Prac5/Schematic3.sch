*version 9.1 3517628829
u 39
M? 3
R? 2
V? 4
@libraries
@analysis
.TRAN 1 0 0 0
+0 1n
+1 100n
.LIB C:\Users\Stephane\Desktop\UPV\TCO\Prac5\Schematic3.lib
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
pageloc 1 0 2750 
@status
n 0 118:04:29:21:20:35;1527621635 e 
s 2832 118:04:29:21:20:35;1527621635 e 
*page 1 0 970 720 iA
@ports
port 7 EGND 280 160 h
port 8 EGND 190 120 h
port 10 egnd 290 60 h
port 12 egnd 100 140 h
port 11 egnd 200 200 h
@parts
part 9 vdc 290 20 h
a 0 x 0:13 0 0 0 hln 100 PKGREF=VDD
a 1 xp 9 0 24 7 hcn 100 REFDES=VDD
a 0 sp 0 0 22 37 hln 100 PART=vdc
a 1 u 13 0 -11 18 hcn 100 DC=5V
part 3 MbreakN3 250 140 h
a 0 a 0:13 0 0 0 hln 100 PKGREF=M2
a 0 ap 9 0 5 10 hln 100 REFDES=M2
a 0 sp 13 0 -18 40 hln 100 MODEL=MbreakN-X1
part 2 MbreakN3 160 100 h
a 0 a 0:13 0 0 0 hln 100 PKGREF=M1
a 0 ap 9 0 5 10 hln 100 REFDES=M1
a 0 sp 13 0 -18 40 hln 100 MODEL=MbreakN-X
a 0 u 0 0 0 0 hln 100 L=1u
a 0 u 0 0 0 0 hln 100 W=2u
part 5 VPULSE 100 100 h
a 0 x 0:13 0 0 0 hln 100 PKGREF=Va
a 1 xp 9 0 20 10 hcn 100 REFDES=Va
a 1 u 0 0 0 0 hcn 100 V1=0V
a 1 u 0 0 0 0 hcn 100 V2=5V
a 1 u 0 0 0 0 hcn 100 TD=10n
a 1 u 0 0 0 0 hcn 100 TF=2n
a 1 u 0 0 0 0 hcn 100 TR=2n
a 1 u 0 0 0 0 hcn 100 PW=40n
a 1 u 0 0 0 0 hcn 100 PER=100n
part 6 VPULSE 200 160 h
a 0 x 0:13 0 0 0 hln 100 PKGREF=Vb
a 1 xp 9 0 20 10 hcn 100 REFDES=Vb
a 1 u 0 0 0 0 hcn 100 V1=0V
a 1 u 0 0 0 0 hcn 100 V2=5V
a 1 u 0 0 0 0 hcn 100 TR=2n
a 1 u 0 0 0 0 hcn 100 TF=2n
a 1 u 0 0 0 0 hcn 100 PW=40n
a 1 u 0 0 0 0 hcn 100 PER=100n
a 1 u 0 0 0 0 hcn 100 TD=30n
part 4 R 210 70 v
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
@conn
w 18
a 0 up 0:33 0 0 0 hln 100 V=
s 210 30 210 10 17
s 210 10 290 10 19
a 0 up 33 0 250 9 hct 100 V=
s 290 10 290 20 21
w 33
a 0 up 0:33 0 0 0 hln 100 V=
s 260 140 250 140 32
s 250 140 200 140 34
a 0 up 33 0 225 139 hct 100 V=
s 200 140 200 160 35
w 38
a 0 up 0:33 0 0 0 hln 100 V=
s 100 100 160 100 37
a 0 up 33 0 130 99 hct 100 V=
w 24
a 0 up 0:33 0 0 0 hln 100 V=
a 0 sr 0 0 0 0 hln 100 LABEL=Vo
s 280 80 330 80 31
a 0 sr 3 0 305 78 hcn 100 LABEL=Vo
s 210 70 210 80 23
s 210 80 190 80 25
s 210 80 280 80 27
a 0 up 33 0 245 79 hct 100 V=
s 280 120 280 80 29
@junction
j 290 60
+ s 10
+ p 9 -
j 100 140
+ s 12
+ p 5 -
j 200 200
+ p 6 -
+ s 11
j 290 20
+ p 9 +
+ w 18
j 210 80
+ w 24
+ w 24
j 280 80
+ w 24
+ w 24
j 200 160
+ p 6 +
+ w 33
j 100 100
+ p 5 +
+ w 38
j 280 160
+ s 7
+ p 3 s
j 280 120
+ p 3 d
+ w 24
j 250 140
+ p 3 g
+ w 33
j 190 120
+ s 8
+ p 2 s
j 190 80
+ p 2 d
+ w 24
j 160 100
+ p 2 g
+ w 38
j 210 30
+ p 4 2
+ w 18
j 210 70
+ p 4 1
+ w 24
@attributes
a 0 s 0:13 0 0 0 hln 100 PAGETITLE=
a 0 s 0:13 0 0 0 hln 100 PAGENO=1
a 0 s 0:13 0 0 0 hln 100 PAGESIZE=A
a 0 s 0:13 0 0 0 hln 100 PAGECOUNT=1
@graphics
