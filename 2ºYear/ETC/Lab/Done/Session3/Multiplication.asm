          .globl __start
          .text 0x00400000
__start:  li $a0,77
		  jal Input
		  move $a2,$v0
		  li $a0,81
		  jal Input
		  move $a3,$v0
		  bgez $a3,Continue
		  sub $a2,$zero,$a2
		  sub $a3,$zero,$a3
Continue: jal Mult
          move $a1,$v0
          li $a0,82
          jal Prompt
		  li $v0,10
		  syscall
Mult:     li $v0, 0
          beqz $a3, MultRet
MultFor:  add $v0, $v0, $a2
          addi $a3, $a3, -1
          bne $a3, $zero, MultFor
MultRet:  jr $ra
Input:    li $v0, 11
		  syscall
		  li $a0,61
		  syscall
		  li $v0,5
          syscall
		  jr $ra
Prompt:   li $v0, 11
		  syscall
		  li $a0,61
		  syscall
		  li $v0,1
		  move $a0,$a1
          syscall
		  li $v0,11
		  li $a0,10
          syscall
		  jr $ra