package component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import computingNode.Tips;

public class CooperativeMerge {

	int tryNums;
	boolean flag = true;
	FileReader fr = null;
	String temp, temp1 = null;
	ThreadPool tp = new ThreadPool(16);
	Logger log = Logger.getLogger("结果合并");
	List<String> fileList = new ArrayList<String>();
	List<String> fileDown = new ArrayList<String>();
	File fileName;
	List<CountDownLatch> cdt = new ArrayList<CountDownLatch>();
	//流式并行合并
	public void MulCompute(CountDownLatch pcdt, Merging merge, JobConfig config) {
		tp.getExecutor();

		fileName = new File(config.getFileIndexPath());
		log.setLevel(Level.INFO);
		tryNums = config.getTryNums();
		if (pcdt != null) {
			try {
				pcdt.await();
				log.info(Tips.BATCH_MULCOMPUTE_START);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			try {
				Thread.sleep(config.getWindowPhase());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		try {
			while (tryNums >= 0) {
				flag = true;
				fr = new FileReader(fileName);
				
				BufferedReader br = new BufferedReader(fr);
				while ((temp = br.readLine()) != null) {
					if (!fileDown.contains(temp)) {
						tryNums = config.getTryNums();
						flag = false;
						log.fine(Tips.STREAM_MULCOMPUTE_DATAIN);
						fileList.add(temp);
						fileDown.add(temp);
					}
					if (fileList.size() == 20) {
						Merging m = (Merging) merge.getThread();
						m.setFile(fileList);
						cdt.add(m.getLatch());
						tp.addJob(m);
						fileList = new ArrayList<String>();
					}
				}
				if (flag) {
					try {
						if (pcdt == null)
							log.info(Tips.STREAM_STATE((double) config.getWindowPhase(), tryNums));
						else
							log.info(Tips.BATCH_MUL_STATE((double) config.getWindowPhase(), tryNums));
						Thread.sleep(config.getWindowPhase());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tryNums--;
					if (tryNums < 0)
						break;
				}
				br.close();

			}
			if (fileList.size() > 0&&fileList.size() < 20) {

				Merging m = (Merging) merge.getThread();
				m.setFile(fileList);
				cdt.add(m.getLatch());
				tp.addJob(m);
				for (CountDownLatch c : cdt) {

					c.await();

				}
				
				BufferedReader	br = new BufferedReader(new FileReader(fileName));
				while ((temp = br.readLine()) != null) {
					temp1 = temp;
				}
				File file = new File(temp1);
				file.renameTo(new File(config.getResultPath() + ".txt"));
				br.close();
				fileName.delete();

			}
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//串行合并
	public void serialCompute(CountDownLatch pcdt, Merging merge, JobConfig config) {
		log.setLevel(Level.INFO);
		fileName = new File(config.getFileIndexPath());
		tryNums = config.getTryNums();
		if (pcdt != null) {
			try {
				pcdt.await();
				log.info(Tips.BATCH_MULCOMPUTE_START);
				batchSerial(pcdt, merge, config);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			try {
				Thread.sleep(config.getWindowPhase());
				log.setLevel(Level.INFO);
				log.info(Tips.STREAM_MULCOMPUTE_START);
				if (fileName.exists()) {
					synchronized (this) {
						try {
							BufferedReader br = new BufferedReader(new FileReader(fileName));
							while ((temp = br.readLine()) != null) {
								if (!fileDown.contains(temp)) {
									fileList.add(temp);
									fileDown.add(temp);
								}
								if (fileList.size() == 20) {
									merge.setFile(fileList);
									merge.merging();
									fileList = new ArrayList<String>();
								}
							}

							if (fileList.size() < 20) {
								merge.setFile(fileList);
								merge.merging();
							}
							temp1 = (temp = br.readLine()) != null ? temp : "result";
							File file = new File(temp1);
							file.renameTo(new File(config.getResultPath() + ".txt"));
							br.close();
							fileName.delete();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		

	}
	//批计算
	public void batchSerial(CountDownLatch pcdt, Merging merge, JobConfig config) {
		fileName = new File(config.getFileIndexPath());
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while ((temp = br.readLine()) != null) {
				if (!fileList.contains(temp)) {
					fileList.add(temp);
				}
			}
			merge.setFile(fileList);
			merge.merging();
			String tempName = null;
			while ((temp = br.readLine()) != null) {
				tempName = temp;
			}
			br.close();
			File file = new File(tempName);
			file.renameTo(new File(config.getResultPath() + ".txt"));
			fileName.delete();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
