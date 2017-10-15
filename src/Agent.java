import java.util.Random;

public class Agent {

	int agentNumber;
	int agentResource[] = new int[3];
	int agentStrategy;
	double[] Q = new double[4];
	int status;
	double reward;
	static int strategy = Main.strategy;

	public Agent(int agentNum, int[] agentRec, Random random) {
		this.agentNumber = agentNum;
		this.agentResource = agentRec;
		if (strategy < 4)
			this.agentStrategy = strategy;
		else
			this.agentStrategy = random.nextInt(4);
		this.status = 0;
	}

	public int[] resource() {
		return this.agentResource;
	}

	public int agentNumber() {
		return this.agentNumber;
	}

	public int agentStrategy() {
		return this.agentStrategy;
	}

	public int agentStrategy(int n) {
		this.agentStrategy = n;
		return n;
	}

	public void busy(int n) {
		this.status = n;
	}

	public boolean elapsedTime() {
		this.status--;
		if (this.status == 0)
			return true;
		else
			return false;
	}

	public void reward(double d) {
		this.reward = d;
	}

	public double reward() {
		return this.reward;
	}

	public String toString() {
		return this.agentNumber + "," + this.agentResource[0] + "," + this.agentResource[1] + ","
				+ this.agentResource[2] + "," + this.status;
	}
}
