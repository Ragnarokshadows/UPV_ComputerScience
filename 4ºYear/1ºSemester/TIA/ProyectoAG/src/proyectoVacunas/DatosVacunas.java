package proyectoVacunas;

public class DatosVacunas 
{	
	public static final int NUM_GRUPOS	= 13;
	//public static final int NUM_VACUNAS	= 3;
	public static final int NUM_VACUNAS	= 4;
	
	// matriz de coste de las 3 vacunas para cada uno de los 13 grupos
	// Jóvenes (J1..J4): 0..3
	// Adultos (A1..A4): 4..7
	// Mayores (M1..M5): 8..12
	
	public static final int[][] matrizCostes =
		{
				{11, 12, 10, 15, 19, 22, 25, 23, 21, 14, 15, 16, 19},  	// vacuna1 
				{14, 9,	8, 12, 20, 25, 21, 18, 14, 18, 16, 15, 15},  	// vacuna2
				{3,	3, 1, 2, 4,	2, 4, 3, 2,	4, 1, 4, 3},				// vacuna3
				{20, 21, 16, 20, 35, 28, 50, 40, 31, 34, 31, 32, 31}
		};	
	
	public static final int[] numeroVoluntarios =
		{50, 40, 30, 35, 60, 48, 56, 50, 40, 38, 20, 24, 35};			
}
