package com.example.httpdemo;

import java.util.EventListener;

import com.xbcx.core.AndroidEventManager;
import com.xbcx.core.Event;
import com.xbcx.core.EventCode;
import com.xbcx.core.EventManager.OnEventListener;
import com.xbcx.im.RecentChatManager;


import android.app.Activity;
import android.app.PendingIntent;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity implements OnEventListener{
	private static final String EXTRA_JUMPID 		= "jumpid";
	private static final String EXTRA_JUMPNAME 		= "jumpname";
	private static final String EXTRA_JUMPACTIVITY 	= "jumpactivity";
	
	private TextView	mTextViewNumber;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		mTextViewNumber = (TextView)findViewById(R.id.tvNumber);
		
		addTab(MsglistActivity.class, R.drawable.selector_btn_tab_msg);
		addTab(JHSingleChatActivity.class, R.drawable.selector_btn_tab_friend);
		
		
		processIntent(getIntent());
		
		updateUnreadMessageTotalCount();
		
		AndroidEventManager.getInstance().addEventListener(EventCode.UnreadMessageCountChanged, this, false);
		
		AndroidEventManager.getInstance().runEvent(JHEventCode.MainActivityLaunched);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		AndroidEventManager.getInstance().removeEventListener(EventCode.UnreadMessageCountChanged, this);
	}

	public static void launch(Activity activity){
		Intent intent = new Intent(activity, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		activity.startActivity(intent);
	}
	
	public static PendingIntent getPendingIntent(Context context,
			String strJumpId,String strJumpName,String strActivity){
		Intent intent = new Intent(context, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(EXTRA_JUMPID, strJumpId);
		intent.putExtra(EXTRA_JUMPNAME, strJumpName);
		intent.putExtra(EXTRA_JUMPACTIVITY, strActivity);
		return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		processIntent(intent);
	}
	/**
	 * 处理选项卡切换使用
	 * @param intent
	 */
	private void processIntent(Intent intent){
		final String strJumpId = intent.getStringExtra(EXTRA_JUMPID);
		final String strJumpName = intent.getStringExtra(EXTRA_JUMPNAME);
		final String strJumpActivity = intent.getStringExtra(EXTRA_JUMPACTIVITY);
		if(!TextUtils.isEmpty(strJumpId)){
			getTabHost().setCurrentTab(0);
			try{
				Intent in = new Intent(this, Class.forName(strJumpActivity));
				in.putExtra("id", strJumpId);
				in.putExtra("name", strJumpName);
				startActivity(in);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * 添加一个选项卡
	 * @param cls
	 * @param nResId
	 */
	private void addTab(Class<?> cls,int nResId){
		final TabHost tabHost = getTabHost();
		final TabSpec tabSpec = tabHost.newTabSpec(cls.getName());
		
		final ImageView imageView = new ImageView(this);
		imageView.setBackgroundResource(nResId);
		Intent intent = new Intent(this,cls);
		tabSpec.setIndicator(imageView).setContent(intent);
		tabHost.addTab(tabSpec);
	}

	
	
	protected void updateUnreadMessageTotalCount(){
		final int count = RecentChatManager.getInstance().getUnreadMessageTotalCount();
		if(count > 0){
			mTextViewNumber.setVisibility(View.VISIBLE);
			mTextViewNumber.setText(String.valueOf(count));
		}else{
			mTextViewNumber.setVisibility(View.GONE);
		}
	}

	@Override
	public void onEventRunEnd(Event event) {
		// TODO Auto-generated method stub
		final int code = event.getEventCode();
		if(code == EventCode.UnreadMessageCountChanged){
			updateUnreadMessageTotalCount();
		}
	}

}
