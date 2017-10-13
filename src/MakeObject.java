import java.util.ArrayList;
import java.util.Random;

public class MakeObject {

	final int RANDOM = Main.RANDOM;
	final int VIAS = Main.VIAS;
	final int MIXED = Main.MIXED;
	final int LENGTH = Main.LENGTH;

	public void makeBid(){

	}

	public void makeTask(ArrayList<Task> task, int generated, Random random, double[] prob) {
		Double checker;
		for (int i = 0; i < task.size(); i++) {
			task.get(i).resetTask(i);
		}
		for (int i = 0; i < generated; i++) {
			checker = random.nextDouble();
			if (checker < prob[0])
				task.add(new Task(task.size(), normalTask(LENGTH, random)));
			else{
				if(checker < prob[0]+prob[1])
					task.add(new Task(task.size(), viasTask(LENGTH, random, 0)));
				else if(checker < prob[0]+prob[1]+prob[2])
					task.add(new Task(task.size(), viasTask(LENGTH, random, 1)));
				else
					task.add(new Task(task.size(), viasTask(LENGTH, random, 2)));
			}

		}
	}

	public void makeAgent(Agent[] agent, int n, Random random) {
		switch (n) {
		case RANDOM:
			for (int i = 0; i < agent.length; i++) {
				agent[i] = new Agent(i, randomAgent(LENGTH, random), random);
			}
			break;
		case VIAS:
			for (int i = 0; i < agent.length / 4; i++) {
				agent[i] = new Agent(i, highAgent(LENGTH, random), random);
			}
			for (int i = agent.length / 4; i < agent.length; i++) {
				agent[i] = new Agent(i, viasAgent(LENGTH, random, random.nextInt(LENGTH)), random);
			}
			break;
		case MIXED:
			for (int i = 0; i < agent.length / 4; i++) {
				agent[i] = new Agent(i, highAgent(LENGTH, random), random);
			}
			for (int i = agent.length / 4; i < agent.length / 2; i++) {
				agent[i] = new Agent(i, lowAgent(LENGTH, random), random);
			}
			for (int i = agent.length / 2; i < agent.length; i++) {
				agent[i] = new Agent(i, viasAgent(LENGTH, random, random.nextInt(LENGTH)), random);
			}
			break;
		default:
			System.out.println("something is wrong.");
			System.exit(1);
		}
	}

	public int[] randomAgent(int length, Random random) {
		int[] resource = new int[length];
		for (int i = 0; i < length; i++) {
			resource[i] = 2 + random.nextInt(6);
		}
		return resource;
	}

	public int[] highAgent(int length, Random random) {
		int[] resource = new int[length];
		for (int i = 0; i < length; i++) {
			resource[i] = 5 + random.nextInt(3);
		}
		return resource;
	}

	public int[] lowAgent(int length, Random random) {
		int[] resource = new int[length];
		for (int i = 0; i < length; i++) {
			resource[i] = 2 + random.nextInt(3);
		}
		return resource;
	}

	public int[] viasAgent(int length, Random random, int number) {
		int[] resource = new int[length];
		for (int i = 0; i < length; i++) {
			if (i == number)
				resource[i] = 7 + random.nextInt(3);
			else
				resource[i] = 2 + random.nextInt(3);
		}
		return resource;
	}

	public int[] normalTask(int length, Random random) {
		int[] resource = new int[length];
		for (int i = 0; i < length; i++) {
			resource[i] = 30 + random.nextInt(31);
		}
		return resource;
	}

	public int[] viasTask(int length, Random random, int number) {
		int[] resource = new int[length];
		for (int i = 0; i < length; i++) {
			if (i == number)
				resource[i] = 70 + random.nextInt(21);
			else
				resource[i] = 20 + random.nextInt(21);
		}
		return resource;
	}
}