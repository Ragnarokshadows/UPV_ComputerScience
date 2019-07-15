	.globl __start 
	.data 0x10000000 
A: .word 25 
B: .word 30 
P: .space 4 
	.text 0x00400000 
__start: la $t0,A 
	la $t1,B 
	la $t2,P 
	lw $s0,0($t0) 
	lw $s1,0($t1) 
	add $s2,$s1,$s0 
	add $s2,$s2,$s2 
	sw $s2,0($t2)