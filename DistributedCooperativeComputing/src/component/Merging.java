package component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/*结果合并类*/
public class Merging implements Serializable,Runnable{

	private static final long serialVersionUID = 4494001788003268637L;
	JobConfig jc = new JobConfig();
	public int count = jc.getTotalDataCount();//数据量
	public String resultPath = jc.getResultPath();//结果路径
	protected CountDownLatch cdt;
	protected List<String> file = new ArrayList<String>();
	//初始化数据文件路径
	public void setFile(List<String> file) {
		this.file = file;
	}
	//获取计数器
	public CountDownLatch getLatch() {
		this.cdt = new CountDownLatch(1);
		return cdt;
	}
	public void merging() {}
	@Override
	//线程执行方法
	public void run() {
		// TODO Auto-generated method stub
		merging();
		cdt.countDown();
		
	}
	//获取自身对象
	public Object getThread() {
		return null;
		
	}
}
