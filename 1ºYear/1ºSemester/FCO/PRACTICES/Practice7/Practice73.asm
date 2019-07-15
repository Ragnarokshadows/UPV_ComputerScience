.text 0x00400000
          .globl __start
__start:
          lui $2, 0x7fff
		lui $3, 0x7fff 
		add $4,$2,$3
		.end 