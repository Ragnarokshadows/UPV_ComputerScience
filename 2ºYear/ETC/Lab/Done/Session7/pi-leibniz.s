                ##########################################################
                # Segmento de datos
                ##########################################################

                .data 0x10000000
cad_entrada:    .asciiz "\nDime el número de iteraciones: "
cad_salida:     .asciiz "El valor calculado de pi es: " 

                ##########################################################
                # Segmento de código
                ##########################################################

                .globl __start
                .text 0x00400000
__start:        
                ########################################################
                # Lectura del número de iteraciones
                ########################################################

                la $a0, cad_entrada     # Cadena a imprimir
                li $v0, 4               # Función print_string
                syscall

                li $v0, 5               # Función read_int
                syscall

                move $a0, $v0           # Parámetro de la subrutina
                jal leibniz             # Salto a la subrutina

                ########################################################
                # Impresión del resultado
                ########################################################

                la $a0, cad_salida      # Cadena a imprimir
                li $v0, 4               # Función print_string
                syscall

                li $v0, 2               # Función print_float
                mfc1 $t0, $f0           # Valor a imprimir
                mtc1 $t0, $f12
                syscall

                ########################################################
                # Finalización del programa 
                # Llamada al sistema denominada "exit"
                ########################################################= 

                li $v0, 10
                syscall
                
                ########################################################
                # Cálculo de pi con el método de Leibniz
                # $a0 = Número de iteraciones de la serie
                ########################################################

leibniz:        li.s $f0, 0.0           # Constante 0.0
                li.s $f4, 1.0           # Constante 1.0
                li.s $f6, 2.0           # Constante 2.0
                move $t0, $a0           # Contador número de iteraciones

bucle:          mtc1 $t0, $f8           # Lleva n a la FPU
                cvt.s.w $f8, $f8        # Convierte n en número real

                mul.s $f8, $f8, $f6     # Calcula 2.0*n
                add.s $f8, $f8, $f4     # Calcula 2.0*n + 1.0
                div.s $f8, $f4, $f8     # Calcula 1.0/(2.0*n + 1.0)  

                andi $t1, $t0, 0x0001   # Extrae bit LSB de n
                bne $t1, $zero, resta   # Salta si es impar (LSB==1)
                add.s $f0, $f0, $f8     # El término se suma
                j continua
resta:          sub.s $f0, $f0, $f8     # El término se resta
continua:       addi $t0, $t0, -1       # Decrementa número de iteraciones
                bgez $t0, bucle         # Vuelve si quedan iteraciones		

                li.s $f4, 4.0           # Constante 4.0
                mul.s $f0, $f0, $f4     # Devuelve en $f0 el cálculo de pi			                
                jr $ra
                .end
