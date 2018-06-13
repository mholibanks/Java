#Question1
#Mholi Mncube
#20/10/2017

.data
   	int0: .space 20
        int1: .space 20
       	int2: .space 20
        int3: .space 20
        int4: .space 20
        inmsg:  .asciiz "Enter a series of 5 formulae:\n"
       	outmsg:  .asciiz "The values are:\n"

.text
.globl main
    	main:
       		la $a0,inmsg #Load and print string asking for string
         	li $v0,4
         	syscall

         	li $v0,8 #take in input
         	la $a0, int0 #load byte space into address
         	li $a1, 20 # allot the byte space for string
         	move $t0,$a0 #save string to t0
         	syscall

		li $v0,8 #take in input
         	la $a0, int1 #load byte space into address
         	li $a1, 20 # allot the byte space for string
         	move $t1,$a0 #save string to t0
         	syscall
         	
         	li $v0,8 #take in input
         	la $a0, int2 #load byte space into address
         	li $a1, 20 # allot the byte space for string
         	move $t2,$a0 #save string to t0
         	syscall
         	
         	li $v0,8 #take in input
         	la $a0, int3 #load byte space into address
         	li $a1, 20 # allot the byte space for string
         	move $t3,$a0 #save string to t0
         	syscall
         	
         	li $v0,8 #take in input
         	la $a0, int4 #load byte space into address
         	li $a1, 20 # allot the byte space for string
         	move $t4,$a0 #save string to t0
         	syscall
         	
         	la $a0,outmsg #load and print "The values are:" string
         	li $v0,4
         	syscall

         	la $a0, int0 #reload byte space to primary address
         	move $a0,$t0 # primary address = t0 address (load pointer)
         	li $v0,4 # print string
         	syscall
         	
         	la $a0, int1 #reload byte space to primary address
         	move $a0,$t1 # primary address = t0 address (load pointer)
         	li $v0,4 # print string
         	syscall
         	
         	la $a0, int2 #reload byte space to primary address
         	move $a0,$t2 # primary address = t0 address (load pointer)
         	li $v0,4 # print string
         	syscall
         	
         	la $a0, int3 #reload byte space to primary address
         	move $a0,$t3 # primary address = t0 address (load pointer)
         	li $v0,4 # print string
         	syscall
         	
         	la $a0, int4 #reload byte space to primary address
         	move $a0,$t4 # primary address = t0 address (load pointer)
         	li $v0,4 # print string
         	syscall

         	li $v0,10 #end program
         	syscall


  