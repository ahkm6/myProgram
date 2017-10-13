public class Bid {

	final static int REWARD = Main.REWARD;
	final static int PROCESSTIME = Main.PROCESSTIME;
	final static int Value = Main.Value;
	Task task;
	Agent agent;
	int reward;
	int preferentialNumber;
	int tempPreference;
	int processTime;
	int value;

	public Bid(Task task, Agent agent, int rew, int pNum, int tpNum, int pTime) {
		this.task = task;
		this.agent = agent;
		this.reward = rew;
		this.preferentialNumber = pNum;
		this.tempPreference = tpNum;
		this.processTime = pTime;
		if (Value == REWARD)
			this.value = rew;
		else if (Value == PROCESSTIME)
			this.value = -pTime;
	}

	public int value(){
		return this.value;
	}

	public int agentNumber() {
		return this.agent.agentNumber();
	}

	public int taskNumber() {
		return this.task.taskNumber();
	}

	public int deadline() {
		return this.task.deadline();
	}

	public void prefPlus(int count) {
		this.preferentialNumber += count;
		this.tempPreference += count;
	}

	public String toString() {
		return this.taskNumber() + "," + this.agentNumber() + "," + this.deadline() + "," + this.processTime + "," + this.value;

	}
}