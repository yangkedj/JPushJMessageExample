package com.example.jpushdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.im.android.api.JMessageClient;

import io.jchat.android.R;


public class NotificationSettingActivity extends InstrumentedActivity implements OnClickListener {
    private static final String TAG_IM = "JPush";

	private Button mSetNotificationMode_default;
	private Button mSetNotificationMode_no_sound;
	private Button mSetNotificationMode_no_vibrate;
	private Button mSetNotificationMode_silence;
	private Button mSetNotificationMode_no_notification;
	private Button mEnterConversaionSigle;
	private Button mEnterConversaionGroup;
	private Button mExitConversation;

	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.notification_setting);
		initView();
	}
	
	private void initView(){

		mSetNotificationMode_default = (Button)findViewById(R.id.setNotificationMode_default);
		mSetNotificationMode_default.setOnClickListener(this);

		mSetNotificationMode_no_sound = (Button)findViewById(R.id.setNotificationMode_no_sound);
		mSetNotificationMode_no_sound.setOnClickListener(this);
		
		mSetNotificationMode_no_vibrate = (Button)findViewById(R.id.setNotificationMode_no_vibrate);
		mSetNotificationMode_no_vibrate.setOnClickListener(this);
		
		mSetNotificationMode_silence = (Button)findViewById(R.id.setNotificationMode_silence);
		mSetNotificationMode_silence.setOnClickListener(this);
		
		mSetNotificationMode_no_notification= (Button)findViewById(R.id.setNotificationMode_no_notification);
		mSetNotificationMode_no_notification.setOnClickListener(this);

		mEnterConversaionSigle = (Button)findViewById(R.id.enterConversaionSigle);
		mEnterConversaionSigle.setOnClickListener(this);

		mEnterConversaionGroup = (Button)findViewById(R.id.enterConversaionGroup);
		mEnterConversaionGroup.setOnClickListener(this);

		mExitConversation = (Button)findViewById(R.id.exitConversation);
		mExitConversation.setOnClickListener(this);

	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setNotificationMode_default:
			JMessageClient.setNotificationMode(JMessageClient.NOTI_MODE_DEFAULT);   
			Toast.makeText(NotificationSettingActivity.this,"setNotificationMode_default", Toast.LENGTH_SHORT).show();	
			break;
		case R.id.setNotificationMode_no_sound:
			JMessageClient.setNotificationMode(JMessageClient.NOTI_MODE_NO_SOUND);
			Toast.makeText(NotificationSettingActivity.this,"setNotificationMode_no_sound", Toast.LENGTH_SHORT).show();
			break;
		case R.id.setNotificationMode_no_vibrate:
			JMessageClient.setNotificationMode(JMessageClient.NOTI_MODE_NO_VIBRATE);
			Toast.makeText(NotificationSettingActivity.this,"setNotificationMode_no_vibrate", Toast.LENGTH_SHORT).show();
			break;
		case R.id.setNotificationMode_silence:
			JMessageClient.setNotificationMode(JMessageClient.NOTI_MODE_SILENCE);
			Toast.makeText(NotificationSettingActivity.this,"setNotificationMode_silence", Toast.LENGTH_SHORT).show();
			break;
		case R.id.setNotificationMode_no_notification:
			JMessageClient.setNotificationMode(JMessageClient.NOTI_MODE_NO_NOTIFICATION);
			Toast.makeText(NotificationSettingActivity.this,"setNotificationMode_no_notification", Toast.LENGTH_SHORT).show();
			break;
		case R.id.enterConversaionSigle:
			EditText enterConversaion_targetIdEdit = (EditText) findViewById(R.id.enterConversaion_targetId);			
			String enterConversaion_targetId = enterConversaion_targetIdEdit.getText().toString().trim();
			
			if (null == enterConversaion_targetId || "".equals(enterConversaion_targetId)) {
				Log.d(TAG_IM, "targetId 参数没有输入");
				Toast.makeText(NotificationSettingActivity.this,"请输入targetId", Toast.LENGTH_SHORT).show();				
			} else {
				JMessageClient.enterSingleConversaion(enterConversaion_targetId);
			}
			
			break;
		case R.id.enterConversaionGroup:
			EditText enterConversaion_groupIdEdit = (EditText) findViewById(R.id.enterConversaion_GroupId);			
			String enterConversaion_groupId = enterConversaion_groupIdEdit.getText().toString().trim();
			
			if (null == enterConversaion_groupId || "".equals(enterConversaion_groupId)) {
				Log.d(TAG_IM, "groupId 参数没有输入");
				Toast.makeText(NotificationSettingActivity.this,"请输入groupId", Toast.LENGTH_SHORT).show();				
			} else {
				JMessageClient.enterGroupConversation(Long.valueOf(enterConversaion_groupId));
			}
			
			break;
		case R.id.exitConversation:
			JMessageClient.exitConversaion();
			break;
		}
	}
	
}

