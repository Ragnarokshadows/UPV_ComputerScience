
(deffacts data
    (location Amy cityA)
    (location car cityB)
    (location shop_groceries cityC)
    (link cityA cityB 5)
    (link cityB cityC 10)
    (link cityA cityC 12))


 (defrule rule1
    (link cityA cityB 5)
  =>
    (printout t  " cityB is 5 km far away from cityA " crlf))

