#-------------------------------------------------#
#
#  PRÁCTICA 15: LLAMADAS AL SISTEMA II
# 
#-------------------------------------------------#

### PROGRAMA BASE PRACTICAS DE ENTRADA/SALIDA MIPS R2000
### Este programa sirve para testear el manejador
### MIMOSv3.handler


# Identificadores de las funciones de sistema

		print_char = 11
		read_char = 12
		get_version = 90
		get_time = 91
		wait_time = 92
		read_char_echo = 93

# segmento de datos

		.data	
retorn:		.word 0
bondia:		.asciiz "MIMOS v."
la_hora:		.asciiz " segundos\n"
espero:		.asciiz "Espero un carácter..."
leido:		.asciiz "\nCarácter leído = "
buffer_int: 	.ascii "          " # No tocar. Buffer de printf_integer

#-------------------------------------------------#

# Segmento de código ("text")
	.text
    	.globl main	

main:
# Guarda adreça de retorn
	sw $ra,retorn

# Saluda y da el número de versión
	la $a0,bondia
	jal print_string
	li $v0,get_version
	syscall
	move $a0,$v0
	jal printf_integer
	jal print_NL


# Prueba de la función read_char 

	li $s0,0
bucle:
	# Dice la hora
	li $v0,get_time
	syscall
	move $a0,$v0
	jal printf_integer
	la $a0,la_hora
	jal print_string

	# Espera carácter
	la $a0,espero
	jal print_string
	li $v0,read_char
	syscall
	move $s1,$a0

	# Informa de lo leído
	la $a0,leido
	jal print_string
	move $a0,$s1
	li $v1,print_char
	syscall
	jal print_NL
	b bucle

# Shutdown
#	lw $ra,retorn
#	jr $ra


#-------------------------------------------------

print_string: # $a0: puntero a string acabado en \0
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

print_NL:	# sense paràmetres: escriu NL
	li $a0,'\n'
	li $v0,print_char 
	syscall
	jr $ra

#-------------------------------------------------

printf_integer: # $a0: valor entero
                         move $t0,$a0		# dividendo inicial
	       li $t1,0          	 	# cuenta de cifras
	       li $t2,10         		# divisor

$L1:	# bucle de cambio de base
	divu $t0,$t2		# división entre 10
	mfhi $t3          		# tomo el resto
	addiu $t3,$t3,'0' 		# calculo código ascii
	sb $t3,buffer_int($t1)	# guardo en buffer
	addi $t1,$t1,1		# avanzo puntero
	mflo $t0			# nou dividendo
	bne $t0,$zero,$L1

$L2:	# bucle de escritura
	addiu $t1,$t1,-1		# retrocedo en buffer
	lb $a0,buffer_int($t1)	# tomo carácter
	li $v0,print_char		# escribo carácter
	syscall			# llamada
	bne $t1,$zero,$L2	
	li $v0,print_char 
	jr $ra

#-------------------------------------------------

mucho_calculo:
	li $t0,0x0004FFFF
$L5:	addiu $t0,$t0,-1
	bne $t0,$zero,$L5
	jr $ra