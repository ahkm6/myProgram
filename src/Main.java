import java.util.ArrayList;
import java.util.Random;

public class Main {

	// strategy
	final static int EDF = 0;
	final static int HRF = 1;
	final static int SPTF = 2;
	final static int CEF = 3;
	final static int ELEARN = 4;
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
	final static int AGENT = 300;

	final static int deadlineUnder = 15;
	final static int deadlineRange = 10;

	final static int little = 0;
	final static int large = 1;
	final static int no = 2;

	static int SIMULATIONTIME = 100001;
	static int Value = PROCESSTIME;
	static int bidNumber = 5;
	static int agentType = RANDOM;
	static int taskReward = TASK_REWARD;
	static int Loop = 1;
	static int incliment = 13;
	static int taskLoad = 24;
	static int strategy = ELEARN;
	static int method = SRNF;
	static int Output = little;

	public static void main(String[] args) {
		MakeObject object = new MakeObject();
		Others other = new Others();
		Allocate allocate = new Allocate();
		Delete delete = new Delete();
		Output output = new Output();
		Learning learning = new Learning();

		String str = other.PrintTactics(strategy);
		String age = other.PrintAgent(agentType);
		String val = other.PrintValue(Value);
		String rew = other.PrintReward(taskReward);

		for (int bias = 0; bias < 4; bias++) {
			double[] prob = other.bias(bias);

			double[] IncSum = new double[incliment];
			double[] IncDrop = new double[incliment];
			double[] IncWorkRate = new double[incliment];
			double[] IncProcessTime = new double[incliment];
			double[] IncDuration = new double[incliment];
			double[][] IncQ = new double[incliment][4];

			double[][] incSum = new double[incliment][SIMULATIONTIME];
			double[][] incDrop = new double[incliment][SIMULATIONTIME];
			double[][] incWorkRate = new double[incliment][SIMULATIONTIME];
			double[][] incProcessTime = new double[incliment][SIMULATIONTIME];
			double[][] incDuration = new double[incliment][SIMULATIONTIME];

			for (int inc = 0; inc < incliment; inc++) {
				int taskload = taskLoad + inc * 2;
				double[] loopSum = new double[SIMULATIONTIME];
				double[] loopDrop = new double[SIMULATIONTIME];
				double[] loopWorkRate = new double[SIMULATIONTIME];
				double[] loopProcessTime = new double[SIMULATIONTIME];
				double[] loopDuration = new double[SIMULATIONTIME];
				double[][] loopQ = new double[SIMULATIONTIME][4];
				for (int loop = 0; loop < Loop; loop++) {

					ArrayList<Bid> KEEP = new ArrayList<Bid>();
					double sum = 0;
					double drop = 0;
					double processTime = 0;
					double duration = 0;

					int processedNumber = 0;

					int time = 1;
					Random random = new Random(10001);

					Agent[] agent = new Agent[AGENT];
					ArrayList<Task> task = new ArrayList<Task>();
					ArrayList<Bid> changingList = new ArrayList<Bid>();
					ArrayList<Agent> busyAgent = new ArrayList<Agent>();
					ArrayList<ArrayList<Bid>> bid = new ArrayList<ArrayList<Bid>>();
					ArrayList<ArrayList<Bid>> agentBid = new ArrayList<ArrayList<Bid>>();
					ArrayList<ArrayList<Bid>> envyList = new ArrayList<ArrayList<Bid>>();

					ArrayList<Bid> allocation = new ArrayList<Bid>();
					ArrayList<Bid> cand = new ArrayList<Bid>();
					Bid item;
					object.makeAgent(agent, agentType, random);
				/*	for(Agent a : agent){
						System.out.println(a.toString());
					}
					System.exit(1);;*/
					System.out.println(agent[258].toString());
					while (time < SIMULATIONTIME) {
						object.makeTask(task, other.poisson(taskload, random), random, prob);
						for (int i = 0; i < task.size(); i++) {
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
								if (cand.isEmpty())
									break;
								cand = allocate.maxPreference(cand);
								cand = allocate.maxValueAgain(cand);
								if (cand.size() > 1)
									item = allocate.SRNF(bid, cand, random);
								else
									item = cand.get(0);
								// System.out.println(" " + item.toString());
								for (int i = 0; i < envyList.size(); i++) {
									for (int j = 0; j < envyList.get(i).size(); j++) {
										if (envyList.get(i).get(j).taskNumber() == item.taskNumber()
												&& envyList.get(i).get(j).value() > item.value())
											changingList.add(envyList.get(i).get(j));
									}
								}

								if (changingList.size() > 0) {
									int num = 0;
									if (changingList.size() > 1) {
										int ini = changingList.get(0).value();
										for (int i = 1; i < changingList.size(); i++) {
											if (changingList.get(i).value() > ini) {
												ini = changingList.get(i).value();
												num = i;
											}
										}
									}
									item = changingList.get(num);

									for (int k = 0; k < allocation.size(); k++) {
										if (changingList.get(num).agentNumber() == allocation.get(k).agentNumber()) {
											task.add(allocation.get(k).task());
											allocation.get(k).task().allocated(0);
											allocation.remove(k);
											break;
										}
									}
									for (int i = 0; i < envyList.size(); i++) {
										if (envyList.get(i).get(0).agentNumber() == changingList.get(num)
												.agentNumber()) {
											if (changingList.get(num).preferentialNumber() == 1) {
												envyList.remove(i);
												break;
											}
											for (int j = 0; j < envyList.get(i).size(); j++) {
												if (envyList.get(i).get(j).preferentialNumber() >= changingList.get(num)
														.preferentialNumber()) {
													envyList.get(i).remove(j);
													j--;
												}
											}
										}
									}
									changingList.clear();
								}
								allocation.add(item);
								delete.DeleteAgent(bid, agentBid, envyList, item);
								delete.DeleteTask(bid, task, item);

							}
							// System.exit(10);
						}
						for (int i = 0; i < task.size(); i++) {
							if (task.get(i).elapsedTime()) {
								drop++;
								task.remove(i);
								i--;
							}
						}
						for (int i = 0; i < busyAgent.size(); i++) {
							if (busyAgent.get(i).elapsedTime()) {
								if (strategy==ELEARN||strategy==RLEARN) {
									learning.update(busyAgent.get(i), busyAgent.get(i).reward());
									learning.greedy(busyAgent.get(i), random);
								}
								busyAgent.remove(i);
								i--;
							}
						}
						if (strategy == ELEARN)
							for (ArrayList<Bid> b : agentBid) {
								learning.update(b.get(0).agent(), -1);
								learning.greedy(b.get(0).agent(), random);
							}
						else if (strategy == RLEARN)
							for (ArrayList<Bid> b : agentBid) {
								learning.update(b.get(0).agent(), 0);
								learning.greedy(b.get(0).agent(), random);
							}
						int count = 0;
						KEEP.addAll(0, allocation);
						while (KEEP.size() > 51)
							KEEP.remove(KEEP.size() - 1);
						other.calculate(KEEP);
						for (Bid b : allocation) {
							count++;
							if(strategy == ELEARN)
								b.agent().reward(other.bias(b));
							else if(strategy == RLEARN)
								b.agent().reward(b.reward());
							sum += b.reward();
							processTime += b.processTime();
							duration += b.duration();
							agent[b.agentNumber()].busy(b.processTime());
							busyAgent.add(b.agent());
						}
						processedNumber = allocation.size();
						if (processedNumber != 0) {
							loopSum[time] = sum;
							loopDrop[time] = drop;
							loopProcessTime[time] = processTime  / processedNumber;
							loopDuration[time] = duration / processedNumber;
						} else {
							loopSum[time] = 0;
							loopDrop[time] = drop;
							loopProcessTime[time] = 0;
							loopDuration[time] = 0;
						}
						if (Output == little) {
						//	if (time > SIMULATIONTIME - 1001)
								for (Agent a : agent)
									loopQ[time][a.agentStrategy()]++;
						}
						loopWorkRate[time] = (double) busyAgent.size() / AGENT;
						bid.clear();
						agentBid.clear();
						envyList.clear();
						allocation.clear();
						sum=drop=processTime=duration=0;
						time++;
						if(time%100 == 0){
							System.out.println(loopQ[time-1][0]+","+loopQ[time-1][1]+","+loopQ[time-1][2]+","+loopQ[time-1][3]);
					//	System.out.printf("%d %d  %.3f, %.3f, %.3f, %.3f, %3f",time,agent[3].status,agent[3].Q[0],agent[3].Q[1],agent[3].Q[2],agent[3].Q[3],agent[3].reward);
					//	System.out.println();
					}
						if(time > 80000)
							System.exit(1);
					}
					if (Output == little) {
						for (int i = SIMULATIONTIME - 1; i > SIMULATIONTIME - 1001; i--) {
							IncSum[inc] += loopSum[i] / 1000;
							IncDrop[inc] += loopDrop[i] / 1000;
							IncProcessTime[inc] += loopProcessTime[i] / 1000;
							IncDuration[inc] += loopDuration[i] / 1000;
							IncWorkRate[inc] += loopWorkRate[i] / 1000;
							IncQ[inc][0] += loopQ[i][0] / 1000;
							IncQ[inc][1] += loopQ[i][1] / 1000;
							IncQ[inc][2] += loopQ[i][2] / 1000;
							IncQ[inc][3] += loopQ[i][3] / 1000;
						}
					} else {
						incSum[inc] = loopSum;
						incDrop[inc] = loopDrop;
						incProcessTime[inc] = loopProcessTime;
						incDuration[inc] = loopDuration;
						incWorkRate[inc] = loopWorkRate;
					}
					System.out.println(loop + "周目終わり");
				}
			}
			output.taskLoad(IncSum, IncDrop, IncProcessTime, IncDuration, IncWorkRate, IncQ, bias, str, age, val, rew);
		}
	}
}
