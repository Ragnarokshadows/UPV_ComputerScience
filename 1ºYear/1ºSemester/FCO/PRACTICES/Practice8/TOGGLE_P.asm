.globl __start

.text 0x00400000
__start:
	addi $8,$0,0x180F
	xori $8,$8,0x1000

.end
