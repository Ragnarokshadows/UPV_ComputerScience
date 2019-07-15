.text 0x00400000
          .globl __start
__start:
          addi $2, 10000
		mult $2, $2 
		mflo $3
		mult $2, $3
		.end #end of program
