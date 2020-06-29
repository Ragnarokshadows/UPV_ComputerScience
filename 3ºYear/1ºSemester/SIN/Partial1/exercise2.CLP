(deffacts data
  (list 1 3 4 5 6 7 4 6 4 4 3 4 5 6 4 5 6 4 8 5 7 3 2 4 4 4 1 5 6 7 1 2 3 2 3 4 6 5 3 4 5 1 4 3)
)
(defrule R1
  ?f <-(list $?x ?y $?z ?y $?w)
  =>
  (assert (list $?x ?y $?z $?w))
  (retract ?f)
  )
