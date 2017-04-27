package com.liuyu.util.pic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

public abstract class BaseProcessor {

	public List<JsonObject> getJSONListByFullPah(String fileName) {
		List<JsonObject> list = new ArrayList<JsonObject>();
		try {
			File file = new File(fileName);
			BufferedReader bu = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = bu.readLine();
			int no = 0;
			while (line != null) {
				try {
					JSONObject json = JSONObject.parseObject(line);
					String dzdpid = json.getString("dzdpid");
					String url = json.getString("photourl");
					download(url, "/home/liuyu/img/" + dzdpid + "/" + dzdpid + ".jpg");
					System.out.println(no++);
					line = bu.readLine();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			bu.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void download(String urlString, String filename) throws Exception {
		System.out.println(filename);
		File file = new File(filename);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		// 构造URL
		URL url = new URL(urlString);
		// 打开连接
		URLConnection con = url.openConnection();
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
	}

	public abstract void process(String json);

}
