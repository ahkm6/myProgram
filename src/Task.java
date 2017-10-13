import java.util.Random;

public class Task {

	final int taskReward = Main.taskReward;

	int taskNumber;
	int taskResource[] = new int[3];
	int reward;
	int deadline;

	public Task(int taskNum, int[] taskRes, int deadline, Random random) {
		this.taskNumber = taskNum;
		this.taskResource = taskRes;
		if (taskReward == 0)
			for (int i = 0; i < taskRes.length; i++)
				this.reward += taskRes[i];
		else
			this.reward = random.nextInt(90) + 90;
		this.deadline = deadline;
	}

	public int taskNumber() {
		return this.taskNumber;
	}

	public void resetTask(int n) {
		this.taskNumber = n;
	}

	public int reward() {
		return this.reward;
	}

	public int[] resource() {
		return this.taskResource;
	}

	public int deadline() {
		return this.deadline;
	}

	public String toString() {
		return this.taskNumber + "," + this.taskResource[0] + "," + this.taskResource[1] + "," + this.taskResource[2]
				+ "," + this.reward + "," + this.deadline;
	}
}