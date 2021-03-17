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

;Reglas
(defrule ventanas1
    (ventana ?v) 
    (test (< ?v 3))
    =>
    (assert (vue_dif more-or-less bajo))
)

(defrule ventanas2
    (ventana ?v) 
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

;Leer del teclado
(deffunction proceso ()
    (reset)

    (printout t "Introduzca la puntuacion de su vivienda:" crlf) ;leemos un valor crisp y se fusifica (y aserta)
    (bind ?Redad (read))
    (fuzzify categoria_dif ?Redad 0)

    (printout t "Introduzca la edad de su vivienda:" crlf) ;leemos un valor crisp y se fusifica (y aserta)
    (bind ?Redad (read))
    (if (integerp ?Redad)
        then 
            (fuzzify edad_dif ?Redad 0)
        else
            (assert-string (format nil "(edad_dif %s)" ?Redad))
    )
    
    (printout t "Introduzca el numero de ventanas de su vivienda:" crlf) ;leemos un valor crisp (y aserta)
    (bind ?Redad (read))
    (assert (ventana ?Redad))

    (run)
)

;Desfusificar
(defrule defuzzificar
    (declare (salience -1))
    (vue_dif ?val)
    =>
    (assert (crisp vue_maximum (maximum-defuzzify ?val)))
    (assert (crisp vue_momentum (moment-defuzzify ?val)))
    (printout t "VUE por moment: " (moment-defuzzify ?val) crlf)
    (printout t "VUE por maximum: " (maximum-defuzzify ?val) crlf)

    (halt)
)