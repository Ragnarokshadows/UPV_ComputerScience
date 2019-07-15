.globl __start 
	.data 0x10000000 
	.text 0x00400000 
__start: move $t0,$t1
	li $t0,100
	li $t0, 0x10003000 
	lw $t1, 2($t0)
	li $t0,-1