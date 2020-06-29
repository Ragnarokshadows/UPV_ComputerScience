(deffacts data
    (number 5)
    (factorial 1))

 (defrule rule1
    ?s1 <- (factorial ?x)
    ?s2 <- (number ?y)
    (test (> ?y 1))
  =>
    (retract ?s1)
    (retract ?s2)
    (assert (number (- ?y 1)))
    (assert (factorial (* ?x ?y))))
