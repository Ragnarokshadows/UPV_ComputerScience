;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Global definitions used in the simulation
;;

(defglobal 

   ?*coldValvePos*     = 0.0
   ?*hotValvePos*      = 0.0
   ?*coldTemp*         = 0.0
   ?*hotTemp*          = 0.0
   ?*coldPress*        = 0.0
   ?*hotPress*         = 0.0
   ?*optimalTempMin*   = 34.0
   ?*optimalTempMax*   = 38.0
   ?*optimalFlowMin*   = 11.0
   ?*optimalFlowMax*   = 13.0
   ?*atmosphericPress* = 30.0
   ?*iterationFactor*  = 1.0
)



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Deftemplates below define the fuzzy variables being used
;;
;;
;; Outputs of the shower
;;
;;   outTemp  -- output temperature
;;
;;  outFlow   -- flow out of the shower

(deftemplate outTemp
   5 65 Celcius
  ((cold  (z 10 35))
   (OK    (pi  2 36))
   (hot   (s 37  60)) )
)

(deftemplate outFlow
   0 100 liters/minute
  ((low    (z 3 11.5))
   (OK     (pi  1 12))
   (strong (s 12.5  25)) )
)
  
  
;; controls for hot and cold valve positions
;;


(deftemplate change_vc
   -1 1
  ((NB (-0.5 1) (-.25 0))
   (NM  (-.35 0) (-.3 1) (-.15 0))
   (NS (-.25 0) (-.15 1) (0 0))
   (Z (-.05 0) (0 1) (.05 0))
   (PS (0 0) (.15 1) (.25 0))
   (PM (.15 0) (.3 1) (.35 0))
   (PB (.25 0)(0.5 1))
  )
)


(deftemplate change_vh
   -1 1
  ((NB (-0.5 1) (-.25 0))
   (NM  (-.35 0) (-.3 1) (-.15 0))
   (NS (-.25 0) (-.15 1) (0 0))
   (Z (-.05 0) (0 1) (.05 0))
   (PS (0 0) (.15 1) (.25 0))
   (PM (.15 0) (.3 1) (.35 0))
   (PB (.25 0)(0.5 1))
  )
)



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Some supporting functions
;;
;; getNumber  - requests a number in a given range
;;
;; ask-question - asks a question and gets answer from provided 
;;                allowed responses
;;
;; yes-or-no-p - requests a yes or no answer to given question
;;               (uses ask-question)
;;
;; initShower - initializes certain global values and requests some
;;              initial values from the user and calls simulate to
;;              calc the out flow and temp and assert these as
;;              fuzzy facts to start the fuzzy rules going
;;
;; Simulate   - calculates the new outFlow and outTemp when changes
;;              to valve positions are made -- also asserts the
;;              fuzzified facts representing these new values --
;;              also checks for finished state (temp and flow in range
;;              or for certain error conditions
;;


(deffunction getNumber (?question ?from ?to)
  (printout t ?question " [" ?from " to " ?to "]: ")
  (bind ?answer (read))
  (while (not (and (numberp ?answer)
                   (>= ?answer ?from)
                   (<= ?answer ?to)
              )
         )
    do
      (printout t "Please enter a numeric answer within the specified range" crlf 
                  ?question " [" ?from " to " ?to "]: ")
      (bind ?answer (read))
  )
  ?answer
)


(deffunction ask-question (?question $?allowed-values)
   (printout t ?question)
   (bind ?answer (read))
   (if (lexemep ?answer) 
       then (bind ?answer (lowcase ?answer)))
   (while (not (member ?answer ?allowed-values)) do
      (printout t ?question)
      (bind ?answer (read))
      (if (lexemep ?answer) 
          then (bind ?answer (lowcase ?answer))))
   ?answer)


(deffunction yes-or-no-p (?question)
   (bind ?response (ask-question ?question yes no y n))
   (if (or (eq ?response yes) (eq ?response y))
       then TRUE 
       else FALSE))



(deffunction Simulate (?coldValveChange ?hotValveChange)

  ;; Check for some rather bad situations where control is lost
  ;;
  (if (and (= ?*coldValvePos* 1.0) (> ?coldValveChange 0.0) (>= ?hotValveChange 0.0))
    then
      (printout t crlf "*** Cold Water Problem -- Cold valve full open and trying to open more ***" crlf
                       "Stopping simulation" crlf)
                       
      (bind ?*iterationFactor* 1.0)  ;; reset iteration Factor
      (halt)
  )
  (if (and (= ?*hotValvePos* 1.0) (> ?hotValveChange 0.0) (>= ?coldValveChange 0.0))
    then
      (printout t crlf "*** Hot Water Problem -- Hot valve full open and trying to open more ***" crlf
                       "Stopping simulation" crlf)
                       
      (bind ?*iterationFactor* 1.0)  ;; reset iteration Factor
      (halt)
  )
  (if (and (= ?*coldValvePos* 0.0) (< ?coldValveChange 0.0) (< (abs  ?hotValveChange) 0.000001)) 
    then
      (printout t crlf "*** HOT TEMPERATURE PROBLEM ***" crlf
                       "Shutting down - cannot get temperature high enough" crlf )                       

      (bind ?*iterationFactor* 1.0)  ;; reset iteration Factor
      (halt)
  )
  (if (and (= ?*hotValvePos* 0.0) (< ?hotValveChange 0.0) (< (abs ?coldValveChange) 0.000001)) 
    then
      (printout t crlf "*** HOT TEMPERATURE PROBLEM ***" crlf
                       "Shutting down - cannot get temperature low enough" crlf )

      (bind ?*iterationFactor* 1.0)  ;; reset iteration Factor
      (halt)
  )
  (if (and (= ?*hotValvePos* 0.0) (< ?hotValveChange 0.0) 
           (= ?*coldValvePos* 1.0)(> ?coldValveChange 0.0)) 
    then
      (printout t crlf "*** HOT TEMPERATURE PROBLEM ***" crlf
                       "Shutting down - cannot get temperature low enough" crlf )

      (bind ?*iterationFactor* 1.0)  ;; reset iteration Factor
      (halt)
  )
  (if (and (= ?*hotValvePos* 1.0) (> ?hotValveChange 0.0) 
           (= ?*coldValvePos* 0.0)(< ?coldValveChange 0.0)) 
    then
      (printout t crlf "*** HOT TEMPERATURE PROBLEM ***" crlf
                       "Shutting down - cannot get temperature high enough" crlf )

      (bind ?*iterationFactor* 1.0)  ;; reset iteration Factor
      (halt)
  )
  
  ;; calc new hot and cold valve positions based on the recommendations
  ;; for change provided 
  ;; NOTE: we perform a scaling factor on the recommendations making the assumption
  ;;       that at high valve settings a change will produce less effect than
  ;;       if we are operating at a low valve setting; this is due to the effect
  ;;       of pressures -- low pressures will tend to put us at higher valve
  ;;       positions; of course in a REAL situation the actual operating conditions
  ;;       of the system would likely be known and we could adjust accordingly;
  ;;       One problem that can occur is that the adjustments cause the system
  ;;       to be too course in its adjustment and it makes a change to go up and then
  ;;       a symmetric change to go down -- it will then endlessly flip flop between
  ;;       a too high and a too low condition -- this is solved by reducing the scale
  ;;       factor after each iteration so the adjustments get smaller and smaller.
  ;;       Also note that we could also tune the fuzzy sets to reduce the range 
  ;;       of changes recommended.
  ;;       REMEMBER the conditions of the problem -- we do not know the temps or
  ;;       pressures of hot and cold supplies
  
  (if (or (<> ?coldValveChange 0.0) (<> ?hotValveChange 0.0))
     then
       (bind ?*coldValvePos* 
                 (max 0.0
                      (min 1.0 
                           (+ ?*coldValvePos* 
                              (* (* (max 0.1 ?*coldValvePos*) ?*iterationFactor*)
                                 ?coldValveChange
                              )
                           )
                      )
                 )
       )
       (bind ?*hotValvePos* 
                 (max 0.0
                      (min 1.0 
                           (+ ?*hotValvePos* 
                              (* (* (max 0.1 ?*hotValvePos*) ?*iterationFactor*)
                                 ?hotValveChange
                              )
                           )
                      )
                 )
       )
       (format t "Cold Valve Position = %5.3f, Hot  Valve Position = %5.3f %n" 
                  ?*coldValvePos* ?*hotValvePos* 
       )
   )
   (bind ?hotFlow (* ?*hotValvePos* (- ?*hotPress* ?*atmosphericPress*)))
   (bind ?coldFlow (* ?*coldValvePos* (- ?*coldPress* ?*atmosphericPress*)))
   (bind ?outFlow (+ ?hotFlow ?coldFlow))
   (if (= ?outFlow 0.0)
      then  ;; system is OFF
         (assert (Shower is OFF)) 
         (bind ?outTemp 5.0)
      else
         (bind ?outTemp (/ (+ (* ?coldFlow ?*coldTemp*)
                              (* ?hotFlow ?*hotTemp*))
                           ?outFlow)
         )
   )
   (format t "Output Flow is %6.3f litres/sec%n" ?outFlow)
   (if (= ?outFlow 0.0)
      then  ;; system is OFF  
         (format t "Output Temp is 'UNKNOWN' %n")
    else
         (format t "Output Temp is %6.3f degrees C%n" ?outTemp)
   )

  ;; ONLY do next tests if outflow not zero -- ie. both valves off
  (if (!= ?outFlow 0.0)
     then
      ;; if hot valve off and temp too high OR cold valve off and temp too low
      ;; cannot control the shower 
      (if (and (= ?*hotValvePos* 0.0) (> ?outTemp ?*optimalTempMax*))  
        then
          (printout t crlf "*** HOT TEMPERATURE PROBLEM ***" crlf
                       "Shutting down - cannot get temperature low enough" crlf )

          (bind ?*iterationFactor* 1.0)  ;; reset iteration Factor
          (halt)
      )
      (if (and (= ?*coldValvePos* 0.0) (< ?outTemp ?*optimalTempMin*)) 
        then
          (printout t crlf "*** HOT TEMPERATURE PROBLEM ***" crlf
                       "Shutting down - cannot get temperature high enough" crlf )                       

          (bind ?*iterationFactor* 1.0)  ;; reset iteration Factor
          (halt)
      )
   )
  
   ;; if both output flow and temp are within correct range control is
   ;; completed -- pause to ask for changes to input temps or pressures
   ;; or to quit
   ;;
   
   (if (and (and (> ?outFlow ?*optimalFlowMin*) (< ?outFlow ?*optimalFlowMax*))
            (and (> ?outTemp ?*optimalTempMin*) (< ?outTemp ?*optimalTempMax*))
       )
      then    
        (printout t "Shower is under control!" crlf)
        (bind ?*iterationFactor* 1.0)  ;; reset iteration Factor
        (if (yes-or-no-p "Would you like to change some parameters? ")
          then
            (bind ?*coldTemp* (getNumber "New Temperature (Celcius) of cold water input?" 5 65))
            (bind ?*hotTemp* (getNumber "New Temperature (Celcius) of hot water input?" 5 65))
            (bind ?*coldPress* (getNumber "New Pressure (kPa) of cold water input?" 42 100))
            (bind ?*hotPress* (getNumber "New Pressure (kPa) of hot water input?" 42 100))       
            ;; need to recalc Flow and Temp since things changed
            (bind ?hotFlow (* ?*hotValvePos* (- ?*hotPress* ?*atmosphericPress*)))
            (bind ?coldFlow (* ?*coldValvePos* (- ?*coldPress* ?*atmosphericPress*)))
            (bind ?outFlow (+ ?hotFlow ?coldFlow))
            (if (= ?outFlow 0.0)
             then  ;; system is OFF
               (assert (Shower is OFF))  ;; change here <------- **********
               (bind ?outTemp 5.0)
              else
               (bind ?outTemp (/ (+ (* ?coldFlow ?*coldTemp*)
                                    (* ?hotFlow ?*hotTemp*))
                                   ?outFlow)
               )
            )
            (format t "Output Flow is %6.3f litres/sec%n" ?outFlow)
            (if (= ?outFlow 0.0)            ;; change here <-------- **********
              then  ;; system is OFF  
                (format t "Output Temp is 'UNKNOWN' %n")
              else
                (format t "Output Temp is %6.3f degrees C%n" ?outTemp)
            )
          else
            (bind ?*iterationFactor* 1.0)  ;; reset iteration Factor
            (halt)
        )
   )
   ;; iterationFactor reduces each cycle through
   (if (> ?*iterationFactor* 0.1)
     then
       (bind ?*iterationFactor* (* ?*iterationFactor* .96))
   )
   
   ;; now assert the fuzzified values of the Flow and Temp of Output
   (if (= ?outFlow 0.0)
     then
       (assert (outFlow (0.0 1.0) (0.5 0.0)))
     else
       (if (= ?outFlow 100.0)
         then
           (assert (outFlow (99.5 0.0) (100.0 1.0)))
         else
           (assert (outFlow ((max 0.0 (- ?outFlow 0.5)) 0.0) (?outFlow 1.0)
                            ((min 100.0 (+ ?outFlow 0.5)) 0.0) ))
       )
   )
   (if (= ?outTemp 5.0)
     then
       (assert (outTemp (5.0 1.0) (5.1 0.0)))
     else
       (if (= ?outTemp 65.0)
         then
           (assert (outTemp (64.9 0.0) (65.0 1.0)))
         else
           (assert (outTemp ((max 5.0 (- ?outTemp 0.1)) 0.0) (?outTemp 1.0)
                            ((min 65.0 (+ ?outTemp 0.1)) 0.0) ))
       )
   )
)





(deffunction initShower ()
   (bind ?*coldValvePos* (getNumber "Initial Cold Valve Position?" 0.0 1.0))
   (bind ?*hotValvePos* (getNumber "Initial Hot Valve Position?" 0.0 1.0))
   (bind ?*coldTemp* (getNumber "Initial Temperature (Celcius) of cold water input?" 5 65))
   (bind ?*hotTemp* (getNumber "Initial Temperature (Celcius) of hot water input?" 5 65))
   (bind ?*coldPress* (getNumber "Initial Pressure (kPa) of cold water input?" 42 100))
   (bind ?*hotPress* (getNumber "Initial Pressure (kPa) of hot water input?" 42 100))
   (Simulate 0.0 0.0)
)



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Initializing rule
;;

(defrule start
   =>
     (initShower) 
    )


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Rules that control the shower with fuzzy inferencing
;;

(defrule Shower_OFF
   ?off <- (Shower is OFF)
  =>
   (assert (change_vh Z))
   (assert (change_vc NB))
   (retract ?off)
)




(defrule cold_low
   (outTemp cold)
   (outFlow low)
   =>
   (assert (change_vh PB))
   (assert (change_vc Z))
)

(defrule cold_OK
    (outTemp cold)
    (outFlow OK)
   =>
    (assert (change_vh  PM))
    (assert (change_vc Z))
)

(defrule cold_strong
    (outTemp cold)
    (outFlow strong)
   =>
    (assert (change_vh Z))
    (assert (change_vc NB))
)

(defrule OK_low
   (outTemp OK)
   (outFlow low)
  =>
   (assert (change_vh  PS))
   (assert (change_vc  PS))
)

(defrule OK_strong
   (outTemp OK)
   (outFlow strong)
  =>
   (assert (change_vh  NS))
   (assert (change_vc NS))
)


(defrule hot_low
  (outTemp hot)
   (outFlow low)
  =>
   (assert (change_vh Z))
   (assert (change_vc  PB))
)

(defrule hot_OK
   (outTemp hot)
   (outFlow OK)
  =>
   (assert (change_vh NM))
   (assert (change_vc Z))
)

(defrule hot_strong
   (outTemp hot)
   (outFlow strong)
  =>
   (assert (change_vh NB))
   (assert (change_vc Z))
)

;; when all rules have fired and contributed to determination of the
;; control changes to be made then defuzzify the change values and
;; pass these new values to the simulate function

(defrule defuzzification-and-control
   (declare (salience -1))
   ?f1 <- (change_vc ?)
   ?f2 <- (change_vh ?)
   ?f3 <- (outTemp ?)
   ?f4 <- (outFlow ?)
 =>
   (bind ?coldChange (moment-defuzzify ?f1))
   (bind ?hotChange  (moment-defuzzify ?f2))
   (retract ?f1 ?f2 ?f3 ?f4)
   (Simulate ?coldChange ?hotChange)
)




