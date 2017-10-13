import java.util.Random;

public class Others{
	public double[] bias(int number){
		double[] bias = new double[4];
		switch (number){
		case 0:
			bias[0] = 1;
			bias[1] = 0;
			bias[2] = 0;
			bias[3] = 0;
			break;
		case 1:
			bias[0] = 0.25;
			bias[1] = 0.25;
			bias[2] = 0.25;
			bias[3] = 0.25;
			break;
		case 2:
			bias[0] = 0.1;
			bias[1] = 0.1;
			bias[2] = 0.1;
			bias[3] = 0.7;
			break;
		case 3:
			bias[0] = 0;
			bias[1] = 0;
			bias[2] = 0;
			bias[3] = 1;
			break;
		}
		return bias;
	}

	public int poisson(int lambda, Random random){
		double xp;
		int k = 0;
		xp = random.nextDouble();
		while (xp >= Math.exp(-lambda)) {
			xp = xp * random.nextDouble();
			k = k + 1;
		}
		return (k);
	}
}