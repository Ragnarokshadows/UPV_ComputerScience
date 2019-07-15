          .globl __start
          .data 0x10000000
askfor:   .asciiz "Write something: "
response: .asciiz "You have written: "
lengthS:  .asciiz "String length is: "
string:   .space 80             

          .text 0x00400000
__start:  la $a0, askfor        
          la $a1, string        
          li $a2, 80            
          jal InputS
		  la $a0, response
		  la $a1, string
		  jal PromptS
		  la $a0, lengthS
		  la $a1, string
		  jal StrLength
          li $v0,10
          syscall

InputS:   li $v0, 4
          syscall
          li $v0, 8
          move $a0, $a1
          move $a1, $a2
          syscall
          jr $ra

PromptS:  li $v0, 4
		  syscall
		  li $v0, 4
		  move $a0, $a1
		  syscall
		  jr $ra
		  
StrLength:li $v0, 4
		  syscall
		  li $t0, 10
		  li $t1, 0
readchar: lb $t2, 0($a1)
		  addu $a1, $a1, 1
		  addi $t1, $t1, 1
		  bne $t2, $t0, readchar
		  addi $t1, $t1, -1
		  li $v0, 1
		  move $a0, $t1
		  syscall
 		  jr $ra