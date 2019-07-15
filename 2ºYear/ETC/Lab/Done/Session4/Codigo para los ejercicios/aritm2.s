; Varias operaciones aritmeticas
; R5 = R4 – R3 + R2
; R6 = R4 + R1 – R3

	.text

start:
	add  r1, r0, #10	; r1 = 10
	add  r2, r0, #20  ; r2 = 20
	add  r3, r0, #30  ; r3 = 30
	add  r4, r0, #40  ; r4 = 40
	sub r5, r4, r3    ; r5 = r5 – r3
	add r5, r5, r2    ; r5 = r5 + r2
	add r6, r4, r1    ; r6 = r4 + r1
	sub r6, r6, r3    ; r6 = r6 – r3
	trap #0       ; Fin del programa
