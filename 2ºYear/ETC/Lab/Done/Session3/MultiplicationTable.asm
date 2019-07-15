          .globl __start
          .text 0x00400000
__start:  li $a0,78
		  jal Input
		  move $a1,$v0
		  li $t1,0
		  li $t2,10
Continue: addi $t1,$t1,1
		  move $a2,$t1
		  jal Mult
          move $a3,$v0
          move $a0,$a1
          jal PromptM
		  addi $t2,$t2,-1
		  bne $t2, $zero, Continue
		  
Final:	  li $v0,10
		  syscall
Mult:     li $v0, 0
MultFor:  add $v0, $v0, $a1
          addi $a2, $a2, -1
          bne $a2, $zero, MultFor
MultRet:  jr $ra
Input:    li $v0,11
		  syscall
		  li $a0,61
		  syscall
		  li $v0,5
          syscall
		  jr $ra
PromptM:  li $v0,1
		  syscall
		  li $v0,11
		  li $a0,120
		  syscall
		  li $v0,1
		  move $a0,$t1
          syscall
		  li $v0,11
		  li $a0,61
		  syscall
		  li $v0,1
		  move $a0,$a3
		  syscall
		  li $v0,11
		  li $a0,10
		  syscall
		  jr $ra