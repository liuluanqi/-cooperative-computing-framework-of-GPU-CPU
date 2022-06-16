package computingNode;

public class GPUConfiguration {//jni调用cuda程序获取GPU算力，以作为节点内数据分配依据
	public native int getGPUInfo();
}
