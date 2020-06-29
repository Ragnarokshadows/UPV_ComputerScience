(deffacts data
  (winning-board 2 5 8 13 24 35)
  (chosen-numbers 3 5 15 24 26 37)
  (win 0)
)
(defrule R1
  (winning-board $? ?y $?)
  ?f2 <- (chosen-numbers $?x ?y $?z)
  ?f <- (win ?w)
  =>
  (assert (win (+ ?w 1)))
  (assert (chosen-numbers $?x $?z))
  (retract ?f)
  (retract ?f2)
  )
