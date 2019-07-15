### ESTRUCTURA DE COMPUTADORES (ETSINF)
###
### Lab 13: Synchronization by interrupts
###
### nothing.asm : template code

###################################################################
##                  USER PROGRAM DATA                            ##
###################################################################
	.data		
var2:	.word 0x77777777

###################################################################
##                  USER PROGRAM CODE                            ##
###################################################################
	.text

main:	jr $ra	# Only one instruction

	.end