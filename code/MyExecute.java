package github_GFNL_0411_sensorStraChange_bigScale;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class MyExecute {

	public static double time=200;
	public static int center_Num=1;
	public static double ppt_of_I=0.9; //控制的感染规模，可以大，用于决定传播结束的时间
	public static double ppt_of_I_sensor=0.1; //sensor感染规模，表征感染规模-------这个才是代表的是感染规模，上边的那个可以很大，目的是保证程序的运行边界

	//部署的sensor比例:
	public static int min_obv_num=5;
	public static int max_obv_num=30;
	public static int add_obv_num=5;

	//当fetch_num被感染，即可以开始溯源，即GFNL算法用到的对应sensor数量的传播信息
	public static int fetch_num=4;
	
	//传染率
	public static double min_infected_p=0.1;
	public static double max_infected_p=0.91;
	public static double add_infected_p=0.4;

	//JordanCenter数量
	//观察点数量和增量
	
	//仿真GFNL算法需要的时间
	static double GFNL_time1;
	static double total_time;
	
	static double time_myStartEXE4;
	static double time_randEXE30;
	
	
	//最新加入的一条线
	static double time_myStartEXE30;

	public static void initNet_heterogeneous_combinedFONC_divideWeight(int type) throws Exception {
		// 文件5
		//String path = "G:\\test"+type+".txt";

		String path="G:\\eclipse workspace\\1\\ComplexNetwork\\src\\test_sensorstrategy1_large.txt";
//		String path="G:\\eclipse workspace\\1\\ComplexNetwork\\src\\test_sensorstrategy2_large_onlydegree+random—no degree1.txt";
		//String path = "G:\\SI_propagation_model\\facebook_randomScale\\路径信息\\((RAND)10%部署比例-10%感染(不修复最短路径(快)—包含路径信息-F_score)final-1.05+wrong-greedy_FNOC+时距比-7)initNet_heterogeneous_combinedFONC_divideWeight_parasType"+type+".txt";
		// file = new File(path);
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		writer.write("使用函数initNet_heterogeneous_combinedFONC且结合了divideWeight, 随机传染规模，输出为单个预测源点: ");
		writer.write("\r\n");

		for(double infected_p=min_infected_p;infected_p<=max_infected_p;infected_p=infected_p+add_infected_p) {
			writer.write("感染率是"+infected_p+": ");
			writer.write("\r\n");

			int allObvNum_rate = -1; //  自带百分比   allObvNum_rate%
			int obv_num=-1;
			int setNum = fetch_num;
			//double setRate = 0;
			for (allObvNum_rate = min_obv_num; allObvNum_rate <= max_obv_num; allObvNum_rate += add_obv_num) {
					System.out.println("总观察点比例: " + allObvNum_rate + " 选择数量: " + setNum);
					double f_score=0;
					total_time=0;
					GFNL_time1=0;
					time_myStartEXE4=0;					
					time_randEXE30=0;
					time_myStartEXE30=0;
					
					for (int time = 0; time < MyExecute.time; time++) {
						Graph1 network = new Graph1();
						CreateGraph3.initialGraph(network);
						System.out.println("图初始化完成......");
						
						if(time==0) {
							System.out.println(CreateGraph3.get_AVG_degree(network));
							//System.out.println(CreateGraph3.get_diameter(network, ALG.shortPath));
							network.diameter=21;
						}
						
						SI mySi = new SI();
//IF(新的数据集需要生成启发信息)  则生成sensor的两种途径：
//						mySi.initNet_SIR_greedy_FullOrder(network, infected_p, 0, 1, 999999);
//						mySi.initNet_SIR_greedy_ratio(network, infected_p, 0, 1, (int)(0.2*network.verNum));					
//ELSE:
						mySi.initNet_SIR_Greedy_fromFile(network, infected_p, 0, center_Num, 
								"G:\\Data Files\\OBV_5-40\\large"+allObvNum_rate+".txt");
						System.out.println("greedy sensor布置完成......");
						
//						mySi.initNet_SIR_Greedy_lowComplexity(allObvNum_rate, network, infected_p, -2, center_Num, (int)((double)(allObvNum_rate)/100l * network.verNum),network.diameter);
//						System.out.println("greedy2 sensor布置完成......");
//						if(true) {
//							break;
//						}

						// mySi.initNet_heterogeneous_rankedObvsDegree(network, 0.5, 1, allObvNum);
						// CreateGraph3.outputGraph(network);
						System.out.println("mySi.observer_num: " + mySi.observer_num);
						obv_num=mySi.observer_num;

						mySi.simulate(network, true, ppt_of_I);// 全感染
						System.out.println("传播模型仿真完成......");
						// mySi.JordanSimulate(network,true);//这个需要注意:
						// 1、getobv_byTime的第二个参数必须要设置为和网络节点一样大，第三个参数的实际意义就是感染规模，这个模型下是没有观察点的
						GFNL_time1=System.currentTimeMillis();

						// 抽取前30%的被感染观察点
						//.1 fetch按比例抽取
						//HashSet<String> obv_set = mySi.getobv_byTime(network, mySi.observer_num, setRate);
						//.2 fetch按数量抽取
						ArrayList<String> obv_set = mySi.getobv_byTime_byNum(network, mySi.observer_num, setNum);

						//ALG.shortPath_removeEDGES=ALG.shortPath;
						
						//大网络下只记录woking sensor的信息
//						ALG.shortPath_fourworkingsensor=mySi.readInf_4(network,obv_set);
//						System.out.println("距离矩阵生成完成......");
						if(ALG.isBigScaleNet) {
							// 1. 是大型网络需要从文件中读取------------此代码段适用于一般大的文件----从一个文件中即可读出信息
//							ALG.shortPath_fourworkingsensor=mySi.readInf_4_from_oneFile(network,obv_set,
//									"G:\\Data Files\\shortestPath_github.txt");
//							System.out.println("4sensor对应的距离矩阵生成完成......");
							
							// 2. 是大型网络需要从文件中读取------------此代码段适用于特别大的文件----从几万个文件中选取
							ALG.shortPath_fourworkingsensor=mySi.readInf_4(network, obv_set);
							
							// 3. 因为只有4个点，直接执行DFS记录四个sensor的origin的全部距离信息
							
						}else {
							//非大型网络直接在内存中已经有信息了
						}

						// HashSet<String>
						// obv_set=mySi.test(network);//这个是论文中的测试，要和数据集JordanCenterDemo配合使用
						System.out.print("保留下来的观察点: ");
						for (String obv:  obv_set ) {
							System.out.print(obv+": "+network.vertexArray[Integer.parseInt(obv)-1].time+"| "+"( "+network.vertexArray[Integer.parseInt(obv)-1].origin+" )");
						}
						//System.out.println("保留下来的观察点: " + obv_set.toString());
						System.out.println();

						long startTime1 = System.currentTimeMillis();
						int num_need_JordanCenter = MyExecute.center_Num;
						//HashSet<String> myFinalSet = mySi.getJordonCenter(network, obv_set, num_need_JordanCenter);
						HashSet<String> myFinalSet = mySi.getJordonCenter_dividedWeight(network, obv_set, num_need_JordanCenter, type, ALG.isBigScaleNet);
						
						
						System.out.println(myFinalSet.toString());
						//System.exit(0);
						long endTime1 = System.currentTimeMillis();

						boolean have_right;
						for (String string : myFinalSet)  {
							if(string.equals(mySi.source + "")){
								f_score=f_score+(2l*1l*(1l/myFinalSet.size())/(1+1l/myFinalSet.size()));
								break;
							}
						}
						
						total_time=total_time+(System.currentTimeMillis()-GFNL_time1);


					}
					System.out.println("正确率: " + f_score / MyExecute.time);
					writer.write("总观察点: ");
					writer.write(obv_num + "");
					writer.write(" 选择数量: ");
					writer.write(setNum + "");
					writer.write(":  ");
//					writer.write((correct_rate / MyExecute.time) + "");
//					writer.write(" 平均错误距离: ");
//					writer.write((error_length / MyExecute.time) + "");
//					writer.write("\r\n");
					writer.write(" F_score: ");
					writer.write((f_score / MyExecute.time) + "");
					writer.write("\r\n");
					writer.write(" 仿真一次程序所用时间: ");
					writer.write((total_time / MyExecute.time) +" (ms)");
					writer.write("\r\n");
					
					writer.flush();

			}
		}
		writer.close();
	}
}
