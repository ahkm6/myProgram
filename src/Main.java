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
	final static int GOODBAD = 3;
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
	final static int QVALUE = 2;
	final static int no = 3;

	final static int ON = 0;
	final static int OFF = 1;

	static int Switch = OFF;
	static int penalty = -5;
	static int SIMULATIONTIME = 310001;
	static int Value = PROCESSTIME;
	static int bidNumber = 5;
	static int agentType = GOODBAD;
	static int taskReward = TASK_REWARD;
	static int Loop = 3;
	static int incliment = 1;
	static int taskLoad = 25;
	static int strategy = RLEARN;
	static int method = SRNF;
	static int Output = large;

	public static void main(String[] args) {
		System.out.println("開始");
		MakeObject object = new MakeObject();
		Others other = new Others();
		Allocate allocate = new Allocate();
		Delete delete = new Delete();
		Output output = new Output();
		Learning learning = new Learning();
		CPLEX cplex = new CPLEX();
		Task TASK = new Task();

		String str = other.PrintTactics(strategy);
		String age = other.PrintAgent(agentType);
		String val = other.PrintValue(Value);
		String rew = other.PrintReward(taskReward);
		String met = other.PrintMethod(method);

		int tempValue[][] = new int[4][2];

		if (Output == large && incliment != 1) {
			System.out.println("something is wrong");
			System.exit(1);
		}
for(int numbers = 0; numbers < 1; numbers++){
//	Task.slice(numbers);
		for (int bias = 0; bias < 2; bias++) {
			if(bias == 1)
				taskLoad = 20;
		/*	if(Output == little)
			switch(bias) {
			case 0:
				taskLoad = 62;    //learn74
				incliment = 8;
				break;
			case 1:
				taskLoad = 62;    //learn82
				incliment = 8;
				break;
			case 2:
				taskLoad = 44;    //learn62
				incliment = 8;
				break;
			case 3:
				taskLoad = 34;    //learn52
				incliment = 8;
				break;
			default:
				break;
			}*/
			double[] prob = other.bias(bias);

			double[] IncSum = new double[incliment];
			double[] IncValue = new double[incliment];
			double[] IncDrop = new double[incliment];
			double[] IncWorkRate = new double[incliment];
			double[] IncProcessTime = new double[incliment];
			double[] IncDuration = new double[incliment];
			double[][][] IncQ = new double[incliment][4][2];
			double[][] IncCalcTime = new double[incliment][2];

			double[] largeSum = new double[SIMULATIONTIME];
			double[] largeValue = new double[SIMULATIONTIME];
			double[] largeDrop = new double[SIMULATIONTIME];
			double[] largeWorkRate = new double[SIMULATIONTIME];
			double[] largeProcessTime = new double[SIMULATIONTIME];
			double[] largeDuration = new double[SIMULATIONTIME];
			double[][] largeCalcTime = new double[SIMULATIONTIME][2];
			double[][][] largeQ = new double[SIMULATIONTIME][4][2];
			double[][][] agentQvalue = null;
			ArrayList<Integer> agentQ = null;
			ArrayList<Agent> AgentQ = null;

			for (int inc = 0; inc < incliment; inc++) {
				int taskload = taskLoad + inc * 2;
				double[] loopSum = new double[SIMULATIONTIME];
				double[] loopValue = new double[SIMULATIONTIME];
				double[] loopDrop = new double[SIMULATIONTIME];
				double[] loopWorkRate = new double[SIMULATIONTIME];
				double[] loopProcessTime = new double[SIMULATIONTIME];
				double[] loopDuration = new double[SIMULATIONTIME];
				double[][][] loopQ = new double[SIMULATIONTIME][4][2];
				double[][] loopCalcTime = new double[SIMULATIONTIME][2];

				for (int loop = 0; loop < Loop; loop++) {
					System.out.println(Task.slice);
					ArrayList<Bid> KEEP = new ArrayList<Bid>();
					double sum = 0;
					double value = 0;
					double drop = 0;
					double processTime = 0;
					double duration = 0;
					double calcTime = 0;
					double allStart = 0;
					double allEnd = 0;
					double start = 0;
					double end = 0;

					int processedNumber = 0;

					int time = 1;
					Random random = new Random(loop * 30 + 1000001);

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

					if (Output == QVALUE) {
						AgentQ = new ArrayList<Agent>();
						agentQ = new ArrayList<Integer>();
						for (Agent a : agent) {
							if (a.agentResource[0] == 2 && a.agentResource[1] == 2 && a.agentResource[2] == 2) {
								agentQ.add(a.agentNumber());
								AgentQ.add(a);
							}
							if (a.agentResource[0] == 3 && a.agentResource[1] ==3 && a.agentResource[2] == 3) {
								agentQ.add(a.agentNumber());
								AgentQ.add(a);
							}
							if (a.agentResource[0] == 4 && a.agentResource[1] == 4 && a.agentResource[2] == 4) {
								agentQ.add(a.agentNumber());
								AgentQ.add(a);
							}
						}
						agentQvalue = new double[agentQ.size()][SIMULATIONTIME][4];
					}

					while (time < SIMULATIONTIME) {
						allStart = System.nanoTime();
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
						start = System.nanoTime();
						// Some processing

						if (method == CPLEX) {
							try {
								cplex.cplex(task, bid, agentBid, allocation);
							} catch (Throwable e) {
								// TODO 自動生成された catch ブロック
								e.printStackTrace();
							}
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
							for (int i = 0; i < task.size(); i++) {
								for (int j = 0; j < envyList.size(); j++) {
									for (int k = 0; k < envyList.get(j).size(); k++) {
										if(task.get(i) == envyList.get(j).get(k).task()) {
											changingList.add(envyList.get(j).get(k));
										}
									}
								}
								if(changingList.size() > 0) {
									int num = 0;
									if (changingList.size() > 1) {
										int ini = changingList.get(0).value();
										for (int j = 1; j < changingList.size(); j++) {
											if (changingList.get(j).value() > ini) {
												ini = changingList.get(j).value();
												num = j;
											}
										}
									}
									for (int k = 0; k < allocation.size(); k++) {
										if (changingList.get(num).agentNumber() == allocation.get(k).agentNumber()) {
											task.add(allocation.get(k).task());
											allocation.get(k).task().allocated(0);
											allocation.remove(k);
											allocation.add(changingList.get(num));
											break;
										}
									}
									for(int k = 0; k < envyList.size();k++) {
										if(envyList.get(k).get(0).agentNumber() == changingList.get(num).agentNumber()) {
											for(int j = 0; j < envyList.get(k).size();j++) {
												if(envyList.get(k).get(j) == changingList.get(num)) {
													if (changingList.get(num).preferentialNumber() == 1) {
														envyList.remove(k);
														break;
													}
													for (int l = 0; l < envyList.get(k).size(); l++) {
														if (envyList.get(k).get(l).preferentialNumber() >= changingList.get(num)
																.preferentialNumber()) {
															envyList.get(k).remove(l);
															l--;
														}
													}
												}
											}
										}
									}
									task.remove(i);
									changingList.clear();
									i--;
								}
							}
						}
						end = System.nanoTime();
						calcTime = end-start;

						for (int i = 0; i < task.size(); i++) {
							if (task.get(i).elapsedTime()) {
								drop++;
								task.remove(i);
								i--;
							}
						}
						for (int i = 0; i < busyAgent.size(); i++) {
							if (busyAgent.get(i).elapsedTime()) {
								sum += busyAgent.get(i).getBid().reward();
								value += busyAgent.get(i).getBid().value();
								processTime += busyAgent.get(i).getBid().processTime();
								duration += busyAgent.get(i).getBid().duration();

								if (strategy == ELEARN || strategy == RLEARN) {
									if(Switch == ON) {
										learning.update(busyAgent.get(i), busyAgent.get(i).reward(),busyAgent.get(i).QValue());
									}else {
										learning.update(busyAgent.get(i), busyAgent.get(i).reward());
									}
									learning.greedy(busyAgent.get(i), random);
								}
								busyAgent.remove(i);
								i--;
							}
						}
						if (strategy == ELEARN)
							for (ArrayList<Bid> b : agentBid) {
								learning.update(b.get(0).agent(), penalty);
								learning.greedy(b.get(0).agent(), random);
							}
						else if (strategy == RLEARN)
							for (ArrayList<Bid> b : agentBid) {
								learning.update(b.get(0).agent(), 0);
								learning.greedy(b.get(0).agent(), random);
							}

						KEEP.addAll(0, allocation);
						while (KEEP.size() > 51)
							KEEP.remove(KEEP.size() - 1);
						other.calculate(KEEP);
						for (Bid b : allocation) {

							if (strategy == ELEARN) {
								b.agent().reward(other.bias(b));
								if(Switch == ON) {
									b.agent().addUtility(b.reward());
									b.agent().calc();
									b.agent().bias(b.reward());
								}
							}else if (strategy == RLEARN)
								b.agent().reward(b.value());
							b.agent().addBid(b);
							agent[b.agentNumber()].busy(b.processTime());
							busyAgent.add(b.agent());
						}
						processedNumber = allocation.size();
						if (processedNumber != 0) {
							loopSum[time] = sum;
							loopValue[time] = value;
							loopDrop[time] = drop;
							loopProcessTime[time] = processTime / processedNumber;
							loopDuration[time] = duration / processedNumber;
						} else {
							loopSum[time] = 0;
							loopValue[time] = 0;
							loopDrop[time] = drop;
							loopProcessTime[time] = 0;
							loopDuration[time] = 0;
						}
						loopCalcTime[time][0] = calcTime;

						tempValue[0][0]=tempValue[1][0]=tempValue[2][0]=tempValue[3][0]=0;
						tempValue[0][1]=tempValue[1][1]=tempValue[2][1]=tempValue[3][1]=0;
						for (Agent a : agent) {
							if(a.sum() > 12)
								tempValue[a.agentStrategy()][0]++;
							else
								tempValue[a.agentStrategy()][1]++;
						}
						for(int i = 0; i < 4; i++) {
							loopQ[time][i][0] = tempValue[i][0];
							loopQ[time][i][1] = tempValue[i][1];
						}
						if(Output == QVALUE) {
							for(int i = 0; i < agentQ.size(); i++) {
								for(int j = 0; j < 4; j++)
									agentQvalue[i][time][j] = agent[agentQ.get(i)].Q[j];
							}
						}
						loopWorkRate[time] = (double) busyAgent.size() / AGENT;
						largeWorkRate[time] = (double) busyAgent.size() / AGENT;

						bid.clear();
						agentBid.clear();
						envyList.clear();
						allocation.clear();
					//	System.out.println(time);

						sum = drop = processTime = duration = value = 0;
						allEnd = System.nanoTime();
						loopCalcTime[time][1] = allEnd - allStart;
						time++;
						if(time > 1000)
							if((time-50000) % ((SIMULATIONTIME-1)/100) == 0 && (Output == large || Output == QVALUE)) {
								TASK.slice();
						//		other.stdout();
						}
					}
					if (Output == little) {
						for (int i = SIMULATIONTIME - 1; i > SIMULATIONTIME - 1001; i--) {
							IncSum[inc] += loopSum[i] / 1000;
							IncValue[inc] += loopValue[i] / 1000;
							IncDrop[inc] += loopDrop[i] / 1000;
							IncProcessTime[inc] += loopProcessTime[i] / 1000;
							IncDuration[inc] += loopDuration[i] / 1000;
							IncWorkRate[inc] += loopWorkRate[i] / 1000;
							IncQ[inc][0][0] += loopQ[i][0][0] / 1000;
							IncQ[inc][1][0] += loopQ[i][1][0] / 1000;
							IncQ[inc][2][0] += loopQ[i][2][0] / 1000;
							IncQ[inc][3][0] += loopQ[i][3][0] / 1000;
							IncQ[inc][0][1] += loopQ[i][0][1] / 1000;
							IncQ[inc][1][1] += loopQ[i][1][1] / 1000;
							IncQ[inc][2][1] += loopQ[i][2][1] / 1000;
							IncQ[inc][3][1] += loopQ[i][3][1] / 1000;
							IncCalcTime[inc][0] += loopCalcTime[i][0]/1000;
							IncCalcTime[inc][1] += loopCalcTime[i][1]/1000;
						}
					}
					if(Output == large) {
						for(int i = 0; i < SIMULATIONTIME - 1; i++) {
							largeSum[i] += loopSum[i];
							largeValue[i] += loopValue[i];
							largeDrop[i] += loopDrop[i];
							largeProcessTime[i] += loopProcessTime[i];
							largeDuration[i] += loopDuration[i];
							largeCalcTime[i][0] += loopCalcTime[i][0];
							largeCalcTime[i][1] += loopCalcTime[i][1];
							largeQ[i][0][0] += loopQ[i][0][0];
							largeQ[i][1][0] += loopQ[i][1][0];
							largeQ[i][2][0] += loopQ[i][2][0];
							largeQ[i][3][0] += loopQ[i][3][0];
							largeQ[i][0][1] += loopQ[i][0][1];
							largeQ[i][1][1] += loopQ[i][1][1];
							largeQ[i][2][1] += loopQ[i][2][1];
							largeQ[i][3][1] += loopQ[i][3][1];
							if(i % 100 == 0)
								System.out.println(largeSum[i]);
						}
					}

					System.out.println(loop + "周目終わり");
					if(Output == large)
					TASK.reset();
				}
				if (Output == large)
					output.changeRatio(largeSum,largeValue, largeDrop, largeProcessTime, largeDuration, largeWorkRate, largeQ,largeCalcTime,
							bias, str,
							age, val, rew,met);
				if(Output == QVALUE)
					output.Qvalue(agentQvalue,agentQ,AgentQ,bias,str,age,val,rew,met);

			}
			if (Output == little)
				output.taskLoad(IncSum,IncValue, IncDrop, IncProcessTime, IncDuration, IncWorkRate, IncQ,IncCalcTime, bias, str, age, val,
						rew,met);
		}

	}
	}
}