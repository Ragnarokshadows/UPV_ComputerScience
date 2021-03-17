; Dominio Transporte
(define
	(domain transporte)
	(:requirements :durative-actions :typing :fluents)
	(:types usuario ciudad - object)
	
	(:predicates
		(at ?x - usuario ?y - ciudad)
		(residente ?x - usuario)
		(ticket-tren ?x - usuario)
		(conexion-bus ?x - ciudad ?y - ciudad)
		(conexion-metro ?x - ciudad ?y - ciudad)
		(conexion-tren ?x - ciudad ?y - ciudad)
	)
	
	(:functions
		(distancia ?x - ciudad ?y - ciudad)
		(velocidad-metro)
		(velocidad-tren)
		(velocidad-bus)
		(precio-metro)
		(precio-tren)
		(saldo ?x - usuario)
		(bono ?x - usuario)
		(coste-total)
	)
	
	(:durative-action viajar-bus
		:parameters (?u - usuario ?c1 - ciudad ?c2 - ciudad)
		:duration (= ?duration (/
			(distancia ?c1 ?c2)
			(velocidad-bus)
		))
		:condition (and
			(at start (at ?u ?c1))
			(at start (residente ?u))
			(at start (conexion-bus ?c1 ?c2))
		)
		:effect (and
			(at start (not (at ?u ?c1)))
			(at end (at ?u ?c2))
		)
	)
	
	(:durative-action viajar-metro
		:parameters (?u - usuario ?c1 - ciudad ?c2 - ciudad)
		:duration (= ?duration (/
				(distancia ?c1 ?c2)
				(velocidad-metro)
		))
		:condition (and
			(at start (at ?u ?c1))
			(at start (>
				(bono ?u)
				0
			))
			(at start (conexion-metro ?c1 ?c2))
		)
		:effect (and
			(at start (not (at ?u ?c1)))
			(at end (decrease (bono ?u) 1))
			(at end (at ?u ?c2))
		)
	)
	
	(:durative-action viajar-tren
		:parameters (?u - usuario ?c1 - ciudad ?c2 - ciudad)
		:duration (= ?duration (/
				(distancia ?c1 ?c2)
				(velocidad-tren)
		))
		:condition (and
			(at start (at ?u ?c1))
			(at start (ticket-tren ?u))
			(at start (conexion-tren ?c1 ?c2))
		)
		:effect (and
			(at start (not (at ?u ?c1)))
			(at start (not (ticket-tren ?u)))
			(at end (at ?u ?c2))
		)
	)
	
	(:durative-action comprar-metro
		:parameters (?u - usuario)
		:duration (= ?duration 1)
		:condition (and
			(at start (>=
				(saldo ?u)
				(precio-metro)
			))
		)
		:effect (and
			(at start (decrease (saldo ?u) (precio-metro)))
			(at start (increase (coste-total) (precio-metro)))
			(at end (increase (bono ?u) 10))
		)
	)
	
	(:durative-action comprar-tren
		:parameters (?u - usuario)
		:duration (= ?duration 2)
		:condition (and
			(at start (>=
				(saldo ?u)
				(precio-tren)
			))
		)
		:effect (and
			(at start (decrease (saldo ?u) (precio-tren)))
			(at start (increase (coste-total) (precio-tren)))
			(at end (ticket-tren ?u))
		)
	)
)