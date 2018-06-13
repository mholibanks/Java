#Question3
#Mholi Mncube
#21/10/2017


.data 

inmsg:	.asciiz "Enter a series of 5 formulae:\n"
outmsg:  .asciiz "The values are:\n"
zero:	.asciiz "0"
one: 	.asciiz "1"
two: 	.asciiz "2"
three: 	.asciiz "3"
name: .space 50
equals: .asciiz "="

.text
.globl main
main:

	la $s0, 0
	la $s2, 0 
	li $s3, 0
				
	#counter  
	li $s4, 5
	li $v0, 4

 	#prints the input message	 
	la $a0, inmsg 
	syscall


input:

	beq $s3, $s4, done

	#reads the input and stores the input into memory
	li $v0,8
	la $a0,name($s0)
	syscall	

	lb $t0,name($s0)
	lb $t1,equals
	  
	# checks if there is a equals sign for reference
 	beq $t0,$t1, ref 
 
	addi $s3, $s3, 1
	addi $s0, $s0, 4

 j input

ref:
	
	move $t2,$s0
	addi $t2,$t2,1 
	lb $t4, one
	lb $t5, two
	lb $t6, three
	lb $t7, zero
	lb $t3, name($t2)
 	beq $t3,$t7, assign0
	beq $t3,$t4, assign1
	beq $t3,$t5, assign2
	beq $t3,$t6, assign3

j input

assign0:
	
	li $t6, 0
	lw $t5, name($t6)
	sw $t5, name($s0)
	addi $s3, $s3, 1
	addi $s0, $s0, 4

j input

assign1:

	li $t6, 4
	lw $t5, name($t6)
	sw $t5, name($s0)
	addi $s3, $s3, 1
	addi $s0, $s0, 4

j input

assign2:

	li $t6, 8
	lw $t5, name($t6)
	sw $t5, name($s0)
	
	addi $s3, $s3, 1
	addi $s0, $s0, 4

j input

assign3:
	li $t6, 12
	lw $t5, name($t6)
	sw $t5, name($s0)
	addi $s3, $s3, 1
	addi $s0, $s0, 4
j input

done:
	
li $s3, 0				# re initalise the counter
li $v0,4 				#prints out the output message
la $a0, outmsg
syscall

output:

	li $v0,4 				 
	la $a0, name($s2)
	syscall

	addi $s3, $s3, 1
	addi $s2, $s2, 4

        bne $s3,$s4, output

jr $ra