package computingNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.hyperic.sigar.SigarException;
import component.JobConfig;
import component.SigarInfoEntity;
import component.SigarUtils;

public class NodePerformanceIndex {
	static List<SigarInfoEntity> sigarInfos = new ArrayList<>();
	static List<SigarInfoEntity> memInfos = new ArrayList<>();
	static List<SigarInfoEntity> fileInfos = new ArrayList<>();
	public static double getSysInfo(String path) {
		int gpuInfo[] = new int[3];
		double nodePI = 0;
		int i =0;
		String str;
		try {
			sigarInfos.addAll(SigarUtils.getCpuInfos());
			memInfos.addAll(SigarUtils.getMemoryInfos());
			fileInfos.addAll(SigarUtils.getFileInfos());
			BufferedReader br = new BufferedReader(new FileReader(path));
			while((str=br.readLine())!=null) {
				gpuInfo[i] = Integer.valueOf(str);
				i++;
			}
			nodePI = 0.3*(sigarInfos.size()/12)*Double.valueOf(sigarInfos.get(1).getValue())/1000+0.2*Double.valueOf(memInfos.get(2).getValue())/1024/1024+0.1*Double.valueOf(fileInfos.get(2).getValue())/1024/1024+0.1*gpuInfo[0]/1024/1024+0.3*gpuInfo[1]*gpuInfo[2]/1000;
			br.close();
			
		} catch (SigarException | NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nodePI;
	}
	public static double getNodePerformanceIndex(JobConfig config) {
		System.loadLibrary("GetGPUInfo");//加载so文件
		GPUInfo call = new GPUInfo();//实例化核函数
		call.GPUDo(config.getGPUInfoPath());//调用核函数
		return getSysInfo(config.getGPUInfoPath());
		
	}
	public static double getCPUDataCount(JobConfig config) {
		int gpuInfo[] = new int[3];
		double CPUDataCount = 0,mem = 0.0,frequen = 0.0;
		int i =0,cores = 0;
		System.loadLibrary("GPUInfo");//加载获取GPU算力的so文件
		GPUConfiguration g = new GPUConfiguration();//实例化GPU配置类
		int GPUPower = g.getGPUInfo();
		try {
			sigarInfos.addAll(SigarUtils.getCpuInfos());
			memInfos.addAll(SigarUtils.getMemoryInfos());
			fileInfos.addAll(SigarUtils.getFileInfos());
			cores =  (sigarInfos.size()/12);
			frequen = Double.valueOf(sigarInfos.get(1).getValue())/1000;
			mem = Double.valueOf(memInfos.get(2).getValue())/1024/1024;
			BufferedReader br = new BufferedReader(new FileReader(new File("/home/jobs/gpuinfo.txt")));
			for(i = 0;i<3;i++) {
				gpuInfo[i] = Integer.valueOf(br.readLine());
			} 
			CPUDataCount = (cores*mem*frequen)/(cores*mem*frequen+GPUPower*gpuInfo[0]/1024/1024*gpuInfo[1]);
			br.close();
			
		} catch (SigarException | NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CPUDataCount;
	}
}
