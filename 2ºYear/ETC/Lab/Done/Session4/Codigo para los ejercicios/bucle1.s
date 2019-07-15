; C[i] = 2*A[i] + B[i]-1

	.data
A:	.word 0,1,2,3,4,5,6,7,8,9
B:	.word 10,11,12,13,14,15,16,17,18,19
C:	.space 40

.text

start:
	addi r1, r0, #10	; r1 = numero de iteraciones
	addi r2, r0, #0		; r2 = desplazamiento a elemento de A
	addi r3, r0, #0		; r3 = desplazamiento a elemento de B
	addi r4, r0, #0		; r4 = desplazamiento a elemento de C
bucle:
	lw r6, A(r2)		; lectura A[i]
	add r6, r6, r6		; r6 = 2*A[i]
	lw r7, B(r3)		; lectura B[i]
	addi r7, r7, #1		; r7 = B[i]+1
	add r8, r6, r7		; r8 = 2*A[i]+B[i]+1
	sw C(r4), r8		; C[i] = r8
	addi r1,r1,#-1   	; r1 = r1 – 1
	addi r2, r2, #4		; r2 = r2 + 4
	addi r3, r3, #4		; r3 = r3 + 4
	addi r4, r4, #4		; r4 = r4 + 4
	seq r5, r1, r0		; r5 = (r1 == 0)
	beqz r5, bucle		; salta si r5 != 0
	trap #0       		; Fin del programa
