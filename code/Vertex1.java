package github_GFNL_0411_sensorStraChange_bigScale;

import java.util.HashSet;

public class Vertex1 {
	public String origin=null;
	public boolean not_get_award=true;
	
	public String verName;//节点名称
	public double time;//被感染的真实时间
	public double time_by_predictSource;//在预测的源点下被感染的时间
	public boolean infe;//该节点状态
	public Vertex1 nextNode;
	public boolean recover=false;
	public int degree=0;

	public boolean isObserver=false;
	public boolean isRandomObserver=false;

	public int index;//这个属性属于EPA算法，用于确定该点在感染矩阵的第几行

	public double infected_p; //让每个节点的传播率不同，异构网络专用参数*且很重要

	public HashSet<Pair> ID_Label=new HashSet<Pair>();
	//public int t_sum=0;
	public String state;

	boolean isVisit=false;//这个参数是为了找足够多的度为1的观测源循环退出的，详见initNet_heterogeneous_combinedFONC函数
}

class Pair {
	String name;
	double time=0;
	boolean isFirsttime=false;
	public boolean temptime=false;
}
