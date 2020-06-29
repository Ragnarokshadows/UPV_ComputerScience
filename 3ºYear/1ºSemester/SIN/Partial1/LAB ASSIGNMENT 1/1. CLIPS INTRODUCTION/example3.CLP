
  (deffacts data
      (number 7)
      (number 18)
      (list 2 14 90)
      (number 45))

  (defrule R1
     (number ?x)
     (number ?y)
   =>
      (printout t "The first number is " ?x " and the second number is " ?y crlf))


   (defrule R2
      (list $?x ?num $?y)
    =>
      (printout t "The number is " ?num crlf))