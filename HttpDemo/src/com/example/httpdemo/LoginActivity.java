package com.example.httpdemo;

import com.example.bean.BaseInfo;
import com.xbcx.core.Event;
import com.xbcx.core.ToastManager;
import com.xbcx.core.XApplication;
import com.xbcx.im.ui.XBaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class LoginActivity extends XBaseActivity implements
		View.OnClickListener {

	EditText login_uidEditText;
	EditText login_pwaEditText;
	Button login_loginButton;
	RelativeLayout t;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	protected void initView() {
		login_uidEditText = (EditText) findViewById(R.id.login_uidEditText);
		login_pwaEditText = (EditText) findViewById(R.id.login_pwaEditText);
		login_loginButton = (Button) findViewById(R.id.login_loginButton);

		login_loginButton.setOnClickListener(this);
	}

	@Override
	protected void onInitAttribute(BaseAttribute ba) {
		// TODO Auto-generated method stub
		super.onInitAttribute(ba);

		ba.mTitleTextStringId = R.string.login_and_register;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == login_loginButton) {
			final String user = login_uidEditText.getText().toString();
			final String pwd = login_pwaEditText.getText().toString();
			if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pwd)) {
				return;
			}
			pushEvent(JHEventCode.HTTP_Login, user, pwd);
		}

	}

	@Override
	public void onEventRunEnd(Event event) {
		// TODO Auto-generated method stub
		super.onEventRunEnd(event);
		final int code = event.getEventCode();
		if (code == JHEventCode.HTTP_Login) {
			if (event.isSuccess()) {

				BaseInfo baseInfo=(BaseInfo)event.getReturnParamAtIndex(0);
				TestApp.saveBaseInfo(baseInfo);
				TestApp.login(baseInfo.getIMUser(), baseInfo.getIMPwd());
				ToastManager.getInstance(XApplication.getApplication()).show(
						R.string.ok);
				Intent intent=new Intent(this, MainActivity.class);
				startActivity(intent);

			}
		}
	}

}
