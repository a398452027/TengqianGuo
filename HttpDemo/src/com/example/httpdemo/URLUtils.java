package com.example.httpdemo;

public class URLUtils {

	private static final String PREFIX 	= "http://xbcx.com.cn/lianhuaxingke/index.php?g=Api&";
	
	public static final String Login 			= PREFIX + "a=index&m=Login&user=%s&pwd=%s";
	
	public static final String Register 		= PREFIX + "a=index&m=Register";
	
	public static final String GetOrg			= PREFIX + "a=index&m=Getdepinfo&id=%s";
	
	public static final String SearchOrg		= PREFIX + "a=index&m=Search&search=%s";
	
	public static final String GetUserInfo		= PREFIX + "m=Getuserinfo&a=index&imuser=%s";
	
	public static final String ChangeUserInfo 	= PREFIX + "a=index&m=Changeuserinfo&name=%s&phone=%s&tel=%s&email=%s&avatar=%s";
	
	public static final String PostFile			= PREFIX + "a=index&m=Postfile";
}
