package com.example.jpushdemo;

import io.jchat.android.R;

import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;


public class MessageConversationActivity extends InstrumentedActivity implements OnClickListener {
    private static final String TAG_IM = "JPush";

	private Button mSendMessageSingle;
	private Button mSendMessageGroup;
	private Button mGetConversationList;
	private Button mGetConversationSingle;
	private Button mGetConversationGroup;
	private Button mDeleteConversationSingle;
	private Button mDeleteConversationGroup;

	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.message_conversation);
		initView();
	}
	
	private void initView(){

		mSendMessageSingle = (Button)findViewById(R.id.sendMessageSingle);
		mSendMessageSingle.setOnClickListener(this);

		mSendMessageGroup = (Button)findViewById(R.id.sendMessageGroup);
		mSendMessageGroup.setOnClickListener(this);
		
		mGetConversationList = (Button)findViewById(R.id.getConversationList);
		mGetConversationList.setOnClickListener(this);
		
		mGetConversationSingle = (Button)findViewById(R.id.getConversationSingle);
		mGetConversationSingle.setOnClickListener(this);
		
		mGetConversationGroup = (Button)findViewById(R.id.getConversationGroup);
		mGetConversationGroup.setOnClickListener(this);

		mDeleteConversationSingle = (Button)findViewById(R.id.deleteConversationSingle);
		mDeleteConversationSingle.setOnClickListener(this);

		mDeleteConversationGroup = (Button)findViewById(R.id.deleteConversationGroup);
		mDeleteConversationGroup.setOnClickListener(this);

	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sendMessageSingle:

			EditText single_targetidEdit = (EditText) findViewById(R.id.sendMessageSingle_targetId);
			EditText single_contentEdit = (EditText) findViewById(R.id.sendMessageSingle_content);
			
			final String mTargetID = single_targetidEdit.getText().toString().trim();
			final String singleMsgContent = single_contentEdit.getText().toString().trim();
			
			final Conversation singleConv = JMessageClient.getSingleConversation(mTargetID);
			if (null != singleConv) {
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
							for(int i =1;i<=100;i++)
							{
							TextContent singleContent = new TextContent(singleMsgContent+i);
							
							final Message singleMsg = singleConv.createSendMessage(singleContent);
							//singleMsg.setOnSendCompleteCallback(basicCallback);
							JMessageClient.sendMessage(singleMsg);
							
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							}
						
					}
				}).start();

				//电量测试：
				/*new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						for(int i = 1;i<=1000;i++)
						{
							JMessageClient.sendMessage(JMessageClient.createSingleTextMessage(mTargetID, String.valueOf(i)));
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}	
					}
				}).start();
				*/
				
			} else {
				Conversation singleConvNew = Conversation.createConversation(ConversationType.single, mTargetID);
				
		       
				if (null == singleConvNew) {					
					Log.e(TAG_IM, "sendMessageSingle_targetId不存在");
					Toast.makeText(MessageConversationActivity.this,"sendMessageSingle_targetId不存在", Toast.LENGTH_SHORT).show();	
				}else {
					TextContent singleContent = new TextContent(singleMsgContent);
		            final Message singleMsg = singleConvNew.createSendMessage(singleContent);
		            singleMsg.setOnSendCompleteCallback(basicCallback);
		            JMessageClient.sendMessage(singleMsg);
				}
			}
            
			break;
		case R.id.sendMessageGroup:

			EditText group_idEdit = (EditText) findViewById(R.id.sendMessageGroup_groupId);
			EditText group_contentEdit = (EditText) findViewById(R.id.sendMessageGroup_content);
			
			String mGroupIDS = group_idEdit.getText().toString().trim();
			long mGroupID = Long.valueOf(mGroupIDS).longValue();
			String groupMsgContent = group_contentEdit.getText().toString().trim();
			
			Conversation groupConv = JMessageClient.getGroupConversation(mGroupID);
			if (null != groupConv) {
				
				
				TextContent groupContent = new TextContent("组消息顺序编号");
		        final Message groupMsg = groupConv.createSendMessage(groupContent);
		        //groupMsg.setOnSendCompleteCallback(basicCallback);
		        JMessageClient.sendMessage(groupMsg);
			
				
			} else {
				Conversation  groupConvNew = Conversation.createConversation(ConversationType.group, mGroupID);
				if (null == groupConvNew) {
					Log.e(TAG_IM, "sendMessageGroup_groupId不存在");
					Toast.makeText(MessageConversationActivity.this,"sendMessageGroup_groupId不存在", Toast.LENGTH_SHORT).show();	
				}else {
					TextContent groupContent = new TextContent(groupMsgContent);
		            final Message groupMsg = groupConvNew.createSendMessage(groupContent);
		            groupMsg.setOnSendCompleteCallback(basicCallback);
		            JMessageClient.sendMessage(groupMsg); 
				}
			}
			break;
		case R.id.getConversationList:
			List<Conversation> conversationList = JMessageClient.getConversationList();
			EditText conversationListEdit = (EditText) findViewById(R.id.getConversationList_text);
			if (null != conversationList) {
				conversationListEdit.setText("会话列表的数量为："+conversationList.size());
				conversationListEdit.setVisibility(android.view.View.VISIBLE);
				Log.d(TAG_IM, "会话列表的数量为："+conversationList.size());
			}else {
				Toast.makeText(MessageConversationActivity.this,"会话列表数据为null", Toast.LENGTH_SHORT).show();
				Log.d(TAG_IM, "会话列表数据为null");
			}
			break;
		case R.id.getConversationSingle:
			EditText usernameEdit = (EditText) findViewById(R.id.getConversation_textsingle);
			Conversation conversationSingle = JMessageClient.getSingleConversation(usernameEdit.getText().toString().trim());
			if (null != conversationSingle) {
				List<Message> messageList = conversationSingle.getAllMessage();
				Toast.makeText(MessageConversationActivity.this,"会话实体得到的message数量为："+messageList.size(), Toast.LENGTH_SHORT).show();
				Log.d(TAG_IM,"会话实体得到的message数量为："+messageList.size());
			}else {
				Toast.makeText(MessageConversationActivity.this,"获取到的会话实体为null", Toast.LENGTH_SHORT).show();
				Log.d(TAG_IM,"获取到的会话实体为null");
			}
			break;
		case R.id.getConversationGroup:
			EditText groupIdEdit = (EditText) findViewById(R.id.getConversation_textgroup);
			Conversation conversationGroup = JMessageClient.getGroupConversation(Long.valueOf(groupIdEdit.getText().toString().trim()).longValue());
			if (null != conversationGroup) {
				List<Message> messageList = conversationGroup.getAllMessage();
				Toast.makeText(MessageConversationActivity.this,"会话实体得到的message数量为："+messageList.size(), Toast.LENGTH_SHORT).show();
				Log.d(TAG_IM,"会话实体得到的message数量为："+messageList.size());
			}else {
				Toast.makeText(MessageConversationActivity.this,"获取到的会话实体为null", Toast.LENGTH_SHORT).show();
				Log.d(TAG_IM,"获取到的会话实体为null");
			}
			break;
		case R.id.deleteConversationSingle:
			EditText delUserEdit = (EditText) findViewById(R.id.deleteConversation_textsingle);
			boolean isDelSingle = JMessageClient.deleteSingleConversation(delUserEdit.getText().toString().trim());
			if (isDelSingle) {
				Toast.makeText(MessageConversationActivity.this,"删除与指定用户的会话(包括本地的聊天记录)成功！", Toast.LENGTH_SHORT).show();
				Log.d(TAG_IM,"删除与指定用户的会话(包括本地的聊天记录)成功！");
			}else {
				Toast.makeText(MessageConversationActivity.this,"删除与指定用户的会话(包括本地的聊天记录)失败！", Toast.LENGTH_SHORT).show();
				Log.d(TAG_IM,"删除与指定用户的会话(包括本地的聊天记录)失败！");
			}
			break;
		case R.id.deleteConversationGroup:
			EditText delGroupEdit = (EditText) findViewById(R.id.deleteConversation_textgroup);	
			boolean isDelGroup = JMessageClient.deleteGroupConversation(Long.valueOf(delGroupEdit.getText().toString().trim()).longValue());
			if (isDelGroup) {
				Toast.makeText(MessageConversationActivity.this,"删除与指定群组的会话(包括本地的聊天记录)成功！", Toast.LENGTH_SHORT).show();
				Log.d(TAG_IM,"删除与指定群组的会话(包括本地的聊天记录)失败！");
			}else {
				Toast.makeText(MessageConversationActivity.this,"删除与指定群组的会话(包括本地的聊天记录)失败！", Toast.LENGTH_SHORT).show();
				Log.d(TAG_IM,"删除与指定群组的会话(包括本地的聊天记录)失败！");
			}
			break;
		}
	}
	
	BasicCallback basicCallback = new BasicCallback() {

		@Override
		public void gotResult(int responseCode, String responseMessage) {
			if (responseCode == 0) {
				Log.d(TAG_IM, "操作成功。");
				Toast.makeText(MessageConversationActivity.this, "操作成功",
						Toast.LENGTH_SHORT).show();
			} else {
				Log.e(TAG_IM, "操作异常：responseCode=" + responseCode
						+ " responseMessage=" + responseMessage);
				Toast.makeText(
						MessageConversationActivity.this,
						"操作异常：responseCode=" + responseCode
								+ "  responseMessage=" + responseMessage,
						Toast.LENGTH_SHORT).show();
			}
		}
	};
			
}

