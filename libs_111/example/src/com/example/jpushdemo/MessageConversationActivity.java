package com.example.jpushdemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
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
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;

import io.jchat.android.R;


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
			
				Log.d("Conv", "旧接口getTargetID()"+singleConv.getTargetId());
				
				
				
				Log.d("Conv", "旧接口getLatestText()"+singleConv.getLatestText());
				Log.d("Conv", "旧接口getLatestType()"+singleConv.getLatestType());
				Log.d("Conv", "旧接口getLatestMsgDate()"+singleConv.getLastMsgDate());
				Log.d("Conv", "新接口"+singleConv.getLatestMessage());
				
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						
							TextContent singleContent = new TextContent(singleMsgContent);
							
							final Message singleMsg = singleConv.createSendMessage(singleContent);
							
							Log.d(TAG_IM, "会话时间"+singleMsg.getCreateTime());
							
							//singleMsg.setOnSendCompleteCallback(basicCallback);
							Log.d(TAG_IM, "=====send notification======");
							
							singleMsg.setOnSendCompleteCallback(new BasicCallback() {
								
								@Override
								public void gotResult(int arg0, String arg1) {
									// TODO Auto-generated method stub
									Log.d("JPush", "发送消息的返回码=="+arg0+"message=="+arg1);
									
								}
							});
							JMessageClient.sendMessage(singleMsg);
							
							
							
// 							发送自定义消息
							
							/*
							HashMap<String, String>  hashMap = new HashMap<String, String>();
							hashMap.put("key", "value");
							hashMap.put("android", "自定义消息");
							
							Message singlgCusMsg = JMessageClient.createSingleCustomMessage(mTargetID, hashMap);
							Log.d(TAG_IM, "=====send Message（自定义消息）======");
							Log.d("Conver", "singlgCusMsg = "+singlgCusMsg);
							if(null != singlgCusMsg)
							{
							singlgCusMsg.setOnSendCompleteCallback(new BasicCallback() {
								
								@Override
								public void gotResult(int arg0, String arg1) {
									// TODO Auto-generated method stub
									Log.d("JPush", "发送消息的返回码=="+arg0+"message=="+arg1);
									
								}
							});
							JMessageClient.sendMessage(singlgCusMsg); 
							
							}
							*/
							
							CustomContent customContent = new CustomContent();
							
						
				
							
							JMessageClient.sendMessage(singleConv.createSendMessage(customContent));
							
							
							
//							发送过长的文本信息
							// 1333个中文
							
							/*
							final String singleLongMsgContent_4000 = "你我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我我";
							TextContent singleLongContent = new TextContent(singleLongMsgContent_4000);
							final Message singleLongMsg = singleConv.createSendMessage(singleLongContent);
							//singleMsg.setOnSendCompleteCallback(basicCallback);
							Log.d(TAG_IM, "=====send Long Message notification======");
							
							//singleLongMsg.setOnSendCompleteCallback
							singleLongMsg.setOnSendCompleteCallback(new BasicCallback() {
								
								@Override
								public void gotResult(int arg0, String arg1) {
									// TODO Auto-generated method stub
									Log.d("JPush", "发送4k大小消息的返回码=="+arg0+"message=="+arg1);
									
								}
							});
							JMessageClient.sendMessage(singleLongMsg);
							
							*/
						
						
							//= "饿哦也哦也都看过大快朵颐恶化先哭后笑异地忒图书馆进行公开新客户端看过的饿哦也哦也都看过大快朵颐恶化先哭后笑异地忒图书馆进行公开新客户端看过的饿哦也哦也都看过大快朵颐恶化先哭后笑异地忒图书馆进行公开新客户端看过的饿哦也哦也都看过大快朵颐恶化先哭后笑异地忒图书馆进行公开新客户端看过的饿哦也哦也都看过大快朵颐恶化先哭后笑异地忒图书馆进行公开新客户端看过的饿哦也哦也都看过大快朵颐恶化先哭后笑异地忒图书馆进行公开新客户端看过的饿哦也哦也都看过大快朵颐恶化先哭后笑异地忒图书馆进行公开新客户端看过的饿哦也哦也都看过大快朵颐恶化先哭后笑异地忒图书馆进行公开新客户端看过的饿哦也哦也都看过大快朵颐恶化先哭后笑异地忒图书馆进行公开新客户端看过的饿哦也哦也都看过大快朵颐恶化先哭后笑异地忒图书馆进行公开新客户端看过的饿哦也哦也都看过大快朵颐恶化先哭后笑异地一二三";
							/*
							final String singleLongMsgContent = "xiaoxi!";
							TextContent singleLongContent1 = new TextContent(singleLongMsgContent);
							final Message singleLongMsg1 = singleConv.createSendMessage(singleLongContent1);
							//singleMsg.setOnSendCompleteCallback(basicCallback);
				
							singleLongMsg1.setOnSendCompleteCallback(new BasicCallback() {
								
								@Override
								public void gotResult(int arg0, String arg1) {
									// TODO Auto-generated method stub
									Log.d("JPush", "发送消息的返回码=="+arg0+"message=="+arg1);
									
								}
							});
							JMessageClient.sendMessage(singleLongMsg1);
							*/
////                          发送语音
//							try {
//								
//								Log.d(TAG_IM, "=====发送语音=====");
//								
//								VoiceContent voiceContent = new VoiceContent(new File("/sdcard/Music/You Are Beautiful.mp3"), 60);
//								
//								final Message voiceMessage = singleConv.createSendMessage(voiceContent);
//								
//								JMessageClient.sendMessage(voiceMessage);
//							} catch (FileNotFoundException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							
//                          发送图片
							Log.d(TAG_IM, "=====发送图片=====");
							
							//ImageContent imageContent = new ImageContent(new File("/sdcard/Pictures/01.png"));
							/*
							ImageContent.createImageContentAsync(new File("/sdcard/Pictures/01.png"), new ImageContent.CreateImageContentCallback() {
								
								@Override
								public void gotResult(int arg0, String arg1, ImageContent arg2) {
									// TODO Auto-generated method stub
									
									Log.d("myIma","创建本地图片返回码===="+arg0 );
									
									final Message imageMessage = singleConv.createSendMessage(arg2);
									imageMessage.setOnSendCompleteCallback(new BasicCallback() {
										
										@Override
										public void gotResult(int arg0, String arg1) {
											// TODO Auto-generated method stub
											Log.d("myIma","发送图片返回码===="+arg0 );
										}
									});
									JMessageClient.sendMessage(imageMessage);
									
								}
							});
							
							*/
					}
				}).start();
				
				//电量测试：
//				new Thread(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						for(int i = 1;i<=1000;i++)
//						{
//							JMessageClient.sendMessage(JMessageClient.createSingleTextMessage(mTargetID, String.valueOf(i)));
//							try {
//								Thread.sleep(500);
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}	
//					}
//				}).start();
//				
				
				
			} else {
				 Conversation singleConvNew= Conversation.createConversation(ConversationType.single, mTargetID);
				//Conversation singleConvNew = Conversation.createSingleConversation(mTargetID);
		       
				
				if (null == singleConvNew) {					
					Log.e(TAG_IM, "sendMessageSingle_targetId不存在");
					Toast.makeText(MessageConversationActivity.this,"sendMessageSingle_targetId不存在", Toast.LENGTH_SHORT).show();	
				}else {
							
					Log.d("Conv", "旧接口getTargetID()"+singleConvNew.getTargetId());
					
				
					Log.d("Conv", "旧接口getLatestText()"+singleConvNew.getLatestText());
					Log.d("Conv", "旧接口getLatestType()"+singleConvNew.getLatestType());
					Log.d("Conv", "旧接口getLatestMsgDate()"+singleConvNew.getLastMsgDate());
					Log.d("Conv", "新接口"+singleConvNew.getLatestMessage());
					
					
					
					
			
					TextContent singleContent = new TextContent(singleMsgContent);
		            final Message singleMsg = singleConvNew.createSendMessage(singleContent);
		            Log.d("Conver", "singlemsg = "+singleMsg);
		            if(null != singleMsg)
		            {
		            	singleMsg.setOnSendCompleteCallback(basicCallback);
		            	Log.d(TAG_IM, "=====send notification======");
		            	JMessageClient.sendMessage(singleMsg);
		            }
		            
		            CustomContent customContent = new CustomContent();
					customContent.setBooleanValue("boolValue", false);
					customContent.setBooleanExtra("boolExtra", true);
					
					JMessageClient.sendMessage(singleConv.createSendMessage(customContent));
					
		            
//					发送自定义消息
		            /*HashMap<String, String>  hashMap = new HashMap<String, String>();
					hashMap.put("key", "value");
					hashMap.put("chuang", "k");
					
					Message singlgCusMsg = JMessageClient.createSingleCustomMessage(mTargetID, hashMap);
					Log.d(TAG_IM, "=====send Message======");
					JMessageClient.sendMessage(singlgCusMsg); */
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
				
				/*TextContent groupContent = new TextContent("组消息顺序编号");
		        final Message groupMsg = groupConv.createSendMessage(groupContent);
		        groupMsg.setOnSendCompleteCallback(basicCallback);
		        JMessageClient.sendMessage(groupMsg);*/
			
//				发送自定义消息
	            HashMap<String, String>  hashMap = new HashMap<String, String>();
				hashMap.put("key", "value");
				hashMap.put("Group", "JPush");
				
				Message GroupCusMsg = JMessageClient.createGroupCustomMessage(mGroupID, hashMap);
				JMessageClient.sendMessage(GroupCusMsg); 
				
			} else {
				Conversation  groupConvNew = Conversation.createConversation(ConversationType.group, mGroupID);
				if (null == groupConvNew) {
					Log.e(TAG_IM, "sendMessageGroup_groupId不存在");
					Toast.makeText(MessageConversationActivity.this,"sendMessageGroup_groupId不存在", Toast.LENGTH_SHORT).show();	
				}else {
					/*TextContent groupContent = new TextContent(groupMsgContent);
		            final Message groupMsg = groupConvNew.createSendMessage(groupContent);
		            groupMsg.setOnSendCompleteCallback(basicCallback);
		            JMessageClient.sendMessage(groupMsg); */
		            
//					发送自定义消息
		            HashMap<String, String>  hashMap = new HashMap<String, String>();
					hashMap.put("key", "value");
					hashMap.put("Group", "JPush");
					
					Message GroupCusMsg = JMessageClient.createGroupCustomMessage(mGroupID, hashMap);
					JMessageClient.sendMessage(GroupCusMsg); 
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
				Log.d(TAG_IM,"会话实体得到的message数量为："+messageList.size()+"\n"+"会话内容："+messageList);
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

