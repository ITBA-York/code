package com.liuyu.util.pic.downloadPic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.liuyu.util.pic.FileUtil;

public abstract class BaseDownLoad {

	static List<String> list = FileUtil.getLineListByJarPah("proxy.txt");

	public void getJSONListByFullPah(boolean proxy, String fileName, String basePath, int... param) {
		try {
			File file = new File(fileName);
			BufferedReader bu = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = bu.readLine();
			int no = 0;
			while (line != null) {
				try {
					no++;
					if (param != null && param.length == 2) {
						int all = param[0];
						int index = param[1];
						if (no % all != index) {
							line = bu.readLine();
							continue;
						}
					}
					JSONObject json = JSONObject.parseObject(line);
					String dzdpid = json.getString("dzdpid");
					String url = json.getString("photourl");
					String path = basePath + dzdpid + File.separator + dzdpid + ".jpg";
					if (new File(path).exists()) {
						System.out.println(path + "finished!");
						line = bu.readLine();
						continue;
					}
					if (proxy) {
						downloadWithProxy(url, path);
					} else {
						download(url, path);
					}
					System.out.println(no);
					line = bu.readLine();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			bu.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void downloadWithProxy(String urlString, String filename) throws Exception {
		String line = null;
		try {
			line = list.get(new Random().nextInt(list.size()));
			InetSocketAddress addr = new InetSocketAddress(line.split("\t")[0], Integer.parseInt(line.split("\t")[1]));
			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
			File file = new File(filename);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			// 构造URL
			URL url = new URL(urlString);
			// 打开连接
			HttpURLConnection con = (HttpURLConnection) url.openConnection(proxy);
			// 设置请求超时为5s
			con.setConnectTimeout(5 * 1000);
			// 输入流
			InputStream is = con.getInputStream();
			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			OutputStream os = new FileOutputStream(filename);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// 完毕，关闭所有链接
			os.close();
			is.close();
		} catch (Exception e) {
			throw new Exception("line:" + line);
		}

	}

	public void download(String urlString, String filename) throws Exception {
		try {
			File file = new File(filename);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			// 构造URL
			URL url = new URL(urlString);
			// 打开连接
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			// 设置请求超时为5s
			con.setConnectTimeout(5 * 1000);
			// 输入流
			InputStream is = con.getInputStream();
			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			OutputStream os = new FileOutputStream(filename);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// 完毕，关闭所有链接
			os.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
