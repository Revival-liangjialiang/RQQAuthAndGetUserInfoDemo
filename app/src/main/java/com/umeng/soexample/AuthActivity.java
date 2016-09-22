package com.umeng.soexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class AuthActivity extends AppCompatActivity {
    TextView mAuth_TV;
    Button mAuth_bt,mDeleteAuth_bt;
    private UMShareAPI mShareAPI = null;
    Map<String,String> map = new HashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity_layout);
        initView();
    }

    private void initView() {
        mShareAPI = UMShareAPI.get( this );
        mAuth_bt = (Button) findViewById(R.id.mDeleteAuth_bt);
        mDeleteAuth_bt = (Button) findViewById(R.id.mDeleteAuth_bt);
        mAuth_TV = (TextView) findViewById(R.id.mAuth_tv);
    }
    //获得授权
    public  void onClickAuth(View view){
        Log.d("ok","授权开始！");
        mShareAPI.doOauthVerify(AuthActivity.this, SHARE_MEDIA.QQ, umAuthListener);
    }


    //删除授权
    public void onClickDeleteAuth(View view){
        mShareAPI.deleteOauth(AuthActivity.this, SHARE_MEDIA.QQ, umdelAuthListener);
    }


    //获取用户信息
    public void onClickUserInfo(View view){
        mShareAPI.getPlatformInfo(this, SHARE_MEDIA.QQ, umUserInfoListener);
    }

    //授权监听
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
            Log.d("user info","user info:"+data.toString());
            Log.d("ok","登陆成功！");
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
            Log.d("ok","登陆失败！");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
            Log.d("ok","登陆取消！");
        }
    };

    //删除授权监听
    private UMAuthListener umdelAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "delete Authorize succeed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "delete Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "delete Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    //获取用户信息监听
    private UMAuthListener umUserInfoListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            if (data!=null){
                //获取用户数据
                String userStr = data.toString();
                userStr = userStr.replace("{","");
                userStr = userStr.replace("}","");
                userStr = userStr.replace(" ","");
                String [] mUserInfoArray = userStr.split(",");
                for(String info:mUserInfoArray){
                    String[] str = info.split("=");
                   if(str.length == 2) {
                       map.put(str[0], str[1]);
                   }
                   }
                mAuth_TV.setText("用户名 = "+map.get("screen_name")+"\n"+"性别 = "+map.get("gender")+"\n"+"头像地址 = "+map.get("profile_image_url"));

            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "get fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "get cancel", Toast.LENGTH_SHORT).show();
        }
    };
    //注意需要重新此方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }
}
