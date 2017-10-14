import java.util.ArrayList;
import java.util.Random;

public class Main {

	// strategy
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

	final static int CPLEX = 0;
	final static int SRNF = 1;

	final static int LENGTH = 3;
	final static int AGENT = 3;

	final static int deadlineUnder = 15;
	final static int deadlineRange = 10;

	static int Value = REWARD;
	static int bidNumber = 5;
	static int agentType = RANDOM;
	static int taskReward = TASK_REWARD;
	static int Loop = 10;
	static int taskLoad = 30;
	static int strategy = EDF;
	static int method = SRNF;

	public static void main(String[] args) {
		MakeObject object = new MakeObject();
		Others other = new Others();
		Allocate allocate = new Allocate();
		Delete delete = new Delete();

		for (int bias = 0; bias < 4; bias++) {
			double[] prob = other.bias(bias);
			int taskload = taskLoad;
			for (int loop = 0; loop < Loop; loop++) {
				Random random = new Random(10001);

				Agent[] agent = new Agent[AGENT];
				ArrayList<Task> task = new ArrayList<Task>();
				ArrayList<ArrayList<Bid>> bid = new ArrayList<ArrayList<Bid>>();
				ArrayList<ArrayList<Bid>> agentBid = new ArrayList<ArrayList<Bid>>();
				ArrayList<ArrayList<Bid>> reserveBid = new ArrayList<ArrayList<Bid>>();
				ArrayList<ArrayList<Bid>> envyList = new ArrayList<ArrayList<Bid>>();

				ArrayList<Bid> allocation = new ArrayList<Bid>();
				ArrayList<Bid> cand = new ArrayList<Bid>();
				Bid item;
				object.makeAgent(agent, agentType, random);

				while (true) {
					object.makeTask(task, other.poisson(taskload, random), random, prob);
					for (Task t : task) {
						bid.add(new ArrayList<Bid>());
					}
					object.makeBid(agent, task, bid, agentBid, random);
					for (int i = 0; i < bid.size(); i++) {
						if (bid.get(i).isEmpty()) {
							bid.remove(i);
							i--;
						}
					}
					// 割当開始
					if (method == CPLEX) {

					} else if (method == SRNF) {
						while (bid.isEmpty() != true) {
							cand = allocate.maxValue(bid, random);
							if(cand.isEmpty())
								break;
							cand = allocate.maxPreference(cand);
							cand = allocate.maxValueAgain(cand);
							if (cand.size() > 1)
								item = allocate.SRNF(bid, cand, random);
							else
								item = cand.get(0);

							allocation.add(item);
							delete.DeleteAgent(bid, agentBid, envyList, item);
							delete.DeleteTask(bid, reserveBid, task, item);
							System.out.println(item.toString());

						}
						System.exit(10);
					}
				}
			}
		}
	}
}