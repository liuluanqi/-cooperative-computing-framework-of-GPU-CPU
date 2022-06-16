package test.sort;

import component.CUDACall;
import computingNode.TypeThreadDo;

public class GPUThreadDo extends TypeThreadDo {

	private static final long serialVersionUID = -4110088395745036002L;

	@Override
	public void startWork() {
		System.loadLibrary("CUDACall");//加载so文件
		CUDACall call = new CUDACall();//实例化核函数
		call.GPUDo(dataName,resultName);//调用核函数
		synchronized(this){
			writeFileName();
		}
		
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
