
(deffacts data
    (location Amy cityA)
    (location car cityB)
    (location shop_groceries cityC)
    (link cityA cityB 5)
    (link cityB cityC 10)
    (link cityA cityC 12))


 (defrule rule1
    (link cityA ?x ?y)
  =>
    (printout t ?x " is " ?y " km far away from cityA " crlf))

