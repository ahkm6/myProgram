import java.util.Random;

public class Agent {

	int agentNumber;
	int agentResource[] = new int[3];
	int agentStrategy;
	int status;
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

	public String toString() {
		return this.agentNumber + "," + this.agentResource[0] + "," + this.agentResource[1] + ","
				+ this.agentResource[2] + "," + this.status;
	}
}
