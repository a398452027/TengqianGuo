package com.example.httpdemo;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import android.content.SharedPreferences;

import com.example.bean.BaseInfo;
import com.xbcx.core.AndroidEventManager;
import com.xbcx.core.SharedPreferenceDefine;
import com.xbcx.core.XApplication;
import com.xbcx.im.IMKernel;
import com.xbcx.im.IMLoginInfo;
import com.xbcx.im.IMMessage;
import com.xbcx.im.RecentChatManager;
import com.xbcx.im.VCardProvider;
import com.xbcx.im.XIMSystem;
import com.xbcx.im.folder.FolderManager;
import com.xbcx.im.messageprocessor.FileMessageUploadProcessor;
import com.xbcx.im.messageprocessor.PhotoMessageUploadProcessor;
import com.xbcx.im.messageprocessor.VideoMessageUploadProcessor;
import com.xbcx.im.messageprocessor.VoiceMessageUploadProcessor;
import com.xbcx.im.recentchatprovider.XMessageRecentChatProvider;
import com.xbcx.im.ui.ActivityType;
import com.xbcx.im.ui.EditViewQQExpressionProvider;
import com.xbcx.im.ui.IMGlobalSetting;
import com.xbcx.im.ui.SendPlugin;
import com.xbcx.im.ui.StatusBarManager;
import com.xbcx.im.ui.TypeSendPlugin;






public class TestApp extends XApplication{
	public static final String KEY_HTTP = "quyd75403l!@#~|}{][=";
	
	public static final String KEY_EMAIL = "email";
	private static final String PREFIX 	= "http://xbcx.com.cn/lianhuaxingke/index.php?g=Api&";

	public static final String PostFile			= PREFIX + "a=index&m=Postfile";
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	
		final IMKernel kernel = IMKernel.getInstance();
		kernel.registerModule(VCardProvider.class.getName(), VCardProvider.class, false);
		kernel.registerModule(FolderManager.class.getName(), FolderManager.class, false);
		kernel.registerModule(StatusBarManager.class.getName(), StatusBarManager.class, false);
		kernel.registerModule(VoiceMessageUploadProcessor.class.getName(), JHVoiceMessageUploadProcessor.class, false);
		kernel.registerModule(PhotoMessageUploadProcessor.class.getName(), JHPhotoMessageUploadProcessor.class, false);
		kernel.registerModule(VideoMessageUploadProcessor.class.getName(), JHVideoMessageUploadProcessor.class, false);
		kernel.registerModule(FileMessageUploadProcessor.class.getName(), FileMessageUploadProcessor.class, false);
		
		final String url = PostFile;
		kernel.setUploadFileUrl(url);
		kernel.setUploadPhotoUrl(url);
		kernel.setUploadVideoThumbUrl(url);
		kernel.setUploadVideoUrl(url);
		kernel.setUploadVoiceUrl(url);
		kernel.initial(this);
		
		IMGlobalSetting.photoSendPreview = true;
		IMGlobalSetting.editViewExpProviders.add(EditViewQQExpressionProvider.class);
		IMGlobalSetting.mapEditViewBtnIdToSendPlugin.put(R.id.btnPhoto, new TypeSendPlugin(SendPlugin.SENDTYPE_PHOTO_ALL));
		IMGlobalSetting.mapEditViewBtnIdToSendPlugin.put(R.id.btnCamera, new TypeSendPlugin(SendPlugin.SENDTYPE_VIDEO_ALL));
		IMGlobalSetting.mapEditViewBtnIdToSendPlugin.put(R.id.btnFile, new TypeSendPlugin(SendPlugin.SENDTYPE_FILE));
	
		ActivityType.registerActivityClassName(ActivityType.SingleChat, JHSingleChatActivity.class.getName());
		
		
		kernel.registerIMSystem(XIMSystem.class);
		RecentChatManager.getInstance().registerRecentChatProvider(
				IMMessage.class.getName(),new XMessageRecentChatProvider());
		initHttpRunner();
		
		
	}
	private void initHttpRunner(){
		AndroidEventManager eventManager = AndroidEventManager.getInstance();
		eventManager.registerEventRunner(JHEventCode.HTTP_Login, new LoginRunner());
		eventManager.registerEventRunner(JHEventCode.HTTP_PostFile, new PostFileRunner());
	
	}
	public static String getUser(){
		return getApplication().getSharedPreferences(SharedPreferenceDefine.SP_IM, 0)
		.getString(KEY_EMAIL, null);
	}
	public static void login(String imUser,String imPwd){
		IMKernel.getInstance().loginUserId(createLoginInfo(imUser, imPwd), true);
	}
	protected static IMLoginInfo createLoginInfo(String imUser,String imPwd){
		return new IMLoginInfo(imUser, imPwd, "lhxk.com", "xbcx.com.cn", 31004);
	}
	
	public static void saveBaseInfo(BaseInfo bi){
		final SharedPreferences sp = getApplication()
				.getSharedPreferences(SharedPreferenceDefine.SP_IM, 0);
		sp.edit().putString(SharedPreferenceDefine.KEY_USER, bi.getIMUser())
		.putString(SharedPreferenceDefine.KEY_PWD,bi.getIMPwd())
		.putString(KEY_EMAIL, bi.getUser())
		.commit();
		
//		JHVCardProvider.getInstance().saveInfo(bi.getIMUser(), bi.getName(), bi.getAvatarUrl());
		
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try{
			fos = getApplication().openFileOutput("login", 0);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(bi);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(oos != null){
				try{
					oos.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	

}
