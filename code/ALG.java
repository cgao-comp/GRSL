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
		//初始化为-1
		for(int i=0;i<network.verNum;i++) {
			shortDis_of_verNum[i]=-1;
		}
		shortDis_of_verNum[verName_int-1]=0;
		
		int dis=1;
		//选中根节点
		Vertex1 root=network.vertexArray[verName_int-1];
		Vertex1 current=root.nextNode;
		while(current!=null){
			//根节点的所有邻居DFS
			queue.offer(new vertex_dij(Integer.parseInt(current.verName), dis));
			exist.add(current.verName);
			current=current.nextNode;
		}
		
		while(!queue.isEmpty()) {
			vertex_dij now=queue.poll();
			shortDis_of_verNum[now.verName_int-1]=now.distance;
			//System.out.println("verName: "+now.verName_int+"  distance: "+now.distance);
			
			//将目前节点的邻居加入，如果已经在exist集合中了，说明有更短的路径，只是还没来得及进行遍历
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
		//1、如需使用新的数据集生成最短距离矩阵，运行一次以下代码即可
//		Graph1 topological_graph=new Graph1();
//		CreateGraph3.initialGraph(topological_graph);
//		System.out.println("网络初始化完成");
//		int[][] short_path=new int[topological_graph.verNum][topological_graph.verNum];	
//		for(int verNum=1;verNum<=topological_graph.verNum;verNum++) {
//			short_path[verNum-1]=adjGraph_ShortPath(topological_graph,verNum);
//			
//		}
//		Write.writeTwoDimension("/Users/houdongpeng/1/ComplexNetwork/src/quick_J_0117/github_shortestPath.txt", short_path);		
//		System.exit(0);
		
		// 2、如需使用新的数据集生成部署的观测点，则:
		//    1) 运行以下被注释函数;
		//    2) 在initNet_heterogeneous_combinedFONC_divideWeight()函数中使用 全覆盖版本initNet_SIR_greedy_FullOrder() 或 自选比例版本initNet_SIR_greedy_ratio()
//		ALG.shortPath= Read.readFromShortPathFile("/Users/houdongpeng/1/ComplexNetwork/src/quick_J_0117/github_shortestPath.txt"
//                , 37700);
//		System.out.println("shortPath已初始化完成...");
	
		
		ALG.isBigScaleNet=false;
		if(ALG.isBigScaleNet) {
			//None
		}else {
			//不是大型网络可以读入到内存中，加速运行效率
			ALG.shortPath= Read.readFromShortPathFile("G:\\Data Files\\shortPath_large.txt", 37700);
			System.out.println("内存中的shortPath初始化完成.....");
			//System.out.println("shortPath.length: "+shortPath.length +"----shortPath[1].length: "+shortPath[1].length);
		}
		
		//有了观察点以后执行溯源方法的算法
		for(int i=-7;i<=-7;i++) {
			MyExecute.initNet_heterogeneous_combinedFONC_divideWeight(i);
		}

	}

}
