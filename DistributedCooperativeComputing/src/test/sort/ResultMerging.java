package test.sort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import component.Merging;

/*结果合并类*/
public class ResultMerging extends Merging {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6811186566078155448L;

	@Override
	public Object getThread() {
		return new ResultMerging();
		
	}

	@Override
	public void merging() {
		try {
			mergeSort(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void mergeSort(List<String> fileNames) throws IOException {
		List<String> tempFileNames = new ArrayList<String>();
		for (int i = 0; i < fileNames.size(); i++) {
			String resultFileName = resultPath + System.nanoTime() + ".txt";
			File resultFile = new File(resultFileName);
			tempFileNames.add(resultFileName);
			BufferedWriter bw = new BufferedWriter(new FileWriter(resultFile));

			File file1 = new File(fileNames.get(i++));
			BufferedReader br1 = new BufferedReader(new FileReader(file1));
			if (i < fileNames.size()) {
				File file2 = new File(fileNames.get(i));
				BufferedReader br2 = new BufferedReader(new FileReader(file2));
				int num1 = 0;
				int num2 = 0;
				boolean isFirst = true;
				boolean firstNext = true;
				String numVal1 = null, numVal2 = null;
				for (;;) {
					if (isFirst) {
						numVal1 = br1.readLine();
						numVal2 = br2.readLine();
						num1 = Integer.valueOf(numVal1);
						num2 = Integer.valueOf(numVal2);
						isFirst = false;
					} else if (firstNext)
						numVal1 = br1.readLine();
					else
						numVal2 = br2.readLine();
					if (numVal1 != null && numVal2 != null) {
						if (firstNext) {
							num1 = Integer.valueOf(numVal1);
						} else {
							num2 = Integer.valueOf(numVal2);
						}
						if (num1 < num2) {
							bw.write(num1 + "\n");
							firstNext = true;
						} else {
							bw.write(num2 + "\n");
							firstNext = false;
						}
					} else {
						if (numVal1 != null)
							bw.write(numVal1 + "\n");
						if (numVal2 != null)
							bw.write(numVal2 + "\n");
						break;
					}
				}
				while (true) {
					numVal2 = br2.readLine();
					if (numVal2 != null)
						bw.write(numVal2 + "\n");
					else
						break;
				}
				br2.close();
				file2.delete();
			}
			while (true) {
				String numVal1 = br1.readLine();
				if (numVal1 != null) {
					bw.write(numVal1 + "\n");
				} else
					break;
			}
			br1.close();
			file1.delete();
			bw.close();
		}
		int size = tempFileNames.size();
		if (size > 1) {
			mergeSort(tempFileNames);
		} else if (size == 1) {
			//File file = new File(tempFileNames.get(0));
			synchronized (this) {
				File fileName = new File("/home/jobs/fileName.txt");
				BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,true));
				bw.write(tempFileNames.get(0) + "\n");
				bw.close();
			}
			
		}
	}
}
