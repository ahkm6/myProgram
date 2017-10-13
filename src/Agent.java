import java.util.Random;

public class Agent{

	int agentNumber;
	int agentResource[] = new int[3];
	int agentStrategy;

	public Agent(int agentNum, int[] agentRec, Random random){
		this.agentNumber = agentNum;
		this.agentResource = agentRec;
		this.agentStrategy = random.nextInt(4);
	}

}
