package test.sort.main;

import Scheduler.CooperativeScheduler;
import Scheduler.JobScheduler;
import Scheduler.NodeScheduler;
import computingNode.ThreadDo;
import test.sort.CPUThreadDo;
import test.sort.GPUThreadDo;
import test.sort.ResultMerging;
import test.sort.ResultSerialMerge;

@SuppressWarnings("unused")
public class SortTest {
	
	public static void main(String[] args)
	{
		
		CooperativeScheduler cs = new CooperativeScheduler();//实例化节点内的协同调度器
		cs.startUp("Sort");//启动节点内的协同调度器并将本节点加入集群
		
		ThreadDo ctd = new CPUThreadDo();
		ThreadDo gtd = new GPUThreadDo();
		ResultMerging rm = new ResultMerging();
		ResultSerialMerge rsm = new ResultSerialMerge();
		JobScheduler jobScheduler = new JobScheduler();//实例化任务调度器
		jobScheduler.startJob("Sort",rm,ctd,gtd);//启动任务调度器并将任务名称、结果合并、CPU/GPU计算逻辑传给计算节点。
		//jobScheduler.startJob("Sort",rsm,ctd,gtd);//启动任务调度器并将任务名称、结果合并、CPU/GPU计算逻辑传给计算节点。
	}
}