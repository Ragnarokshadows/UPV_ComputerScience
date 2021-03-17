;; Deffunction fuzzify
;;
;; Inputs:   ?fztemplate  - name of a fuzzy deftemplate
;;           ?value       - float value to be fuzzified
;;           ?delta       - precision of the value
;;
;; Asserts a fuzzy fact for the fuzzy deftemplate. The fuzzy set is
;; a triangular shape centered on the value provided with zero
;; possibility at value+delta and value-delta. Note that the function
;; checks the bounds of the universe of discourse to generate a fuzzy
;; set that does not have values outside of the universe range.
;;

 (deffunction fuzzify (?fztemplate ?value ?delta)

        (bind ?low (get-u-from ?fztemplate))
        (bind ?hi  (get-u-to   ?fztemplate))

        (if (<= ?value ?low)
          then
            (assert-string
              (format nil "(%s (%g 1.0) (%g 0.0))" ?fztemplate ?low ?delta))
          else
            (if (>= ?value ?hi)
              then
                (assert-string
                   (format nil "(%s (%g 0.0) (%g 1.0))"
                               ?fztemplate (- ?hi ?delta) ?hi))
              else
                (assert-string
                   (format nil "(%s (%g 0.0) (%g 1.0) (%g 0.0))"
                               ?fztemplate (max ?low (- ?value ?delta))
                               ?value (min ?hi (+ ?value ?delta)) ))
            )
        )
  )


In this example the routines get-u-from and get-u-to have a symbol
as the argument. This symbol is the name of the fuzzy deftemplate
for which the universe information is requested.

For the call

    (fuzzify temperature 95 0.1)

when the fuzzy deftemplate 'temperature' has been defined and has a 
universe of discourse specified as "0 100 degrees-C", the following 
assert would be executed:

   (assert-string "(temperature (94.9 0) (95 1) (95.1 0))")

All of the routines get-u, get-u-from, get-u-to and get-u-units now
will accept a symbol argument that should be the name of a fuzzy 
deftemplate and will return the required values from the universe
of discourse associated with that deftemplate.

