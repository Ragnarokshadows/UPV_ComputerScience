;;; A simple example of a backward chaining program
;;;
;;; Given the simple circuit below ask for the output for any node
;;; and the program will determine what inputs are needed (if not
;;; already supplied) to calculate the output.
;;;
;;;    A input node  B input node  C input node  D input node
;;;          |           |             |             |
;;;          |           \             /             |
;;;          \            \           /              |
;;;           \            \         /               /
;;;            \             E Adder                /
;;;             \               /\                 /
;;;              \             /  \               /
;;;               \           /    \             /
;;;                \         /      \           /
;;;                  F Adder          G Multiplier
;;;                    |                   |
;;;                    |                   |
;;;                I Output node      J Output node
;;;


;;; These are the function facts

(deffacts functions
	(function a input)
	(function b input)
	(function c input)
	(function d input)
	(function e adder)
	(function f adder)
	(function g multiplier)
	(function i output)
	(function j output))

;;; These are the connection facts

(deffacts connections
	(connection a f 1)
	(connection b e 1)
	(connection c e 2)
	(connection d g 2)
	(connection e f 2)
	(connection e g 1)
	(connection f i 1)
	(connection g j 1))

(deftemplate goal (slot output))

(defrule get-inputs
 (goal (output ?node))
 (function ?node input)
=>
  (printout t "Please enter a value for node: " ?node " " t)
  (bind ?in (read))
  (printout t "Enter Certainty Factor for node (0.0 to 1.0): " ?node " " t)
  (bind ?cf (read))
  (if (> ?cf 1.0) then (bind ?cf 1.0))
  (if (< ?cf 0.0) then (bind ?cf 0.0))
 (assert (input ?node ?in 1) CF ?cf))

(defrule direct-connection
        (goal (output ?to-node))
        (output ?from-node ?value)
        (connection ?from-node ?to-node ?port-number)
        =>
        (assert (input ?to-node ?value ?port-number)))

(defrule adder
	(function ?node adder)
	(input ?node ?value-1 1)
	(input ?node ?value-2 2)
	=>
	(assert (output ?node =(+ ?value-1 ?value-2))))

(defrule multiplier
	(function ?node multiplier)
	(input ?node ?value-1 1)
	(input ?node ?value-2 2)
	=>
	(assert (output ?node =(* ?value-1 ?value-2))))

(defrule print-solution
  ?x <- (goal (output ?node))
  ?y <- (target ?node)
  ?z <- (output ?node ?value)
        =>
        (retract ?x)
        (retract ?y)
        (printout t "The output value at node " ?node " is " ?value 
                    " with certainty of " (get-cf ?z) t))

(defrule user-input
 (not (goal (output ?)))
=>
 (printout t "which node are you interested in? (a-j or q to quit)" t)
 (bind ?target (read))
 (if (eq ?target "q") then (halt))
 (assert (target ?target))
 (assert (goal (output ?target))))

(defrule chain-backwards-to-inputs
 (logical (connection ?parent ?node ?)
  (goal (output ?node))
  (not (output ?parent ? )))
=>
 (assert (goal (output ?parent))))

(defrule input
        (function ?node input)
        (input ?node ?value-1 1)
        =>
        (assert (output ?node ?value-1)))

(defrule output
        (function ?node output)
        (input ?node ?value-1 1)
        =>
        (assert (output ?node ?value-1)))

