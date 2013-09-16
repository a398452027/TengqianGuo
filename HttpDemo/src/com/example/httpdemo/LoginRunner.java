package com.example.httpdemo;

import java.net.URLEncoder;
import java.util.Locale;

import org.json.JSONObject;

import com.example.bean.BaseInfo;
import com.xbcx.core.Event;

import com.xbcx.utils.Encrypter;

public class LoginRunner extends HttpRunner{

	private static final String PREFIX 	= "http://xbcx.com.cn/lianhuaxingke/index.php?g=Api&";
	
	public static final String Login 			= PREFIX + "a=index&m=Login&user=%s&pwd=%s";
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEventRun(Event event) throws Exception {
		// TODO Auto-generated method stub
		final String user = (String)event.getParamAtIndex(0);
		final String pwd = (String)event.getParamAtIndex(1);
		JSONObject jo = doGet(String.format(
				Locale.getDefault(), Login, 
				URLEncoder.encode(user + "-" + Encrypter.encryptBySHA1(user + KEY_HTTP)),
				Encrypter.encryptByMD5(pwd)));
		event.addReturnParam(new BaseInfo(jo));
		event.setSuccess(true);
	}

}
