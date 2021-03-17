package proyectoVacunas;

import org.opt4j.core.problem.ProblemModule;

public class ProyectoModule extends ProblemModule 
{

	@Override
	protected void config() 
	{
		bindProblem(ProyectoCreator.class, ProyectoDecoder.class, ProyectoEvaluator.class);
	}

}
