package Scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.fourinone.WareHouse;
import com.fourinone.WorkerLocal;
import component.JobConfig;
import component.Merging;
import computingNode.ThreadDo;

public class JobScheduler {
	WareHouse[] jobInfo;
	WareHouse service = new WareHouse();
	WorkerLocal[] wks;
	Merging rm = null;
	String jobType = null;
	ThreadDo[] td = new ThreadDo[2];
	Logger log = Logger.getLogger("任务调度器");
	JobBaseScheduler jbs = new JobBaseScheduler();

	public void assignJob(String jobType) {
		int i = 0;
		log.setLevel(Level.INFO);
		log.info("Assign Job!");
		wks = jbs.getComputeNodes(jobType);// 获取工人
		log.info(jobType + "类型的工人数量：" + wks.length);
		jobInfo = loadBlance(wks.length);// 定义消息仓库数组
		for (i = 0; i < wks.length; i++) {

			wks[i].doTask(jobInfo[i]);// 开始任务
		}

	}

	public WareHouse[] loadBlance(int nodeCount) {
		double sum = 0.0;
		WareHouse[] jobInfo = new WareHouse[nodeCount];
		JobConfig[] config = new JobConfig[nodeCount];// 实例化配置类
		JobConfig tempConfig = new JobConfig();
		int fileCount, startIndex = 0, lastNode = tempConfig.getTotalGlobalData();
		service.setString("ServiceType", "getPerformance");
		service.setObj("config", tempConfig);
		WareHouse[] performance = new WareHouse[nodeCount];
		List<WareHouse> resultList = new ArrayList<WareHouse>();
		for (int i = 0; i < nodeCount; i++) {
			config[i] = new JobConfig();
			jobInfo[i] = new WareHouse();
			performance[i] = wks[i].doTask(service);
			resultList.add(performance[i]);
			jobInfo[i].setObj("cpuThread", td[0]);
			jobInfo[i].setObj("gpuThread", td[1]);
			jobInfo[i].setObj("resultMerge", rm);
		}

		while (resultList.size() != 0) {
			int resultSize = resultList.size();
			for (int i = 0; i < resultSize; i++) {
				if (resultList.get(i).getStatus() == WareHouse.READY) {
					sum += Double.valueOf(resultList.get(i).getString("performance"));
					resultList.remove(i);
				}

			}
		}
		config[0].setCpuStartIndex(0);

		for (int i = 0; i < nodeCount - 1; i++) {
			fileCount = (int) (tempConfig.getTotalDataCount() * Double.valueOf(performance[i].getString("performance"))
					/ sum);
			startIndex = startIndex + fileCount;
			config[i+1].setCpuStartIndex(startIndex);
			config[i].setTotalDataCount(fileCount);
			jobInfo[i].setObj("config", config[i]);
			lastNode = lastNode - fileCount;
		}
		config[nodeCount-1].setTotalDataCount(lastNode);
		jobInfo[nodeCount-1].setObj("config", config[nodeCount-1]);
		return jobInfo;
	}

	public void startJob(String jobType, Merging rm, ThreadDo ctd, ThreadDo gtd) {
		this.jobType = jobType;
		this.td[0] = ctd;
		this.td[1] = gtd;
		this.rm = rm;
		jbs.startJob(this);

	}
}
