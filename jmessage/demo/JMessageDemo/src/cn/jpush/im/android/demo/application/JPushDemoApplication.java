package cn.jpush.im.android.demo.application;

import android.app.Application;
import android.util.Log;

import cn.jpush.im.android.api.JMessageClient;

public class JPushDemoApplication extends Application {

	public static final int REQUESTCODE_CONV_LIST = 0;
	public static final int RESULTCODE_CONV_LIST = 2;
	public static final int REQUESTCODE_TAKE_PHOTO = 4;
    public static final int REQUESTCODE_SELECT_PICTURE = 6;
    public static final int RESULTCODE_SELECT_PICTURE = 8;
    public static final int REFRESH_GROUP_NAME = 3000;
    public static final int ADD_GROUP_MEMBER_EVENT = 3001;
    public static final int REMOVE_GROUP_MEMBER_EVENT = 3002;
    public static final int ON_GROUP_EXIT_EVENT = 3003;
	//从服务器收到广播更新消息
	public static String RECEIVE_ACTION = JMessageClient.ACTION_RECEIVE_IM_MESSAGE;
	//从本地收到广播更新聊天界面
	public static String REFRESH_CHATTING_ACTION = "cn.jpush.im.demo.activity.ACTION_RECEIVER_CHATTING_MESSAGE";
	//从本地收到广播更新会话列表界面
	public static String REFRESH_CONVLIST_ACTION = "cn.jpush.im.demo.activity.ACTION_RECEIVE_CONVERSATION_MESSAGE";
	//从本地收到广播更新群成员变动
    public static String ADD_GROUP_MEMBER_ACTION = "cn.jpush.im.demo.activity.ACTION_ADD_GROUP_MEMBER";
    public static String REMOVE_GROUP_MEMBER_ACTION = "cn.jpush.im.demo.activity.ACTION_REMOVE_GROUP_MEMBER";
    public static String UPDATE_GROUP_NAME_ACTION = "cn.jpush.im.demo.activity.ACTION_UPDATE_GROUP_NAME";
	//从本地收到广播（图片）更新会话界面
	public static String REFRESH_CHATTING_ACTION_IMAGE = "refresh_image";
	
	@Override
	public void onCreate() {
		super.onCreate();
        Log.i("JpushDemoApplication", "init");

		JMessageClient.init(getApplicationContext());
        JMessageClient.setNotificationMode(JMessageClient.NOTI_MODE_NO_NOTIFICATION);
	}

}
