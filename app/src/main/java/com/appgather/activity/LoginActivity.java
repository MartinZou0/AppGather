package com.appgather.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.appgather.R;
import com.appgather.view.TextWatcherForJudge;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_loginname;
    private EditText et_loginpassword;
    private TextView tv_blank;
    private TextWatcher textWatcherForName;
    private TextWatcher textWatcherForPassword;
    private Button btn_login;
    private Button btn_register;
    private Button btn_canclename;
    private Button btn_canclepassword;

    private boolean isfocuseditname=false;
    private boolean isfocuseditpassword=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
        getSupportActionBar().hide();
        et_loginpassword = (EditText) findViewById(R.id.et_loginpassword);
        et_loginname = (EditText) findViewById(R.id.et_loginname);
        tv_blank = (TextView) findViewById(R.id.tv_blank);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register=(Button)findViewById(R.id.btn_forregister);
        btn_canclename=(Button)findViewById(R.id.btn_canclename);
        btn_canclepassword=(Button)findViewById(R.id.btn_canclepassword);

        btnListen();//按钮事件监听
        editviewFocus();//编辑框焦点事件监听
        inputModeListen();//软键盘弹出监听
        editViewListenInput();//编辑框输入监听


    }

    //按钮监听器
    //
    private void btnListen(){
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_canclename.setOnClickListener(this);
        btn_canclepassword.setOnClickListener(this);
    }
    //
    //输入框焦点监听
    private void editviewFocus() {

        et_loginname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                isfocuseditname = isFocus;
                btn_canclename.setVisibility(View.VISIBLE);
                btn_canclepassword.setVisibility(View.INVISIBLE);

            }
        });
        et_loginpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                isfocuseditpassword = isFocus;
                btn_canclepassword.setVisibility(View.VISIBLE);
                btn_canclename.setVisibility(View.INVISIBLE);
            }
        });
    }

    //
    //监听软键盘弹出，屏幕上移,不遮挡登陆按钮
    private void inputModeListen() {
        et_loginpassword.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            //当键盘弹出隐藏的时候会 调用此方法。
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //获取当前界面可视部分
                LoginActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = LoginActivity.this.getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;
                Log.d("dddd",""+isfocuseditname+"....."+isfocuseditpassword);
                if (heightDifference > 0) {
                    tv_blank.setVisibility(View.GONE);
                } else {
                    tv_blank.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //
    //监听文本框用户名和密码是否有输入
    private void editViewListenInput() {
        textWatcherForName = new TextWatcherForJudge() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0 && et_loginpassword.getText().toString().length() != 0) {
                    btn_login.setEnabled(true);
                } else {
                    btn_login.setEnabled(false);
                }
            }
        };
        et_loginname.addTextChangedListener(textWatcherForName);

        textWatcherForPassword = new TextWatcherForJudge() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0 && et_loginname.getText().toString().length() != 0) {
                    btn_login.setEnabled(true);
                } else {
                    btn_login.setEnabled(false);
                }
            }
        };
        et_loginpassword.addTextChangedListener(textWatcherForPassword);

    }


    /*
    * 监听点击事件   dispatch事件分发
    * */
    /*
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {

        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_forregister:
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);break;
            case R.id.btn_login:
                Intent intent2=new Intent(LoginActivity.this,MainViewActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_canclename:
                et_loginname.setText("");
                et_loginpassword.setText("");
                break;
            case R.id.btn_canclepassword:
                et_loginpassword.setText("");
            default:break;
        }

    }
}
