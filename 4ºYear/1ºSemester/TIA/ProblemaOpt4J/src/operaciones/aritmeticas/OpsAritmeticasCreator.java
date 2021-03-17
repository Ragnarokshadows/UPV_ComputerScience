package operaciones.aritmeticas;

import java.util.Random;
import org.opt4j.core.genotype.SelectGenotype;
import org.opt4j.core.problem.Creator;

public class OpsAritmeticasCreator implements Creator<SelectGenotype<MathematicalSymbol>> 
{
	public SelectGenotype<MathematicalSymbol> create() 
	{
		MathematicalSymbol[] Symbols = {MathematicalSymbol.PLUS, MathematicalSymbol.MINUS, 
				MathematicalSymbol.MULTIPLICATION, MathematicalSymbol.DIVISION };
		
		SelectGenotype<MathematicalSymbol> genotype = new SelectGenotype<MathematicalSymbol>(Symbols);
		// el genotipo estara formado por "numeroSimbolos" matematicos elegidos al azar
		// en nuestro caso la poblacion sera un conjunto de individuos, donde cada individuo son 5 simbolos
		genotype.init(new Random(), Data.numeroSimbolos);  
		
		return genotype;
	}
}
