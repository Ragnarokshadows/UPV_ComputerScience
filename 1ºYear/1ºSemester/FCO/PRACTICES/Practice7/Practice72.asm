          .text 0x00400000
          .globl __start
__start:
          addi $2, $0, 1
		addi $3, $0, -2
          add  $4, $2, $3
		.end 