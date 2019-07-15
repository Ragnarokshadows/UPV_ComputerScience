                ##########################################################
                # Segmento de datos
                ##########################################################

                .data 0x10000000
dimension:      .word 4
valores:        .float 2.3, 1.0, 3.5, 4.8
pesos:          .float 0.4, 0.3, 0.2, 0.1
media_s:        .float  0.0
media_d:        .double 0.0

                ##########################################################
                # Segmento de código
                ##########################################################

                .globl __start
                .text 0x00400000

__start:        la $t0, dimension      # Dirección de la dimensión
                lw $t0, 0($t0)         # Lectura de la dimensión
                mtc1 $t0, $f4          # Lleva la dimensión a $f4
                la $t1, valores        # Dirección de los valores       
                mtc1 $zero, $f0        # Lleva 0.0 a $f0

bucle:          lwc1 $f6, 0($t1)       # Lee valor[i]
                add.s $f0, $f0, $f6    # Suma del valor
                addiu $t1, $t1, 4      # Dirección de valor[i+1] 
                addiu $t0, $t0, -1     # Decrementa contador
                bgtz $t0, bucle        

                cvt.s.w $f4, $f4       # Convierte dimension a real
                div.s $f0, $f0, $f4    # Calcula media aritmética
                cvt.d.s $f2, $f0       # Convierte a doble precisión
                la $t0, media_s        # Dirección del resultado media_s
                swc1 $f0, 0($t0)       # Escribe resultado simple precisión
                la $t0, media_d        # Dirección del resultado media_d
                swc1 $f2, 0($t0)       # Escribe parte baja doble precisión
                swc1 $f3, 4($t0)       # Escribe parte alta doble precisión
 
                ########################################################
                # Finalización del programa 
                # Llamada al sistema denominada "exit"
                ########################################################= 

                li $v0, 10
                syscall
                .end
