.globl __start
.data 0x10000000 
vector: .word 3,4,5
ncomp: .word 3
result: .space 4
.text 0x00400000
__start:
la $8, vector ;direccion vector
la $9, ncomp  ;direccion ncomp
lw $9,0($9) ;carga el primer word de ncomp (3)
addi $10, $0, 0 ;$10 a 0
addi $12, $0, 1  ;$12 a 1
bucle:
beq $9, $0, fin ;si $9 = 0 fin
lw $11,0($8) ;carga en $11 word de vector(3)
mult $11,$12 ;muktiplica (3*1)
mflo $11 ;mueve el resultado
add $10,$10,$11  ;(3)
addi $8,$8,4 ;cambia a la siguiente direccion
addi $9, $9, -1 ;le resta a $9
addi $12, $12, 1 ;le añade a $12 1
j bucle
fin:
la $12, result
sw $10,0($12)
.end 