/**
 * 
 */

/**
 * @author user KongKang
 *
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ClusterAOI {

	public ClusterAOI() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	static String inputTab[][];
	static String genTab[][];
	static double tupleCost = 0;
	static double minMethodCost[][];
	static double minMethod = 0.0;
	static double maxMethodCost[][];
	static double maxMethod = 0.0;
	static double avgMethodCost[][];
	static double avgMethod = 0.0;
	static double wgtMethodCost[][];
	static double wgtMethod = 0.0;
	static String[] tempTab;
	static double min = Double.MAX_VALUE; // ***********不能是0**************
	static int mini = 0;
	static int minj = 0;
	static int s = 20;
	static String printTab[][];
	static int Tag = 0;

	public static void main(String[] args) throws IOException {

		// 讀取檔案
		ArrayList<String> age = new ArrayList();
		ArrayList<String> education = new ArrayList();
		ArrayList<String> occupation = new ArrayList();
		ArrayList<String> income = new ArrayList();
		ArrayList<String> creditExp = new ArrayList();
		FileReader fr = new FileReader("C:/Users/user/Desktop/論文/開始寫程式惹(抖/CreditData.csv"); // **************檔名要改回去*****************
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		while ((line = br.readLine()) != null) {
			String[] inputData = line.split(",");// 分割逗號
			age.add(inputData[0]);
			education.add(inputData[1]);
			occupation.add(inputData[2]);
			income.add(inputData[3]);
			creditExp.add(inputData[4]);
		}
		// 產生陣列
		inputTab = new String[age.size()][5];
		for (int i = 0; i < age.size(); i++) {
			inputTab[i][0] = (String) age.get(i);
			inputTab[i][1] = (String) education.get(i);
			inputTab[i][2] = (String) occupation.get(i);
			inputTab[i][3] = (String) income.get(i);
			inputTab[i][4] = (String) creditExp.get(i);

		}
		genTab = new String[501][7];
		for (int i = 0; i < genTab.length; i++) {
			genTab[i][0] = (String) age.get(i);
			genTab[i][1] = (String) education.get(i);
			genTab[i][2] = (String) occupation.get(i);
			genTab[i][3] = (String) income.get(i);
			genTab[i][4] = (String) creditExp.get(i);
		}
		genTab[0][5] = "數量";
		genTab[0][6] = "成本";
		for (int i = 1; i < genTab.length; i++) {
			genTab[i][5] = "1";
			genTab[i][6] = "0.0";
		}

		// 建立成本矩陣
		minMethodCost = new double[genTab.length - 1][genTab.length - 1];
		maxMethodCost = new double[genTab.length - 1][genTab.length - 1];
		avgMethodCost = new double[genTab.length - 1][genTab.length - 1];
		wgtMethodCost = new double[genTab.length - 1][genTab.length - 1];
		for (int i = 0; i < genTab.length - 1; i++) {
			for (int j = 0; j < genTab.length - 1; j++) {
				minMethodCost[i][j] = 0.0;
				maxMethodCost[i][j] = 0.0;
				avgMethodCost[i][j] = 0.0;
				wgtMethodCost[i][j] = 0.0;
			}
		}
		tempTab = new String[7];
		for (int i = 0; i < 7; i++) {
			tempTab[i] = "0";
		}
		// System.out.println("========================原始資料======================");
		// for (int i = 0; i < genTab.length; i++) {
		// System.out.print(i + ". ");
		// for (int j = 0; j < 7; j++) {
		// System.out.print(genTab[i][j] + ", ");
		// }
		// System.out.println();
		//
		// }
		System.out.println();
		ClusterAOI cluAOI = new ClusterAOI();
		cluAOI.InputTree(genTab);
		// System.out.println("================原始資料轉化後====================== ");
		// System.out.println();
		// for (int i = 0; i < genTab.length; i++) {
		// System.out.print(i + ". ");
		// for (int j = 0; j < 7; j++) {
		// System.out.print(genTab[i][j] + ", ");
		// }
		// System.out.println();
		// }
		// System.out.println();
		// ****************************************最小成本方法**************************************
		System.out.println();
		System.out.println("******************************最小成本方法*******************************");
		System.out.println();
		Tag = 1;
		for (int i = 1; i < genTab.length; i++) {
			for (int j = 1; j < genTab.length; j++) {
				if (i != j) {
					if (i < j) {
						cluAOI.CombineTuple(i, j);
						cluAOI.ComputTupleCost();
						cluAOI.MinMethod(i, j);
					}
				}
			}
		}
		// System.out.println("===================initial
		// costarray==================");
		// System.out.println();
		// for (int i = 0; i < genTab.length - 1; i++) {
		// for (int j = 0; j < genTab.length - 1; j++) {
		// System.out.printf("%5.2f ", minMethodCost[i][j]);
		// }
		// System.out.println();
		// }
		// System.out.println("=======================================================");
		for (int k = s; k < genTab.length - 1; k++) {
			cluAOI.FindMin(minMethodCost);
			// *********
			// System.out.println();
			// System.out.println("**** FindMin(minMethodCost): " +
			// minMethodCost[mini - 1][minj - 1] + ", mini: " + mini
			// + ", minj: " + minj);
			// *********
			cluAOI.ReplaceTuple(mini, minj);
			cluAOI.UpdateCostArray(minMethodCost, mini, minj);
		}
		System.out.println("");
		System.out.println("============================結果===========================");
		System.out.println("");
		for (int i = 0; i < genTab.length; i++) {
			if (!genTab[i][0].equals("x")) {
				System.out.print(i + ". ");
				for (int j = 0; j < 7; j++) {
					System.out.print(genTab[i][j] + ", ");
				}
				System.out.println();

			}
		}
		System.out.println();
		System.out.println("==========================轉換成文字========================");
		System.out.println();
		printTab = new String[genTab.length][7];
		for (int i = 0; i < genTab.length; i++) {
			if (!genTab[i][0].equals("x")) {
				System.out.print(i + ". ");
				for (int j = 0; j < 7; j++) {
					printTab[i][j] = genTab[i][j];
					if (i != 0) {
						cluAOI.ToPrint(printTab, i, j);
					}
					System.out.print(printTab[i][j] + ", ");
				}
				System.out.println();

			}
		}

		// **************************************************最大成本方法*************************************************
		System.out.println();
		System.out.println("******************************最大成本方法*******************************");
		System.out.println();
		Tag = 2;
		for (int i = 0; i < genTab.length; i++) {
			genTab[i][0] = (String) age.get(i);
			genTab[i][1] = (String) education.get(i);
			genTab[i][2] = (String) occupation.get(i);
			genTab[i][3] = (String) income.get(i);
			genTab[i][4] = (String) creditExp.get(i);
		}
		genTab[0][5] = "數量";
		genTab[0][6] = "成本";
		for (int i = 1; i < genTab.length; i++) {
			genTab[i][5] = "1";
			genTab[i][6] = "0.0";
		}
		cluAOI.InputTree(genTab);
		for (int i = 1; i < genTab.length; i++) {
			for (int j = 1; j < genTab.length; j++) {
				if (i != j) {
					if (i < j) {
						cluAOI.CombineTuple(i, j);
						cluAOI.ComputTupleCost();
						cluAOI.MaxMethod(i, j);
					}
				}
			}
		}
		// System.out.println("===================initial cost
		// array==================");
		// System.out.println();
		// for (int i = 0; i < genTab.length - 1; i++) {
		// for (int j = 0; j < genTab.length - 1; j++) {
		// System.out.printf("%5.2f ", maxMethodCost[i][j]);
		// }
		// System.out.println();
		// }
		// System.out.println("=======================================================");
		for (int k = s; k < genTab.length - 1; k++) {
			cluAOI.FindMin(maxMethodCost);
			// // *********
//			System.out.println("**** FindMin(maxMethodCost): " + maxMethodCost[mini - 1][minj - 1] + ", mini: " + mini
//					+ ", minj: " + minj);
			// // *********
			cluAOI.ReplaceTuple(mini, minj);
			cluAOI.UpdateCostArray(maxMethodCost, mini, minj);
		}
		System.out.println("");
		System.out.println("============================結果===========================");
		System.out.println("");
		for (int i = 0; i < genTab.length; i++) {
			if (!genTab[i][0].equals("x")) {
				System.out.print(i + ". ");
				for (int j = 0; j < 7; j++) {
					System.out.print(genTab[i][j] + ", ");
				}
				System.out.println();

			}
		}
		System.out.println();
		System.out.println("==========================轉換成文字========================");
		System.out.println();
		for (int i = 0; i < genTab.length; i++) {
			if (!genTab[i][0].equals("x")) {
				System.out.print(i + ". ");
				for (int j = 0; j < 7; j++) {
					printTab[i][j] = genTab[i][j];
					if (i != 0) {
						cluAOI.ToPrint(printTab, i, j);
					}
					System.out.print(printTab[i][j] + ", ");
				}
				System.out.println();

			}
		}

		// **************************************************平均成本方法*************************************************
		System.out.println();
		System.out.println("******************************平均成本方法*******************************");
		System.out.println();
		Tag = 3;
		for (int i = 0; i < genTab.length; i++) {
			genTab[i][0] = (String) age.get(i);
			genTab[i][1] = (String) education.get(i);
			genTab[i][2] = (String) occupation.get(i);
			genTab[i][3] = (String) income.get(i);
			genTab[i][4] = (String) creditExp.get(i);
		}
		genTab[0][5] = "數量";
		genTab[0][6] = "成本";
		for (int i = 1; i < genTab.length; i++) {
			genTab[i][5] = "1";
			genTab[i][6] = "0.0";
		}
		cluAOI.InputTree(genTab);
		for (int i = 1; i < genTab.length; i++) {
			for (int j = 1; j < genTab.length; j++) {
				if (i != j) {
					if (i < j) {
						cluAOI.CombineTuple(i, j);
						cluAOI.ComputTupleCost();
						cluAOI.AvgMethod(i, j);
					}
				}
			}
		}
		// System.out.println("===================initial cost
		// array==================");
		// System.out.println();
		// for (int i = 0; i < genTab.length - 1; i++) {
		// for (int j = 0; j < genTab.length - 1; j++) {
		// System.out.printf("%5.2f ", avgMethodCost[i][j]);
		// }
		// System.out.println();
		// }
		// System.out.println("=======================================================");
		for (int k = s; k < genTab.length - 1; k++) {
			cluAOI.FindMin(avgMethodCost);
			// // *********
//			System.out.println("**** FindMin(avgMethodCost): " + avgMethodCost[mini - 1][minj - 1] + ", mini: " + mini
//					+ ", minj: " + minj);
			// // *********
			cluAOI.ReplaceTuple(mini, minj);
			cluAOI.UpdateCostArray(avgMethodCost, mini, minj);
		}
		System.out.println("");
		System.out.println("============================結果===========================");
		System.out.println("");
		for (int i = 0; i < genTab.length; i++) {
			if (!genTab[i][0].equals("x")) {
				System.out.print(i + ". ");
				for (int j = 0; j < 7; j++) {
					System.out.print(genTab[i][j] + ", ");
				}
				System.out.println();

			}
		}
		System.out.println();
		System.out.println("==========================轉換成文字========================");
		System.out.println();
		for (int i = 0; i < genTab.length; i++) {
			if (!genTab[i][0].equals("x")) {
				System.out.print(i + ". ");
				for (int j = 0; j < 7; j++) {
					printTab[i][j] = genTab[i][j];
					if (i != 0) {
						cluAOI.ToPrint(printTab, i, j);
					}
					System.out.print(printTab[i][j] + ", ");
				}
				System.out.println();

			}
		}
		// **************************************************加權成本方法*************************************************
		System.out.println();
		System.out.println("******************************加權成本方法*******************************");
		System.out.println();
		Tag = 4;
		for (int i = 0; i < genTab.length; i++) {
			genTab[i][0] = (String) age.get(i);
			genTab[i][1] = (String) education.get(i);
			genTab[i][2] = (String) occupation.get(i);
			genTab[i][3] = (String) income.get(i);
			genTab[i][4] = (String) creditExp.get(i);
		}
		genTab[0][5] = "數量";
		genTab[0][6] = "成本";
		for (int i = 1; i < genTab.length; i++) {
			genTab[i][5] = "1";
			genTab[i][6] = "0.0";
		}
		cluAOI.InputTree(genTab);
		for (int i = 1; i < genTab.length; i++) {
			for (int j = 1; j < genTab.length; j++) {
				if (i != j) {
					if (i < j) {
						cluAOI.CombineTuple(i, j);
						cluAOI.ComputTupleCost();
						cluAOI.WgtMethod(i, j);
					}
				}
			}
		}
		// System.out.println("===================initial cost
		// array==================");
		// System.out.println();
		// for (int i = 0; i < genTab.length - 1; i++) {
		// for (int j = 0; j < genTab.length - 1; j++) {
		// System.out.printf("%5.2f ", wgtMethodCost[i][j]);
		// }
		// System.out.println();
		// }
		// System.out.println("=======================================================");
		for (int k = s; k < genTab.length - 1; k++) {
			cluAOI.FindMin(wgtMethodCost);
			// // *********
//			System.out.println("**** FindMin(wgtMethodCost): " + wgtMethodCost[mini - 1][minj - 1] + ", mini: " + mini
//					+ ", minj: " + minj);
			// // *********
			cluAOI.ReplaceTuple(mini, minj);
			cluAOI.UpdateCostArray(wgtMethodCost, mini, minj);
		}
		System.out.println("");
		System.out.println("============================結果===========================");
		System.out.println("");
		for (int i = 0; i < genTab.length; i++) {
			if (!genTab[i][0].equals("x")) {
				System.out.print(i + ". ");
				for (int j = 0; j < 7; j++) {
					System.out.print(genTab[i][j] + ", ");
				}
				System.out.println();

			}
		}
		System.out.println();
		System.out.println("==========================轉換成文字========================");
		System.out.println();
		for (int i = 0; i < genTab.length; i++) {
			if (!genTab[i][0].equals("x")) {
				System.out.print(i + ". ");
				for (int j = 0; j < 7; j++) {
					printTab[i][j] = genTab[i][j];
					if (i != 0) {
						cluAOI.ToPrint(printTab, i, j);
					}
					System.out.print(printTab[i][j] + ", ");
				}
				System.out.println();

			}
		}
	}

	public void InputTree(String[][] array) {
		// 將屬性值與概念樹結合
		for (int i = 1; i < array.length; i++) {

			// age屬性編號
			switch (array[i][0]) {
			case "1":
				array[i][0] = "11101";
				break;
			case "2":
				array[i][0] = "11102";
				break;
			case "3":
				array[i][0] = "11203";
				break;
			case "4":
				array[i][0] = "11204";
				break;
			case "5":
				array[i][0] = "12305";
				break;
			case "6":
				array[i][0] = "12306";
				break;
			case "7":
				array[i][0] = "12307";
				break;
			case "8":
				array[i][0] = "12408";
				break;
			case "9":
				array[i][0] = "12409";
				break;
			}

			// education屬性編號
			switch (genTab[i][1]) {
			case "1":
				array[i][1] = "1101";
				break;
			case "2":
				array[i][1] = "1102";
				break;
			case "3":
				array[i][1] = "1203";
				break;
			case "4":
				array[i][1] = "1204";
				break;
			case "5":
				array[i][1] = "1305";
				break;

			}
			// occupation屬性編號
			switch (array[i][2]) {
			case "1":
				array[i][2] = "11101";
				break;
			case "2":
				array[i][2] = "11102";
				break;
			case "3":
				array[i][2] = "11103";
				break;
			case "4":
				array[i][2] = "11204";
				break;
			case "5":
				array[i][2] = "11205";
				break;
			case "6":
				array[i][2] = "11306";
				break;
			case "7":
				array[i][2] = "11307";
				break;
			case "8":
				array[i][2] = "12408";
				break;
			case "9":
				array[i][2] = "12409";
				break;
			case "10":
				array[i][2] = "12410";
				break;
			case "11":
				array[i][2] = "12511";
				break;
			case "12":
				array[i][2] = "12412";
				break;
			case "13":
				array[i][2] = "12513";
				break;
			case "14":
				array[i][2] = "12514";
				break;
			case "15":
				array[i][2] = "12615";
				break;
			case "16":
				array[i][2] = "12616";
				break;
			case "17":
				array[i][2] = "12617";
				break;
			case "18":
				array[i][2] = "12618";
				break;
			case "19":
				array[i][2] = "13719";
				break;
			case "20":
				array[i][2] = "13720";
				break;
			case "21":
				array[i][2] = "13821";
				break;
			}
			// income屬性編號
			switch (array[i][3]) {
			case "1":
				array[i][3] = "11101";
				break;
			case "2":
				array[i][3] = "11102";
				break;
			case "3":
				array[i][3] = "11203";
				break;
			case "4":
				array[i][3] = "11204";
				break;
			case "5":
				array[i][3] = "12305";
				break;
			case "6":
				array[i][3] = "12306";
				break;
			case "7":
				array[i][3] = "12407";
				break;
			case "8":
				array[i][3] = "12408";
				break;

			}
			// occupation屬性編號
			switch (array[i][4]) {
			case "1":
				array[i][4] = "11101";
				break;
			case "2":
				array[i][4] = "11102";
				break;
			case "3":
				array[i][4] = "11203";
				break;
			case "4":
				array[i][4] = "11204";
				break;
			case "5":
				array[i][4] = "12305";
				break;
			case "6":
				array[i][4] = "12306";
				break;
			case "7":
				array[i][4] = "12407";
				break;
			case "8":
				array[i][4] = "12408";
				break;

			}
		}
	}

	// 合併tuple
	public void CombineTuple(int i, int j) {
		int tempi1;
		int tempj1;
		int tempi2;
		int tempj2;
		int tempi3;
		int tempj3;
		int dif;
		if (!(genTab[j][0]).equals("x")) {
			if (!(genTab[i][0]).equals("x")) {
				for (int k = 0; k < 5; k++) {
					tempi1 = Integer.parseInt(genTab[i][k]) / 10;
					tempj1 = Integer.parseInt(genTab[j][k]) / 10;
					tempi2 = Integer.parseInt(genTab[i][k]) / 100;
					tempj2 = Integer.parseInt(genTab[j][k]) / 100;
					tempi3 = Integer.parseInt(genTab[i][k]) / 1000;
					tempj3 = Integer.parseInt(genTab[j][k]) / 1000;
					if (genTab[i][k].equals(genTab[j][k])) {
						tempTab[k] = genTab[i][k];
					} else {
						// 若屬性值於概念階層同一層
						if (genTab[i][k].length() == genTab[j][k].length()) {
							// 往上爬升一層
							if ((genTab[i][k].length() != 5) && (genTab[i][k].length() != 4)) {
								if (tempi1 == tempj1) {
									tempTab[k] = Integer.toString(tempi1);
								} else {
									tempTab[k] = "1";
								}
							} else {
								if (tempi2 == tempj2) {
									tempTab[k] = Integer.toString(tempi2);
								} else {
									if (tempi3 == tempj3) {
										tempTab[k] = Integer.toString(tempi3);
									} else {
										tempTab[k] = "1";
									}
								}
							}
						}
						// 屬性值於概念階層不同層
						else {
							dif = genTab[i][k].length() - genTab[j][k].length();
							switch (dif) {
							// 若j列高於i列一層
							case 1: {
								// 往上爬升至j
								if (tempi1 == Integer.parseInt(genTab[j][k])) {
									tempTab[k] = genTab[j][k];
								} else {
									// i往上爬升兩層,j往上爬升一層
									if (tempi2 == tempj1) {
										tempTab[k] = Integer.toString(tempi2);
									}
									// 爬升至any
									else {
										tempTab[k] = "1";
									}
								}
								break;
							}
								// i列高於j列一層
							case -1: {
								// 往上爬升至i
								if (tempj1 == Integer.parseInt(genTab[i][k])) {
									tempTab[k] = genTab[i][k];
								} else {
									// i往上爬升兩層,j往上爬升一層
									if (tempi1 == tempj2) {
										tempTab[k] = Integer.toString(tempi1);
									}
									// 爬升至any
									else {
										tempTab[k] = "1";
									}
								}
								break;
							}
								// j列高於i列兩層 or i第四層與j第三層
							case 2: {
								// i往上爬升至j
								if ((genTab[i][k].length() != 5) && (genTab[i][k].length() != 4)) {
									tempTab[k] = "1";
								} else {
									if (tempi2 == Integer.parseInt(genTab[j][k])) {
										tempTab[k] = genTab[j][k];
									} else {
										if ((tempi3) == tempj1) {
											tempTab[k] = Integer.toString(tempj1);
										}
										// 爬升至any
										else {
											tempTab[k] = "1";

										}
									}
								}
								break;
							}
								// i列高於j列兩層or j第四層與i第三層
							case -2: {
								// 若相等,爬升至i
								if ((genTab[j][k].length() != 5) && (genTab[i][k].length() != 4)) {
									tempTab[k] = "1";
								} else {
									if (tempj2 == Integer.parseInt(genTab[i][k])) {
										tempTab[k] = genTab[i][k];
									} else {
										if ((tempj3) == tempi1) {
											tempTab[k] = Integer.toString(tempi1);
										}
										// 爬升至any
										else {
											tempTab[k] = "1";

										}
									}
								}
								break;
							}
								// j第二層,i第四層
							case 3: {
								if (tempi3 == Integer.parseInt(genTab[j][k])) {
									tempTab[k] = Integer.toString(tempi3);
								} else {
									tempTab[k] = "1";
								}
								break;
							}
								// i第二層,j第四層
							case -3: {
								if (tempj3 == Integer.parseInt(genTab[i][k])) {
									tempTab[k] = Integer.toString(tempj3);
								} else {
									tempTab[k] = "1";
								}
								break;
							}
							default:
								tempTab[k] = "1";
							}

						}
					}
				}
			}
			tempTab[5] = Integer.toString(Integer.parseInt(genTab[i][5]) + Integer.parseInt(genTab[j][5]));

		}

	}

	// 判斷屬性值位於概念樹的何處計算資料列成本
	public void ComputTupleCost() {
		// age
		double tempAgeCost = 0;
		switch (tempTab[0].length()) {
		case 5:
			tempAgeCost = 0;
			break;
		case 3:
			tempAgeCost = 0.25;
			break;
		case 2:
			tempAgeCost = 1;
			break;
		case 1:
			tempAgeCost = 3;
			break;
		}

		// education
		double tempEduCost = 0;
		switch (tempTab[1].length()) {
		case 4:
			tempEduCost = 0;
			break;
		case 2:
			tempEduCost = 0.333;
			break;
		case 1:
			tempEduCost = 2;
			break;
		}
		// occupation
		double tempOcuCost = 0;
		switch (tempTab[2].length()) {
		case 5:
			tempOcuCost = 0;
			break;
		case 3:
			tempOcuCost = 0.125;
			break;
		case 2:
			tempOcuCost = 0.666;
			break;
		case 1:
			tempOcuCost = 3;
			break;
		}

		// income
		double tempIncCost = 0;
		switch (tempTab[3].length()) {
		case 5:
			tempIncCost = 0;
			break;
		case 3:
			tempIncCost = 0.25;
			break;
		case 2:
			tempIncCost = 1;
			break;
		case 1:
			tempIncCost = 3;
			break;
		}

		// creditExp
		double tempcreditExpCost = 0;
		switch (tempTab[4].length()) {
		case 5:
			tempcreditExpCost = 0;
			break;
		case 3:
			tempcreditExpCost = 0.25;
			break;
		case 2:
			tempcreditExpCost = 1;
			break;
		case 1:
			tempcreditExpCost = 3;
			break;
		}
		tupleCost = tempAgeCost + tempEduCost + tempOcuCost + tempIncCost + tempcreditExpCost;
		tempTab[6] = Double.toString(tupleCost);
	}

	// 使用取較小成本方法計算成本
	public void MinMethod(int i, int j) {
		double minTuple = Double.parseDouble(genTab[i][6]);
		if (minTuple > Double.parseDouble(genTab[j][6])) {
			minTuple = Double.parseDouble(genTab[j][6]);
		}
		minMethod = Double.parseDouble(tempTab[6]) - minTuple;
		minMethodCost[i - 1][j - 1] = minMethod;
	}

	// 使用取較大成本方法計算成本
	public void MaxMethod(int i, int j) {
		Double maxTuple = Double.parseDouble(genTab[i][6]);
		if (maxTuple < Double.parseDouble(genTab[j][6])) {
			maxTuple = Double.parseDouble(genTab[j][6]);
		}
		maxMethod = Double.parseDouble(tempTab[6]) - maxTuple;
		maxMethodCost[i - 1][j - 1] = maxMethod;
	}

	// 使用取平均成本方法計算成本
	public void AvgMethod(int i, int j) {
		avgMethod = Double.parseDouble(tempTab[6])
				- ((Double.parseDouble(genTab[i][6]) + Double.parseDouble(genTab[j][6])) / 2.0);
		avgMethodCost[i - 1][j - 1] = avgMethod;
	}

	// 使用取加權平均成本方法計算成本
	public void WgtMethod(int i, int j) {
		Double wgti = Double.parseDouble(genTab[i][6]) * Double.parseDouble(genTab[i][5])
				/ (Double.parseDouble(genTab[i][5]) + Double.parseDouble(genTab[j][5]));
		Double wgtj = Double.parseDouble(genTab[j][6]) * Double.parseDouble(genTab[j][5])
				/ (Double.parseDouble(genTab[i][5]) + Double.parseDouble(genTab[j][5]));
		wgtMethod = Double.parseDouble(tempTab[6]) - (wgti + wgtj);
		wgtMethodCost[i - 1][j - 1] = wgtMethod;
	}

	// 找矩陣之最小值
	public void FindMin(double array[][]) {
		min = Double.MAX_VALUE;
		double tempminij = 0.0;
		double tempij = 0.0;
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				if (i != j) {
					if (i < j) {
						if (array[i][j] != -1) {
							if (min >= array[i][j]) {
								if (min == array[i][j]) {
									CombineTuple(mini, minj);
									ComputTupleCost();
									tempminij = tupleCost;
									CombineTuple(i + 1, j + 1);
									ComputTupleCost();
									tempij = tupleCost;
									if (tempminij > tempij) {
										min = array[i][j];
										mini = i + 1;
										minj = j + 1;
									}
								} else {
									min = array[i][j];
									mini = i + 1;
									minj = j + 1;
								}
							}
						}
					}
				}
			}

		}
	}

	// 將被合併之tuple替換掉
	public void ReplaceTuple(int i, int j) {
		CombineTuple(i, j);
		ComputTupleCost();
		for (int k = 0; k < 7; k++) {
			genTab[i][k] = tempTab[k];
			genTab[j][k] = "x";

		}
		// for (int x = 0; x < genTab.length; x++) {
		// System.out.print(x + ". ");
		// for (int y = 0; y < 7; y++) {
		// System.out.print(genTab[x][y] + ", ");
		// }
		// System.out.println();
		// }
	}

	// 更新成本矩陣
	public void UpdateCostArray(double array[][], int i, int j) {
		for (int k = 0; k < array.length; k++) {
			// 更新第i列相關的
			if (k == i - 1) {
				for (int l = 0; l < array.length; l++) {
					if (k != l) {
						if (k < l) {
							if (l != j - 1) {
								if (array[k][l] != (-1)) {
									CombineTuple(k + 1, l + 1);
									ComputTupleCost();
									switch (Tag) {
									case 1:
										MinMethod(k + 1, l + 1);
										array[k][l] = minMethod;
										break;
									case 2:
										MaxMethod(k + 1, l + 1);
										array[k][l] = maxMethod;
										break;
									case 3:
										AvgMethod(k + 1, l + 1);
										array[k][l] = avgMethod;
										break;
									case 4:
										WgtMethod(k + 1, l + 1);
										array[k][l] = wgtMethod;
										break;
									}

								}
							}
							if (l == j - 1) {
								array[k][l] = -1;
							}
						}
					}
				}
			} else {
				// 更新第j列相關的
				if (k == j - 1) {
					for (int l = 0; l < array.length; l++) {
						if (k != l) {
							if (k < l) {
								if (array[k][l] != -1) {
									array[k][l] = -1;
								}
							}
						}
					}
				} else {
					for (int l = 0; l < array.length; l++) {
						if (array[k][l] != -1) {
							if (k != l) {
								if (k < l) {
									if (l == i - 1) {
										CombineTuple(k + 1, l + 1);
										ComputTupleCost();
										switch (Tag) {
										case 1:
											MinMethod(k + 1, l + 1);
											array[k][l] = minMethod;
											break;
										case 2:
											MaxMethod(k + 1, l + 1);
											array[k][l] = maxMethod;
											break;
										case 3:
											AvgMethod(k + 1, l + 1);
											array[k][l] = avgMethod;
											break;
										case 4:
											WgtMethod(k + 1, l + 1);
											array[k][l] = wgtMethod;
											break;
										}
									}
									if (l == j - 1) {
										array[k][l] = -1;
									}
								}
							}
						}
					}
				}
			}
		}

		// System.out.println();
		// for (int x = 0; x < array.length; x++) {
		// for (int y = 0; y < array.length; y++) {
		// System.out.printf("%5.2f ", array[x][y]);
		// }
		// System.out.println();
		// }

	}

	public void ToPrint(String Array[][], int i, int j) {
		if (!Array[i][j].equals("x")) {
			switch (j) {
			case 0:
				switch (Array[i][j].length()) {
				case 1:
					Array[i][j] = "Any";
					break;
				case 2:
					switch (Integer.parseInt(Array[i][j]) % 10) {
					case 1:
						Array[i][j] = "青壯年";
						break;
					case 2:
						Array[i][j] = "中老年";
						break;
					}
					break;
				case 3:
					switch (Integer.parseInt(Array[i][j]) % 10) {
					case 1:
						Array[i][j] = "15-24";
						break;
					case 2:
						Array[i][j] = "25-34";
						break;
					case 3:
						Array[i][j] = "35-49";
						break;
					case 4:
						Array[i][j] = ">49";
						break;
					}
					break;
				case 5:
					switch (Integer.parseInt(Array[i][j]) % 10) {
					case 1:
						Array[i][j] = "15-19";
						break;
					case 2:
						Array[i][j] = "20-24";
						break;
					case 3:
						Array[i][j] = "25-30";
						break;
					case 4:
						Array[i][j] = "30-34";
						break;
					case 5:
						Array[i][j] = "35-39";
						break;
					case 6:
						Array[i][j] = "40-44";
						break;
					case 7:
						Array[i][j] = "45-49";
						break;
					case 8:
						Array[i][j] = "50-54";
						break;
					case 9:
						Array[i][j] = "55-59";
						break;
					}
					break;
				}
				break;

			case 1:
				switch (Array[i][j].length()) {
				case 1:
					Array[i][j] = "Any";
					break;
				case 2:
					switch (Integer.parseInt(Array[i][j]) % 10) {
					case 1:
						Array[i][j] = "低學歷";
						break;
					case 2:
						Array[i][j] = "中學歷";
						break;
					case 3:
						Array[i][j] = "高學歷";
						break;
					}
					break;
				case 4:
					switch (Integer.parseInt(Array[i][j]) % 10) {
					case 1:
						Array[i][j] = "小學及以下";
						break;
					case 2:
						Array[i][j] = "國初中";
						break;
					case 3:
						Array[i][j] = "高中職";
						break;
					case 4:
						Array[i][j] = "專科";
						break;
					case 5:
						Array[i][j] = "大學及以上";
						break;
					}
					break;
				}
				break;

			case 2:
				switch (Array[i][j].length()) {
				case 1:
					Array[i][j] = "Any";
					break;
				case 2:
					switch (Integer.parseInt(Array[i][j]) % 10) {
					case 1:
						Array[i][j] = "學生";
						break;
					case 2:
						Array[i][j] = "工作者";
						break;
					case 3:
						Array[i][j] = "未被雇用者";
						break;
					}
					break;
				case 3:
					switch (Integer.parseInt(Array[i][j]) % 10) {
					case 1:
						Array[i][j] = "青年學子";
						break;
					case 2:
						Array[i][j] = "專科學子";
						break;
					case 3:
						Array[i][j] = "大學生";
						break;
					case 4:
						Array[i][j] = "高層級工作者";
						break;
					case 5:
						Array[i][j] = "低層級工作者";
						break;
					case 6:
						Array[i][j] = "自營工作者";
						break;
					case 7:
						Array[i][j] = "家庭工作者";
						break;
					case 8:
						Array[i][j] = "待業";
						break;
					}
					break;
				case 5:
					switch (Integer.parseInt(Array[i][j]) % 100) {
					case 1:
						Array[i][j] = "國中及以下學生";
						break;
					case 2:
						Array[i][j] = "高中、高職學生";
						break;
					case 3:
						Array[i][j] = "夜間部高中、高職學生";
						break;
					case 4:
						Array[i][j] = "專科學生";
						break;
					case 5:
						Array[i][j] = "夜間部專科學生";
						break;
					case 6:
						Array[i][j] = "大學生";
						break;
					case 7:
						Array[i][j] = "夜間部大學生";
						break;
					case 8:
						Array[i][j] = "管理職";
						break;
					case 9:
						Array[i][j] = "專門職";
						break;
					case 10:
						Array[i][j] = "技術職";
						break;
					case 11:
						Array[i][j] = "事務職";
						break;
					case 12:
						Array[i][j] = "銷售職";
						break;
					case 13:
						Array[i][j] = "勞務職";
						break;
					case 14:
						Array[i][j] = "服務職";
						break;
					case 15:
						Array[i][j] = "農林漁牧自營";
						break;
					case 16:
						Array[i][j] = "商工服務自營";
						break;
					case 17:
						Array[i][j] = "自由業自營";
						break;
					case 18:
						Array[i][j] = "經營者";
						break;
					case 19:
						Array[i][j] = "家庭主婦";
						break;
					case 20:
						Array[i][j] = "家庭主婦";
						break;
					case 21:
						Array[i][j] = "無職";
						break;
					}
					break;
				}
				break;

			case 3:
				switch (Array[i][j].length()) {
				case 1:
					Array[i][j] = "Any";
					break;
				case 2:
					switch (Integer.parseInt(Array[i][j]) % 10) {
					case 1:
						Array[i][j] = "低收入";
						break;
					case 2:
						Array[i][j] = "高收入";
						break;
					}
					break;
				case 3:
					switch (Integer.parseInt(Array[i][j]) % 10) {
					case 1:
						Array[i][j] = "<10001";
						break;
					case 2:
						Array[i][j] = "10001-30000";
						break;
					case 3:
						Array[i][j] = "30001-50000";
						break;
					case 4:
						Array[i][j] = ">50000";
						break;
					}
					break;
				case 5:
					switch (Integer.parseInt(Array[i][j]) % 10) {
					case 1:
						Array[i][j] = "無收入";
						break;
					case 2:
						Array[i][j] = "10000以下";
						break;
					case 3:
						Array[i][j] = "10001-20000";
						break;
					case 4:
						Array[i][j] = "20001-30000";
						break;
					case 5:
						Array[i][j] = "30001-40000";
						break;
					case 6:
						Array[i][j] = "40001-50000";
						break;
					case 7:
						Array[i][j] = "50001-60000";
						break;
					case 8:
						Array[i][j] = "60001元以上";
						break;
					}
					break;
				}
				break;

			case 4:
				switch (Array[i][j].length()) {
				case 1:
					Array[i][j] = "Any";
					break;
				case 2:
					switch (Integer.parseInt(Array[i][j]) % 10) {
					case 1:
						Array[i][j] = "低刷卡額";
						break;
					case 2:
						Array[i][j] = "高刷卡額";
						break;
					}
					break;
				case 3:
					switch (Integer.parseInt(Array[i][j]) % 10) {
					case 1:
						Array[i][j] = "0 – 40000";
						break;
					case 2:
						Array[i][j] = "40001 – 80000";
						break;
					case 3:
						Array[i][j] = "80001 - 150000";
						break;
					case 4:
						Array[i][j] = "> 150000";
						break;
					}
					break;
				case 5:
					switch (Integer.parseInt(Array[i][j]) % 10) {
					case 1:
						Array[i][j] = "20000元以下";
						break;
					case 2:
						Array[i][j] = "20001-40000";
						break;
					case 3:
						Array[i][j] = "40001-60000";
						break;
					case 4:
						Array[i][j] = "60001-80000";
						break;
					case 5:
						Array[i][j] = "80001-100000";
						break;
					case 6:
						Array[i][j] = "100001-150000";
						break;
					case 7:
						Array[i][j] = "150001-200000";
						break;
					case 8:
						Array[i][j] = "200000以上";
						break;
					}
					break;
				}
				break;
			}
		}
	}

}
