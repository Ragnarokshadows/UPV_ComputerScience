### TEMPLATE FOR MIPS R2000 I/O LAB SESSIONS ###

#-------------------------------------------------#
#
#  LAB 12: SINCHRONIZATION BY POLLING
# 
#-------------------------------------------------#

# ACTIVITY 2:  Add the instruction that reads the keyboard 
#              data register on the appropriate location:


#------- Data segment ---------#
	.data		
T1:	.asciiz "Waiting...\n"
T2:	.asciiz "\nEnd\n"

#------- Code segment ---------#
	.text
    	.globl __start	

__start:
	# Writes on the console message T1
	li $v0, 4
	la $a0, T1
	syscall	
	
	# Loads keyboard base address
	la $t0, 0xffff0000

wait:   # Waits for bit R == 1
	lw $t1,0($t0)
	andi $t1,$t1,1
	beqz $t1,wait

### TO BE COMPLETED: Reads keyboard data register ###	

	lw $t2,4($t0)


	# Writes on the console message T2
	li $v0, 4
	la $a0, T2
	syscall

	# exit
	li $v0, 10
	syscall			

	.end
