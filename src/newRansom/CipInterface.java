package newRansom;

import java.util.Map;

public interface CipInterface {
	// 개인키, 공개키를 생성
	public void CreateKey();
	// 파일 암호화 하는 부분
	public boolean CreateReadFile(String filepath,String filename);
	// 경고 메세지를 알려준다.
	public void CreateListFile(String filepath);
	// 파일 암호화 하는 부분
	public void FileEncrypt(String filepath , String filename);
	// 무작위 파일 이름을 생성한다.
	private String CreateFileName(String filepath, String filename);
	// 파일 복호화
	public void FileDecryte(String filepath ,String filename ) ;
	// 원본 파일 이름과 암호화 된 다음의 파일 이름을 저장하는 부분
	public Map<String, String> ReturnList();
	// 저장된 리스트 파일로부터 복호화 키 값을 읽어온다.
	public void ReadListFile(String filepath);
	// 암호화가 끝났으면 리스트 파일도 암호화 시킨다.
	public void ListEncrypt(String filepath, String filename);
	// 복호화 시킬때 리스트를 읽어오기 위해 리스트를 복호화 시킨다.
	public void ListDecryte(String filepath, String filename);
}
