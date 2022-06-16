package test.str.match;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import component.CUDACall;
import component.JobConfig;
import computingNode.ThreadDo;

public class GPUThreadDo implements ThreadDo{

	/**
	 * 
	 */
	private static final long serialVersionUID = -204876863638057129L;
	public CountDownLatch cdt;//计数器
	public String dataName;//数据文件名
	public String resultName;//结果文件名
	public GPUThreadDo() {}

	@Override
	public void run() {
		// TODO Auto-generated method stub
			try {
				startWork();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//开始计算
			cdt.countDown();//计数器-1
	}

	public void startWork() throws IOException{
		System.loadLibrary("CUDACall");//加载so文件
		CUDACall call = new CUDACall();//实例化核函数
		call.GPUDo(dataName,resultName);//调用核函数
		
		synchronized(this){
			File fileName =new File("/home/jobs/fileName.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,true));
			try {
				bw.write(resultName+"\n");
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void setThreadInfo(JobConfig config, int index, CountDownLatch cdt) {
		// TODO Auto-generated method stub
		this.dataName = config.getDataPath() + index + ".txt";
		this.resultName = config.getResultPath() + index + ".txt";
		this.cdt = cdt;
		
	}

	@Override
	public Object[] getThreadsList(int nums) {
		// TODO Auto-generated method stub
		GPUThreadDo[] threadsList = new GPUThreadDo[nums];
		for(int i = 0;i<nums;i++) {
			threadsList[i] = new GPUThreadDo();
		}
		return threadsList;
	}

}
