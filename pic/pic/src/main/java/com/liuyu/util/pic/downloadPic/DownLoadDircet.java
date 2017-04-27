package com.liuyu.util.pic.downloadPic;

public class DownLoadDircet extends BaseDownLoad {

	public static void main(String[] args) {
		if (args.length == 4) {
			new DownLoadDircet().getJSONListByFullPah(false,args[0], args[1], Integer.parseInt(args[2]),
					Integer.parseInt(args[3]));
		} else {
			new DownLoadDircet().getJSONListByFullPah(false,args[0], args[1]);
		}
	}

}
