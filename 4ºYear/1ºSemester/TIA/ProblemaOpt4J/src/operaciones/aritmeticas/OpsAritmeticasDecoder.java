package operaciones.aritmeticas;

import java.util.ArrayList;
import org.opt4j.core.genotype.SelectGenotype;
import org.opt4j.core.problem.Decoder;

public class OpsAritmeticasDecoder 
			      implements Decoder<SelectGenotype<MathematicalSymbol>, ArrayList<MathematicalSymbol>>
{
	//no es necesaroio devolverlo como String, pero si un tipo imprimible/visualizable en el visor Opt4J
	
	public ArrayList<MathematicalSymbol> decode(SelectGenotype<MathematicalSymbol> genotype)
	{
		ArrayList<MathematicalSymbol> phenotype = new ArrayList<MathematicalSymbol>();
		//aqui se podría poner código para validar que el fenotipo cumpla con ciertas restricciones
		for (int i = 0 ; i <genotype.size(); i++)
		{
			phenotype.add(genotype.getValue(i));
		}
	return phenotype;
	}
}
