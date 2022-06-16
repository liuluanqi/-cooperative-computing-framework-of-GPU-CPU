package computingNode;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import component.JobConfig;

/*初始化GPU线程执行类的实例*/
public class GPUWorker  extends ProcessorOperate{

	public GPUWorker(JobConfig config, CountDownLatch cdt,ThreadDo td)
			throws IOException {
		super(config, cdt,2,td);//调用父类构造方法初始化线程执行类的实例
		// TODO Auto-generated constructor stub
	}

}
