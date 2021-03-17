; simple example with 2 rules that reason about how to change speed given 
; an error rating of the speed

(deftemplate speed_error   ; lv  -- linguistic variable
  0 1    ; universe of discourse (range)
  ( ; linguistic term definitions
	(large_positive	 (0 0) (.1 .1) (.2 .2) (.3 .3) (.4 .4)
                         	 (.5 .5) (.6 .6) (.7 .7) (.8 .8) (.9 .9) (1 1))
	(zero	 (0 1)  (.11 0)) 
	(small_positive	 (0 1) (1 0))
  )
)

(deftemplate speed_change   ; lv  -- linguistic variable
    0 1   ; universe of discourse (range)
   ( ; linguistic term definitions
	(large_negative	  (.1 0) (.2 .2) (.3 .4) (.4 .6) (.5 .8) (.6 1))
	(none	  (0 1) (.1 .1) (.2 0)) 
	(small_negative  (.4 1) (.5 .8) (.6 .6) (.7 .4) (.8 .2) (.9 0))
 )
)





(deffacts my_facts
	(speed_error zero) CF .9
)


; NOTE: both of these next 2  rules have a contribution to make to the solution
;            resulting in a fuzzy set for speed-change that reflects this
;            combination after both rules have fired



(defrule speed-too-fast  ; type fuzzy-fuzzy
	(declare (CF .7))

	(speed_error large_positive)   
  =>
	(assert (speed_change large_negative))
)



(defrule speed-ok  ; type fuzzy-fuzzy
	(declare (CF .7))

	(speed_error zero)   
  =>
	(assert (speed_change none))
)



; the next rule takes the result of the previous 2 rules and produces a non-fuzzy
; result to identify the amount (between 0 and 1) to change the speed
; and finally prinst out the de-fuzzified result

(defrule get-crisp-value-and-print-rslt    
	(declare  (salience -1))
             ?sc <- (speed_change ?)
   =>
	(bind ?f (moment-defuzzify ?sc))
	(printout t  "Change speed by a factor of:  " ?f crlf)
)

