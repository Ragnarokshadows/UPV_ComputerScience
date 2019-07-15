.globl __start
.data 0x10000000
base: .word 3
altura: .word 10
area: .space 4
.text 0x00400000
__start:
la $10, base
lw $10, 0($10)
la $11, altura
lw $11, 0($11)
mult $10,$11
mflo $10
addi $11,$0,2
div $10,$11
mflo $10
la $11, area
sw $10, 0($11)
.end