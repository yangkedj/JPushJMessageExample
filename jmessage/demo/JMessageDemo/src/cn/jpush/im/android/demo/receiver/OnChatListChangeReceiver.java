package cn.jpush.im.android.demo.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.test.v171.R;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.Conversation;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.Message;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ConversationType;

import cn.jpush.im.android.demo.activity.ChatActivity;
import cn.jpush.im.android.demo.application.JPushDemoApplication;

public class OnChatListChangeReceiver extends BroadcastReceiver{
	
	private boolean mIsGroup = false;
    private static int count = 0;
    public static List<String> mNotificationList = new ArrayList<String>();
    private static long mLastTime = 0;

	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent data) {
		if (data.getAction() == JPushDemoApplication.RECEIVE_ACTION) {
			int messageID = data.getIntExtra(JMessageClient.LOCAL_MSG_ID, 0);
            Conversation conv;
            String targetID = data.getStringExtra(JMessageClient.TARGET_ID);
			if (messageID != 0) {
				if(data.getStringExtra(JMessageClient.CONVERSATION_TYPE).equals(ConversationType.group.toString())){
                    mIsGroup = true;
                    conv = JMessageClient.getConversation(ConversationType.group, targetID);
                    Log.d("OnChatListChangeReceiver", "conv.toString() " + conv.toString());
				}else {
                    conv = JMessageClient.getConversation(ConversationType.single, targetID);
                    Log.d("OnChatListChangeReceiver", "conv.toString() " + conv.toString());
                }
				final Message msg = conv.getMessage(messageID);
				Log.i("收到消息", "msg = "+ msg.toString());
				String content;
				switch(msg.getContentType()){
				case image:
					content = "[图片]";
					break;
				case voice:
					content = "[语音]";
					break;
				case location:
					content = "[位置]";
					break;
					default:
						content = ((TextContent) msg.getContent()).getText();
				
				}
				// 如果在聊天界面，发送广播到ChatActivity更新UI 
				if (ChatActivity.SIGN.equals(conv.getType() + ":"
						+ conv.getTargetId())) {
					Intent intent = new Intent(JPushDemoApplication.REFRESH_CHATTING_ACTION);
					intent.putExtra("messageID", messageID);
                    intent.putExtra("isGroup", mIsGroup);
					intent.putExtra("targetID", conv.getTargetId());
					context.sendBroadcast(intent);
				}
				// 不在聊天界面，发送广播，更新会话列表
				else {
					Intent intent = new Intent(JPushDemoApplication.REFRESH_CONVLIST_ACTION);
					intent.putExtra("targetID", conv.getTargetId());
					Log.i("Receiver","正在发送更新ConversationList的广播");
					context.sendBroadcast(intent);
//					//发送通知栏消息
					NotificationManager manager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
					Notification notification = new Notification(R.drawable.icon, "收到新消息", System.currentTimeMillis());
                    long currentTime = System.currentTimeMillis();
                    if(currentTime - mLastTime > 5000){
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
                    //List中不包含这个会话的TargetID，即收到新的会话
                    if(!mNotificationList.contains(conv.getTargetId()) ){
                        if(count < 5){
                            if(mNotificationList.size() < 5){
                                mNotificationList.add( conv.getTargetId());
                                count++;
                                //List中超过5个会话对象，即Notification数量等于或大于5个，实现替换掉最早出现的Notification
                            }else {
                                mNotificationList.set(count, conv.getTargetId());
                                count++;
                            }
                            //Notification超过5个后，count置为0
                        }else if (count >= 5){
                            count = 0;
                            mNotificationList.set(count, conv.getTargetId());
                            count++;
                        }
                    }
                    notificationIntent.putExtra("count", mNotificationList.indexOf(conv.getTargetId()));
					notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
		                    | Intent.FLAG_ACTIVITY_NEW_TASK);
                    //requestCode为conv的TargetID在mNotificationList中的索引，以此来标识Notification
					PendingIntent contentIntent = PendingIntent.getActivity(context, mNotificationList.indexOf(conv.getTargetId()), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
					notification.setLatestEventInfo(context, conv.getDisplayName(), content, contentIntent);
					manager.notify(mNotificationList.indexOf(conv.getTargetId()), notification);
				}
			}

		}
	}

}
