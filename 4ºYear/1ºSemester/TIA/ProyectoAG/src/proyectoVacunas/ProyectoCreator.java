package proyectoVacunas;

import java.util.Random;

import org.opt4j.core.genotype.IntegerGenotype;
import org.opt4j.core.problem.Creator;

public class ProyectoCreator implements Creator<IntegerGenotype>
{
	public IntegerGenotype create() 
	{
		IntegerGenotype genotype = new IntegerGenotype(0, DatosVacunas.NUM_VACUNAS-1);
	
		genotype.init(new Random(), DatosVacunas.NUM_GRUPOS);  
		
		return genotype;
	}
}
