.globl __start
.data 0x10000000
base: .word 3
altura: .word 10
area: .space 4
.text 0x00400000
__start:
la $10, base
lw $11, 0($10)
la $12, altura
lw $13, 0($12)
mult $11,$13
mflo $14
addi $15,$0,2
div $14,$15
mflo $16
la $17, area
sw $16, 0($17)
.end