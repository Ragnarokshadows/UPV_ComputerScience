                ##########################################################
                # Segmento de datos
                ##########################################################

                .data 0x10000000
reloj:          .word 0                # HH:MM:SS (3 bytes de menor peso)

cad_asteriscos: .asciiz "\n  **************************************"
cad_horas:      .asciiz "\n   Horas: "
cad_minutos:    .asciiz " Minutos: "
cad_segundos:   .asciiz " Segundos: "
cad_reloj_en_s: .asciiz "\n   Reloj en segundos: "

                ##########################################################
                # Segmento de código
                ##########################################################

                .globl __start
                .text 0x00400000

__start:        la $a0, reloj
				#li $a1, 0x6502030C
				li $a1, 0x0012202D
				#li $a1, 0x02
				#li $a2, 0x03
				#li $a3, 0x0C
				jal inicializa_reloj
					
				#la $a0, reloj
				#jal devuelve_reloj_en_s
				#move $a0, $v0
				#jal imprime_s
				
				#la $a0, reloj
				#li $a1, 765432
				#jal inicializa_reloj_en_s
				
				#la $a0, reloj
                jal imprime_reloj
				
				#PRUEBA DEVUELVE_RELOJ_EN_S
				
				la $a0, reloj
				jal devuelve_reloj_en_sd
				move $a0, $v0
				jal imprime_s
				
				la $a0, reloj
				jal devuelve_reloj_en_srd
				move $a0, $v0
				jal imprime_s
				
				#jal salir
				
				#PRUEBA PASA_SEGUNDO
				
				la $a0, reloj
				li $a1, 0x00173b3b # Hora 23:59:59
				jal inicializa_reloj
				la $a0, reloj
				jal imprime_reloj
				la $a0, reloj
				jal pasa_segundo # Increase 1 second
				jal pasa_segundo # Increase 1 second
				la $a0, reloj
				jal imprime_reloj
				
				jal salir

pasa_segundo:   lbu $t0, 0($a0)
				li $t2, 60
				li $t3, 24
				addi $t0, 1
				beq  $t0, $t2, incmin
				sb $t0, 0($a0)
				jr $ra

incmin:			sb $zero, 0($a0)
				lbu $t0, 1($a0)
				addi $t0, 1
				beq $t0, $t2, inch
				sb $t0, 1($a0)
				jr $ra
				
inch:           sb $zero, 1($a0)
				lbu $t0, 2($a0)
				addi $t0, 1
				beq $t0, $t3, alter
				sb $t0, 2($a0)
				jr $ra
				
alter:          sb $zero, 2($a0)
				jr $ra
				
constant36:     sll $v0, $a0, 2 
				sll $t0, $a0, 5
				addu $v0, $v0, $t0
				jr $ra

inicializa_reloj:
				li $t0, 0x001F3F3F
				and $a1, $a1, $t0
				sw $a1, 0($a0)
				jr $ra

inicializa_reloj_alt:
				sb $a1, 2($a0)
				sb $a2, 1($a0)
				sb $a3, 0($a0)
				jr $ra
				
inicializa_reloj_hh:
				sb $a1, 2($a0)
				jr $ra

inicializa_reloj_mm:
				sb $a1, 1($a0)
				jr $ra

inicializa_reloj_ss:
				sb $a1, 0($a0)
				jr $ra
				
devuelve_reloj_en_sd:
				#li $t0, 3600
				lbu $t1, 2($a0)
				sll $t2, $t1, 11
				sll $t0, $t1, 10
				addu $t2, $t2, $t0
				sll $t0, $t1, 9
				addu $t2, $t2, $t0
				sll $t0, $t1, 4
				addu $v0, $t2, $t0
				#li $t0, 60
				lbu $t1, 1($a0)
			    sll $t2, $t1, 5
				sll $t0, $t1, 4
				addu $t2, $t0, $t2
				sll $t0, $t1, 3
				addu $v0, $t0, $v0
				sll $t0, $t1, 2
				addu $v0, $t0, $v0
				addu $v0, $t2, $v0
				lbu $t1, 0($a0)
				addu $v0, $v0, $t1
				jr $ra

devuelve_reloj_en_srd:
				#li $t0, 3600
				lbu $t1, 2($a0)
				sll $t2, $t1, 12
				sll $t0, $t1, 9
				subu $t2, $t2, $t0
				sll $t0, $t1, 5
				addu $t2, $t2, $t0
				sll $t0, $t1, 4
				subu $v0, $t2, $t0
				#li $t0, 60
				lbu $t1, 1($a0)
			    sll $t2, $t1, 6
				sll $t0, $t1, 2
				addu $v0, $t2, $v0
				subu $v0, $v0, $t0
				lbu $t1, 0($a0)
				addu $v0, $v0, $t1
				jr $ra
				
devuelve_reloj_en_s:
				li $t0, 3600
				lbu $t1, 2($a0)
				mult $t0, $t1
				mfhi $t2
				bgtz $t2, salir
				mflo $v0
				li $t0, 60
				lbu $t1, 1($a0)
				mult $t0, $t1
				mfhi $t2
				bgtz $t2, salir
				mflo $t1
				addu $v0, $v0, $t1
				lbu $t1, 0($a0)
				addu $v0, $v0, $t1
				jr $ra
				
inicializa_reloj_en_s:
				li $t0, 60
				beqz $t0, salir
				div $a1, $t0
				mfhi $t1
				mflo $t2
				sb $t1, 0($a0)
				beqz $t0, salir
				div $t2, $t0
				mfhi $t1
				mflo $t2
				sb $t1, 1($a0)
				sb $t2, 2($a0)
				jr $ra
				
salir:          li $v0, 10              # Código de exit (10)
                syscall                 # Última instrucción ejecutada
                .end


                ########################################################## 
                # Subrutina que imprime el valor del reloj
                # Entrada: $a0 con la dirección de la variable reloj
                ########################################################## 

imprime_reloj:  move $t0, $a0
                la $a0, cad_asteriscos  # Dirección de la cadena
                li $v0, 4               # Código de print_string
                syscall

                la $a0, cad_horas       # Dirección de la cadena
                li $v0, 4               # Código de print_string
                syscall

                lbu $a0, 2($t0)         # Lee el campo HH
                li $v0, 1               # Código de print_int
                syscall

                la $a0, cad_minutos     # Dirección de la cadena
                li $v0, 4               # Código de print_string
                syscall

                lbu $a0, 1($t0)         # Lee el campo MM
                li $v0, 1               # Código de print_int
                syscall

                la $a0, cad_segundos    # Dirección de la cadena
                li $v0, 4               # Código de print_string
                syscall

                lbu $a0, 0($t0)         # Lee el campo SS
                li $v0, 1               # Código de print_int
                syscall

                la $a0, cad_asteriscos  # Dirección de la cadena
                li $v0, 4               # Código de print_string
                syscall
                jr $ra

                ########################################################## 
                # Subrutina que imprime los segundos calculados
                # Entrada: $a0 con los segundos a imprimir
                ########################################################## 

imprime_s:      move $t0, $a0
                la $a0, cad_asteriscos  # Dirección de la cadena
                li $v0, 4               # Código de print_string
                syscall


                la $a0, cad_reloj_en_s  # Dirección de la cadena
                li $v0, 4               # Código de print_string
                syscall

                move $a0, $t0           # Valor entero a imprimir
                li $v0, 1               # Código de print_int
                syscall

                la $a0, cad_asteriscos  # Dirección de la cadena
                li $v0, 4               # Código de print_string
                syscall
                jr $ra
                
                ########################################################## 
                # Subrutina que incrementa el reloj en una hora
                # Entrada: $a0 con la dirección del reloj
                # Salida: reloj incrementado en memoria
                # Nota: 23:MM:SS -> 00:MM:SS
                ########################################################## 
                
pasa_hora:      lbu $t0, 2($a0)         # $t0 = HH
                addiu $t0, $t0, 1       # $t0 = HH++
                li $t1, 24
                beq $t0, $t1, H24       # Si HH==24 se pone HH a cero
                sb $t0, 2($a0)          # Escribe HH++
                j fin_pasa_hora
H24:            sb $zero, 2($a0)        # Escribe HH a 0
fin_pasa_hora:  jr $ra
