public class Task{

	int taskNumber;
	int taskResource[] = new int[3];
	int reward;

	public Task(int taskNum, int[] taskRes){
		this.taskNumber = taskNum;
		this.taskResource = taskRes;
		for(int i = 0; i < taskRes.length; i++)
		this.reward += taskRes[i];
	}

	public void resetTask(int n){
		this.taskNumber = n;
	}
}