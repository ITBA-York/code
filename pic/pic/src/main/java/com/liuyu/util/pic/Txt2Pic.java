package com.liuyu.util.pic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.swing.plaf.FileChooserUI;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

import sun.misc.BASE64Decoder;

public class Txt2Pic extends BaseProcessor {

	public static String PATH = null;

	public static void main(String[] args) {

		File file = new File(args[0]);
		PATH = file.getParentFile().getAbsolutePath();
		if (args.length == 2) {
			PATH = args[1];
		}

		new Txt2Pic().getJSONListByFullPah(args[0]);
	}

	@Override
	public void process(String json) {
		try {
			JSONObject.parse(json);
		} catch (Exception e) {
			String txt = json.substring(json.lastIndexOf("\"pictxt\":\"") + 10, json.lastIndexOf("\",\"contentlength\""));
			String dzdpid = json.substring(json.indexOf("\"dzdpid\":\"") + 10, json.indexOf("\",\"photourl\""));
			txt =txt.replaceAll("\\\\n", "\n");
			generateImage(txt, PATH + File.separator + "img" + File.separator + dzdpid + File.separator + dzdpid + ".jpg");
		}
		

	}

	public static boolean generateImage(String imgStr, String imgFilePath) {
		if (imgStr == null)
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			File file = new File(imgFilePath);

			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
