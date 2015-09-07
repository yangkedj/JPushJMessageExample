package com.example.jpushdemo;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;


public class MessageEventReceiver {

    private static final String TAG = "JPush";

	private static final String TAG_IM = "JPush";
	
	public Context mcontext;

    public static List<String> mNotificationList = new ArrayList<String>();

//	测试自动回复功能
    //public String SendFrom = "llll"; 
    
    public MessageEventReceiver(Context context) {
        JMessageClient.registerEventReceiver(this);
        mcontext = context;
    }

    
    public void onEvent(NotificationClickEvent event)
    {
    	Log.d("JPush", "用户点击了通知栏JMessage消息");
    	Message message = event.getMessage();
    	Intent intent = new Intent();
    	intent.setAction("jpush.testAction");
    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	intent.putExtra("targetID", message.getTargetID());
    	mcontext.startActivity(intent);
    }
    
    
    @SuppressWarnings("incomplete-switch")
	public void onEvent(MessageEvent event) {
    	Message msg = event.getMessage();
    	
    	Log.e(TAG, "=================收到消息=============");
    	Log.d(TAG,"msg:"+msg);
    	
    	/*Log.d(TAG,"消息发送者:"+msg.getFromID());
    	// 自动回复功能
    	
    		Conversation singleConvNew = Conversation.createConversation(ConversationType.single, msg.getFromID());
    		
			if (null == singleConvNew) {					
				Log.e(TAG_IM, "sendMessageSingle_targetId不存在");
			}else {
				TextContent singleContent = new TextContent("113自动回复");
	            final Message singleMsg = singleConvNew.createSendMessage(singleContent);
	            
	            singleMsg.setOnSendCompleteCallback(new BasicCallback() {
					
					@Override
					public void gotResult(int arg0, String arg1) {
						// TODO Auto-generated method stub
						
					}
				});
	            JMessageClient.sendMessage(singleMsg);
			}
			
			Log.d(TAG, "=================最后一条消息的时间============="+singleConvNew.getLastMsgDate());
			Log.d(TAG, "=================最后一条消息的内容============="+singleConvNew.getLatestMessage());
			*/
    	
        switch (msg.getContentType()){
            case text:
            //处理文字消息
            break;
            case image:
            //处理图片消息
            break;
            case voice:
            //处理语音消息
            break;
            case custom:
            //处理自定义消息
            Log.d(TAG_IM, "======接收自定义消息======");
            CustomContent customContent = (CustomContent)msg.getContent();
            
            Log.d(TAG_IM, "boolValues ======"+customContent.getAllBooleanValues()+"boolExtras ======" +customContent.getBooleanExtras());
            Log.d(TAG_IM, "numberValues ======"+customContent.getNumberValue("NumberValues")+ "numberExtras ======="+ customContent.getNumberExtras());
            Log.d(TAG_IM, "stringValues ======"+customContent.getAllStringValues()+ "stringExtras ======="+ customContent.getStringExtras());
            break;
            case eventNotification:
            //处理事件提醒消息
            EventNotificationContent eventNotificationContent = (EventNotificationContent)msg.getContent();
            switch (eventNotificationContent.getEventNotificationType()){
                case group_member_added:
                	Log.d(TAG_IM, "======添加群成员======");
                //群成员加群事件
                break;
                case group_member_removed:
                	Log.d(TAG_IM, "======群主踢人======");
                //群成员被踢事件
                break;
                case group_member_exit:
                	Log.d(TAG_IM, "======有人退了群组======");
                //群成员退群事件
                break;
            }
            break;
        }
        
        
      }
            
            
        

    }

