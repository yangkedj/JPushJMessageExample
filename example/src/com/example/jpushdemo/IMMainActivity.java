package com.example.jpushdemo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.jpush.android.api.InstrumentedActivity;
import com.test.v171.R;


public class IMMainActivity extends InstrumentedActivity implements OnClickListener{
	
	private Button mUser;
	private Button mMessage;
	private Button mGroups;
	private Button mNotification;
	private Button mFrends;
	
	public static boolean isForeground = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.im_main);
		initView();
	}
	
	private void initView(){
		
	    mUser = (Button)findViewById(R.id.user_test);
		mUser.setOnClickListener(this);
		
		mMessage = (Button)findViewById(R.id.message);
		mMessage.setOnClickListener(this);
		
		mGroups = (Button)findViewById(R.id.groups);
		mGroups.setOnClickListener(this);
		
		mNotification = (Button)findViewById(R.id.notification);
		mNotification.setOnClickListener(this);
		
		mFrends = (Button)findViewById(R.id.friends);
		mFrends.setOnClickListener(this);
		
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user_test:
			Intent userIntent = new Intent(IMMainActivity.this, UsertestActivity.class);
			startActivity(userIntent);
			break;
		case R.id.message:
			Intent msgIntent = new Intent(IMMainActivity.this, MessageConversationActivity.class);
			startActivity(msgIntent);
			break;
		case R.id.groups:
			Intent groupsIntent = new Intent(IMMainActivity.this, GroupsActivity.class);
			startActivity(groupsIntent);
			break;
		case R.id.notification:
			Intent notificationIntent = new Intent(IMMainActivity.this, NotificationSettingActivity.class);
			startActivity(notificationIntent);
			break;
		case R.id.friends:
			Intent friendsIntent = new Intent(IMMainActivity.this, FriendsActivity.class);
			startActivity(friendsIntent);
			break;
		}
	}
	
	@Override
	protected void onResume() {
		isForeground = true;
		super.onResume();
	}


	@Override
	protected void onPause() {
		isForeground = false;
		super.onPause();
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	

}