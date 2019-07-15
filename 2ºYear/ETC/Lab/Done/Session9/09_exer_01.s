          .globl __start
          .data 0x10000000
M:        .space 4
Q:		  .space 4
R:		  .space 4
          
          .text 0x00400000
__start:  li $a0,'M'
          la $a1, M
          jal InputV
		  li $a0, 'Q'
		  la $a1, Q
		  jal InputV
		  la $a0, M
		  la $a1, Q
		  la $a2, R
		  jal MultV
		  li $a0, 'R'
		  la $a1, R
		  jal PromptV
          li $v0,10
          syscall

InputV:   li $v0, 11
          syscall
          li $v0, 11
          li $a0, '='
          syscall
          li $v0, 5
          syscall
          sw $v0, 0($a1)
          jr $ra
		  
PromptV:  li $v0, 11
		  syscall
		  li $v0, 11
		  li $a0, '='
		  syscall
		  li $v0, 1
		  lw $a0, 0($a1)
		  syscall
		  jr $ra
		  
MultV:    li $v0, 0
		  lw $t0, 0($a0)
		  lw $t1, 0($a1)
		  bgez $t1, Continue
		  sub $t1, $zero, $t1
		  sub $t0, $zero, $t0
Continue: beqz $t1, End
While:	  add $v0, $v0, $t0
		  add $t1, $t1, -1
		  bgtz $t1, While
		  sw $v0, 0($a2)
End:	  jr $ra