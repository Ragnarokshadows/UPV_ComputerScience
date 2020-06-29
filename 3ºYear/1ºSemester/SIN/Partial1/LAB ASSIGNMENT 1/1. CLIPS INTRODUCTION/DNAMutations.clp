  (deffacts data
      (ADN 1 A A C C T C G A A A)
      (ADN 2 A G G C T A G A A A)
      (mutations 0))

  (defrule R_mutation
    ?f1 <-   (ADN ?n1 $?x ?i1 $?y1)
    ?f2 <-   (ADN ?n2 $?x ?i2 $?y2)
    ?f3 <-   (mutations ?m)
       (test (and (neq ?n1 ?n2)(neq ?i1 ?i2)))
    =>
       (retract ?f1 ?f2 ?f3)
       (assert (ADN ?n1 $?y1))
       (assert (ADN ?n2 $?y2))
       (assert (mutations (+ ?m 1))))


  (defrule final
    (declare (salience -10))
     (mutations ?m)
   =>
     (printout t "The number of mutations is " ?m crlf))
