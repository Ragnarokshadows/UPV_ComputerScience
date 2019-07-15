.globl __start

.text 0x00400000
__start:
	addi $8,$0,0x180F
	andi $9,$8,0x0F00
	srl $9,$9,8 

.end
