package proyectoVacunas;

import java.util.ArrayList;
import java.lang.Math;

import org.opt4j.core.Objectives;
import org.opt4j.core.Objective.Sign;
import org.opt4j.core.problem.Evaluator;

public class ProyectoEvaluator implements Evaluator<ArrayList<Integer>>
{
	final int INFINITO = (int) Math.pow(2, 31);
	
	public Objectives evaluate (ArrayList<Integer> phenotype)
	{
		int coste = 0;
		int voluntarios = 0;
		boolean vac1 = false, vac2 = false, vac3 = false, inv = false;
		boolean vac4 = false;
			
		for (int i = 0; i < phenotype.size(); ++i)
		{
			switch (phenotype.get(i))
			{
			case 0: 
				coste += DatosVacunas.matrizCostes[0][i]; 
				voluntarios += DatosVacunas.numeroVoluntarios[i];
				vac1 = true;
				break;
			case 1: 
				coste += DatosVacunas.matrizCostes[1][i]; 
				voluntarios += DatosVacunas.numeroVoluntarios[i];
				vac2 = true;
				break;
			case 2: 
				coste += DatosVacunas.matrizCostes[2][i]; 
				vac3 = true;
				break;
			case 3:
				coste += DatosVacunas.matrizCostes[3][i]; 
				vac4 = true;
				break;
			}
			
			
			if (i == 3 || i == 7 || i == 12) 
			{
				
				//if (!(vac1 && vac2)) 
				//if (!(vac1 && vac2 && vac3)) 
				if (!(vac1 && vac2 && vac3 && vac4)) 
				{
					inv = true;
				} 

				vac1 = false;
				vac2 = false;
				vac3 = false;
				vac4 = false;
			}
		}
		
		/**
		if(phenotype.get(2) == 2 && phenotype.get(3) == 2) {
			coste -= 5;
		}
		if(phenotype.get(5) == 2 && phenotype.get(6) == 2) {
			coste += 4;
		}
		
		if (!(phenotype.get(3) == 3)) {
			inv = true;
		}
		*/
		
		if (inv) 
		{
			coste = INFINITO;
			voluntarios = -INFINITO;
		}
		Objectives objectives = new Objectives();
		objectives.add("Coste-MIN",  Sign.MIN, coste);
		objectives.add("Voluntarios-MAX",  Sign.MAX, voluntarios);
		return objectives;
	}
}
