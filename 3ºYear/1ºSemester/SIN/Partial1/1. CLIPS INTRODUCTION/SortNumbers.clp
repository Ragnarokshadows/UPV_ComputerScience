  (deffacts data
      (list 4 5 3 46 12 10))

  (defrule sort
    ?f1 <- (list $?x ?y ?z $?w)
	   (test (< ?z ?y))    ;; check if ?z is less than ?y
	=>
	   (retract ?f1)
	   (assert (list $?x ?z ?y $?w)))   ;; exchange elements
