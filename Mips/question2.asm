#Question 2
#Mholi Mncube 
#20/10/2017

.data
  inmsg: .asciiz "Enter a string:\n"
  outmsg: .asciiz "The value +5 is:\n"
  hold: .space 100

.text
 
main:
  #print input message
    li $v0,4
    la $a0,inmsg
    syscall

  #get rid of string
     li $v0,12
     syscall

  #hold onto Int
     li $v0,5
     syscall

     move $s1,$v0
     addi $s1,$s1,5

  #print output msg
    li $v0,4
    la $a0,outmsg
    syscall

  #output Int
     move $a0,$s1
     li $v0,1
     syscall

  jr $ra
