	.globl __start 
	.text 0x00400000 
__start: li $t0,25 
	li $t1,30 
	add $s0,$t1,$t0 
	add $s0,$s0,$s0