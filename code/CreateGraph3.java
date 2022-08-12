package github_GFNL_0411_sensorStraChange_bigScale;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CreateGraph3 {

	public static int[] all_nodes;
	public static int[][] all_edges;
	public static int vernum;
	public static int edgenum;


	static{
		//���˽ṹ�ı������ͽڵ�����
		vernum=37700;//37700;//317080;//21;//22470;//4648;//62;//115;//198;//18;//198;//7066;//4648;//4039;
		edgenum=289003;//289003;//1049866;//31;//170823;//159;//613;//2742;//22;//2742;//103663;//59382;//88234;

		all_nodes=new int[vernum];
		all_edges=new int[edgenum][2];

		for(int i=1;i<=vernum;i++) {
			all_nodes[i-1]=i;
		}

		try {
			//�������˽ṹ�ļ�·��
			String path="G:\\eclipse workspace\\1\\ComplexNetwork\\src\\epaAlgorithm\\large_facebook.txt";
//			String path="G:\\eclipse workspace\\1\\ComplexNetwork\\src\\epaAlgorithm\\viki_adjG_continue_no_same.txt";
			//String path = "G:\\Data Files\\Index1_com-dblp.ungraph_continue_no_same.txt";
			//String path = "G:\\eclipse workspace\\1\\ComplexNetwork\\src\\epaAlgorithm\\jazz_edge.txt";
			FileInputStream fileInputStream = new FileInputStream(path);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
			String line = null;
			int line_index = 0;
			while ((line = bufferedReader.readLine()) != null) {
				//����һ�У���߶��еĲ�������Ҫ�����Լ����ˣ�Ҫ�����еĸ�ʽ�����±ߴ���
				String[] cut = line.split(" ");
				int edge1 = Integer.parseInt(cut[0]);
				int edge2 = Integer.parseInt(cut[1]);
				//System.out.println(edge1+"  "+edge2);

				all_edges[line_index][0] = edge1;
				all_edges[line_index][1] = edge2;
				line_index++;
			}
			fileInputStream.close();
			System.out.println("һ������"+line_index+"��");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�ļ������쳣");
		}

	}

	/**
	 * �����û������string���͵Ķ��㷵�ظö���
	 * @param graph ͼ
	 * @param str ��������
	 * @return����һ������
	 */
	public static Vertex1 getVertex(Graph1 graph,String str){
		for(int i=0;i<graph.verNum;i++){
			if(graph.vertexArray[i].verName.equals(str)){
				return graph.vertexArray[i];
			}
		}
		return null;
	}

	/**
	 * �����û���������ݳ�ʼ��һ��ͼ�����ڽӱ����ʽ����!
	 * @param graph ���ɵ�ͼ
	 */
	public static void initialGraph(Graph1 graph){
		@SuppressWarnings("resource")
		Scanner scan=new Scanner(System.in);
		//System.out.println("����������������ͱ�����34 78");
		//graph.verNum=scan.nextInt();
		//graph.edgeNum=scan.nextInt();
		graph.verNum=vernum;
		graph.edgeNum=edgenum;

		//System.out.println("���������������ƣ�");
		for(int i=0;i<graph.verNum;i++){
			Vertex1 vertex=new Vertex1();
			//String name=scan.next();
			String name=all_nodes[i]+"";
			vertex.verName=name;
			vertex.nextNode=null;
			vertex.time=-1;
			vertex.infe=false;
			vertex.recover=false;
			graph.vertexArray[i]=vertex;
		}

		//System.out.println("����������ÿ���ߵ��������㣺");
		for(int i=0;i<graph.edgeNum;i++){
			//String preV=scan.next();
			//String folV=scan.next();

			//Vertex1 v1=getVertex(graph,preV);//�����õ�����network��Ӧ���ڽӱ�ĵ���󣬼���һ��
			//System.out.println("all_edges[i][0]-1 : "+(all_edges[i][0]-1));
			Vertex1 v1=graph.vertexArray[all_edges[i][0]-1];
			if(v1==null)
				System.out.println("����ߴ���ͼ��û�еĶ��㣡");

//���������ͼ�����ĺ��ģ��������
			Vertex1 v2=new Vertex1();
			v2.verName=all_edges[i][1]+"";
			v2.nextNode=v1.nextNode;
			v2.time=-1;
			v2.infe=false;
			v1.nextNode=v2;
			v1.degree++;

//����������ע�͵Ĵ�����ϱ��ǹ�������ͼ�ģ��������ǹ�������ͼ�ģ�
			Vertex1 reV2=graph.vertexArray[all_edges[i][1]-1];
			if(reV2==null)
				System.out.println("����ߴ���ͼ��û�еĶ��㣡");
			Vertex1 reV1=new Vertex1();
			reV1.verName=all_edges[i][0]+"";
			reV1.time=-1;
			reV1.infe=false;
			reV1.nextNode=reV2.nextNode;
			reV2.nextNode=reV1;
			reV2.degree++;
		}
	}

	/**
	 * ����������ƽ����
	 * @param network ��������
	 * @return
	 */
	public static double get_AVG_degree(Graph1 network) {
		double totol_degree=0;
		for(int i=0;i<network.verNum;i++) {
			totol_degree+=network.vertexArray[i].degree;
		}
		network.avg_degree=((double)totol_degree) / ((double)network.verNum);

		return network.avg_degree;
	}
	
	/**
	 * ����������ֱ��
	 * @param network ��������
	 * @return
	 */
	public static int get_diameter(Graph1 network, int[][] shortest_Path) {
		for(int i=0;i<shortest_Path.length;i++) {
			for(int j=0;j<shortest_Path[0].length;j++) {
				if(network.diameter<shortest_Path[i][j]) {
					network.diameter=shortest_Path[i][j];
				}
			}
		}
		
		return network.diameter;
	}

	/**
	 * ����ͼ���ڽӱ�
	 * @param graph �������ͼ
	 */
	public static void outputGraph(Graph1 graph){
		System.out.println("���������ڽӱ�Ϊ��");
		for(int i=0;i<graph.verNum;i++){
			Vertex1 vertex=graph.vertexArray[i];
			System.out.print(vertex.verName);
			//System.out.print("("+vertex.degree+" "+vertex.time+")");
			System.out.print("("+vertex.time+" "+vertex.state+")");

			Vertex1 current=vertex.nextNode;
			while(current!=null){
				System.out.print("-->"+current.verName);
				//System.out.print("("+current.time+" "+current.state+")");
				System.out.print("("+current.time+")");
				current=current.nextNode;
			}
			System.out.println();
		}
	}

	public Graph1 createSpanningTree(Graph1 graph,String source){
		Graph1 tree=new Graph1();
		tree.verNum=vernum;
		for(int i=0;i<graph.verNum;i++){
			Vertex1 vertex=new Vertex1();
			//String name=scan.next();
			String name=all_nodes[i]+"";
			vertex.verName=name;
			vertex.nextNode=null;
			vertex.time=-1;
			vertex.infe=false;
			tree.vertexArray[i]=vertex;
		}//����������������ֻ�����˽ڵ���Ϣ

		//Vertex1 vertex1=getVertex(graph, source);
		//vertex1.time=0;//��ͼ�е�Դ���Ϊ�ѱ���״̬

		ConcurrentLinkedQueue<String> Queue=new ConcurrentLinkedQueue<String>();
		HashSet<String> travelled_node=new HashSet<>();
		travelled_node.add(source);
		Queue.add(source);
		while(travelled_node.size()!=vernum){
			String centerNodeName=Queue.poll();
			Vertex1 centerNode=getVertex(graph,centerNodeName);
			Vertex1 neighborVertex=centerNode.nextNode;
			while(neighborVertex!=null){
				if(!travelled_node.contains(neighborVertex.verName)){//�ھӽڵ�һ�����ڣ���ô����˵��100%�Ѿ�����ͨ·�����Ͳ��ù���
					travelled_node.add(neighborVertex.verName);
					Queue.add(neighborVertex.verName);

					//����������tree���ǹ�������ͼ
					Vertex1 neighborNode=getVertex(graph,neighborVertex.verName);

					Vertex1 v1=getVertex(tree,centerNode.verName);
					Vertex1 v2=new Vertex1();
					v2.verName=neighborNode.verName;
					v2.time=neighborNode.time;
					v2.infe=neighborNode.infe;

					v2.nextNode=v1.nextNode;
					v1.nextNode=v2;

					v1.degree=centerNode.degree;
					v1.infe=centerNode.infe;
					v1.infected_p=centerNode.infected_p;
					v1.isObserver=centerNode.isObserver;
					v1.isVisit=centerNode.isVisit;
					v1.state=centerNode.state;
					v1.time=centerNode.time;

					//����ͼ�ķ��򹹽�
					Vertex1 reNeighbor=getVertex(tree,neighborNode.verName);
					Vertex1 reCenter=new Vertex1();
					reCenter.verName=centerNode.verName;
					reCenter.time=centerNode.time;
					reCenter.infe=centerNode.infe;

					reCenter.nextNode=reNeighbor.nextNode;
					reNeighbor.nextNode=reCenter;

					reNeighbor.degree=neighborNode.degree;
					reNeighbor.infe=neighborNode.infe;
					reNeighbor.infected_p=neighborNode.infected_p;
					reNeighbor.isObserver=neighborNode.isObserver;
					reNeighbor.isVisit=neighborNode.isVisit;
					reNeighbor.state=neighborNode.state;
					reNeighbor.time=neighborNode.time;
				}

				neighborVertex=neighborVertex.nextNode;
			}
			//if(travelled_node)

		}
		return tree;
	}

}
