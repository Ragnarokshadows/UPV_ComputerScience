
import java.util.Scanner;
import java.math.BigInteger;

class PreComputing
{
	final static int [] values = { 0, 1, 2, 3, 1 };

	static Scanner input = new Scanner(System.in);
	static int counter;

	public static void main(String [] args)
	{
		int n = 1;
		do {
			possible(n);
			System.out.println("\n In total " + counter + " combinations.");
			System.out.print("\n\n n: ");
			n = input.nextInt();
		} while( n > 0 );
	}

	static int sum(int [] A, int k)
	{
		int sum = 0;
		for (int i = 0; i < k; i++) sum += values[A[i]];
		return sum;
	}
	static void show(int [] A, int k)
	{
		for (int i = 0; i < k; i++) System.out.print(A[i]);
		System.out.println();
	}

	static void possible(int n)
	{
		int [] combination = new int [n + 1];
		counter = 0;

		possible(0, combination, n);
	}

	static void possible(int k, int [] A, int n)
	{
		if (k <= n) {
			int s = sum(A, k);

			if (s == n) {
//				show(A, k);
				counter++;
			} else if (s < n) {
				for (int i = 1; i < values.length; i++) {
					A[k] = i;
					possible(k + 1, A, n);
				}
			}
		}
	}
}
