import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Output {

	static int loop = Main.Loop;

	public void changeRatio(double[] sum, double[] drop, double[] pTime, double[] duration, double[] rate, double[][] q,
			int bias, String str, String age, String val, String rew) {
		try {
			FileWriter fw = new FileWriter("//Users/n.iijima/Box Sync/菅原研SharedBox/2014_Iijima/実験データ保存用/Llarge" + str
					+ age + val + rew + ".csv", true); // ※１
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
			double[] Sum = new double[sum.length / 10];
			double[] Drop = new double[sum.length / 10];
			double[] PTime = new double[sum.length / 10];
			double[] Duration = new double[sum.length / 10];
			double[] Rate = new double[sum.length / 10];
			double[][] Q = new double[sum.length][4];
			for (int i = 0; i < Sum.length; i++) {
				for (int j = i * 10; j < i * 10 + 10; j++) {
					Sum[i] += sum[j] / 10;
					Drop[i] += drop[j] / 10;
					PTime[i] += pTime[j] / 10;
					Duration[i] += duration[j] / 10;
					Rate[i] += rate[j] / 10;
					Q[i][0] = q[j][0] / 10;
					Q[i][1] = q[j][1] / 10;
					Q[i][2] = q[j][2] / 10;
					Q[i][3] = q[j][3] / 10;
				}
			}
			pw.println(bias);
			if (Main.strategy < 4) {
				pw.println(",reward,drop,pTime,duration,workRate");
				for (int i = 0; i < Sum.length; i++) {

					pw.println(i * 10 + "," + Sum[i] / loop + "," + Drop[i] / loop + "," + PTime[i] / loop + ","
							+ Duration[i] / loop + "," + Rate[i] / loop);

				}
			} else {
				pw.println(",reward,drop,pTime,duration,workRate,,EDF,HRF,SPTF,CEF");
				for (int i = 0; i < Sum.length; i++) {

					pw.println(i * 10 + "," + Sum[i] / loop + "," + Drop[i] / loop + "," + PTime[i] / loop + ","
							+ Duration[i] / loop + "," + Rate[i] / loop + "," + i * 10 + "," + Q[i][0] / Main.AGENT
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
			int bias, String str, String age, String val, String rew) {
		try {
			FileWriter fw = new FileWriter("//Users/n.iijima/Box Sync/菅原研SharedBox/2014_Iijima/実験データ保存用/little" + str
					+ age + val + rew + ".csv", true); // ※１
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