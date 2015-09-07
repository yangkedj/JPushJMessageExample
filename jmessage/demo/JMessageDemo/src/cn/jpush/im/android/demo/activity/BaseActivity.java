package cn.jpush.im.android.demo.activity;

import java.util.List;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import cn.jpush.im.android.api.Conversation;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.ConversationListRefreshListener;
import cn.jpush.im.android.api.callback.GroupMemberChangeCallback;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.demo.application.JPushDemoApplication;

import com.test.v171.R;

/**
 * Created by Ken on 2015/3/13.
 */
public class BaseActivity extends Activity {
    
    private static final String TAG = "BaseActivity";

    private BaseHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mHandler =  new BaseHandler();
        JMessageClient.setConversationListRefreshListener(new ConversationListRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessage(JPushDemoApplication.REFRESH_GROUP_NAME);
            }
        });

        //璁剧疆鐩戝惉缇ゆ垚鍛樺彉鍔ㄧ殑鐩戝惉鍣�
        JMessageClient
                .setGroupMemberChangeListener(new GroupMemberChangeCallback() {
                    @Override
                    public void OnNewGroupMembersAdded(long groupID,
                                                       List<String> memberSet) {
                        Conversation conv = JMessageClient.getConversation(ConversationType.group, groupID);
                        if(null == conv){
                            return;
                        }
                        Log.i(TAG, "onGroupMemberAdded");
                        for (String member : memberSet) {
                            CustomContent content = new CustomContent();
                            content.setValue("content", member + BaseActivity.this.getString(R.string.join_group_toast));
                            Log.i(TAG, member + BaseActivity.this.getString(R.string.join_group_toast));
                            conv.createSendMessage(content);

                            android.os.Message msg = mHandler.obtainMessage();
                            msg.what = JPushDemoApplication.ADD_GROUP_MEMBER_EVENT;
                            Bundle bundle = new Bundle();
                            bundle.putLong("groupID", groupID);
                            msg.setData(bundle);
                            msg.sendToTarget();
                        }
                    }

                    @Override
                    public void OnGroupMemberRemoved(long groupID,
                                                     List<String> memberSet) {
                        Conversation conv = JMessageClient.getConversation(ConversationType.group, groupID);
                        if(null == conv){
                            return;
                        }
                        Log.i(TAG, "onGroupMemberRemoved");
                        for (String member : memberSet) {
                            android.os.Message msg = mHandler.obtainMessage();
                            msg.what = JPushDemoApplication.REMOVE_GROUP_MEMBER_EVENT;
                            Bundle bundle = new Bundle();
                            CustomContent content = new CustomContent();
                            if(member.equals(JMessageClient.getCurrentUser().getUserName())){
                                content.setValue("content", BaseActivity.this.getString(R.string.deleted_by_creator));
                                bundle.putBoolean("deleted", true);
                            }else {
                                content.setValue("content", member + BaseActivity.this.getString(R.string.exit_group_by_creator));
                            }
                            conv.createSendMessage(content);

                            bundle.putLong("groupID", groupID);
                            msg.setData(bundle);
                            msg.sendToTarget();
                        }
                    }

                    @Override
                    public void OnMemberExit(long groupID, List<String> userNameList, boolean isCreator) {
                        Conversation conv = JMessageClient.getConversation(ConversationType.group, groupID);
                        if(conv == null){
                            return;
                        }else {
                            CustomContent content = new CustomContent();
                            if(isCreator){
                                android.os.Message msg = mHandler.obtainMessage();
                                msg.what = JPushDemoApplication.ON_GROUP_EXIT_EVENT;
                                Bundle bundle = new Bundle();
                                content.setValue("content", BaseActivity.this.getString(R.string.delete_group_by_creator));
                                conv.createSendMessage(content);
                                bundle.putBoolean("isCreator", true);
                                bundle.putLong("groupID", groupID);
                                msg.setData(bundle);
                                msg.sendToTarget();
                            }else {
                                for(String userName : userNameList){
                                    android.os.Message msg = mHandler.obtainMessage();
                                    msg.what = JPushDemoApplication.ON_GROUP_EXIT_EVENT;
                                    Bundle bundle = new Bundle();
                                    Log.i(TAG, userName + BaseActivity.this.getString(R.string.exit_group_event));
                                    content.setValue("content", userName + BaseActivity.this.getString(R.string.exit_group_event));
                                    conv.createSendMessage(content);
                                    bundle.putLong("groupID", groupID);
                                    msg.setData(bundle);
                                    msg.sendToTarget();
                                }

                            }
                        }
                    }
                });
    }

//    private class NetworkReceiver extends BroadcastReceiver{
//
//    }

    public class BaseHandler extends Handler {

        @Override
        public void handleMessage(android.os.Message msg) {
            handleMsg(msg);
        }
    };

    public void handleMsg(Message message){};

}
