(deffacts data
    (location Amy cityA)
    (location car cityB)
    (location shop_groceries cityC)
    (link cityA cityB 5)
    (link cityB cityC 10)
    (link cityA cityC 12))


 (defrule move
    (location Amy ?loc)
    (link ?loc ?loc2 ?km)
  =>
    (printout t "Amy moves from " ?loc " to " ?loc2 crlf))

