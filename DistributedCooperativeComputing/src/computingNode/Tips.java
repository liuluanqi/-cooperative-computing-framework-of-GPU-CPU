package computingNode;

public class Tips {
	public static String STREAM_START = "";
	public static String BATCH_MULCOMPUTE_START = "\r\r\n-----------------批 计 算-----------------"
	        +"\r\r\n*                                        *"
	        +"\r\r\n*              开始合并结果                *"
	        +"\r\r\n*×××××××××××××××××××××××××××××××××××××××*";
	public static String BATCH_MULCOMPUTE_STOP = "\r\r\n-----------------批 计 算-----------------"
	        +"\r\r\n*                                       *"
	        +"\r\r\n*              任 务 结 束               *"
	        +"\r\r\n*×××××××××××××××××××××××××××××××××××××××*";
	public static String STREAM_MULCOMPUTE_START = "\r\r\n-----------------流式计算-----------------"
	        +"\r\r\n*                                       *"
	        +"\r\r\n*              开始合并结果                *"
	        +"\r\r\n*×××××××××××××××××××××××××××××××××××××××*";
	public static String STREAM_MULCOMPUTE_DATAIN = "\r\r\n-----------------流式计算-----------------"
	        +"\r\r\n*                                       *"
	        +"\r\r\n*                数据流入                 *"
	        +"\r\r\n*×××××××××××××××××××××××××××××××××××××××*";
	public static String STREAM_MULCOMPUTE_STOP = "\r\r\n-----------------流式计算-----------------"
	        +"\r\r\n*                                       *"
	        +"\r\r\n*              任 务 结 束               *"
	        +"\r\r\n*×××××××××××××××××××××××××××××××××××××××*";
	
	public static String STREAM_STATE(double windowTime,int tryNums) {
		return "\r\r\n-----------------流式计算-----------------"
		        +"\r\r\n*            当前窗口时长"+windowTime/1000+"s             *"
		        +"\r\r\n*             剩余等待次数:"+tryNums+"              *"
		        +"\r\r\n*              等待数据进入               *"
		        +"\r\r\n*×××××××××××××××××××××××××××××××××××××××*";
	}
	public static String BATCH_MUL_STATE(double windowTime,int tryNums) {
		return "\r\r\n-----------------批 计 算-----------------"
		        +"\r\r\n*            当前窗口时长"+windowTime/1000+"s             *"
		        +"\r\r\n*             剩余等待次数:"+tryNums+"              *"
		        +"\r\r\n*              等待数据进入               *"
		        +"\r\r\n*×××××××××××××××××××××××××××××××××××××××*";
	}
}
