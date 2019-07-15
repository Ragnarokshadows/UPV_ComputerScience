          .globl __start
          .text 0x00400000
__start:  li $v0,5
          syscall
          move $a0,$v0
          li $v0,5
          syscall
          move $a1,$v0
          jal Mult
          move $a0,$v0
          li $v0,1
          syscall
          li $v0,10
          syscall
Mult:     li $v0, 0
          beqz $a1, MultRet
MultFor:  add $v0, $v0, $a0
          addi $a1, $a1, -1
          bne $a1, $zero, MultFor
MultRet:  jr $ra
