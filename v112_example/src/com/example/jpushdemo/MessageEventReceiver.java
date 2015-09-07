package com.example.jpushdemo;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;
import cn.jpush.im.android.api.JMessageClient;
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
  //  public String SendFrom = "yk98"; 
    
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
    	
    	Log.d(TAG, "=================收到消息=============");
    	Log.d(TAG,"msg:"+msg);
    	
    	// 自动回复功能
    	
    		
    	
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
            break;
            case eventNotification:
            //处理事件提醒消息
            EventNotificationContent eventNotificationContent = (EventNotificationContent)msg.getContent();
            switch (eventNotificationContent.getEventNotificationType()){
                case group_member_added:
                //群成员加群事件
                break;
                case group_member_removed:
                //群成员被踢事件
                break;
                case group_member_exit:
                //群成员退群事件
                break;
            }
            break;
        }
      }
            
            
            /*String content;
            switch (msg.getContentType()) {
                case image:
                    content = context.getString(R.string.noti_content_type_img);
                    break;
                case voice:
                    content = context.getString(R.string.noti_content_type_voice);
                    break;
                case location:
                    content = context.getString(R.string.noti_content_type_location);
                    break;
                default:
                    content = ((TextContent) msg.getContent()).getText();

            }
            // �����������棬���͹㲥��ChatActivity����UI
            if (ChatActivity.SIGN.equals(conv.getType() + ":"
                    + conv.getTargetId())) {
                Intent intent = new Intent(ExampleApplication.REFRESH_CHATTING_ACTION);
                intent.putExtra("messageID", messageID);
                intent.putExtra("isGroup", mIsGroup);
                intent.putExtra("targetID", conv.getTargetId());
                context.sendBroadcast(intent);
            }
            // ����������棬���͹㲥�����»Ự�б�
            else {
                Intent intent = new Intent(JPushDemoApplication.REFRESH_CONVLIST_ACTION);
                intent.putExtra("targetID", conv.getTargetId());
                Log.i("Receiver", "���ڷ��͸���ConversationList�Ĺ㲥");
                context.sendBroadcast(intent);
//					//����֪ͨ����Ϣ
                NotificationManager manager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification = new Notification(R.drawable.icon, context.getText(R.string.noti_new_message_receive), System.currentTimeMillis());
                long currentTime = System.currentTimeMillis();
                if (currentTime - mLastTime > 5000) {
                    notification.defaults = Notification.DEFAULT_ALL;
                    mLastTime = System.currentTimeMillis();
                }
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                Intent notificationIntent = new Intent(context, ChatActivity.class);
                notificationIntent.setAction(Intent.ACTION_MAIN);
//                    notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                notificationIntent.putExtra("targetID", conv.getTargetId());
                notificationIntent.putExtra("isGroup", mIsGroup);
                notificationIntent.putExtra("fromGroup", false);
                //List�в�������Ự��TargetID�����յ��µĻỰ
                if (!mNotificationList.contains(conv.getTargetId())) {
                    if (count < 5) {
                        if (mNotificationList.size() < 5) {
                            mNotificationList.add(conv.getTargetId());
                            count++;
                            //List�г���5���Ự���󣬼�Notification�������ڻ����5����ʵ���滻��������ֵ�Notification
                        } else {
                            mNotificationList.set(count, conv.getTargetId());
                            count++;
                        }
                        //Notification����5����count��Ϊ0
                    } else if (count >= 5) {
                        count = 0;
                        mNotificationList.set(count, conv.getTargetId());
                        count++;
                    }
                }
                notificationIntent.putExtra("count", mNotificationList.indexOf(conv.getTargetId()));
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                //requestCodeΪconv��TargetID��mNotificationList�е������Դ�����ʶNotification
                PendingIntent contentIntent = PendingIntent.getActivity(context, mNotificationList.indexOf(conv.getTargetId()), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setLatestEventInfo(context, conv.getDisplayName(), content, contentIntent);
                manager.notify(mNotificationList.indexOf(conv.getTargetId()), notification);
            }*/
        

    }

