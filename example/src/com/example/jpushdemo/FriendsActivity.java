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
import cn.jpush.im.android.api.callback.GetBlacklistCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

import java.util.ArrayList;
import java.util.List;

import com.test.v171.R;


public class FriendsActivity extends InstrumentedActivity implements OnClickListener {
    private static final String TAG_IM = "JPush";

	private Button mSendInvitation;
	private Button mSendInvitationInBackground;
	private Button mAcceptInvitationInBackground;
	private Button mGetFriendList;
	private Button mDeleteFriend;
	private Button mAddToBlackList;
	private Button mRemoveFromBlackList;
	private Button mUpdateNoteName;
	private Button mFriendBlackList;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.friends);
		initView();
	}
	
	private void initView(){

		mSendInvitation = (Button)findViewById(R.id.sendInvitation);
		mSendInvitation.setOnClickListener(this);

		mSendInvitationInBackground = (Button)findViewById(R.id.sendInvitationInBackground);
		mSendInvitationInBackground.setOnClickListener(this);
		
		mAcceptInvitationInBackground = (Button)findViewById(R.id.acceptInvitationInBackground);
		mAcceptInvitationInBackground.setOnClickListener(this);
		
		mGetFriendList = (Button)findViewById(R.id.getFriendList);
		mGetFriendList.setOnClickListener(this);
		
		mDeleteFriend = (Button)findViewById(R.id.deleteFriend);
		mDeleteFriend.setOnClickListener(this);

		mAddToBlackList = (Button)findViewById(R.id.addToBlackList);
		mAddToBlackList.setOnClickListener(this);

		mRemoveFromBlackList = (Button)findViewById(R.id.removeFromBlackList);
		mRemoveFromBlackList.setOnClickListener(this);

		mUpdateNoteName = (Button)findViewById(R.id.updateNoteName);
		mUpdateNoteName.setOnClickListener(this);
		
		mFriendBlackList = (Button)findViewById(R.id.FriendBlackList);
		mFriendBlackList.setOnClickListener(this);

	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sendInvitation:

			EditText sendTargetIdEdit = (EditText) findViewById(R.id.sendInvitation_targetID);
			
			String sendTargetId = sendTargetIdEdit.getText().toString().trim();

			if (null == sendTargetId || "".equals(sendTargetId)) {
				Log.d(TAG_IM, "targetId 参数没有输入");
				Toast.makeText(FriendsActivity.this,"请输入targetId", Toast.LENGTH_SHORT).show();
			}else {

			}
			
			break;
		case R.id.sendInvitationInBackground:

			EditText sendBackgroundTargetIdEdit = (EditText) findViewById(R.id.sendInvitationInBackground_targetID);
			
			String sendBackgroundTargetId = sendBackgroundTargetIdEdit.getText().toString().trim();
			
			if (null == sendBackgroundTargetId || "".equals(sendBackgroundTargetId)) {
				Log.d(TAG_IM, "targetId 参数没有输入");
				Toast.makeText(FriendsActivity.this,"请输入targetId", Toast.LENGTH_SHORT).show();
			}else {
				
			}
			
			break;
		case R.id.acceptInvitationInBackground:
			EditText acceptInvitationFriendIdEdit = (EditText) findViewById(R.id.acceptInvitationInBackground_friendID);
			
			String acceptInvitationFriendId = acceptInvitationFriendIdEdit.getText().toString().trim();
			
			if (null == acceptInvitationFriendId || "".equals(acceptInvitationFriendId)) {
				Log.d(TAG_IM, "friendId 参数没有输入");
				Toast.makeText(FriendsActivity.this,"请输入friendId", Toast.LENGTH_SHORT).show();
			}else {
				
			}
			
			break;
		case R.id.getFriendList:
			
			break;
		case R.id.deleteFriend:
			
			EditText deleteFriendIdEdit = (EditText) findViewById(R.id.deleteFriend_friendID);
			
			String deleteFriendId = deleteFriendIdEdit.getText().toString().trim();
			
			if (null == deleteFriendId || "".equals(deleteFriendId)) {
				Log.d(TAG_IM, "friendId 参数没有输入");
				Toast.makeText(FriendsActivity.this,"请输入friendId", Toast.LENGTH_SHORT).show();
			}else {
				
			}
			
			break;
		case R.id.addToBlackList:
			List<String> Ladd = new ArrayList<String>();
			// Ladd = null;
			// Ladd = 为空集合;
			
			EditText addToBlackListTargetIdEdit = (EditText) findViewById(R.id.addToBlackList_targetID);			
			String addToBlackListTargetId = addToBlackListTargetIdEdit.getText().toString().trim();
			
			if (null == addToBlackListTargetId || "".equals(addToBlackListTargetId)) {
				Log.d(TAG_IM, "targetId 参数没有输入");
				Toast.makeText(FriendsActivity.this,"请输入targetId", Toast.LENGTH_SHORT).show();				
			} else {
				
				String[] sArray = addToBlackListTargetId.split(",");
				//Set<String> tagSet = new LinkedHashSet<String>();
				for (String sTagItme : sArray) {
					Ladd.add(sTagItme);
				}
				
				
				JMessageClient.addUsersToBlacklist(Ladd, new BasicCallback() {
					
					@Override
					public void gotResult(int arg0, String arg1) {
						// TODO Auto-generated method stub
						Log.d("JPush","添加黑名单返回码=="+arg0+"\n 信息为"+arg1 );
						Toast.makeText(FriendsActivity.this,"添加黑名单返回码=="+arg0+"\n 信息为"+arg1 , Toast.LENGTH_SHORT).show();				
						
					}
				});
			}
			
			break;
		case R.id.removeFromBlackList:
			List<String> L_delete = new ArrayList<String>();
			// L_delete = null
			// L_delete 空集合
			
			EditText removeFromBlackListTargetIdEdit = (EditText) findViewById(R.id.removeFromBlackList_targetID);			
			String removeFromBlackListTargetId = removeFromBlackListTargetIdEdit.getText().toString().trim();
			
			if (null == removeFromBlackListTargetId || "".equals(removeFromBlackListTargetId)) {
				Log.d(TAG_IM, "targetId 参数没有输入");
				Toast.makeText(FriendsActivity.this,"请输入targetId", Toast.LENGTH_SHORT).show();				
			} else {			
				String[] sArray_d = removeFromBlackListTargetId.split(",");
				//Set<String> tagSet = new LinkedHashSet<String>();
				for (String sTagItme : sArray_d) {
					L_delete.add(sTagItme);
				}
				
				//L_delete = null;
				
				JMessageClient.delUsersFromBlacklist(L_delete, new BasicCallback() {
					
					@Override
					public void gotResult(int arg0, String arg1) {
						// TODO Auto-generated method stub
						Log.d("JPush","移除黑名单返回码=="+arg0+"\n 信息为"+arg1 );
						Toast.makeText(FriendsActivity.this,"移除黑名单返回码=="+arg0+"\n 信息为"+arg1 , Toast.LENGTH_SHORT).show();				
						
					}
				});
			}
			
			break;
			
		case R.id.FriendBlackList:
			JMessageClient.getBlacklist(new GetBlacklistCallback() {
				
				@Override
				public void gotResult(int arg0, String arg1, List<UserInfo> arg2) {
					// TODO Auto-generated method stub
					Log.d("JPush","我拉黑的用户，返回码=="+arg0+"\n 信息为"+arg2 );
					Toast.makeText(FriendsActivity.this,"当前用户黑名单列表="+arg2+"返回码=="+arg0+"\n 信息为"+arg1 , Toast.LENGTH_SHORT).show();				
					
				}
			});
			
		case R.id.updateNoteName:
			EditText updateNoteName_targetIdEdit = (EditText) findViewById(R.id.updateNoteName_targetID);
			
			String updateNoteName_targetId = updateNoteName_targetIdEdit.getText().toString().trim();
			
			if (null == updateNoteName_targetId || "".equals(updateNoteName_targetId)) {
				Log.d(TAG_IM, "targetId 参数没有输入");
				Toast.makeText(FriendsActivity.this,"请输入targetId", Toast.LENGTH_SHORT).show();
			}else {
				
			}
			
			break;
		}
	}
			
}

