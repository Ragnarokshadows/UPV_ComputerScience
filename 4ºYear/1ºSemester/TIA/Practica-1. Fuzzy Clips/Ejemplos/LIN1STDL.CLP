;
; Example based on paper by Ying, Siler, and Buckley
;
; 'Fuzzy Control Theory: A Non-linear Case'
;
; Automation, Vol 26, No. 3, pp 513-520
;
; Linear 1st Order Example (with time delay  -- tau)

;
; note: this is not a very general implementation of a 1st order system
;       with time delay --- we assume the delay is 0.2 and 
;       sampling is 0.1
;
;
;  creates output in 2 files:
;
;	1. lin1std.dat  - x,y pairs that when plotted show the fuzzy results
;	2. lin1std1.dat - x,y pairs that when plotted show the PI results
;
;                            (x,y pairs are actually t,y pairs)
;


(deftemplate error
   -20 20
;    ((negative (z -2 2))
;     (positive (s -2 2))
;    )
    ((negative (-2.01 1) (-2 1) (2 0) (2.01 0) )
     (positive (-2.01 0) (-2 0) (2 1) (2.01 1) )
    )
)

(deftemplate rate
   -20 20
;    ((negative (z -2 2))
;     (positive (s -2 2))
;    )
    ((negative (-2.01 1) (-2 1) (2 0) (2.01 0) )
     (positive (-2.01 0) (-2 0) (2 1) (2.01 1) )
    )
)

(deftemplate output
   -2 2
;    ((negative (z -2 0))
;     (positive (s 0 2))
;     (zero (O 2 0))
;    )
    ((negative  (-2 1) (0 0) )
     (positive  (0 0) (2 1) )
     (zero (-2 0) (0 1) (2 0) )
    )
)


(deffacts initial
   (yval 0.0)
   (last_yval 0.0)
   (control_val 0.0)
   (setpoint 3.0)
   (time 0.0)
   (delta_time 0.1)
   (time_const 1.0)
   (GU 4.0)
   (GR 0.3)
   (GE 1.0)
   (Ki 1.0)
   (Kp 0.3)
   (K 1.0)
   (last_error -3.0)

   (yvalpi 0.0)
   (control_valpi 0.0)
   (last_yvalpi 0.0)
   (last_errorpi -3.0)

   (tau 0.2)

   (K0 0.0)
   (K1 0.0)
   (K2 0.0)
   (K0pi 0.0)
   (K1pi 0.0)
   (K2pi 0.0)

)


(defrule init
   (declare (salience 200))

   =>
    (open "lin1std.dat" ofile "w")
    (printout ofile "0.0, 0.0" crlf)
    (open "lin1std1.dat" PIfile "w")
    (printout PIfile "0.0, 0.0" crlf)

)


;
; When a yval is calculated then can determine the error and rate
;
;   error = yval - setpoint
;	  = ?y - ?s
;
;   rate = (current_error - last_error)
;	 = (?err - ?le)
;
; Note: error is scaled by GE and rate is scaled by GR
;


(defrule fuzzyfy_yval

  (declare (salience 100))

            (setpoint ?s)
	    (delta_time ?dt)
            (GR ?gr)
            (GE ?ge)
	    (Ki ?ki)
	    (Kp ?kp)
    ?yvf <- (yval ?y)
    ?lef <- (last_error ?le)

    ?yvfpi <- (yvalpi ?ypi)
    ?lefpi <- (last_errorpi ?lepi)
  =>
   (bind ?err (- ?y ?s))
   (bind ?GEerr (* ?ge ?err))
   (bind ?rate (/ (- ?err ?le) ?dt))
   (bind ?GRrate (* ?gr ?rate))
;
; when using asserts such as 
;
;   (assert (error (PI 0.01 ?GEerr)))
;   (assert (rate (PI 0.01 ?GRrate)))
;
; must be careful since it may be that ?GEerr is at limits of
; universe of discourse (-20 or 20) and we would get error message
; since trying to build a fuzzy set with points outside of limits
;
;
   (assert (error (PI 0.01 ?GEerr)))
   (assert (rate (PI 0.01 ?GRrate)))

   (retract ?lef ?yvf)
   (assert (last_yval ?y))
   (assert (last_error ?err))

   (bind ?errpi (- ?ypi ?s))
   (bind ?ratepi (/ (- ?errpi ?lepi) ?dt))
   (assert (DU_PI =(- 0 (+ (* ?ki ?errpi) (* ?kp ?ratepi)))))
   (retract ?lefpi ?yvfpi)
   (assert (last_errorpi ?errpi))
   (assert (last_yvalpi ?ypi))
)


(defrule rule_pos_pos
   (error positive)
   (rate positive)
  =>
   (assert (output negative))
)


(defrule rule_pos_neg
   (error positive)
   (rate negative)
  =>
   (assert (output zero))
)


(defrule rule_neg_pos
   (error negative)
   (rate positive)
  =>
   (assert (output zero))
)


(defrule rule_neg_neg
   (error negative)
   (rate negative)
  =>
   (assert (output positive))
)






;
; de-fuzzyfy the output and get next value for the y value
;

(defrule next_output

  (declare (salience -100))
  
   ?of <- (output ?)
  =>
   (assert (crisp output (moment-defuzzify ?of)))
   (retract ?of)
)




;
; Y(s) / U(s) = 1 / (s + T)   OR  Y(s) = U(s) / (s + T)
;
;  Therefore,	since this is a Laplace transform Y(s) = G(s) * U(s)
;
;	with Y(s) the output response
;	     U(s) control variable response after a change
;	     G(s) = exp(-tau*s) / (s + T) the Gain 
;
;       and  u(t) = K (the change in the control variable is a step function)
;	so that 
;		U(s) = K/s
;
;		Y(s) = (K * exp(-tau*s)) / (s * (s+T))
;
;	=>  y(t) = K * ( 1-exp(-(t-tau)/T) )  (tau = 0.2 seconds delay)
;
;	This represents the equation for the response to the change in the
;	control variable. K is the factor relating the change in control
;	to the output -- ie. GU * OV
;
;	and T is the time-constant parameter (the time after which the curve
;	reaches 63% of its max value). In this example T = 1.

;		
;
;  Note: Kp=0.3, Ki=1.0 therefore GE chosen as 1 and GR=.3, GU=4
;        and L = 2.0
;

(defrule set_yval

  (declare (salience -100))
   
   ?crisp <-    (crisp output ?ov)
   ?lyvf <-     (last_yval ?lyv)
   ?controlf <- (control_val ?cv)
                (GU ?gu)
   ?errf <-     (error ?)
   ?ratef <-    (rate ?)
   ?timef <-    (time ?t)
   		(delta_time ?dt)
		(time_const ?Time_const)
   ?K1f <-      (K1 ?k1)
   ?K2f <-      (K2 ?k2)
   ?K1fpi <-    (K1pi ?k1pi)
   ?K2fpi <-    (K2pi ?k2pi)
		(K ?k)

   ?lyvfpi <-   (last_yvalpi ?lyvpi)
   ?controlfpi <- (control_valpi ?cvpi)
   ?DU_PIf <-   (DU_PI ?du_pi)
  =>
   (retract ?controlf ?crisp ?errf ?ratef ?timef ?lyvf)
   (retract  ?K1f ?K2f)
   (bind ?OV (* ?gu ?ov))
   (bind ?CV (+ ?cv ?OV))
   (bind ?EXPMINUS (- 1 (exp (/ (- 0 ?dt) ?Time_const))))
   (bind ?YVAL (+ ?lyv (* (* ?k ?k1) ?EXPMINUS)))
   (assert (yval ?YVAL))
   (assert (control_val ?CV))
   (assert 
	   (K1 ?k2)
           (K2 =(+ ?OV (* (- 1 ?EXPMINUS) ?k2)))
   )
   (bind ?T (+ ?t ?dt))
   (assert (time ?T))
   (printout ofile ?T ", " ?YVAL crlf)

   (retract ?DU_PIf ?controlfpi ?lyvfpi)
   (retract  ?K1fpi ?K2fpi)
   (bind ?OV ?du_pi)
   (bind ?CVPI (+ ?cvpi ?OV))
   (bind ?EXPMINUS (- 1 (exp (/ (- 0 ?dt) ?Time_const))))
   (bind ?YVAL (+ ?lyvpi (* (* ?k ?k1pi) ?EXPMINUS)))
   (assert (yvalpi ?YVAL))
   (assert (control_valpi ?CVPI))
   (assert 
	   (K1pi ?k2pi)
           (K2pi =(+ ?OV (* (- 1 ?EXPMINUS) ?k2pi)))
   )
   (printout PIfile ?T ", " ?YVAL crlf)
)

(defrule stop

  (declare (salience 200))

          (setpoint ?s)
          (time ?t)
   ?yv <- (yval ?yval) 

   (test (or (>= ?t 15)
             (and (> ?t 10)
                  (< (abs (- ?s ?yval)) 0.01)
             )
         )
   )
  =>
   (close ofile)
   (close PIfile)
   (retract ?yv)  ; this will stop things !!
)

