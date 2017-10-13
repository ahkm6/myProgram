import java.util.ArrayList;
import java.util.Random;

public class Main {

	//strategy
	final static int EDF = 0;
	final static int HRF = 1;
	final static int SPTF = 2;
	final static int CEF = 3;
	final static int EREARN = 4;
	final static int RLEARN = 5;
	final static int LANDOM = 6;
	// agent
	final static int RANDOM = 0;
	final static int BIAS = 1;
	final static int MIXED = 2;
	// task
	final static int TASK_REWARD = 0;
	final static int TASK_RANDOM = 1;
	final static int REWARD = 0;
	final static int PROCESSTIME = 1;

	final static int LENGTH = 3;
	final static int AGENT = 100;

	final static int deadlineUnder = 15;
	final static int deadlineRange = 10;

	static int Value = REWARD;
	static int bidNumber = 5;
	static int agentType = RANDOM;
	static int taskReward = TASK_REWARD;
	static int Loop = 10;
	static int taskLoad = 30;
	static int strategy = HRF;

	public static void main(String[] args) {
		MakeObject object = new MakeObject();
		Others other = new Others();
		for (int bias = 0; bias < 4; bias++) {
			double[] prob = other.bias(bias);
			int taskload = taskLoad;
			for (int loop = 0; loop < Loop; loop++) {
				Random random = new Random(10001);

				Agent[] agent = new Agent[AGENT];
				ArrayList<Task> task = new ArrayList<Task>();
				ArrayList<ArrayList<Bid>> bid = new ArrayList<ArrayList<Bid>>();
				ArrayList<ArrayList<Bid>> agentBid = new ArrayList<ArrayList<Bid>>();

				ArrayList<Bid> allocation = new ArrayList<Bid>();
				object.makeAgent(agent, agentType, random);

				while (true) {
					object.makeTask(task, other.poisson(taskload, random), random, prob);
					for(Task t : task){
						bid.add(new ArrayList<Bid>());
					}
					object.makeBid(agent,task,bid,agentBid,random);

				}
			}
		}
	}
}