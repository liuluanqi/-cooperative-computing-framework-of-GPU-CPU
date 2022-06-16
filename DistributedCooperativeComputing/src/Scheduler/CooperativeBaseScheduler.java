package Scheduler;

import com.fourinone.MigrantWorker;
import com.fourinone.WareHouse;

import component.JobConfig;
import computingNode.NodePerformanceIndex;

@SuppressWarnings("serial")
//Э��������
public class CooperativeBaseScheduler extends MigrantWorker
{	
	CooperativeScheduler cs = null;
	public WareHouse doTask(WareHouse inhouse)
	{
		return cs.executeTask(inhouse);
		
		
	}
	
	public void joinCluster(String jobType,CooperativeScheduler cs)
	{
		this.cs = cs;
		this.waitWorking(jobType);
	}
	public WareHouse getPerformance(JobConfig config,WareHouse wh){
		WareHouse result = new WareHouse();
		result.setString("performance", String.valueOf(NodePerformanceIndex.getNodePerformanceIndex(config)));
		return result;
	}
}