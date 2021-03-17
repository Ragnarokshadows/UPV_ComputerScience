package operaciones.aritmeticas;

import org.opt4j.core.problem.ProblemModule;

public class OpsAritmeticasModule  extends ProblemModule
{
protected void config() {
	bindProblem(OpsAritmeticasCreator.class, OpsAritmeticasDecoder.class, OpsAritmeticasEvaluator.class);
}
}
