package test.str.match;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import component.JobConfig;
import computingNode.ThreadDo;

/*CPU线程类*/
public class CPUThreadDo implements ThreadDo {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5786544464054679444L;
	private String dataName;//数据文件名
	private String resultName;//结果文件名
	Set<String> feature = FeatureStr.getFeature();
	private int[] result = new int[30];//结果
	public CountDownLatch cdt;//计数器，同步线程时使用
	public CPUThreadDo() {}
	//初始化数据信息
	public CPUThreadDo(JobConfig config, int index,CountDownLatch cdt) {
		this.dataName = config.getDataPath() + index + ".txt";
		this.resultName = config.getResultPath() + index + ".txt";
		this.cdt = cdt;
	}

	@Override
	public void run() {
		try {
			
			startWork();//开始计算

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startWork() throws IOException {
		FileReader fr = new FileReader(dataName);//读文件
		BufferedReader bf = new BufferedReader(fr);
		String str;
		while ((str = bf.readLine()) != null) {//按行读取文本
			int i = 0;
			for (String f :feature) {//循环匹配字符串
				 
				 if(str.contains(f)){
					 result[i] = result[i] + 1;//特征字符串出现次数+1
				 }
				 i++;
			}

		}
		writeResult();//中间结果写入外存
		bf.close();
		fr.close();
		File fileName =new File("/home/jobs/fileName.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,true));
		synchronized(this){
			bw.write(resultName+"\n");
		}
		bw.close();
		cdt.countDown();//计数器-1

	}

	public void writeResult() throws IOException {
		FileWriter fw = new FileWriter(new File(resultName));//写文件
		for (int i = 0; i < 30; i++) {//循环写入结果
			fw.write(result[i] + "\r\n");
		}
		fw.close();
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
		CPUThreadDo[] threadsList = new CPUThreadDo[nums];
		for(int i = 0;i<nums;i++) {
			threadsList[i] = new CPUThreadDo();
		}
		return threadsList;
	}

}
