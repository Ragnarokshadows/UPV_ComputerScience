.globl __start 
	.data 0x10000000 
X:	.space 4
	.text 0x00400000 
__start: #la $t0,X
	#sw $zero,0($t0)
	
	#la $t0,X
	#sh $zero,0($t0)
	#sh $zero,2($t0)
	
	#la $t0,X 
	#sb $zero,0($t0)
	#sb $zero,1($t0)
	#sb $zero,2($t0)
	#sb $zero,3($t0)
	
	#la $t0, X
	#lui $t1, 0x0001
	#sw $t1,0($t0)
	
	#la $t0,X 
	#lui $t1,0xFFFF 
	#ori $t1,$t1,0xFFFF 
	#sw $t1,0($t0)
	
	#lui $t0,0x1000 
	#andi $t1,$t1,0x0000 
	#sw $t1,0($t0)
	
	#la $t0,X
	#lw $t1,0($t0)
	#sw $t1,0($t0)
	
	#li $t1,50
	#sw $t1,X
	
	#li $t0,0x50
	#sw $t0,X
	
	li $t0,0x10000000
	li $t1,0xFFFFFFFF
	sw $t1,0($t0)