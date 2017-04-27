package com.liuyu.util.pic;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.filechooser.FileSystemView;

public class FileUtil {

	final static String FILEPATH = "e:\\data\\test\\";

	public static void writeLine(String str, String fileName) {
		fileName = FILEPATH + fileName;
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(str);
			writer.write("\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized static void writeLineByFullPath(String str, String fileName) {
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fileName, true), "UTF-8");
			out.append(str);
			out.append("\n");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getBodyByHtml(String no) throws Exception {
		File file = new File("d:\\Users\\liuyub\\Desktop\\html\\" + no);
		FileInputStream fos = new FileInputStream(file);
		BufferedReader bu = new BufferedReader(new InputStreamReader(fos, "utf-8"));
		String line = bu.readLine();
		String result = "";
		while (line != null) {
			result += line;
			line = bu.readLine();
		}
		bu.close();
		return result;
	}

	public static List<String> getLineListByFile(String fileName) throws Exception {
		fileName = FILEPATH + fileName;
		File file = new File(fileName);
		BufferedReader bu = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		List<String> list = new ArrayList<String>();
		String line = bu.readLine();
		while (line != null) {
			list.add(line);
			line = bu.readLine();
		}
		bu.close();
		return list;
	}

	public static List<String> getLineListByFullPah(String fileName) {
		List<String> list = new ArrayList<String>();
		try {
			File file = new File(fileName);
			BufferedReader bu = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = bu.readLine();
			while (line != null) {
				list.add(line);
				line = bu.readLine();
			}
			bu.close();
		} catch (Exception e) {

		}
		return list;
	}

	public static List<String> getLineListByJarPah(String fileName) {

		List<String> list = new ArrayList<String>();
		try {
			BufferedReader bu = new BufferedReader(
					new InputStreamReader(FileUtil.class.getResourceAsStream(fileName), "UTF-8"));
			String line = bu.readLine();
			while (line != null) {
				list.add(line);
				line = bu.readLine();
			}
			bu.close();
		} catch (Exception e) {

		}
		return list;
	}

	public static String getBodyByJAVA(String no) throws Exception {
		File file = new File(no);
		FileInputStream fos = new FileInputStream(file);
		ObjectInputStream oos = new ObjectInputStream(fos);
		String text = (String) oos.readObject();
		oos.close();
		return text;
	}

	public static String getValueByKey(String href, String key) {
		String uri[] = href.split("&");
		for (String str : uri) {
			if (str.contains(key)) {
				return str.split("=")[1];
			}
		}
		return null;
	}

	public static String getBodyByFile(String filePath) throws Exception {
		File file = new File(filePath);
		FileInputStream fos = new FileInputStream(file);
		BufferedReader bu = new BufferedReader(new InputStreamReader(fos, "utf-8"));
		String line = bu.readLine();
		String result = "";
		while (line != null) {
			result += line;
			line = bu.readLine();
		}
		bu.close();
		return result;
	}

	public static void downloadNet(String uri, File file) throws Exception {
		// 下载网络文件
		int byteread = 0;
		URL url = null;
		url = new URL(uri);
		URLConnection conn;
		FileOutputStream fs = null;
		InputStream inStream = null;
		conn = url.openConnection();
		inStream = conn.getInputStream();
		fs = new FileOutputStream(file);
		byte[] buffer = new byte[1204];
		while ((byteread = inStream.read(buffer)) != -1) {
			fs.write(buffer, 0, byteread);
		}
		try {
			fs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void copyFile(String oldPath, String newPath) {
		try {
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				fs.close();
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();
		}
	}

	public static String getDeskPath() {
		FileSystemView fsv = FileSystemView.getFileSystemView();
		File com = fsv.getHomeDirectory(); // 这便是读取桌面路径的方法了
		return com.getPath();
	}

	public static int getLineNum(String filePath) throws IOException {
		// File file = new File(filePath);
		// BufferedReader bu1 = new BufferedReader(new InputStreamReader(new
		// FileInputStream(file)));
		// int count = 0;
		// String line = bu1.readLine();
		// while (line != null) {
		// count++;
		// //System.out.println(count);
		// line = bu1.readLine();
		// }
		// bu1.close();
		// return count;
		InputStream is = new BufferedInputStream(new FileInputStream(filePath));
		byte[] c = new byte[1024];
		int count = 0;
		int readChars = 0;
		while ((readChars = is.read(c)) != -1) {
			for (int i = 0; i < readChars; ++i) {
				if (c[i] == '\n')
					++count;
			}
		}
		is.close();
		return count;
	}

	public static Map<String, String> getMapByFile(String filePath) throws IOException {
		File file = new File(filePath);
		Map<String, String> map = new HashMap<String, String>();
		BufferedReader bu1 = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = bu1.readLine();
		while (line != null) {
			line = bu1.readLine();
			map.put(line.split("\t")[1], line.split("\t")[0]);
		}
		bu1.close();
		return map;
	}

	public static String getLine(String fileName) throws Exception {
		File file = new File(fileName);
		BufferedReader bu = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = bu.readLine();
		bu.close();
		return line;
	}

	public static String getLine(String fileName, int lineNum) throws Exception {
		File file = new File(fileName);
		int i = 1;
		BufferedReader bu = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = bu.readLine();
		while (line != null) {
			if (i == lineNum) {
				return line;
			}
			line = bu.readLine();
			i++;
		}
		bu.close();
		return line;
	}

	public static void main(String[] args) throws IOException {

		System.out.println(new File("resource").getAbsolutePath());
		// int line =
		// getLineNum("F:\\20170308\\SanFranciscoYelp\\huwei_yelp_comment_detail_0_1489714903316.txt");
		// System.out.println(line);
		// BufferedReader bu = new BufferedReader(
		// new InputStreamReader(new
		// FileInputStream("F:\\20170328\\manguta\\TACommentPic2_0_1490775294488.txt")));
		// List<String> list = new ArrayList<String>();
		// String line = bu.readLine();
		// while (line != null) {
		// list.add(line);
		// line = bu.readLine();
		// System.out.println(line);
		// }
		// bu.close();
	}

	public synchronized static void delete(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
	}

	public static boolean hasFileInCurrentFolder(File file, String filename) {
		for (File files : file.getParentFile().listFiles()) {
			if (files.getName().equals(filename)) {
				return true;
			}
		}
		return false;
	}

	public static int getFilesNum(int count, String path) {
		File file = new File(path);
		for (File f : file.listFiles()) {
			if (f.isDirectory()) {
				count = getFilesNum(count, f.getAbsolutePath());
			} else {
				if (!"Thumbs.db".equals(f.getName())) {
					count++;
				}
			}
		}
		return count;
	}

}
