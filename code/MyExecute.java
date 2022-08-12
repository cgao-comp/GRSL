package github_GFNL_0411_sensorStraChange_bigScale;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class MyExecute {

	public static double time=200;
	public static int center_Num=1;
	public static double ppt_of_I=0.9; //���Ƶĸ�Ⱦ��ģ�����Դ����ھ�������������ʱ��
	public static double ppt_of_I_sensor=0.1; //sensor��Ⱦ��ģ��������Ⱦ��ģ-------������Ǵ�����Ǹ�Ⱦ��ģ���ϱߵ��Ǹ����Ժܴ�Ŀ���Ǳ�֤��������б߽�

	//�����sensor����:
	public static int min_obv_num=5;
	public static int max_obv_num=30;
	public static int add_obv_num=5;

	//��fetch_num����Ⱦ�������Կ�ʼ��Դ����GFNL�㷨�õ��Ķ�Ӧsensor�����Ĵ�����Ϣ
	public static int fetch_num=4;
	
	//��Ⱦ��
	public static double min_infected_p=0.1;
	public static double max_infected_p=0.91;
	public static double add_infected_p=0.4;

	//JordanCenter����
	//�۲������������
	
	//����GFNL�㷨��Ҫ��ʱ��
	static double GFNL_time1;
	static double total_time;
	
	static double time_myStartEXE4;
	static double time_randEXE30;
	
	
	//���¼����һ����
	static double time_myStartEXE30;

	public static void initNet_heterogeneous_combinedFONC_divideWeight(int type) throws Exception {
		// �ļ�5
		//String path = "G:\\test"+type+".txt";

		String path="G:\\eclipse workspace\\1\\ComplexNetwork\\src\\test_sensorstrategy1_large.txt";
//		String path="G:\\eclipse workspace\\1\\ComplexNetwork\\src\\test_sensorstrategy2_large_onlydegree+random��no degree1.txt";
		//String path = "G:\\SI_propagation_model\\facebook_randomScale\\·����Ϣ\\((RAND)10%�������-10%��Ⱦ(���޸����·��(��)������·����Ϣ-F_score)final-1.05+wrong-greedy_FNOC+ʱ���-7)initNet_heterogeneous_combinedFONC_divideWeight_parasType"+type+".txt";
		// file = new File(path);
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		writer.write("ʹ�ú���initNet_heterogeneous_combinedFONC�ҽ����divideWeight, �����Ⱦ��ģ�����Ϊ����Ԥ��Դ��: ");
		writer.write("\r\n");

		for(double infected_p=min_infected_p;infected_p<=max_infected_p;infected_p=infected_p+add_infected_p) {
			writer.write("��Ⱦ����"+infected_p+": ");
			writer.write("\r\n");

			int allObvNum_rate = -1; //  �Դ��ٷֱ�   allObvNum_rate%
			int obv_num=-1;
			int setNum = fetch_num;
			//double setRate = 0;
			for (allObvNum_rate = min_obv_num; allObvNum_rate <= max_obv_num; allObvNum_rate += add_obv_num) {
					System.out.println("�ܹ۲�����: " + allObvNum_rate + " ѡ������: " + setNum);
					double f_score=0;
					total_time=0;
					GFNL_time1=0;
					time_myStartEXE4=0;					
					time_randEXE30=0;
					time_myStartEXE30=0;
					
					for (int time = 0; time < MyExecute.time; time++) {
						Graph1 network = new Graph1();
						CreateGraph3.initialGraph(network);
						System.out.println("ͼ��ʼ�����......");
						
						if(time==0) {
							System.out.println(CreateGraph3.get_AVG_degree(network));
							//System.out.println(CreateGraph3.get_diameter(network, ALG.shortPath));
							network.diameter=21;
						}
						
						SI mySi = new SI();
//IF(�µ����ݼ���Ҫ����������Ϣ)  ������sensor������;����
//						mySi.initNet_SIR_greedy_FullOrder(network, infected_p, 0, 1, 999999);
//						mySi.initNet_SIR_greedy_ratio(network, infected_p, 0, 1, (int)(0.2*network.verNum));					
//ELSE:
						mySi.initNet_SIR_Greedy_fromFile(network, infected_p, 0, center_Num, 
								"G:\\Data Files\\OBV_5-40\\large"+allObvNum_rate+".txt");
						System.out.println("greedy sensor�������......");
						
//						mySi.initNet_SIR_Greedy_lowComplexity(allObvNum_rate, network, infected_p, -2, center_Num, (int)((double)(allObvNum_rate)/100l * network.verNum),network.diameter);
//						System.out.println("greedy2 sensor�������......");
//						if(true) {
//							break;
//						}

						// mySi.initNet_heterogeneous_rankedObvsDegree(network, 0.5, 1, allObvNum);
						// CreateGraph3.outputGraph(network);
						System.out.println("mySi.observer_num: " + mySi.observer_num);
						obv_num=mySi.observer_num;

						mySi.simulate(network, true, ppt_of_I);// ȫ��Ⱦ
						System.out.println("����ģ�ͷ������......");
						// mySi.JordanSimulate(network,true);//�����Ҫע��:
						// 1��getobv_byTime�ĵڶ�����������Ҫ����Ϊ������ڵ�һ���󣬵�����������ʵ��������Ǹ�Ⱦ��ģ�����ģ������û�й۲���
						GFNL_time1=System.currentTimeMillis();

						// ��ȡǰ30%�ı���Ⱦ�۲��
						//.1 fetch��������ȡ
						//HashSet<String> obv_set = mySi.getobv_byTime(network, mySi.observer_num, setRate);
						//.2 fetch��������ȡ
						ArrayList<String> obv_set = mySi.getobv_byTime_byNum(network, mySi.observer_num, setNum);

						//ALG.shortPath_removeEDGES=ALG.shortPath;
						
						//��������ֻ��¼woking sensor����Ϣ
//						ALG.shortPath_fourworkingsensor=mySi.readInf_4(network,obv_set);
//						System.out.println("��������������......");
						if(ALG.isBigScaleNet) {
							// 1. �Ǵ���������Ҫ���ļ��ж�ȡ------------�˴����������һ�����ļ�----��һ���ļ��м��ɶ�����Ϣ
//							ALG.shortPath_fourworkingsensor=mySi.readInf_4_from_oneFile(network,obv_set,
//									"G:\\Data Files\\shortestPath_github.txt");
//							System.out.println("4sensor��Ӧ�ľ�������������......");
							
							// 2. �Ǵ���������Ҫ���ļ��ж�ȡ------------�˴�����������ر����ļ�----�Ӽ�����ļ���ѡȡ
							ALG.shortPath_fourworkingsensor=mySi.readInf_4(network, obv_set);
							
							// 3. ��Ϊֻ��4���㣬ֱ��ִ��DFS��¼�ĸ�sensor��origin��ȫ��������Ϣ
							
						}else {
							//�Ǵ�������ֱ�����ڴ����Ѿ�����Ϣ��
						}

						// HashSet<String>
						// obv_set=mySi.test(network);//����������еĲ��ԣ�Ҫ�����ݼ�JordanCenterDemo���ʹ��
						System.out.print("���������Ĺ۲��: ");
						for (String obv:  obv_set ) {
							System.out.print(obv+": "+network.vertexArray[Integer.parseInt(obv)-1].time+"| "+"( "+network.vertexArray[Integer.parseInt(obv)-1].origin+" )");
						}
						//System.out.println("���������Ĺ۲��: " + obv_set.toString());
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
					System.out.println("��ȷ��: " + f_score / MyExecute.time);
					writer.write("�ܹ۲��: ");
					writer.write(obv_num + "");
					writer.write(" ѡ������: ");
					writer.write(setNum + "");
					writer.write(":  ");
//					writer.write((correct_rate / MyExecute.time) + "");
//					writer.write(" ƽ���������: ");
//					writer.write((error_length / MyExecute.time) + "");
//					writer.write("\r\n");
					writer.write(" F_score: ");
					writer.write((f_score / MyExecute.time) + "");
					writer.write("\r\n");
					writer.write(" ����һ�γ�������ʱ��: ");
					writer.write((total_time / MyExecute.time) +" (ms)");
					writer.write("\r\n");
					
					writer.flush();

			}
		}
		writer.close();
	}
}
