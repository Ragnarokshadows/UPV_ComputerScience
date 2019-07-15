#---------------------------------------------------------#
#
#  user0.s
# 
#---------------------------------------------------------#

### TEST PROGRAM FOR MIMOSv0.handler


# System call IDs

		print_char = 11
		get_version = 90
		get_time = 91
		wait_time = 92


# Data segment

		.data	
return:		.word 0
hello:		.asciiz "MIMOS v."
buffer_int: 	.ascii "          " # Do not change: printf_integer buffer

#-------------------------------------------------#

# Code segment ("text")
	.text
    	.globl main	

main:
# Save returning address
	sw $ra,return

# Say hello and give handler version number
	la $a0,hello
	jal print_string
	li $v0,get_version
	syscall
	move $a0,$v0
	jal printf_integer
	jal print_NL


# print_char test

	li $s0,0
loop:
	jal much_computation
	addiu $s0,$s0,1
	move $a0,$s0
	jal printf_integer		# console echo
	jal print_NL

	b loop

# Shutdown
#	lw $ra,return
#	jr $ra


#-------------------------------------------------

print_string: # $a0: point to string ended by \0
	move $t0,$a0
	lb $a0,0($t0)
	beq $a0,$zero,$L4
$L3:	li $v0,print_char 
	syscall
	addiu $t0,$t0,1
	lb $a0,0($t0)
	bne $a0,$zero,$L3
$L4:	jr $ra

#-------------------------------------------------

print_NL:	# without parameters: it writes NL
	li $a0,'\n'
	li $v0,print_char 
	syscall
	jr $ra

#-------------------------------------------------

printf_integer: # $a0: integer value
       	move $t0,$a0    	# initial dividend
	li $t1,0          	# counts digits
	li $t2,10         	# divisor

$L1:	# base change loop
	divu $t0,$t2		# dividing by 10
	mfhi $t3        	# get the reminder
	addiu $t3,$t3,'0'	# compute ascii code
	sb $t3,buffer_int($t1)	# save buffer
	addi $t1,$t1,1		# pointer forward
	mflo $t0		# new dividend
	bne $t0,$zero,$L1

$L2:	# writint loop
	addiu $t1,$t1,-1	# buffer backward
	lb $a0,buffer_int($t1)	# get character
	li $v0,print_char	# write character
	syscall			# system call
	bne $t1,$zero,$L2	
	li $v0,print_char 
	jr $ra

#-------------------------------------------------

much_computation:
	li $t0,0x0004FFFF
$L5:	addiu $t0,$t0,-1
	bne $t0,$zero,$L5
	jr $ra