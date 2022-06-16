package component;

import java.io.File;
import java.io.Serializable;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/*XML配置文件操作类*/
public class XMLOperate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8432120738292550822L;
	Element root;
	
	public XMLOperate(String configFile) {
		// TODO Auto-generated constructor stub
		String configPath = "";
		configPath = "config/"+configFile+".xml";
		try {
			this.root = getRoot(configPath);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Element getRoot(String configFile) throws DocumentException {//获取根节点
		SAXReader reader = new SAXReader();
		reader.setEncoding("UTF-8");
		Document doc = reader.read(new File(configFile));
		return doc.getRootElement();
		
	}
	
	public String getSpecifyElement(String nodeName) throws DocumentException {//获取某个节点的值

		String nodeValue = root.elementText(nodeName);
		return nodeValue;
		
	}

}
