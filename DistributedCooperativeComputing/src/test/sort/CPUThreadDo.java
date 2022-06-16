package test.sort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import computingNode.TypeThreadDo;

/*CPU线程类*/
public class CPUThreadDo extends TypeThreadDo {

	private static final long serialVersionUID = -1699636376804691725L;
	@Override
	public void startWork() {
		String str;
		List<Integer> list = new ArrayList<Integer>();
		FileReader fr;
		try {
			fr = new FileReader(dataName);
			BufferedReader bf = new BufferedReader(fr);
			BufferedWriter bw=new BufferedWriter(new FileWriter(new File(resultName)));
			while ((str = bf.readLine()) != null) {//按行读取文本
				list.add(Integer.valueOf(str));
			}
			Collections.sort(list);
			bf.close();
			fr.close();
			
			for (Integer i : list) {//循环写入结果
				
				bw.write(i + "\n");
			}
			bw.close();
			
			synchronized(this){
				writeFileName();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//读文件
		
		
		cdt.countDown();//计数器-1

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
