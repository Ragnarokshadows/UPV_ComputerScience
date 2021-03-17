;(0) DEFINIMOS LA TEMPLATE DE LA CLASE 'CASA' CON SLOTS CRISP
(deftemplate casa
    (slot categoria_crisp (type INTEGER))
    (slot edad_aparente_crisp (type INTEGER))
    (slot vue_momentum_crisp (type INTEGER))
    (slot vue_maximum_crisp (type INTEGER))
)

;(0) DEFINIMOS LAS TEMPLATES DE LAS VARIABLES DIFUSAS
(deftemplate categoria 
    0 150 puntos
    (
        (economica (40 1) (70 0) )
        (estandar (40 0) (70 1) (100 0) )
        (intermedia (70 0) (100 1) (130 0) )
        (alta (100 0) (130 1) )
    )
)

(deftemplate edad_aparente
    0 100 anyos
    (
        (reciente (0 1) (12 0) )
        (nuevo (0 0) (12 1) (24 0) )
        (medio (24 0) (36 1) (48 0) )
        (viejo (48 0) (60 1) )
    )
)

(deftemplate vuedif
    0 10000 euros
    ( 
        (bajisimo (500 1) (1500 0) )
        (bajo (500 0) (1500 1) (2500 0) )
        (medio (2500 0) (3500 1) (4500 0) )
        (alto (4500 0) (5500 1) (6500 0) )
        (altisimo (5500 0) (6500 1) )
    )
)


;(1) ASERTAMOS UNA CASA CON LOS VALORES CRISP LEIDOS POR TECLADO PARA CATEGORIA Y EDAD
(deffunction procesar ()
    (reset)

    (printout t "Introduzca la puntuacion de la categoria de su vivienda (0 - 150):" crlf)
    (printout t "-> " )
    (bind ?cat (read))

    (printout t "Introduzca la edad de su vivienda (0 - 100):" crlf)
    (printout t "-> " )
    (bind ?edad (read))
    
    ;añado los slots a 0 para facilitarme el trabajo luego, podria no añadir el slot
    (assert (casa (categoria_crisp ?cat) (edad_aparente_crisp ?edad) (vue_momentum_crisp 0) (vue_maximum_crisp 0)))

    (run)
)


;METODO PARA FUSIFICAR
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

;(2) FUSIFICAMOS LOS VALORES DE CATEGORIA Y EDAD PARA LA CASA INTANCIADA
(defrule fusificar
	?c <- (casa (categoria_crisp ?cat) (edad_aparente_crisp ?edad) (vue_momentum_crisp ?vm) (vue_maximum_crisp ?vx))
	=>
	(fuzzify categoria ?cat 0)
	(fuzzify edad_aparente ?edad 0)
)

;(3) APLICAMOS PATTERN MATCHING CON LA REGLAS Y ASERTAMOS UN VALOR VUE DIFUSO
(defrule r1
    (categoria alta)
    (edad_aparente reciente)
    =>
    (assert (vuedif altisimo))
)

(defrule r2
    (categoria alta)
    (edad_aparente not [ medio or viejo ] )
    =>
    (assert (vuedif alto))
)

(defrule r3
    (categoria alta)
    (edad_aparente not [ reciente or nuevo ] )
    =>
    (assert (vuedif medio))
)

(defrule r4
    (categoria intermedia)
    (edad_aparente nuevo)
    =>
    (assert (vuedif not [ medio or alto ]))
)

(defrule r5
    (categoria intermedia)
    (edad_aparente not [ medio or viejo ] )
    =>
    (assert (vuedif bajo))
)

(defrule r6
    (categoria estandar)
    (edad_aparente nuevo)
    =>
    (assert (vuedif medio))
)

(defrule r7
    (categoria estandar)
    (edad_aparente viejo)
    =>
    (assert (vuedif bajisimo))
)

(defrule r8
    (categoria economica)
    (edad_aparente nuevo)
    =>
    (assert (vuedif not [ bajo or medio ] ))
)

(defrule r9
    (categoria economica)
    (edad_aparente not [ reciente or nuevo ] )
    =>
    (assert (vuedif bajisimo))
)

;(4) DEFUSIFICAMOS EL VALOR VUE POR MOMENTUM Y MAXIMUM Y LO AÑADIMOS A LA INSTANCIA DE LA CASA
(defrule defuzzificar
    (declare (salience -1)) 
    (vuedif ?val)
    ?c <- (casa (categoria_crisp ?cat) (edad_aparente_crisp ?edad) (vue_momentum_crisp ?vm) (vue_maximum_crisp ?vx))
    =>
    (bind ?vm (moment-defuzzify ?val))
    (bind ?vx (maximum-defuzzify ?val))

    ;#### transformar a enteros ####
    (bind ?vm (integer ?vm))
    (bind ?vx (integer ?vx))

    ;Modificamos la instancia de la casa para añadirle los VUE
    (modify ?c (vue_momentum_crisp ?vm) (vue_maximum_crisp ?vx))
    
    (printout t "VUE por momentum: " ?vm crlf)
    (printout t "VUE por maximum: " ?vx crlf)
    (halt)
)




