package computingNode;

import com.fourinone.FttpAdapter;
import com.fourinone.FttpAdapter.FileProperty;
import com.fourinone.FttpException;
import component.JobConfig;

public class FileOperate
{
	
	JobConfig config;//任务配置类
	int dataIndex;//数据文件索引
	String dataSource;//远程数据源
	String dataDestination;//数据目的地

	public FileOperate(JobConfig config)//构造方法初始化配置类
	{
		this.config = config;
	}
	//获取文件大小
	public double getDataSize() {
		double fileSize=0.0;
		try {
			FttpAdapter dir = new FttpAdapter(config.getFolderDataPath());
			FileProperty[] fileList = dir.getChildProperty();
			for(int i = 0;i<fileList.length;i++) {
				fileSize = fileSize+fileList[i].length();
			}
		} catch (FttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileSize/1024/1024;
		
	} 
	public Boolean getData() //计算节点获取数据
	{
		FttpAdapter source;//数据源对象
		FttpAdapter target;//目标对象
		int startIndex = config.getCpuStartIndex();//数据文件起始索引

		for (int i = 0; i < config.getTotalDataCount(); i++) //循环copy数据文件
		{
			
			dataIndex = startIndex + i;//计算索引
			dataSource = config.getRemoteAddress() + dataIndex + ".txt";//拼接源文件信息
			dataDestination = config.getLocalAddress() + dataIndex + ".txt";//拼接目标文件信息
			
			try {//复制数据文件
				source = new FttpAdapter(dataSource);
				target = source.copyTo(dataDestination);
				if(target == null) //复制失败返回false
				{
					return false;
				}
			} catch (FttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
}