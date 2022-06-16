package test.str.match.main;

import Scheduler.CooperativeScheduler;
import Scheduler.JobScheduler;
import Scheduler.NodeScheduler;
import computingNode.ThreadDo;
import test.str.match.CPUThreadDo;
import test.str.match.GPUThreadDo;
import test.str.match.ResultMerging;
import test.str.match.ResultMulMerging;

@SuppressWarnings("unused")
public class StrMatch {
	
	public static void main(String[] args)
	{
		//NodeScheduler nodeScheduler = new NodeScheduler();//实例化计算节点调度器
		//nodeScheduler.startNodeScheduler();//启动计算节点调度器
		CooperativeScheduler cs = new CooperativeScheduler();//实例化节点内的协同调度器
		ThreadDo ctd = new CPUThreadDo();
		ThreadDo gtd = new GPUThreadDo();
		ResultMerging rm = new ResultMerging();
		ResultMulMerging rmm = new ResultMulMerging();
		cs.startUp("strMatch");//启动节点内的协同调度器加入集群
		JobScheduler jobScheduler = new JobScheduler();//实例化任务调度器
		//jobScheduler.startJob("strMatch",rm,ctd,gtd);//任务调度器分配任务
		jobScheduler.startJob("strMatch",rmm,ctd,gtd);//任务调度器分配任务
	}
}