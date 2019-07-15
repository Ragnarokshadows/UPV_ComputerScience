          .globl __start
          .text 0x00400000
__start:  li $a0,67
		  jal Input
		  move $a1,$v0
		  jal PrintChar
Final:	  li $v0,10
		  syscall
		  
Input:    li $v0,11
		  syscall
		  li $a0,61
		  syscall
		  li $v0,5
          syscall
		  jr $ra
PrintChar:li $v0,11
		  li $a0,39
		  syscall 
		  li $t1,10
		  bne $a1,$zero,SecCase
		  li $v0,11
		  li $a0,92
		  syscall
		  li $v0,11
		  li $a0,48
		  syscall
		  j Continue
SecCase:  bne $a1,$t1,Default
		  li $v0,11
		  li $a0,92
		  syscall
		  li $v0,11
		  li $a0,110
		  syscall
		  j Continue
Default:  li $v0,11
		  move $a0,$a1
		  syscall
Continue: li $v0,11
		  li $a0,39
		  syscall
          jr $ra		  