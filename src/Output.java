import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Output {

	static int loop = Main.Loop;

	public void changeRatio(double[][] sum, double[][] drop, double[][] pTime, double[][] duration, double[][] rate,
			int bias, String str, String age, String val, String rew) {
		try {
			FileWriter fw = new FileWriter(
					"//Users/n.iijima/Box Sync/菅原研SharedBox/2014_Iijima/実験データ保存用/" + str + age + val + rew + ".csv",
					true); // ※１
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
			if (Main.strategy < 4) {
				pw.println(",reward,drop,pTime,duration,workRate");
				for (int i = 0; i < sum.length; i++) {
					for (int j = 0; j < sum[0].length; j++) {
						pw.println(sum[i][j] / loop + "," + drop[i][j] / loop + "," + pTime[i][j] / loop + ","
								+ duration[i][j] / loop + "," + rate);
					}
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
			FileWriter fw = new FileWriter(
					"//Users/n.iijima/Box Sync/菅原研SharedBox/2014_Iijima/実験データ保存用/" + str + age + val + rew + ".csv",
					true); // ※１
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
			if (Main.strategy < 4) {
				pw.println(",reward,drop,pTime,duration,workRate");
				for (int i = 0; i < sum.length; i++) {
					pw.println(((i * 2) + Main.taskLoad) + "," + sum[i] / loop + "," + drop[i] / loop + ","
							+ pTime[i] / loop + "," + duration[i] / loop + "," + rate[i] / loop);
				}
			} else {
				pw.println(",reward,drop,pTime,duration,workRate,,EDF,HRF,SPTF,CEF");
				for (int i = 0; i < sum.length; i++) {
					pw.println(((i * 2) + Main.taskLoad) + "," + sum[i] / loop + "," + drop[i] / loop + ","
							+ pTime[i] / loop + "," + duration[i] / loop + "," + rate[i] / loop + "," + ((i * 2) + Main.taskLoad)
							+ "," + q[i][0]/Main.AGENT + "," + q[i][1]/Main.AGENT + "," + q[i][2]/Main.AGENT + "," + q[i][3]/Main.AGENT + ",");
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