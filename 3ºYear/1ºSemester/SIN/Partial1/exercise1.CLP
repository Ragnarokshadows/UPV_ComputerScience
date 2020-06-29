(deffacts data
  (list 15 2 4 6 5 3 7 8 15 10)
)
(defrule R1
  ?f <-(list $?x ?y $?z)
  (test (<> ?y 0))
  (test (<> (+ (length $?x) 1) ?y))
  =>
  (assert (list $?x 0 $?z))
  (retract ?f)
  )
