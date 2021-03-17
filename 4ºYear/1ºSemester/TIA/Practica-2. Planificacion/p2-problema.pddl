; Problema a resolver
(define
	(problem a-resolver)
	(:domain transporte)
	
	(:objects
		juan - usuario
		maria - usuario
		eva - usuario
		ana - usuario
		pedro - usuario
		ciudad_A - ciudad
		ciudad_B - ciudad
		ciudad_C - ciudad
		ciudad_D - ciudad
		ciudad_E - ciudad
	)
	
	(:init
		(at juan ciudad_A)
		(residente juan)
		(= (bono juan) 5)
		(= (saldo juan) 50)
		
		(at maria ciudad_C)
		(residente maria)
		(= (bono maria) 1)
		(= (saldo maria) 15)
		
		(at eva ciudad_C)
		(= (bono eva) 0)
		(= (saldo eva) 13)
		
		(at ana ciudad_D)
		(residente ana)
		(= (bono ana) 0)
		(= (saldo ana) 18)
		
		(at pedro ciudad_E)
		(= (bono pedro) 4)
		(= (saldo pedro) 14)
		
		(= (distancia ciudad_A ciudad_B) 40)
		(= (distancia ciudad_A ciudad_C) 80)
		(= (distancia ciudad_A ciudad_D) 120)
		(= (distancia ciudad_A ciudad_E) 200)
		(= (distancia ciudad_B ciudad_A) 40)
		(= (distancia ciudad_B ciudad_C) 40)
		(= (distancia ciudad_B ciudad_D) 80)
		(= (distancia ciudad_B ciudad_E) 80)
		(= (distancia ciudad_C ciudad_A) 80)
		(= (distancia ciudad_C ciudad_B) 40)
		(= (distancia ciudad_C ciudad_D) 80)
		(= (distancia ciudad_C ciudad_E) 200)
		(= (distancia ciudad_D ciudad_A) 120)
		(= (distancia ciudad_D ciudad_B) 80)
		(= (distancia ciudad_D ciudad_C) 40)
		(= (distancia ciudad_D ciudad_E) 120)
		(= (distancia ciudad_E ciudad_A) 200)
		(= (distancia ciudad_E ciudad_B) 120)
		(= (distancia ciudad_E ciudad_C) 240)
		(= (distancia ciudad_E ciudad_D) 160)
		
		(conexion-bus ciudad_A ciudad_B)
		(conexion-bus ciudad_A ciudad_C)
		(conexion-bus ciudad_A ciudad_D)
		(conexion-bus ciudad_B ciudad_A)
		(conexion-bus ciudad_B ciudad_C)
		(conexion-bus ciudad_B ciudad_D)
		(conexion-bus ciudad_C ciudad_A)
		(conexion-bus ciudad_C ciudad_B)
		(conexion-bus ciudad_C ciudad_D)
		(conexion-bus ciudad_D ciudad_A)
		(conexion-bus ciudad_D ciudad_B)
		(conexion-bus ciudad_D ciudad_C)
		
		(conexion-metro ciudad_A ciudad_B)
		(conexion-metro ciudad_A ciudad_C)
		(conexion-metro ciudad_A ciudad_D)
		(conexion-metro ciudad_B ciudad_A)
		(conexion-metro ciudad_B ciudad_C)
		(conexion-metro ciudad_B ciudad_D)
		(conexion-metro ciudad_C ciudad_A)
		(conexion-metro ciudad_C ciudad_B)
		(conexion-metro ciudad_D ciudad_A)
		(conexion-metro ciudad_D ciudad_B)
		
		(conexion-tren ciudad_A ciudad_B)
		(conexion-tren ciudad_A ciudad_C)
		(conexion-tren ciudad_A ciudad_E)
		(conexion-tren ciudad_B ciudad_A)
		(conexion-tren ciudad_B ciudad_C)
		(conexion-tren ciudad_B ciudad_E)
		(conexion-tren ciudad_C ciudad_A)
		(conexion-tren ciudad_C ciudad_B)
		(conexion-tren ciudad_E ciudad_A)
		(conexion-tren ciudad_E ciudad_B)
		
		(= (velocidad-bus) 1)
		(= (velocidad-metro) 2)
		(= (velocidad-tren) 4)
		(= (precio-metro) 12)
		(= (precio-tren) 6)
		(= (coste-total) 0)
	)
	
	(:goal (and
		(at juan ciudad_E)
		(at maria ciudad_E)
		(at eva ciudad_D)
		(at ana ciudad_A)
		(at pedro ciudad_B)
	))
	
	(:metric minimize
		(+
			(* 0.8 (total-time))
			(* 0.2 (coste-total))
		)
	)
)