package component;

import java.io.Serializable;

import org.dom4j.DocumentException;

import computingNode.GPUConfiguration;

public class JobConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6169008415712001524L;
	private String DataPath;//数据路径
	private String ResultPath;//结果路径
	private int TotalDataCount;//单个计算节点的总数据量
	private int CPUDataCount;//CPU计算的数据量
	private int GPUDataCount;//GPU计算的数据量
	private String AutoAllocate;//计算节点内数据的分配方式
	private int cpuStartIndex;//CPU计算的起始索引
	private int gpuStartIndex;//GPU计算的起始索引
	private String SourceType;//资源类型
	private String LocalAddress;//本地地址
	private String RemoteAddress;//远程地址
	private String ComputeMode;//计算模式
	XMLOperate xmlOperate;
	private int WindowPhase;
	private int TryNums;
	private String IsMulThreading;
	private String FileIndexPath;
	private String GPUInfoPath;
	private String FolderDataPath;
	private int TotalGlobalData;
	public JobConfig() {//默认构造方法初始化配置
		xmlOperate = new XMLOperate("JobConfig");//实例化配置文件操作类
		try {
			this.SourceType = xmlOperate.getSpecifyElement("SourceType");//获取资源类型
			this.LocalAddress = xmlOperate.getSpecifyElement("LocalAddress");//同上
			this.RemoteAddress = xmlOperate.getSpecifyElement("RemoteAddress");//同上
			this.AutoAllocate = xmlOperate.getSpecifyElement("AutoAllocate");//同上
			this.DataPath = xmlOperate.getSpecifyElement("DataPath");//同上
			this.ResultPath =  xmlOperate.getSpecifyElement("ResultPath");//同上
			this.TotalDataCount = Integer.valueOf( xmlOperate.getSpecifyElement("TotalDataCount") );//同上
			this.ComputeMode = xmlOperate.getSpecifyElement("ComputeMode");
			this.WindowPhase = Integer.valueOf( xmlOperate.getSpecifyElement("WindowPhase") );
			this.TryNums = Integer.valueOf( xmlOperate.getSpecifyElement("TryNums") );
			this.IsMulThreading = xmlOperate.getSpecifyElement("IsMulThreading");
			this.FileIndexPath = xmlOperate.getSpecifyElement("FileIndexPath");
			this.GPUInfoPath = xmlOperate.getSpecifyElement("GPUInfoPath");
			this.FolderDataPath = xmlOperate.getSpecifyElement("FolderDataPath");
			this.TotalGlobalData = Integer.valueOf(xmlOperate.getSpecifyElement("TotalGlobalData"));
			if ( AutoAllocate.equals("1") ) {//判断数据分配类型，若为手动分配则从配置文件中读取配置
				this.CPUDataCount = Integer.valueOf( xmlOperate.getSpecifyElement("CPUDataCount") );
				this.GPUDataCount = Integer.valueOf( xmlOperate.getSpecifyElement("GPUDataCount") );
			}
			
			else {//自动分配
				System.loadLibrary("GPUInfo");//加载获取GPU算力的so文件
				GPUConfiguration g = new GPUConfiguration();//实例化GPU配置类
				this.GPUDataCount = this.TotalDataCount*( g.getGPUInfo()/(g.getGPUInfo()+1) );//计算GPU数据量，GPU数据量占比是“GPU算力/(GPU算力+1)”
				this.CPUDataCount = this.TotalDataCount - this.GPUDataCount;//CPU数据量 = 总数据量 - GPU数据量
			}
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//以下为各配置项的get与set方法
	public String getDataPath() {
		return DataPath;
	}

	public void setDataPath(String dataPath) {
		DataPath = dataPath;
	}

	public String getResultPath() {
		return ResultPath;
	}

	public void setResultPath(String resultPath) {
		ResultPath = resultPath;
	}

	public int getTotalDataCount() {
		return TotalDataCount;
	}

	public void setTotalDataCount(int totalDataCount) {
		TotalDataCount = totalDataCount;
	}

	public int getCPUDataCount() {
		return CPUDataCount;
	}

	public void setCPUDataCount(int cPUDataCount) {
		CPUDataCount = cPUDataCount;
	}

	public int getGPUDataCount() {
		return GPUDataCount;
	}

	public void setGPUDataCount(int gPUDataCount) {
		GPUDataCount = gPUDataCount;
	}

	public int getCpuStartIndex() {
		return cpuStartIndex;
	}

	public void setCpuStartIndex(int cpuStartIndex) {
		this.cpuStartIndex = cpuStartIndex;
	}

	public int getGpuStartIndex() {
		return gpuStartIndex;
	}

	public void setGpuStartIndex(int gpuStartIndex) {
		this.gpuStartIndex = gpuStartIndex;
	}

	public String getSourceType() {
		return SourceType;
	}

	public void setSourceType(String sourceType) {
		SourceType = sourceType;
	}

	public String getLocalAddress() {
		return LocalAddress;
	}

	public void setLocalAddress(String localAddress) {
		LocalAddress = localAddress;
	}

	public String getRemoteAddress() {
		return RemoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		RemoteAddress = remoteAddress;
	}

	public String getComputeMode() {
		return ComputeMode;
	}

	public void setComputeMode(String computeMode) {
		ComputeMode = computeMode;
	}

	public int getWindowPhase() {
		return WindowPhase;
	}

	public void setWindowPhase(int windowPhase) {
		WindowPhase = windowPhase;
	}

	public int getTryNums() {
		return TryNums;
	}

	public void setTryNums(int tryNums) {
		TryNums = tryNums;
	}

	public String getIsMulThreading() {
		return IsMulThreading;
	}

	public void setIsMulThreading(String isMulThreading) {
		IsMulThreading = isMulThreading;
	}

	public String getFileIndexPath() {
		return FileIndexPath;
	}

	public void setFileIndexPath(String fileIndexPath) {
		FileIndexPath = fileIndexPath;
	}

	public String getGPUInfoPath() {
		return GPUInfoPath;
	}

	public void setGPUInfoPath(String gPUInfoPath) {
		GPUInfoPath = gPUInfoPath;
	}

	public String getFolderDataPath() {
		return FolderDataPath;
	}

	public void setFolderDataPath(String folderDataPath) {
		FolderDataPath = folderDataPath;
	}

	public int getTotalGlobalData() {
		return TotalGlobalData;
	}

	public void setTotalGlobalData(int totalGlobalData) {
		TotalGlobalData = totalGlobalData;
	}
	
}
