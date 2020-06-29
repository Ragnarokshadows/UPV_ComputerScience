
  (deffacts data
      (number 7)
      (number 18)
      (list 2 14 90)
      (number 45))

  (defrule R1
     (number ?x)
   =>
      (printout t "The number is " ?x crlf))