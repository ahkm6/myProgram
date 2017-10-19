import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Output {

	final static int slice = 1;

	static int loop = Main.Loop;

	public void changeRatio(double[] sum, double[] drop, double[] pTime, double[] duration, double[] rate, double[][] q,
			int bias, String str, String age, String val, String rew, String met) {
		try {
			FileWriter fw = new FileWriter("//Users/n.iijima/Box Sync/菅原研SharedBox/2014_Iijima/実験データ保存用/100" + str
					+","+ age +","+ val+"," + rew+"," +met+ ".csv", true); // ※１
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
			double[] Sum = new double[sum.length / slice];
			double[] Drop = new double[sum.length / slice];
			double[] PTime = new double[sum.length / slice];
			double[] Duration = new double[sum.length / slice];
			double[] Rate = new double[sum.length / slice];
			double[][] Q = new double[sum.length][4];
			for (int i = 0; i < Sum.length; i++) {
				for (int j = i * slice; j < i * slice + slice; j++) {
					Sum[i] += sum[j] / slice;
					Drop[i] += drop[j] / slice;
					PTime[i] += pTime[j] / slice;
					Duration[i] += duration[j] / slice;
					Rate[i] += rate[j] / slice;
					Q[i][0] = q[j][0] / slice;
					Q[i][1] = q[j][1] / slice;
					Q[i][2] = q[j][2] / slice;
					Q[i][3] = q[j][3] / slice;
				}
			}
			pw.println(bias);
			if (Main.strategy < 4) {
				pw.println(",reward,drop,pTime,duration,workRate");
				for (int i = 0; i < Sum.length; i++) {

					pw.println(i * slice + "," + Sum[i] / loop + "," + Drop[i] / loop + "," + PTime[i] / loop + ","
							+ Duration[i] / loop + "," + Rate[i] / loop);

				}
			} else {
				pw.println(",reward,drop,pTime,duration,workRate,,EDF,HRF,SPTF,CEF");
				for (int i = 0; i < Sum.length; i++) {

					pw.println(i * slice + "," + Sum[i] / loop + "," + Drop[i] / loop + "," + PTime[i] / loop + ","
							+ Duration[i] / loop + "," + Rate[i] / loop + "," + i * slice + "," + Q[i][0] / Main.AGENT
							+ "," + Q[i][1] / Main.AGENT + "," + Q[i][2] / Main.AGENT + "," + Q[i][3] / Main.AGENT
							+ ",");

				}
			}
			System.out.println("終了");
			pw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void taskLoad(double[] sum, double[] drop, double[] pTime, double[] duration, double[] rate, double[][] q,
			int bias, String str, String age, String val, String rew, String met) {
		try {
			FileWriter fw = new FileWriter("//Users/n.iijima/Box Sync/菅原研SharedBox/2014_Iijima/実験データ保存用/little" + str
					+","+ age +","+ val+"," + rew+"," +met+ ".csv", true); // ※１
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
			if (Main.strategy < 4) {
				pw.println(bias);
				pw.println(",reward,drop,pTime,duration,workRate");
				for (int i = 0; i < sum.length; i++) {
					pw.println(((i * 2) + Main.taskLoad) + "," + sum[i] / loop + "," + drop[i] / loop + ","
							+ pTime[i] / loop + "," + duration[i] / loop + "," + rate[i] / loop);
				}
			} else {
				pw.println(",reward,drop,pTime,duration,workRate,,EDF,HRF,SPTF,CEF");
				for (int i = 0; i < sum.length; i++) {
					pw.println(((i * 2) + Main.taskLoad) + "," + sum[i] / loop + "," + drop[i] / loop + ","
							+ pTime[i] / loop + "," + duration[i] / loop + "," + rate[i] / loop + ","
							+ ((i * 2) + Main.taskLoad) + "," + q[i][0] / Main.AGENT + "," + q[i][1] / Main.AGENT + ","
							+ q[i][2] / Main.AGENT + "," + q[i][3] / Main.AGENT + ",");
				}
			}
			pw.println();
			System.out.println("終了");
			pw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}