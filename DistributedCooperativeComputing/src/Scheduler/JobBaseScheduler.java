package Scheduler;

import com.fourinone.Contractor;
import com.fourinone.WareHouse;
import com.fourinone.WorkerLocal;

public class JobBaseScheduler extends Contractor{

	WorkerLocal[] wks;
	JobScheduler js = null;
	@Override
	public WareHouse giveTask(WareHouse inhouse){

		js.assignJob(js.jobType);
		return null;
		
	}
	public WorkerLocal[] getComputeNodes(String jobType){
		wks=getWaitingWorkers(jobType);
		return wks;
	}
	public void startJob(JobScheduler js){
		this.js=js;
		this.giveTask(null);
		this.exit();
	}
	public WareHouse[] doJobBatch(WorkerLocal[] arg0, WareHouse arg1){
		WareHouse[] result = doTaskBatch(arg0, arg1);
		return result;
	}
}