.globl __start
.data 0x10000000
vector: .word 3,4,5
ncomp: .word 3
result: .space 4
.text 0x00400000
__start: 
la $8, vector  ;pone la dirección de vestor en el registro $8
la $9, ncomp ;pone la dirección de ncomp en el registro $9
lw $9, 0($9) ;guarda la primera word de ncomp (3) en el registro $9
addi $10, $0, 0 ;pone a 0 el registro $10
bucle:
beq $9, $0, fin ;si el registro $9 es igual al registro $0, se acaba el programa
lw $11, 0($8) ;guarda la primera word de vector (3) en el registro $11  
add $10, $10, $11 ;pone en el registro $10 la suma de los registros $10 y $11 (0+3)
addi $8, $8, 4 ;pone en el registro $8 la suma del registro $8 más 4 (vector + 4 (la próxima word))
addi $9, $9, -1  ;pone en el registro $9 la resta del registro $9 menos 1 (2)
j bucle ;salta al bucle
fin:
la $12, result ;pone en el registro $12 la dirección de result
sw $10,0($12) ;guarda en el registro $12 el contenido del registro $10
.end