; Clase vivienda
(deftemplate vivienda
	(slot categoria (type INTEGER))
	(slot edad (type INTEGER))
    (slot ventanas (type INTEGER))
	(slot vue_momentum (type FLOAT))
	(slot vue_maximum (type FLOAT))
)

;Variables difusas
(deftemplate categoria_dif
    0 150 puntos
    (
        (economica (40 1) (70 0))
        (estandard (40 0) (70 1) (100 0))
        (intermedia (70 0) (100 1) (130 0))
        (alta (100 0) (130 1))
    )
)

(deftemplate edad_dif
    0 100 anyos
    (
        (reciente (0 1) (12 0))
        (nuevo (0 0) (12 1) (24 0))
        (medio (24 0) (36 1) (48 0))
        (viejo (48 0) (60 1))
    )
)

(deftemplate vue_dif
    0 10000 euros
    (
        (bajisimo (500 1) (1500 0))
        (bajo (500 0) (1500 1) (2500 0))
        (medio (2500 0) (3500 1) (4500 0))
        (alto (4500 0) (5500 1) (6500 0))
        (altisimo (5500 0) (6500 1))
    )
)

;Leer del teclado
(deffunction proceso ()
    (reset)

    (printout t "Introduzca la puntuacion de su vivienda:" crlf) ;leemos un valor crisp y se fusifica (y aserta)
    (bind ?cat (read))

    (printout t "Introduzca la edad de su vivienda:" crlf) ;leemos un valor crisp y se fusifica (y aserta)
    (bind ?edad (read))

    (printout t "Introduzca el numero de ventanas de su vivienda:" crlf) ;leemos un valor crisp (y aserta)
    (bind ?nv (read))
    (assert (ventanas ?nv))

    (if (integerp ?edad) 
        then 
            (assert (vivienda (categoria ?cat) (edad ?edad) (ventanas ?nv)))
            (assert (edad_tipo crisp))
        else
            (assert-string (format nil "(edad_dif %s)" ?edad))
            (assert (vivienda (categoria ?cat) (ventanas ?nv)))
            (assert (edad_tipo dif))
    )


    (run)
)


;Fusificar
(deffunction fuzzify (?fztemplate ?value ?delta)
    (bind ?low (get-u-from ?fztemplate))
    (bind ?hi (get-u-to ?fztemplate))

    (if (<= ?value ?low)
        then
            (assert-string (format nil "(%s (%g 1.0) (%g 0.0))" ?fztemplate ?low ?delta))
        else
            (if (>= ?value ?hi)
                then
                    (assert-string (format nil "(%s (%g 0.0) (%g 1.0))" ?fztemplate (- ?hi ?delta) ?hi))
                else
                    (assert-string (format nil "(%s (%g 0.0) (%g 1.0) (%g 0.0))" ?fztemplate (max ?low (- ?value ?delta)) ?value (min ?hi (+ ?value ?delta)) ))
            )
    )
)

;Fusificar
(defrule fusificar_crisp
	?c <- (vivienda (categoria ?cat) (edad ?edad) (ventanas ?ventanas) (vue_momentum ?vm) (vue_maximum ?vx))
	(edad_tipo crisp)
    =>
	(fuzzify categoria_dif ?cat 0)
	(fuzzify eda_dif ?edad 0)
)

(defrule fusificar_dif
	?c <- (vivienda (categoria ?cat) (edad ?edad) (ventanas ?ventanas) (vue_momentum ?vm) (vue_maximum ?vx))
	(edad_tipo dif)
    =>
	(fuzzify categoria_dif ?cat 0)
)

;Reglas
(defrule ventanas1
    (ventanas ?v) 
    (test (< ?v 3))
    =>
    (assert (vue_dif more-or-less bajo))
)

(defrule ventanas2
    (ventanas ?v) 
    (test (> ?v 5))
    =>
    (assert (vue_dif very alto))
)

(defrule r1
    (categoria_dif alta) 
    (edad_dif reciente)
    =>
    (assert (vue_dif altisimo))
)

(defrule r2
    (categoria_dif alta) 
    (edad_dif not [ medio or viejo ])
    =>
    (assert (vue_dif alto))
)

(defrule r3
    (categoria_dif alta) 
    (edad_dif not [ reciente or nuevo ])
    =>
    (assert (vue_dif medio))
)

(defrule r4
    (categoria_dif intermedia) 
    (edad_dif nuevo)
    =>
    (assert (vue_dif not [ medio or alto ]))
)

(defrule r5
    (categoria_dif intermedia) 
    (edad_dif not [ medio or viejo ])
    =>
    (assert (vue_dif bajo))
)

(defrule r6
    (categoria_dif estandard) 
    (edad_dif nuevo)
    =>
    (assert (vue_dif medio))
)

(defrule r7
    (categoria_dif estandard) 
    (edad_dif viejo)
    =>
    (assert (vue_dif bajisimo))
)

(defrule r8
    (categoria_dif economica) 
    (edad_dif nuevo)
    =>
    (assert (vue_dif not [ bajo or medio ]))
)

(defrule r9
    (categoria_dif economica) 
    (edad_dif not [ reciente or nuevo ])
    =>
    (assert (vue_dif bajisimo))
)

;Defusicar
(defrule defuzzificar
    (declare (salience -1)) 
    (vue_dif ?val)
    ?c <- (vivienda (categoria ?cat) (edad ?edad) (vue_momentum ?vm) (vue_maximum ?vx))
    =>
    (bind ?vm (moment-defuzzify ?val))
    (bind ?vx (maximum-defuzzify ?val))

    (modify ?c (vue_momentum ?vm) (vue_maximum ?vx))
    
    (printout t "VUE por momentum: " ?vm crlf)
    (printout t "VUE por maximum: " ?vx crlf)
    (halt)
)




