public class Bid{

	int taskNumber;
	int agentNumber;
	int reward;
	int preferentialNumber;
	int tempPreference;
	int processTime;

	public Bid(int taskNum, int agentNum, int rew, int pNum, int tpNum, int pTime){
		taskNumber = taskNum;
		agentNumber = agentNum;
		reward = rew;
		preferentialNumber = pNum;
		tempPreference = tpNum;
		processTime = pTime;
	}
}