(defglobal ?*nod-gen* = 0)

(deffacts data
  (path GATE2 GATE4)
  (path GATE4 GATE3)
  (path GATE3 GATE1)
  (path GATE2 CHECK_IN)
  (path GATE1 CHECK_IN)
  (path GATE1 GATE5)
  (path GATE5 GATE7)
  (path GATE7 GATE8)
  (path GATE5 BAGGAGE_CLAIM)
  (path GATE8 GATE6)
  (path GATE6 BAGGAGE_CLAIM)
  (suitcase S1 weight 12 origin CHECK_IN destination GATE3)
  (suitcase S2 weight 18 origin CHECK_IN destination GATE5)
  (suitcase S3 weight 20 origin GATE1 destination BAGGAGE_CLAIM)
  (suitcase S4 weight 14 origin GATE6 destination BAGGAGE_CLAIM)
  (kart T1 0 15)
  (kart T2 15 23)

  (ALC GATE6 1 kart T1 ALC 0 kart T2 GATE2 0
    suitcase S1 CHECK_IN
    suitcase S2 CHECK_IN
    suitcase S3 GATE1
    suitcase S4 GATE6
    Level 0)
)

(defrule loadingSuitcase
  (suitcase ?x weight ?weight origin ?origin $?)
  (kart ?kart ?a ?b)
  (test (<= ?weight ?b))
  (test (> ?weight ?a))
  (ALC ?origin 1 $?e kart ?kart ALC ?n $?g suitcase ?x ?origin $?h Level ?level)
  (max-depth ?prof)
  (test (< ?level ?prof))
  =>
  (assert (ALC ?origin 1 $?e kart ?kart ALC (+ ?n 1) $?g suitcase ?x ?kart $?h Level (+ ?level 1)))
  (bind ?*nod-gen* (+ ?*nod-gen* 1))
)

(defrule unloadingSuitcase
  (suitcase ?x weight ?weight $? destination ?dest)
  (ALC ?dest 1 $?e kart ?kart ALC ?n $?g suitcase ?x ?kart $?h Level ?level)
  (max-depth ?prof)
  (test (< ?level ?prof))
  =>
  (assert (ALC ?dest 1 $?e kart ?kart ALC (- ?n 1) $?g $?h Level (+ ?level 1)))
  (bind ?*nod-gen* (+ ?*nod-gen* 1))
)

(defrule hookKart
  (ALC ?loc 0 $?e kart ?kart ?loc $?g Level ?level)
  (max-depth ?prof)
  (test (< ?level ?prof))
  =>
  (assert (ALC ?loc 1 $?e kart ?kart ALC $?g Level (+ ?level 1)))
  (bind ?*nod-gen* (+ ?*nod-gen* 1))
)

(defrule unhitchingKart
  (ALC ?loc 1 $?e kart ?kart ALC 0 $?g Level ?level)
  (max-depth ?prof)
  (test (< ?level ?prof))
  =>
  (assert (ALC ?loc 0 $?e kart ?kart ?loc 0 $?g Level (+ ?level 1)))
  (bind ?*nod-gen* (+ ?*nod-gen* 1))
)

(defrule moveALC
  (ALC ?loc $?e Level ?level)
  (max-depth ?prof)
  (test (< ?level ?prof))
  (or (path ?loc ?x) (path ?x ?loc))
  =>
  (assert (ALC ?x $?e Level (+ ?level 1)))
  (bind ?*nod-gen* (+ ?*nod-gen* 1))
)

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
  (assert (max-depth ?prof))
)

(defrule goal
    (declare (salience 100))
    ?f<-(ALC $? kart ?kart ?loc ?x Level ?n)
   =>
    (printout t "SOLUTION FOUND AT LEVEL " ?n crlf)
    (printout t "NUMBER OF EXPANDED NODES OR TRIGGERED RULES " ?*nod-gen* crlf)
    (printout t "GOAL FACT " ?f crlf)

    (halt)
)

(defrule no_solution
    (declare (salience -99))
    ?f<-(ALC $? Level ?n)

=>
    (printout t "SOLUTION NOT FOUND" crlf)
    (printout t "NUMBER OF EXPANDED NODES OR TRIGGERED RULES " ?*nod-gen* crlf)

    (halt))
