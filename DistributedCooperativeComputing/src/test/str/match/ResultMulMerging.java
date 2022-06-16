package test.str.match;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import component.Merging;

public class ResultMulMerging extends Merging {

	private static final long serialVersionUID = 947403979233232397L;

	@Override
	public Object getThread() {
		// TODO Auto-generated method stub
		return new ResultMulMerging();
	}

	@Override
	public void merging() {
		// TODO Auto-generated method stub
		try {
			merging(file);
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void merging(List<String> file) throws NumberFormatException, IOException {// 合并结果
		int[] featureNum = new int[30];// 暂存累加结果
		String resultFileName = resultPath + System.nanoTime() + ".txt";
		for (int i = 0; i < file.size(); i++) {// 循环各结果文件

			String str;
			File file1 = new File(file.get(i));
			FileReader fr = new FileReader(file1);// 读文本
			BufferedReader bf = new BufferedReader(fr);

			for (int j = 0; (str = bf.readLine()) != null; j++) {// 按行读取结果

				featureNum[j] = featureNum[j] + Integer.parseInt(str);// 汇总结果
			}

			bf.close();
			fr.close();
			file1.delete();

		}
		FileWriter fw = new FileWriter(new File(resultFileName));// 写文件
		for (int i = 0; i < 30; i++) {
			fw.write(featureNum[i] + "\n");
		}
		fw.close();
		synchronized (this) {
			File fileName = new File("/home/jobs/fileName.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));
			bw.write(resultFileName + "\n");
			bw.close();
		}

	}
}
