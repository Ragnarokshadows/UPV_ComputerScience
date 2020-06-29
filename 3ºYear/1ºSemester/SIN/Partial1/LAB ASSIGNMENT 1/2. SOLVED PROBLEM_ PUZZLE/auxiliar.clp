;;CHECK IF THE GENERATED CONFIGURATION HAS A SOLUTION
;;As we use a fixed final configuration, it always has the value 11 
;;we count, for each value v, how many values are lower than v are left on its right-hand side
;;we sum over all these v values and the resulting sum must be an odd number 
;;(so that initial configuration plus final configuration is even)

(deffunction check_conf (?list)
	(bind ?sum 0)
	(bind ?i 1)
        (bind ?list (replace-member$ ?list 9 0))   
	(while (<= ?i 9)
		(bind ?value (integer (nth 1 (subseq$ $?list ?i ?i))))
		;;(printout t ?value crlf)
		(bind ?cont 0)
		(bind ?j (+ 1 ?i))
		(while (<= ?j 9)
			(if (> ?value (integer (nth 1 (subseq$ $?list ?j ?j)))) then
				(bind ?cont (+ 1 ?cont))
			) 
			(bind ?j (+ 1 ?j))
		) 
		(bind ?sum (+ ?cont ?sum))
		(bind ?i (+ 1 ?i))
	)
        (bind ?pos_zero (member 9 ?list))	;;; EVA
        (bind ?row_zero (div ?pos_zero 3))	;;; EVA
        (bind ?col_zero (mod ?pos_zero 3))	;;; EVA
        (if (evenp (+ ?row_zero ?col_zero)) then (bind ?sum (+ ?sum 1)))	;;; EVA
	(<> (mod ?sum 2) 0)
) 
