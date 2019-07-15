### TEMPLATE FOR MIPS R2000 I/O LAB SESSIONS ###

#-------------------------------------------------#
#
#  LAB 12: SINCHRONIZATION BY POLLING
# 
#-------------------------------------------------#

# ACTIVITY 3:  Complete the following functions:
#    char getchar() - gets the typed character
#    void putchar(char c) - prints one character on the console

# Data segment

	.data		

#-------------------------------------------------#

# Code segement ("text")
	.text
    	.globl __start	



__start:			

	li $a0, 'P'		# 
	jal putchar		# putchar('P')
	li $a0, '1'		# 
	jal putchar		# putchar('1')
	li $a0, '2'		# 
	jal putchar		# putchar('2')
	li $a0, 13		# return character ('\n')
	jal putchar		# putchar('\n')
	
bucle:
	jal getchar		# $v0 = getchar()
	move $a0, $v0		#
	li $t0, 0x1b      	# detects ESC (0x1b = 27)
	beq $a0, $t0, fin
	jal putchar		# putchar($a0)
	b bucle
fin:	
	li $v0, 10
	syscall			# exit
	
	
	
getchar:			# $v0 = getchar()
### TO BE COMPLETED: function code

	la $t0, 0xffff0000
	
wait:
	lw $t1,0($t0)
	andi $t1,$t1,1
	beqz $t1,wait

	lw $v0,4($t0)
	
###
	jr $ra


putchar:			# putchar($a0)
### TO BE COMPLETED: function code

	la $t0, 0xffff0008
	
wait2:
	lw $t1,0($t0)
	andi $t1,$t1,1
	beqz $t1,wait2

	sw $a0,4($t0)

###
	jr $ra
