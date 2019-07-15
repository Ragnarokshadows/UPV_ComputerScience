          .globl __start
          .data 0x10000000          
alpha:	  .aciiz "α"	

          .text 0x00400000
__start:  lb $t0, alpha

		  lw $t0, 0x1000
		  lw $t0, 0x10000000
		  lw $t0, 0x101000
		  
		  lw $t0, A
		  addi $t0, $t0, 1
		  sw $t0, A
		  
		  la $t0, A
		  lw $t1, 0($t0)
		  addi $t1, $t1, 1
		  sw $t1, 0($t0)
		  
		  