package test.str.match;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

//从文件中读取特征字符串存入HashSet
public class FeatureStr {
	
	static Set<String> feature=new HashSet<String>();
	public static Set<String> getFeature(){
		
		FileReader fr;
		BufferedReader bf;
			
		try {
			fr = new FileReader("/home/jobs/feature.txt");
			bf = new BufferedReader(fr);
			String str;
			try {
				while ((str = bf.readLine()) != null) {
					feature.add(str);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				bf.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return feature;
	}
}
