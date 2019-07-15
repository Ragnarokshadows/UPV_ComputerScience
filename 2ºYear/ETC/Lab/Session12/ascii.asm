.text
    	.globl __start	

__start:
	# Loads keyboard base address
	la $t0, 0xffff0000
	
	li $t3, 97

wait:
	lw $t1,0($t0)
	andi $t1,$t1,1
	beqz $t1,wait
	
	# sw $zero,0($t0)
	
	lw $t2,4($t0)

	li $v0, 1
	move $a0, $t2
	syscall
	
	bne $t2, $t3, wait 

exit:
	li $v0, 10
	syscall			

	.end
