.globl __start

.text 0x00400000
__start:
	addi $8,$0,0x180F
	andi $8,$8,0xFFC0

.end
