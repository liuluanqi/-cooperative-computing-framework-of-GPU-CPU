package computingNode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import component.JobConfig;

public class TypeThreadDo implements ThreadDo {

	private static final long serialVersionUID = 4650457082385691739L;
	protected String dataName;//数据文件名
	protected String resultName;//结果文件名
	public CountDownLatch cdt;//计数器，同步线程时使用
	protected JobConfig config;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		startWork();//开始计算
		cdt.countDown();
	}

	protected void startWork() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setThreadInfo(JobConfig config, int index, CountDownLatch cdt) {
		// TODO Auto-generated method stub
		this.dataName = config.getDataPath() + index + ".txt";
		this.resultName = config.getResultPath() + index + ".txt";
		this.cdt = cdt;
		this.config = config;
	}

	@Override
	public Object[] getThreadsList(int nums) {
		// TODO Auto-generated method stub
		return null;
	}

	public void writeFileName() {
		File fileName =new File(config.getFileIndexPath());
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(fileName,true));
			bw.write(resultName+"\n");
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
