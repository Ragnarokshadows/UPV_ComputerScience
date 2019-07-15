## ESTRUCTURA DE COMPUTADORES (ETSInformatica)
##
## Lab 13: Synchronization by interrupts
##
## User test program

#------- DATA SEGMENT ---------#
	.data		
T1:	.asciiz "Working...\n"

T2:	.asciiz "\nEnd\n"

#------- CODE SEGMENT ---------#
	.text
    	.globl __start

main:	la $a0,T1
	li $v0,4
	syscall
	li $t0, 10

loopE: li $t1, 1000000  

loopI:	addi $t1,$t1,-1	
	bgtz $t1, loopI

	addi $t0,$t0,-1  
	bgtz $t0, loopE 

	la $a0,T2
	li $v0,4
	syscall

	jr $ra

	.end