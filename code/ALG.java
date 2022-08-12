package github_GFNL_0411_sensorStraChange_bigScale;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import tool.Read;
import tool.Write;

class vertex_dij{
	int verName_int;
	int distance;
	
	public vertex_dij(int verName_int,int distance) {
		this.verName_int=verName_int;
		this.distance=distance;
	}
}

public class ALG {
	public static int diameter=-10000;
	
	public static int[][] shortPath;
	public static int[] shortDis_of_verNum;
	public static int[][] shortPath_fourworkingsensor;
	public static boolean isBigScaleNet;
	
	public static int[] adjGraph_ShortPath(Graph1 network,int verName_int) {
		shortDis_of_verNum=new int[network.verNum];
		 Queue<vertex_dij> queue = new LinkedList<vertex_dij>();
		 Set<String> exist=new HashSet<String>();
		 exist.add(verName_int+"");
		//��ʼ��Ϊ-1
		for(int i=0;i<network.verNum;i++) {
			shortDis_of_verNum[i]=-1;
		}
		shortDis_of_verNum[verName_int-1]=0;
		
		int dis=1;
		//ѡ�и��ڵ�
		Vertex1 root=network.vertexArray[verName_int-1];
		Vertex1 current=root.nextNode;
		while(current!=null){
			//���ڵ�������ھ�DFS
			queue.offer(new vertex_dij(Integer.parseInt(current.verName), dis));
			exist.add(current.verName);
			current=current.nextNode;
		}
		
		while(!queue.isEmpty()) {
			vertex_dij now=queue.poll();
			shortDis_of_verNum[now.verName_int-1]=now.distance;
			//System.out.println("verName: "+now.verName_int+"  distance: "+now.distance);
			
			//��Ŀǰ�ڵ���ھӼ��룬����Ѿ���exist�������ˣ�˵���и��̵�·����ֻ�ǻ�û���ü����б���
			Vertex1 now_vertex=network.vertexArray[now.verName_int-1];
			while(now_vertex.nextNode!=null) {
				now_vertex=now_vertex.nextNode;
				if(!exist.contains(now_vertex.verName)) {
					queue.offer(new vertex_dij(Integer.parseInt(now_vertex.verName), now.distance+1));
					exist.add(now_vertex.verName);
				}
			}
		}
		
		return shortDis_of_verNum;
		
	}

	public static void main(String[] args) throws Exception {
		//1������ʹ���µ����ݼ�������̾����������һ�����´��뼴��
//		Graph1 topological_graph=new Graph1();
//		CreateGraph3.initialGraph(topological_graph);
//		System.out.println("�����ʼ�����");
//		int[][] short_path=new int[topological_graph.verNum][topological_graph.verNum];	
//		for(int verNum=1;verNum<=topological_graph.verNum;verNum++) {
//			short_path[verNum-1]=adjGraph_ShortPath(topological_graph,verNum);
//			
//		}
//		Write.writeTwoDimension("/Users/houdongpeng/1/ComplexNetwork/src/quick_J_0117/github_shortestPath.txt", short_path);		
//		System.exit(0);
		
		// 2������ʹ���µ����ݼ����ɲ���Ĺ۲�㣬��:
		//    1) �������±�ע�ͺ���;
		//    2) ��initNet_heterogeneous_combinedFONC_divideWeight()������ʹ�� ȫ���ǰ汾initNet_SIR_greedy_FullOrder() �� ��ѡ�����汾initNet_SIR_greedy_ratio()
//		ALG.shortPath= Read.readFromShortPathFile("/Users/houdongpeng/1/ComplexNetwork/src/quick_J_0117/github_shortestPath.txt"
//                , 37700);
//		System.out.println("shortPath�ѳ�ʼ�����...");
	
		
		ALG.isBigScaleNet=false;
		if(ALG.isBigScaleNet) {
			//None
		}else {
			//���Ǵ���������Զ��뵽�ڴ��У���������Ч��
			ALG.shortPath= Read.readFromShortPathFile("G:\\Data Files\\shortPath_large.txt", 37700);
			System.out.println("�ڴ��е�shortPath��ʼ�����.....");
			//System.out.println("shortPath.length: "+shortPath.length +"----shortPath[1].length: "+shortPath[1].length);
		}
		
		//���˹۲���Ժ�ִ����Դ�������㷨
		for(int i=-7;i<=-7;i++) {
			MyExecute.initNet_heterogeneous_combinedFONC_divideWeight(i);
		}

	}

}
