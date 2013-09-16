package com.example.httpdemo;

import java.util.Collection;

import com.xbcx.core.Event;
import com.xbcx.core.EventCode;

import com.xbcx.im.IMContact;
import com.xbcx.im.ui.XBaseActivity;
import com.xbcx.im.ui.simpleimpl.RecentChatActivity;
import com.xbcx.im.ui.simpleimpl.RecentChatAdapter;

import android.os.Bundle;

import android.annotation.SuppressLint;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MsglistActivity extends RecentChatActivity {
	protected Collection<IMContact>  	mContacts;

	public Button chat;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		chat=(Button)findViewById(R.id.chatwithone);
		if(chat!=null)
		chat.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				JHSingleChatActivity.launch(MsglistActivity.this, id, name)
//				
//				pushEvent(eventCode, params)
				
				final Event e1 = mEventManager.pushEventEx(EventCode.IM_GetFriendList, MsglistActivity.this);
				if(e1.isSuccess()){
					mContacts = (Collection<IMContact>)e1.getReturnParamAtIndex(0);
				}
			}
		});

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onEventRunEnd(Event event) {
		// TODO Auto-generated method stub
		super.onEventRunEnd(event);
		final int code = event.getEventCode();
		if(code == EventCode.IM_GetFriendList){
			mContacts = (Collection<IMContact>)event.getReturnParamAtIndex(0);
			final IMContact contact = (IMContact)mContacts.iterator().next();
			JHSingleChatActivity.launch(this, contact.getId(), contact.getName());
		}
	}
	

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
	
	}

	@Override
	protected void onInitAttribute(BaseAttribute ba) {
		// TODO Auto-generated method stub
		super.onInitAttribute(ba);
		ba.mActivityLayoutId=R.layout.activity_msglist;
		ba.mTitleTextStringId = R.string.lexin;
	
	}

}
