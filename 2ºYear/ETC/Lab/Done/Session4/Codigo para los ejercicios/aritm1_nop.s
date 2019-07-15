	.text

start:
	add  r1,r0,r0     ; r1 = 0
	addi r2,r0,#64    ; r2 = 64
	nop
	nop
	addi r3,r2,#10    ; r3 = r2 + 10 = 74
	nop
	nop
	sub  r4,r3,r2     ; r4 = r3 – r2 = 10
	trap #0       ; Fin del programa



        


