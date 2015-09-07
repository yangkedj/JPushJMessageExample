package com.example.jpushdemo;

import io.jchat.android.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.model.UserInfo.Gender;
import cn.jpush.im.api.BasicCallback;

public class UsertestActivity extends InstrumentedActivity implements
		OnClickListener {
	private static final String TAG_IM = "JPush";

	private Button mImRegister;
	private Button mImLogin;
	private Button mImLogout;
	private Button mGetCurrnetUser;
	private Button mGetUserInfor;
	private Button mUpdateUserPassword;
	private Button mUpdateUserNickName;
	private Button mUpdateUserBirthday;
	private Button mUpdateUserSignature;
	private Button mUpdateUserGender;
	private Button mUpdateUserRegion;
	private Button mUpdateUserAvatar;
	
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.register_login_profile);
		initView();
	}

	private void initView() {

		mImRegister = (Button) findViewById(R.id.register);
		mImRegister.setOnClickListener(this);

		mImLogin = (Button) findViewById(R.id.imlogin);
		mImLogin.setOnClickListener(this);

		mImLogout = (Button) findViewById(R.id.imlogout);
		mImLogout.setOnClickListener(this);

		mGetCurrnetUser = (Button) findViewById(R.id.getCurrentUser);
		mGetCurrnetUser.setOnClickListener(this);

		mGetUserInfor = (Button) findViewById(R.id.getUserInfo);
		mGetUserInfor.setOnClickListener(this);

		mUpdateUserPassword = (Button) findViewById(R.id.updateUserPassword);
		mUpdateUserPassword.setOnClickListener(this);

		mUpdateUserNickName = (Button) findViewById(R.id.updateUserNickName);
		mUpdateUserNickName.setOnClickListener(this);

		mUpdateUserBirthday = (Button) findViewById(R.id.updateUserBirthday);
		mUpdateUserBirthday.setOnClickListener(this);

		mUpdateUserSignature = (Button) findViewById(R.id.updateUserSignature);
		mUpdateUserSignature.setOnClickListener(this);

		mUpdateUserGender = (Button) findViewById(R.id.updateUserGender);
		mUpdateUserGender.setOnClickListener(this);

		mUpdateUserRegion = (Button) findViewById(R.id.updateUserRegion);
		mUpdateUserRegion.setOnClickListener(this);

		mUpdateUserAvatar = (Button) findViewById(R.id.updateUserAvatar);
		mUpdateUserAvatar.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register:
			EditText regUserNameEdit = (EditText) findViewById(R.id.register_username);
			EditText regUserPwdEdit = (EditText) findViewById(R.id.register_password);
			String regUserName = regUserNameEdit.getText().toString().trim();
			String regUserPwd = regUserPwdEdit.getText().toString().trim();
			
			
			
			JMessageClient.register(regUserName, regUserPwd, basicCallback);
			break;
		case R.id.imlogin:

			EditText loginUserNameEdit = (EditText) findViewById(R.id.login_username);
			EditText loginUserPwdEdit = (EditText) findViewById(R.id.login_password);
			String loginUserName = loginUserNameEdit.getText().toString()
					.trim();
			String loginUserPwd = loginUserPwdEdit.getText().toString().trim();

			for (int i =1;i<=20;i++)
			{
				JMessageClient.login(loginUserName, loginUserPwd, basicCallback);
				Log.e("Login", "Login "+"次数:"+i);
			}
			break;
		case R.id.imlogout:
			JMessageClient.logout();
			break;
		case R.id.getCurrentUser:
			UserInfo userInfo = JMessageClient.getMyInfo();
			String userinfoString = getUserinfo(userInfo);
			EditText currentUser_textEdit = (EditText) findViewById(R.id.getCurrentUser_text);
			currentUser_textEdit.setText(userinfoString);
			currentUser_textEdit.setVisibility(android.view.View.VISIBLE);
			break;
		case R.id.getUserInfo:

			EditText userNameEdit = (EditText) findViewById(R.id.getUserInfo_username);
			String userName = userNameEdit.getText().toString().trim();

			JMessageClient.getUserInfo(userName, new GetUserInfoCallback() {

				@Override
				public void gotResult(int responseCode, String responseMessage,
						UserInfo userInfo) {

					if (responseCode == 0) {
						Log.d(TAG_IM, "获取用户信息成功：");
						getUserinfo(userInfo);
					} else {
						Toast.makeText(
								UsertestActivity.this,
								"获取用户信息失败：responseCode=" + responseCode
										+ "  responseMessage=" + responseMessage,
								Toast.LENGTH_SHORT).show();
						Log.e(TAG_IM, "获取用户信息失败，responseCode：" + responseCode
								+ "，responseMessage=" + responseMessage);
					}
				}
			});
			break;
		case R.id.updateUserPassword:

			EditText oldPwdEdit = (EditText) findViewById(R.id.updateUserPassword_oldPassword);
			String oldPwd = oldPwdEdit.getText().toString().trim();

			EditText newPwdEdit = (EditText) findViewById(R.id.updateUserPassword_newPassword);
			String newPwd = newPwdEdit.getText().toString().trim();

			/*for(int i =1;i<=128;i++)
				oldPwd += "1";*/
			
			
			JMessageClient.updateUserPassword(oldPwd, newPwd, basicCallback);
			break;
		case R.id.updateUserNickName:

			EditText nickNameEdit = (EditText) findViewById(R.id.updateUserNickName_nickname);
			String nickName = nickNameEdit.getText().toString().trim();
			UserInfo usernick = JMessageClient.getMyInfo();
			usernick.setNickname(nickName);
			JMessageClient.updateMyInfo(UserInfo.Field.nickname, usernick, basicCallback);
			break;
		case R.id.updateUserBirthday:

			EditText birthdayEdit = (EditText) findViewById(R.id.updateUserBirthday_birthday);
			String birthday = birthdayEdit.getText().toString().trim();
			long bir = System.currentTimeMillis();
			UserInfo userbirth = JMessageClient.getMyInfo();
			Log.d(TAG_IM, "修改个人的生日时间为："+bir);
			userbirth.setBirthday(bir);
			JMessageClient.updateMyInfo(UserInfo.Field.birthday, userbirth, basicCallback);
			break;
		case R.id.updateUserSignature:

			EditText signatureEdit = (EditText) findViewById(R.id.updateUserSignature_signature);
			String signature = signatureEdit.getText().toString().trim();

			/*for (int i=1 ;i<=249;i++)
				signature = signature+"1";*/
			
			UserInfo usersign = JMessageClient.getMyInfo();
			usersign.setSignature(signature);
			JMessageClient.updateMyInfo(UserInfo.Field.signature, usersign, basicCallback);
			break;
		case R.id.updateUserGender:

			EditText genderEdit = (EditText) findViewById(R.id.updateUserGender_gender);
			String gender = genderEdit.getText().toString().trim();
			UserInfo usergender = JMessageClient.getMyInfo();
			if (null == gender || "".equals(gender)) {
				
				usergender.setGender(Gender.unknown);
				JMessageClient.updateMyInfo(UserInfo.Field.gender, usergender,basicCallback);
			} else if (gender.equals("男")) {
				usergender.setGender(Gender.male);
				JMessageClient.updateMyInfo(UserInfo.Field.gender, usergender,basicCallback);
			} else if (gender.equals("女")) {
				usergender.setGender(Gender.female);
				JMessageClient.updateMyInfo(UserInfo.Field.gender, usergender,basicCallback);
			} else if (gender.equals("未知")) {
				usergender.setGender(Gender.unknown);
				JMessageClient.updateMyInfo(UserInfo.Field.gender, usergender,basicCallback);
			} else {
				Log.e(TAG_IM, "传递信息不符合要求，放弃操作");
				Toast.makeText(UsertestActivity.this, "传递信息不符合要求，放弃操作",
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.updateUserRegion:

			EditText regionEdit = (EditText) findViewById(R.id.updateUserRegion_region);
			String region = regionEdit.getText().toString().trim();
			
			UserInfo userregion = JMessageClient.getMyInfo();
			userregion.setRegion(region);
			JMessageClient.updateMyInfo(UserInfo.Field.region, userregion,basicCallback);
			break;
		case R.id.updateUserAvatar:

			EditText avatarEdit = (EditText) findViewById(R.id.updateUserAvatar_avatar);
			String avatar = avatarEdit.getText().toString().trim();

			/*try {
				URL imageURl = new URL(avatar);
				URLConnection con = imageURl.openConnection();
				con.connect();
				InputStream in = con.getInputStream();
				Bitmap bitmap = BitmapFactory.decodeStream(in);
				String tempPath = saveBitmapToLocal(bitmap);
				JMessageClient.updateUserAvatar(new File(tempPath),
						basicCallback);
			} catch (MalformedURLException e) {
				Log.e(TAG_IM, "操作异常：" + e);
			} catch (IOException e) {
				Log.e(TAG_IM, "操作异常：" + e);
			}*/

			JMessageClient.updateUserAvatar(new File("/sdcard/JPushDemo/pictures/2015_0401_015754.jpg"), new BasicCallback() {
				
				@Override
				public void gotResult(int arg0, String arg1) {
					// TODO Auto-generated method stub
					if ( arg0 == 0)
					{
						Log.i(TAG_IM, "头像更新！");
					}
				}
			});
			
			break;

		}
	}

	private String getUserinfo(UserInfo userInfo) {

		StringBuffer buffer = new StringBuffer();
		if (null != userInfo) {
			buffer.append("getAddress:").append(userInfo.getAddress());
			buffer.append(",");
			buffer.append("getAvatar:").append(userInfo.getAvatar());
			buffer.append(",");
			buffer.append("getAvatarMediaID:").append(
					userInfo.getAvatarMediaID());
			buffer.append(",");
			buffer.append("getBirthday:").append(userInfo.getBirthday());
			buffer.append(",");
			buffer.append("getBlacklist:").append(userInfo.getBlacklist());
			buffer.append(",");
			buffer.append("getGender:").append(userInfo.getGender());
			buffer.append(",");
			buffer.append("getGenderString:")
					.append(userInfo.getGenderString());
			buffer.append(",");
			buffer.append("getNickname:").append(userInfo.getNickname());
			buffer.append(",");
			buffer.append("getNotename:").append(userInfo.getNotename());
			buffer.append(",");
			buffer.append("getNoteText:").append(userInfo.getNoteText());
			buffer.append(",");
			buffer.append("getRegion:").append(userInfo.getRegion());
			buffer.append(",");
			buffer.append("getSignature:").append(userInfo.getSignature());
			buffer.append(",");
			buffer.append("getStar:").append(userInfo.getStar());
			buffer.append(",");
			buffer.append("getUserID:").append(userInfo.getUserID());
			buffer.append(",");
			buffer.append("getUserName:").append(userInfo.getUserName());
			buffer.append(",");
			//buffer.append("getUserPwd:").append(userInfo.getu);

			Log.d(TAG_IM, buffer.toString());
			Toast.makeText(UsertestActivity.this, "用户信息：" + buffer.toString(),
					Toast.LENGTH_SHORT).show();

		} else {
			Log.e(TAG_IM, "用户信息为空");
			Toast.makeText(UsertestActivity.this, "用户信息为空", Toast.LENGTH_SHORT)
					.show();
		}
		return buffer.toString();
	}

	BasicCallback basicCallback = new BasicCallback() {

		@Override
		public void gotResult(int responseCode, String responseMessage) {
			
			//test
			Log.e("Login", "Login "+"结果:"+responseCode+"==="+responseMessage);
			
			if (responseCode == 0) {
				Log.d(TAG_IM, "操作成功。");
				Toast.makeText(UsertestActivity.this, "操作成功",
						Toast.LENGTH_SHORT).show();
			} else {
				Log.e(TAG_IM, "操作异常：responseCode=" + responseCode
						+ " responseMessage=" + responseMessage);
				Toast.makeText(
						UsertestActivity.this,
						"操作异常：responseCode=" + responseCode
								+ "  responseMessage=" + responseMessage,
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	public static String saveBitmapToLocal(Bitmap bitmap) {
		if (null == bitmap) {
			return null;
		}
		String filePath;
		FileOutputStream fileOutput = null;
		File imgFile = null;
		try {
			String dir = "sdcard/JPushDemo/pictures/";
			File desDir = new File(dir);
			if (!desDir.exists()) {
				desDir.mkdirs();
			}
			// 使用随机数使得发送的图片的缩略图文件名不相同
			imgFile = new File(desDir.getAbsoluteFile() + "/"
					+ String.valueOf(bitmap.hashCode() + Math.random()));
			imgFile.createNewFile();
			fileOutput = new FileOutputStream(imgFile);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutput);
			fileOutput.flush();
			filePath = imgFile.getAbsolutePath();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			filePath = null;
		} catch (IOException e) {
			e.printStackTrace();
			filePath = null;
		} finally {
			if (null != fileOutput) {
				try {
					fileOutput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return filePath;
	}

}
