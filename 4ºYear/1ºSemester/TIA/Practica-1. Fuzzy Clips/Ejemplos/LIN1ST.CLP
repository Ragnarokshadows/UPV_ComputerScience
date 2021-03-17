;
; Example based on paper by Ying, Siler, and Buckley
;
; 'Fuzzy Control Theory: A Non-linear Case'
;
; Automation, Vol 26, No. 3, pp 513-520
;
; Linear 1st Order Example
;
;
;  creates output in 2 files:
;
;	1. lin_1st.dat  - x,y pairs that when plotted show the fuzzy results
;	2. lin_1st1.dat - x,y pairs that when plotted show the PI results
;
;                            (x,y pairs are actually t,y pairs)
;
;  to plot with the xvgr program type the following
;
;		xvgr -p lin1st.par lin_1st.dat lin_1st1.dat
;


(deftemplate error
   -20 20
    ((negative (-2 1) (2 0) )
     (positive (-2 0) (2 1) )
    )
)

(deftemplate rate
   -20 20
    ((negative (-2 1) (2 0) )
     (positive (-2 0) (2 1) )
    )
)

(deftemplate output
   -2 2      ; note this restricts the output to -2 to + 2 !!!
    ((negative (-2 1) (0 0) )
     (positive (0 0) (2 1) )
     (zero (-2 0) (0 1) (2 0) )
    )
)


(deffacts initial
   (yval 0.0)
   (last_yval 0.0)
   (last_error -3.0)
   (control_val 0.0)
   (residual_ov 0.0)
   (setpoint 3.0)
   (time 0.0)
   (delta_time 0.1)
   (time_const 1.0)
   (GU 24.0)
   (GR 0.04)
   (GE 0.1)
   (Ki 0.3)
   (Kp 0.12)
   (K 1.0)

   (yvalpi 0.0)
   (control_valpi 0.0)
   (residual_ovpi 0.0)
   (last_yvalpi 0.0)
   (last_errorpi -3.0)

)


(defrule init
   (declare (salience 200))

   =>
    (open "lin_1st.dat" ofile "w")
    (printout ofile "0.0, 0.0" crlf)
    (open "lin_1st1.dat" PIfile "w")
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
   (assert (error (PI 0.001 ?GEerr)))
   (assert (rate (PI 0.001 ?GRrate)))

   (retract ?lef ?yvf)
   (assert (last_yval ?y))
   (assert (last_error ?err))

   (bind ?errpi (- ?ypi ?s))
   (bind ?ratepi (/ (- ?errpi ?lepi) ?dt))
   (assert (DU_PI =(- 0 (+ (* ?ki ?errpi) (* ?kp ?ratepi)))))
   (retract ?lefpi ?yvfpi)
   (assert (last_yvalpi ?ypi))
   (assert (last_errorpi ?errpi))
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
;	     G(s) = 1 / (s + T) the Gain 
;
;       and  u(t) = K (the change in the control variable is a step function)
;	so that 
;		U(s) = K/s
;
;		Y(s) = K / (s * (s+T))
;
;	=>  y(t) = K * ( 1-exp(-t/T) )
;
;	This represents the equation for the response to the change in the
;	control variable. K is the factor relating the change in control
;	to the output -- ie. GU * OV
;
;	and T is the time-constant parameter (the time after which the curve
;	reaches 63% of its max value). In this example T = 1.
;		
;
;  Note: Kp=0.12, Ki=0.3 therefore GE chosen as 1 and GR=.4, GU=1.2
;        and L = 2.0
;

(defrule set_yval

  (declare (salience -100))
   
   ?crisp <-    (crisp output ?ov)
   ?rovf <-     (residual_ov ?rov)
   ?lyvf <-     (last_yval ?lyv)
   ?controlf <- (control_val ?cv)
                (GU ?gu)
   ?errf <-     (error ?)
   ?ratef <-    (rate ?)
   ?timef <-    (time ?t)
   		(delta_time ?dt)
		(time_const ?Time_const)
	        (K ?k)

   ?rovfpi <-   (residual_ovpi ?rovpi)
   ?lyvfpi <-   (last_yvalpi ?lyvpi)
   ?controlfpi <- (control_valpi ?cvpi)
   ?DU_PIf <-   (DU_PI ?du_pi)
  =>
   (retract ?controlf ?crisp ?errf ?ratef ?timef ?rovf ?lyvf)
   (bind ?OV (* ?gu ?ov))
   (bind ?CV (+ ?cv ?OV))
   (bind ?OV_ROV (+ ?rov ?OV))
   (bind ?EXPMINUS (- 1 (exp (/ (- 0 ?dt) ?Time_const))))
   (bind ?YVAL (+ ?lyv (* (* ?k ?OV_ROV) ?EXPMINUS)))
   (assert (yval ?YVAL))
   (assert (control_val ?CV))
   (assert (residual_ov =(* (- 1 ?EXPMINUS) ?OV_ROV)))
   (bind ?T (+ ?t ?dt))
   (assert (time ?T))
   (printout ofile ?T ", " ?YVAL crlf)

   (retract ?DU_PIf ?controlfpi ?rovfpi ?lyvfpi)
   (bind ?OV ?du_pi)
   (bind ?CVPI (+ ?cvpi ?OV))
   (bind ?OV_ROV (+ ?rovpi ?OV))
   (bind ?EXPMINUS (- 1 (exp (/ (- 0 ?dt) ?Time_const))))
   (bind ?YVAL (+ ?lyvpi (* (* ?k ?OV_ROV) ?EXPMINUS)))
   (assert (yvalpi ?YVAL))
   (assert (control_valpi ?CVPI))
   (assert (residual_ovpi =(* (- 1 ?EXPMINUS) ?OV_ROV)))
   (printout PIfile ?T ", " ?YVAL crlf)
)

(defrule stop

  (declare (salience 200))

          (setpoint ?s)
          (time ?t)
   ?yv <- (yval ?yval) 

   (test (or (> ?t 25)
             (and (> ?t 10)
                  (< (abs (- ?s ?yval)) 0.03)
             )
         )
   )
  =>
   (close ofile)
   (close PIfile)
   (retract ?yv)  ; this will stop things !!
)

