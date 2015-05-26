package com.example.jpushdemo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.CreateGroupCallback;
import cn.jpush.im.android.api.callback.GetGroupIDListCallback;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.callback.GetGroupMembersCallback;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.api.BasicCallback;

import com.test.v171.R;


public class GroupsActivity extends InstrumentedActivity implements OnClickListener {
    private static final String TAG_IM = "JPush";

	private Button mCreateGroup;
	private Button mAddGroupMembers;
	private Button mRemoveGroupMembers;
	private Button mExitGroup;
	private Button mGetGroupIDList;
	private Button mGetGroupInfo;
	private Button mGetGroupMembers;
	private Button mUpdateGroupName;
	private Button mUpdateGroupDescription;

	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.groups);
		initView();
	}
	
	private void initView(){

		mCreateGroup = (Button)findViewById(R.id.createGroup);
		mCreateGroup.setOnClickListener(this);

		mAddGroupMembers = (Button)findViewById(R.id.addGroupMembers);
		mAddGroupMembers.setOnClickListener(this);
		
		mRemoveGroupMembers = (Button)findViewById(R.id.removeGroupMembers);
		mRemoveGroupMembers.setOnClickListener(this);
		
		mExitGroup = (Button)findViewById(R.id.exitGroup);
		mExitGroup.setOnClickListener(this);
		
		mGetGroupIDList = (Button)findViewById(R.id.getGroupIDList);
		mGetGroupIDList.setOnClickListener(this);

		mGetGroupInfo = (Button)findViewById(R.id.getGroupInfoFromServer);
		mGetGroupInfo.setOnClickListener(this);

		mGetGroupMembers = (Button)findViewById(R.id.getGroupMembersFromServer);
		mGetGroupMembers.setOnClickListener(this);

		mUpdateGroupName = (Button)findViewById(R.id.updateGroupName);
		mUpdateGroupName.setOnClickListener(this);

		mUpdateGroupDescription = (Button)findViewById(R.id.updateGroupDescription);
		mUpdateGroupDescription.setOnClickListener(this);

	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.createGroup:

			EditText createGroupNameEdit = (EditText) findViewById(R.id.createGroup_groupName);
			EditText createGroupDescEdit = (EditText) findViewById(R.id.createGroup_groupDesc);
//			EditText createGroupLevelEdit = (EditText) findViewById(R.id.createGroup_groupLevel);
			
			String createGroupName = createGroupNameEdit.getText().toString().trim();
			String createGroupDesc = createGroupDescEdit.getText().toString().trim();
//			String createGroupLevel = createGroupLevelEdit.getText().toString().trim();
			

			Log.e(TAG_IM, "创建群组，groupLevel参数接口实际没用到");
//			Toast.makeText(GroupsActivity.this,"groupLevel参数接口实际没用到", Toast.LENGTH_SHORT).show();	
			
			
			JMessageClient.createGroup(createGroupName, createGroupDesc, new CreateGroupCallback() {
				
				@Override
				public void gotResult(int responseCode,String responseMessage,long groupID) {

					Log.e(TAG_IM, "responseCode:"+responseCode + "  responseMessage: "+responseMessage + "  groupID: "+groupID);
					Toast.makeText(GroupsActivity.this,"responseCode:"+responseCode + "  responseMessage: "+responseMessage + "  groupID: "+groupID, Toast.LENGTH_SHORT).show();	
					
								
				}
			});
			
			
			
			
            
			break;
		case R.id.addGroupMembers:

			EditText addGroupMemberGroupIdEdit = (EditText) findViewById(R.id.addGroupMembers_groupId);
			EditText addGroupMemberUserNameEdit = (EditText) findViewById(R.id.addGroupMembers_username);
			
			String addGroupMemberGroupId = addGroupMemberGroupIdEdit.getText().toString().trim();
			String addGroupMemberUserName = addGroupMemberUserNameEdit.getText().toString().trim();
			
			if (null == addGroupMemberGroupId || "".equals(addGroupMemberGroupId)) {
				Log.d(TAG_IM, "groupId 参数没有输入");
				Toast.makeText(GroupsActivity.this,"请输入groupId", Toast.LENGTH_SHORT).show();
			}else {
				String[] sArrayGroup = addGroupMemberUserName.split(",");
				List<String> userNameList = new ArrayList<String>();
				
				for (String sTagItme : sArrayGroup) {
					
					userNameList.add(sTagItme);
				
				}
				
				JMessageClient.addGroupMembers(Long.valueOf(addGroupMemberGroupId), userNameList, basicCallback);
			}
			
			break;
		case R.id.removeGroupMembers:
			EditText removeGroupMemberGroupIdEdit = (EditText) findViewById(R.id.removeGroupMembers_groupId);
			EditText removeGroupMemberUserNameEdit = (EditText) findViewById(R.id.removeGroupMembers_username);
			
			String removeGroupMemberGroupId = removeGroupMemberGroupIdEdit.getText().toString().trim();
			String removeGroupMemberUserName = removeGroupMemberUserNameEdit.getText().toString().trim();
			
			if (null == removeGroupMemberGroupId || "".equals(removeGroupMemberGroupId)) {
				Log.d(TAG_IM, "groupId 参数没有输入");
				Toast.makeText(GroupsActivity.this,"请输入groupId", Toast.LENGTH_SHORT).show();
			}else {
				List<String> removeUserNameList = new ArrayList<String>();
				removeUserNameList.add(removeGroupMemberUserName);
				
				JMessageClient.removeGroupMembers(Long.valueOf(removeGroupMemberGroupId), removeUserNameList, basicCallback);
			}
			
			break;
		case R.id.exitGroup:
			EditText exitGroupIdEdit = (EditText) findViewById(R.id.exitGroup_groupId);			
			String exitGroupId = exitGroupIdEdit.getText().toString().trim();
			if (null == exitGroupId || "".equals(exitGroupId)) {
				Log.d(TAG_IM, "groupId 参数没有输入");
				Toast.makeText(GroupsActivity.this,"请输入groupId", Toast.LENGTH_SHORT).show();
			}else {
				JMessageClient.exitGroup(Long.valueOf(exitGroupId), basicCallback);
			}
			break;
		case R.id.getGroupIDList:
			
			JMessageClient.getGroupIDList(new GetGroupIDListCallback() {
				
				@Override
				public void gotResult(int responseCode,String responseMessage,List<Long> groupIDList) {

					Log.e(TAG_IM, "responseCode="+responseCode+" responseMessage="+responseMessage);
					Toast.makeText(GroupsActivity.this,"responseCode="+responseCode+"  responseMessage="+responseMessage, Toast.LENGTH_SHORT).show();
					if (responseCode == 0) {
						if (null != groupIDList) {
							TextView groupIDListEdit = (TextView) findViewById(R.id.getGroupIDList_groupId);
							StringBuffer groupidBuffer = new StringBuffer();
							for (Iterator iterator = groupIDList.iterator(); iterator.hasNext();) {
								String gid = iterator.next() + "";
								groupidBuffer.append(gid);
								groupidBuffer.append(",");
							}
							groupIDListEdit.setText(groupidBuffer.toString().substring(0, groupidBuffer.toString().length()-1));
							groupIDListEdit.setVisibility(android.view.View.VISIBLE);
						}
					}
				}
			});
			break;
		case R.id.getGroupInfoFromServer:
			EditText getGroupInfo_groupIdEdit = (EditText) findViewById(R.id.getGroupInfoFromServer_groupId);			
			String getGroupInfo_groupId = getGroupInfo_groupIdEdit.getText().toString().trim();
			
			if (null == getGroupInfo_groupId || "".equals(getGroupInfo_groupId)) {
				Log.d(TAG_IM, "groupId 参数没有输入");
				Toast.makeText(GroupsActivity.this,"请输入groupId", Toast.LENGTH_SHORT).show();				
			} else {
				JMessageClient.getGroupInfo(Long.valueOf(getGroupInfo_groupId), new GetGroupInfoCallback() {
					
					@Override
					public void gotResult(int responseCode,String responseMessage,GroupInfo group) {
						Log.e(TAG_IM, "responseCode="+responseCode+" responseMessage"+responseMessage);
						Toast.makeText(GroupsActivity.this,"responseCode="+responseCode+"  responseMessage"+responseMessage, Toast.LENGTH_SHORT).show();
						if (responseCode == 0) {
							Log.e(TAG_IM, group.toString());
						}
					}
				});
			}
			
			break;
		case R.id.getGroupMembersFromServer:
			EditText getGroupMember_groupIdEdit = (EditText) findViewById(R.id.getGroupMembersFromServer_groupId);	
			String getGroupMember_groupId = getGroupMember_groupIdEdit.getText().toString().trim();
			
			if (null == getGroupMember_groupId || "".equals(getGroupMember_groupId)) {
				Log.d(TAG_IM, "groupId 参数没有输入");
				Toast.makeText(GroupsActivity.this,"请输入groupId", Toast.LENGTH_SHORT).show();	
			} else {
				JMessageClient.getGroupMembers(Long.valueOf(getGroupMember_groupId), new GetGroupMembersCallback() {
					
					@Override
					public void gotResult(int responseCode,String responseMessage,List<String> members) {

						Log.e(TAG_IM, "responseCode="+responseCode+" responseMessage"+responseMessage);
						Toast.makeText(GroupsActivity.this,"responseCode="+responseCode+"  responseMessage"+responseMessage, Toast.LENGTH_SHORT).show();
						if (responseCode == 0) {
							if (null != members) {
								StringBuffer groupidBuffer = new StringBuffer();
								for (Iterator iterator = members.iterator(); iterator.hasNext();) {
									String username = (String) iterator.next();
									groupidBuffer.append(username);
									groupidBuffer.append(",");
								}
								Log.d(TAG_IM, "群组成员数："+members.size());
								Log.d(TAG_IM, groupidBuffer.toString().substring(0, groupidBuffer.toString().length()-1));
							}
						}					
					}
				});
			}
			
			break;
		case R.id.updateGroupName:
			EditText updateGroupName_groupIdEdit = (EditText) findViewById(R.id.updateGroupName_groupID);
			EditText updateGroupName_groupNameEdit = (EditText) findViewById(R.id.updateGroupName_groupName);
			
			String updateGroup_groupId = updateGroupName_groupIdEdit.getText().toString().trim();
			String updateGroup_groupName = updateGroupName_groupNameEdit.getText().toString().trim();
			
			if (null == updateGroup_groupId || "".equals(updateGroup_groupId)) {
				Log.d(TAG_IM, "groupId 参数没有输入");
				Toast.makeText(GroupsActivity.this,"请输入groupId", Toast.LENGTH_SHORT).show();
			}else {
				JMessageClient.updateGroupName(Long.valueOf(updateGroup_groupId), updateGroup_groupName, basicCallback);
			}
			
			break;
		case R.id.updateGroupDescription:
			EditText updateGroupDesc_groupIdEdit = (EditText) findViewById(R.id.updateGroupDescription_groupID);
			EditText updateGroupDesc_groupDescEdit = (EditText) findViewById(R.id.updateGroupDescription_groupDesc);
			
			String updateGroupDesc_groupId = updateGroupDesc_groupIdEdit.getText().toString().trim();
			String updateGroupDesc_groupDesc = updateGroupDesc_groupDescEdit.getText().toString().trim();
			
			if (null == updateGroupDesc_groupId || "".equals(updateGroupDesc_groupId)) {
				Log.d(TAG_IM, "groupId 参数没有输入");
				Toast.makeText(GroupsActivity.this,"请输入groupId", Toast.LENGTH_SHORT).show();
			}else {
				JMessageClient.updateGroupDescription(Long.valueOf(updateGroupDesc_groupId), updateGroupDesc_groupDesc, basicCallback);
			}			
			break;
		}
	}
	
	BasicCallback basicCallback = new BasicCallback() {
		
		@Override
		public void gotResult(int responseCode,String responseMessage) {
			Log.e(TAG_IM, "responseCode="+responseCode+" responseMessage"+responseMessage);
			Toast.makeText(GroupsActivity.this,"responseCode="+responseCode+"  responseMessage"+responseMessage, Toast.LENGTH_SHORT).show();
		}
	};
			
}

