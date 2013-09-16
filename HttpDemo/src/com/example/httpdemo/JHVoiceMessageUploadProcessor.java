package com.example.httpdemo;

import com.xbcx.core.AndroidEventManager;
import com.xbcx.core.Event;
import com.xbcx.im.XMessage;
import com.xbcx.im.messageprocessor.VoiceMessageUploadProcessor;


public class JHVoiceMessageUploadProcessor extends VoiceMessageUploadProcessor {

	@Override
	public void onEventRun(Event event) throws Exception {
		XMessage xm = (XMessage)event.getParamAtIndex(0);
		Event e = AndroidEventManager.getInstance().runEvent(JHEventCode.HTTP_PostFile, 
				(xm.isFromGroup() ? "3" : "0"),xm.getVoiceFilePath());
		if(e.isSuccess()){
			xm.setVoiceDownloadUrl((String)e.getReturnParamAtIndex(0));
			event.setSuccess(true);
		}
	}
}
