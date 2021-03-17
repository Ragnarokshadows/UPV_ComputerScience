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

(deffacts startup 
   (currid -1)
)

(deftemplate vivienda
    (slot id (type INTEGER))
    (slot categoria (type INTEGER))
    (slot edad_aparente (type INTEGER))
    (slot ventanas (type INTEGER))
    (slot vue_momentum (type INTEGER))
    (slot vue_maximum (type INTEGER))
)

(deftemplate Categoria_Vivienda 0 150 puntos
    (
        (Economica (40 1) (70 0))
        (Estandar (40 0) (70 1) (100 0))
        (Intermedia (70 0) (100 1) (130 0))
        (Alta (100 0) (130 1))
    )
)

(deftemplate Edad_Aparente 0 100 anyos
    (
        (Reciente (0 1) (12 0))
        (Nuevo (0 0) (12 1) (24 0))
        (Medio (24 0) (36 1) (48 0))
        (Viejo (48 0) (60 1))
    )
)

(deftemplate VUE_DIF 0 10000 euros_metrocuadrado
    (
        (Bajisimo (500 1) (1500 0))
        (Bajo (500 0) (1500 1) (2500 0))
        (Medio (2500 0) (3500 1) (4500 0))
        (Alto (4500 0) (5500 1) (6500 0))
        (Altisimo (5500 0) (6500 1))
    )
)

(defrule rule1
    (Categoria_Vivienda Alta)
    (Edad_Aparente Reciente)
    =>
    (assert (VUE_DIF Altisimo))
)

(defrule rule2
    (Categoria_Vivienda Alta)
    (Edad_Aparente NOT Medio AND NOT Viejo)
    =>
    (assert (VUE_DIF Alto))
)

(defrule rule3
    (Categoria_Vivienda Alta)
    (Edad_Aparente NOT Reciente AND NOT Nuevo)
    =>
    (assert (VUE_DIF Medio))
)

(defrule rule4
    (Categoria_Vivienda Intermedia)
    (Edad_Aparente Nuevo)
    =>
    (assert (VUE_DIF NOT Medio AND NOT Alto))
)

(defrule rule5
    (Categoria_Vivienda Intermedia)
    (Edad_Aparente NOT Medio AND NOT Viejo)
    =>
    (assert (VUE_DIF Bajo))
)

(defrule rule6
    (Categoria_Vivienda Estandar)
    (Edad_Aparente Nuevo)
    =>
    (assert (VUE_DIF Medio))
)

(defrule rule7
    (Categoria_Vivienda Estandar)
    (Edad_Aparente Viejo)
    =>
    (assert (VUE_DIF Bajisimo))
)

(defrule rule8
    (Categoria_Vivienda Economica)
    (Edad_Aparente Nuevo)
    =>
    (assert (VUE_DIF NOT Bajo AND NOT Medio))
)

(defrule rule9
    (Categoria_Vivienda Economica)
    (Edad_Aparente NOT Reciente AND NOT Nuevo)
    =>
    (assert (VUE_DIF Bajisimo))
)

(defrule ventanas1
    (Ventanas ?v)
    (test (< ?v 3))
    =>
    (assert (VUE_DIF more-or-less Bajo))
)

(defrule ventanas2
    (Ventanas ?v)
    (test (> ?v 5))
    =>
    (assert (VUE_DIF very Alto))
)

(defrule defuzzedad
    (Edad_Aparente ?ed)
    =>
    (assert (Edad_Aparente_Crisp (moment-defuzzify ?ed)))
)

(defrule readcosole
    ?ini <- (initial-fact)
    ?idf <- (currid ?id)
    =>
    (retract ?ini ?idf)
    (printout t "Como desea introducir datos? (0 - Valor, 1 - Texto, 2 - Halt) " crlf)
    (bind ?Rmode (read))
    (if (< ?Rmode 2) then 
        (assert (readmode ?Rmode) (currid (+ ?id 1)))
    else
        (assert (initial-fact) (currid ?id))
        (halt)
    )
)

(defrule readcosole_value
    ?rmf <- (readmode 0)
    (currid ?id)
    =>
    (retract ?rmf)
    (printout t "Introduzca la categoria de la vivienda (puntos): " crlf)
    (bind ?Rcat (read))
    (printout t "Introduzca la edad de la vivienda (anyos): " crlf)
    (bind ?Redad (read))
    (printout t "Introduzca las ventanas de la vivienda: " crlf)
    (bind ?Rvent (read))
    (fuzzify Categoria_Vivienda ?Rcat 0.0)
    (fuzzify Edad_Aparente ?Redad 0.0)
    (assert (Ventanas ?Rvent))
    (assert (vivienda (id ?id) (categoria ?Rcat) (edad_aparente ?Redad) (ventanas ?Rvent) (vue_momentum 0) (vue_maximum 0)))
)

(defrule readcosole_text
    ?rmf <- (readmode 1)
    (currid ?id)
    =>
    (retract ?rmf)
    (printout t "Introduzca la categoria de la vivienda (puntos): " crlf)
    (bind ?Rcat (read))
    (printout t "Introduzca la edad aparente de la vivienda (texto fuzzy p. ej: \"Nuevo\"): " crlf)
    (bind ?Redad (read))
    (printout t "Introduzca las ventanas de la vivienda: " crlf)
    (bind ?Rvent (read))
    (fuzzify Categoria_Vivienda ?Rcat 0.0)
    (assert-string (format nil "(Edad_Aparente %s)" ?Redad))
    (assert (Ventanas ?Rvent))
    (assert (vivienda (id ?id) (categoria ?Rcat) (edad_aparente -1) (ventanas ?Rvent) (vue_momentum 0) (vue_maximum 0)))
)

(defrule writeconsole
    (declare (salience -1))
    ?vuef <- (VUE_DIF ?f)
    ?edf <- (Edad_Aparente ?)
    ?edcf <- (Edad_Aparente_Crisp ?edc)
    ?catf <- (Categoria_Vivienda ?)
    ?venf <- (Ventanas ?)
    ?casf <- (vivienda (id ?id) (categoria ?Rcat) (edad_aparente ?crispedad) (ventanas ?Rvent) (vue_momentum 0) (vue_maximum 0))
    =>
    (bind ?maxdef (maximum-defuzzify ?f))
    (bind ?momdef (moment-defuzzify ?f))
    (printout t "Valor VUE para casa ID: " ?id " Maximum: " ?maxdef " Momentum: " ?momdef crlf crlf)
    (retract ?vuef ?edf ?edcf ?catf ?venf ?casf)
    (if (eq ?crispedad -1) then (bind ?crispedad ?edc))
    (assert (vivienda (id ?id) (categoria ?Rcat) (edad_aparente ?crispedad) (ventanas ?Rvent) (vue_momentum ?momdef) (vue_maximum ?maxdef)))
    (assert (initial-fact))
)
