package Scheduler;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.fourinone.WareHouse;
import component.CooperativeMerge;
import component.JobConfig;
import component.Merging;
import computingNode.CPUWorker;
import computingNode.FileOperate;
import computingNode.GPUWorker;
import computingNode.NodePerformanceIndex;
import computingNode.ThreadDo;
import computingNode.Tips;

//协调调度类
public class CooperativeScheduler implements Serializable {

	private static final long serialVersionUID = 7362085610651098502L;
	CPUWorker cpuWorker;// CPU工人
	GPUWorker gpuWorker;// GPU工人
	JobConfig config;// 实例化配置类
	Logger log = Logger.getLogger("任务调度器");
	CooperativeBaseScheduler cbs = new CooperativeBaseScheduler();

	public WareHouse executeTask(WareHouse inhouse) {
		log.setLevel(Level.INFO);
		int cpuStartIndex = 0;// CPU开始索引
		config = (JobConfig) inhouse.getObj("config");
		if (inhouse.getString("ServiceType") != null && inhouse.getString("ServiceType").equals("getPerformance")) {
			return cbs.getPerformance(config, inhouse);
		}
		
		config = (JobConfig)(inhouse.getObj("config"));// 以下为计算数据文件索引
		double CPURate = loadBalance(judgeAndGetData(config));
		cpuStartIndex = config.getCpuStartIndex();
		System.out.println("cpu占比"+CPURate);
		config.setCPUDataCount((int)(CPURate * config.getTotalDataCount()));
		config.setGPUDataCount(config.getTotalDataCount() - (int) (CPURate * config.getTotalDataCount()));
		config.setGpuStartIndex(cpuStartIndex + config.getCPUDataCount());
		System.out.println(config.getCPUDataCount() + "-----" + config.getGPUDataCount());

		CountDownLatch cdt = new CountDownLatch(config.getTotalDataCount());// 初始化计数器
		try {
			cpuWorker = new CPUWorker(config, cdt, (ThreadDo) inhouse.getObj("cpuThread"));// 初始化并启动CPU任务
			gpuWorker = new GPUWorker(config, cdt, (ThreadDo) inhouse.getObj("gpuThread"));// 初始化并启动GPU任务
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Merging merge = (Merging) inhouse.getObj("resultMerge");
		if (config.getComputeMode().equals("1") && config.getIsMulThreading().equals("1")) {
			log.info(Tips.STREAM_MULCOMPUTE_START);
			long start = System.currentTimeMillis();
			new CooperativeMerge().MulCompute(null, merge, config);
			log.info(Tips.STREAM_MULCOMPUTE_STOP);
			long end = System.currentTimeMillis();
			log.info("\n用时：" + (double) (end - start) / 1000 + "s");
		} else if (config.getComputeMode().equals("0") && config.getIsMulThreading().equals("1")) {
			new CooperativeMerge().MulCompute(cdt, merge, config);
			log.info(Tips.BATCH_MULCOMPUTE_STOP);
		} else if (config.getComputeMode().equals("1") && config.getIsMulThreading().equals("0")) {
			new CooperativeMerge().serialCompute(null, merge, config);
			log.info(Tips.STREAM_MULCOMPUTE_STOP);
		} else {
			new CooperativeMerge().serialCompute(cdt, merge, config);
			log.info(Tips.BATCH_MULCOMPUTE_STOP);
		}

		return null;

	}

	public double judgeAndGetData(JobConfig config) {
		double fileSize = 0.0;
		FileOperate file = new FileOperate(config);// 实例化数据文件操作类
		if (config.getSourceType().equals("1")) // 判断资源类型，1为分布式存储节点中的数据
		{
			log.info("数据存储在分布式节点上。");
			if (file.getData()) // 计算节点获取数据文件并判断成功与否
			{
				log.info("获取远程数据成功！");

			} else {
				log.info("获取远程数据失败！");
				System.exit(0);
			}

		}
		
		fileSize = file.getDataSize();
		System.out.println("文件大小----"+fileSize);
		return fileSize;
	}

	private double loadBalance(double dataSize) {
		double dataCount = 0.0;
		if (dataSize > 100) {
			dataCount = NodePerformanceIndex.getCPUDataCount(config); 
		} else {
			dataCount = 3 / 5;
		}
		return dataCount;
	}

	public void startUp(String jobType) {
		cbs.joinCluster(jobType, this);
	}

}