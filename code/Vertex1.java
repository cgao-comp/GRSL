package github_GFNL_0411_sensorStraChange_bigScale;

import java.util.HashSet;

public class Vertex1 {
	public String origin=null;
	public boolean not_get_award=true;
	
	public String verName;//�ڵ�����
	public double time;//����Ⱦ����ʵʱ��
	public double time_by_predictSource;//��Ԥ���Դ���±���Ⱦ��ʱ��
	public boolean infe;//�ýڵ�״̬
	public Vertex1 nextNode;
	public boolean recover=false;
	public int degree=0;

	public boolean isObserver=false;
	public boolean isRandomObserver=false;

	public int index;//�����������EPA�㷨������ȷ���õ��ڸ�Ⱦ����ĵڼ���

	public double infected_p; //��ÿ���ڵ�Ĵ����ʲ�ͬ���칹����ר�ò���*�Һ���Ҫ

	public HashSet<Pair> ID_Label=new HashSet<Pair>();
	//public int t_sum=0;
	public String state;

	boolean isVisit=false;//���������Ϊ�����㹻��Ķ�Ϊ1�Ĺ۲�Դѭ���˳��ģ����initNet_heterogeneous_combinedFONC����
}

class Pair {
	String name;
	double time=0;
	boolean isFirsttime=false;
	public boolean temptime=false;
}
