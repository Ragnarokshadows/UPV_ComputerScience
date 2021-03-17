;    fzCmplr.clp
;
; An implementation of the example given in the paper 'A Compiler for Fuzzy Logic 
; Controllers' by P. Bonissone in Fuzzy Eng toward Human Friendly Systems Vol 2
; pp. 706-717
;
; Does not do any compile as described, just outputs the results of the controller
; over a range of input values for comparison.
; (output to file fzCmplr.dat)

; Note: in FuzzyCLIPS we use centre of gravity (COG) to defuzzify
;

; Define the fuzzy linguistic variable needed -- 2 inputs (temp and pressure) and
; 1 output (throttle)

; perhaps rather than use the pi function to describe the fuzzy sets we should use 
; some traingular fuzzy sets via a singleton description to be more like those of the paper
;
;    eg.    (PI 20 50)  can be represented as singletons  (30 0) (50 1) (70 0)
;
;           The PI, Z and S functions generate 9 singletons to represent an
;           approximation of the curves.
;

(deftemplate temp
  0 100 C
    ((low (PI 20 20))
     (med (PI 20 50))
     (high (PI 20 80))
    )
)

(deftemplate pressure
  0 500 kPa
    ((low (PI 100 100))
     (med (PI 100 250))
     (high (PI 100 400))
    )
)

(deftemplate throttle
  0 1 units
    ((very_low (PI .1 .1))
     (low      (PI .15 .25))
     (mid_low  (PI .1 .4))
     (med      (PI .15 .55))
     (mid_high (PI .1 .75))
     (high     (PI .1 .9))
    )
)


; Now define the rules that work to set the value of the throttle given a set of
; inputs  (9 rules)

(defrule low_low
   (temp low)
   (pressure low)
  =>
   (assert (throttle high))
)

(defrule low_med
   (temp low)
   (pressure med)
  =>
   (assert (throttle med))
)

(defrule low_high
   (temp low)
   (pressure high)
  =>
   (assert (throttle mid_low))
)

(defrule med_low
   (temp med)
   (pressure low)
  =>
   (assert (throttle mid_high))
)

(defrule med_med
   (temp med)
   (pressure med)
  =>
   (assert (throttle mid_low))
)

(defrule med_high
   (temp med)
   (pressure high)
  =>
   (assert (throttle low))
)

(defrule high_low
   (temp high)
   (pressure low)
  =>
   (assert (throttle mid_low))
)

(defrule high_med
   (temp high)
   (pressure med)
  =>
   (assert (throttle low))
)

(defrule high_high
   (temp high)
   (pressure high)
  =>
   (assert (throttle very_low))
)


; rules to control the setting of values to test and produce the outputs

(defrule init
  
  =>
   (open "fzCmplr.dat" fzctl "w")
   (format fzctl "Temp       5   10   15   20   25   30   35   40   45   50   55   60   65   70   75   80   85   90   95%nPress%n  10   ")
   (assert (temp (PI .5 5))
           (pressure (PI .5 10))
   )
)

(defrule match_temp_throttle
   (declare
	(salience -100)
   )
   ?t <- (temp ?)
   ?th <- (throttle ?)
  =>
   (assert (crisp temp (moment-defuzzify ?t)))
   (assert (crisp throttle (moment-defuzzify ?th)))
   (retract ?t ?th)
)

(defrule next_temp
   ?ct <- (crisp temp ?t)
   ?p <- (pressure ?)
   ?cth <- (crisp throttle ?th)
  =>
   (format fzctl "%5.2f" ?th)
   (if (>= ?t 95)
       then
	   (bind ?t 5)
	   (assert (crisp pressure (moment-defuzzify ?p)))
	   (retract ?p)
       else
	   (bind ?t (+ 5 (integer (+ 0.1 ?t))))
    )
           (assert (temp (PI .5 ?t)))  
           (retract ?ct ?cth)
)


(defrule next_press
   ?cp <- (crisp pressure ?p)
  =>
    (retract ?cp)
    (bind ?p (+ 10 (integer (+ 0.1 ?p))))
    (if (> ?p 490)
         then
	   (format fzctl "%n%n")
	   (close fzctl)
	   (halt)
        else
           (format fzctl "%n %3d   " ?p)
           (assert (pressure (PI .5 ?p)))
    )
)


