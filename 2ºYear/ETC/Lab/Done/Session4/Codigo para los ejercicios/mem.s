; Varias operaciones con memoria

	.data
A:	.word 0
B:	.word 20
C:	.word 30
D:	.word 0

	.text

start:
	addi r1, r0, #10	; r1 = 10
	sw A(r0), r1		; almacenar 10 en A
	
	lw r1, B(r0)		; r1 = 20
	lw r2, C(r0)		; r2 = 30
	add r3, r1, r2	; r3 = r1 + r2 = 50
	sw D(r0), r3		; almacenar 50 en D
	trap #0       ; Fin del programa



        


