;; =========================================================
;; ===      B R E A D T H     A N D      D E P T H      ====
;; =========================================================

(defglobal ?*nod-gen* = 0)


(defrule right
  ?f<-(puzzle $?x 0 ?y $?z level ?level movement ?mov fact ?)
  (max-depth ?prof)
  (test (and (<> (length$ $?x) 2) (<> (length$ $?x) 5)))
  (test (neq ?mov left))
  (test (< ?level ?prof))
  =>
  (assert (puzzle $?x ?y 0 $?z level (+ ?level 1) movement right fact ?f))
  (bind ?*nod-gen* (+ ?*nod-gen* 1)))


(defrule left
  ?f<-(puzzle $?x ?y 0 $?z level ?level movement ?mov fact ?)
  (max-depth ?prof)
  (test (and (<> (length$ $?x) 2) (<> (length$ $?x) 5)))
  (test (neq ?mov right))
  (test (< ?level ?prof))
  =>
  (assert (puzzle $?x 0 ?y $?z level (+ ?level 1) movement left fact ?f))
  (bind ?*nod-gen* (+ ?*nod-gen* 1)))


(defrule down
  ?f<-(puzzle $?x 0 ?a ?b ?c $?z level ?level movement ?mov fact ?)
  (max-depth ?prof)
  (test (neq ?mov up))
  (test (< ?level ?prof))
  =>
  (assert (puzzle $?x ?c ?a ?b 0 $?z level (+ ?level 1) movement down fact ?f))
  (bind ?*nod-gen* (+ ?*nod-gen* 1)))


(defrule up
  ?f<-(puzzle $?x ?a ?b ?c 0 $?y level ?level movement ?mov fact ?)
  (max-depth ?prof)
  (test (neq ?mov down))
  (test (< ?level ?prof))
  =>
  (assert (puzzle $?x 0 ?b ?c ?a $?y level (+ ?level 1) movement up fact ?f))
  (bind ?*nod-gen* (+ ?*nod-gen* 1)))


;; ========================================================
;; =========      S E A R C H   S T R A T E G Y    ========
;; ========================================================
;; The goal rule is used to detect when the goal state has been reached 

(defrule goal
    (declare (salience 100))
    ?f<-(puzzle 1 2 3 8 0 4 7 6 5 level ?n movement ?mov fact ?)
    
   =>
    (printout t "SOLUTION FOUND AT LEVEL " ?n crlf)
    (printout t "NUMBER OF EXPANDED NODES OR TRIGGERED RULES " ?*nod-gen* crlf)
    (printout t "GOAL FACT " ?f crlf)
    
    (halt))

(defrule no_solution
    (declare (salience -99))
    (puzzle $? level ?n $?)
    
=>
    (printout t "SOLUTION NOT FOUND" crlf)
    (printout t "NUMBER OF EXPANDED NODES OR TRIGGERED RULES " ?*nod-gen* crlf)
    
    (halt))		


(deffunction start ()
        (reset)
	(printout t "Maximum depth:= " )
	(bind ?prof (read))
	(printout t "Search strategy " crlf "    1.- Breadth" crlf "    2.- Depth" crlf )
	(bind ?a (read))
	(if (= ?a 1)
	       then    (set-strategy breadth)
	       else   (set-strategy depth))
        (printout t " Execute run to start the program " crlf)
	;;(assert (puzzle 2 8 3 1 6 4 7 0 5 level 0 movement null fact 0))
	(assert (puzzle 8 1 3 7 2 5 4 0 6 level 0 movement null fact 0))
	(assert (max-depth ?prof))
	
)

(deffunction path
	(?f)
	(bind ?list (fact-slot-value ?f implied))
	(bind ?l2 (member$ level ?list))
	(bind ?n (nth (+ ?l2 1) ?list)) 
	;;(printout t "Nivel=" ?n crlf)
	(bind ?dir (nth (length ?list) ?list))
	(bind ?mov (subseq$ ?list (+ ?l2 3) (- (length ?list) 2))) 
	(bind ?path (create$ ?dir ?mov))
	;;(printout t ?dir "    " ?mov crlf)

	(loop-for-count (- ?n 1) 
		(bind ?list (fact-slot-value (fact-index ?dir) implied))
		(bind ?dir (nth (length ?list) ?list))
		(bind ?l2 (member$ level ?list))
		(bind ?mov (subseq$ ?list (+ ?l2 3) (- (length ?list) 2)))
		(bind ?path (create$ ?dir ?mov ?path)) 
	)

	(printout t "Path: " ?path crlf)
)
