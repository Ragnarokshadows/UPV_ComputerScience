(deffacts data
	(list 2 1 5 3)
)
(defrule R1 
(declare (salience 200)) 
?f <- (list $?x ?z ?y $?w) 
(test (< ?z ?y)) 
=> =>
(assert (list $?x ?z ?y $?w))) 

(defrule R2
(declare (salience 50))
?f <- (list $?x ?z ?y $?w)
(test (>= ?z ?y))
(assert (list $?x ?z ?y $?w)))

(defrule final
(declare (salience 150))
(list $?list)
=>
(halt))