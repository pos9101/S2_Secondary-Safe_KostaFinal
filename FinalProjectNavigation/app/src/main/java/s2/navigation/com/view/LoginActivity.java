package s2.navigation.com.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import s2.navigation.com.R;
import s2.navigation.com.model.RequestToServerDAO;
import s2.navigation.com.model.SignVO;

public class LoginActivity extends AppCompatActivity {


        EditText etId;
        EditText etPassword;
        CheckBox autoLogin;
        SharedPreferences sharePre;
        SharedPreferences.Editor shareEditor;
        Boolean loginChecked=false;
        Button btnSignUp;
        Button btnLogIn;
        boolean result=false;
        Handler mHandler=new Handler();

        RequestToServerDAO reqdao;
        SignVO vo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etId =(EditText)findViewById(R.id.et_id);
        etPassword = (EditText)findViewById(R.id.et_password);
        autoLogin = (CheckBox)findViewById(R.id.cb_auto);
        btnSignUp = (Button)findViewById(R.id.btn_signup);
        btnLogIn = (Button)findViewById(R.id.btn_login);

        sharePre = getSharedPreferences("login",MODE_PRIVATE);
        shareEditor = sharePre.edit();

        reqdao = new RequestToServerDAO();
        vo= new SignVO();
        //set View
        if(sharePre.getBoolean("autoLogin",false)){
           etId.setText(sharePre.getString("id",""));
           etPassword.setText(sharePre.getString("pw",""));
            autoLogin.setChecked(true);
            loginChecked=true;
        }else{
            etId.setText("");
            etPassword.setText("");
            autoLogin.setChecked(false);
            loginChecked=false;
        }


        //--! login check change
        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    loginChecked=true;
                }else{
                    loginChecked=false;
                    shareEditor.clear();
                    shareEditor.commit();
                }
            }
        });//end Oncheck

        /*btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        LoginActivity.this.getApplicationContext(),
                        SignupActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });*/

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vo.setId(etId.getText().toString());
                vo.setPw(etPassword.getText().toString());
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        result=reqdao.loginCheck(vo);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                loginCheck();
                            }
                        });//end Handler
                    }//endl run
                }.start();//end thread;


            }
        });//end btnLogIn

    }//end onCreate
    @Override
    protected void onPause() {
        super.onPause();
        if(loginChecked) {
            String email = etId.getText().toString();
            String password = etPassword.getText().toString();

            shareEditor.putString("id", email);
            shareEditor.putString("pw", password);
            shareEditor.putBoolean("autoLogin",true);
            shareEditor.commit();
        }else{
            shareEditor.clear();
            shareEditor.commit();
        }
    }//end onPause

//    private boolean loginValidation(String id, String password){
//        if(sharePre.getString("id","").equals(id) && sharePre.getString("pw","").equals(password)){
//
//            return true;
//        }else{
//
//            //login failed
//            return false;
//        }
//    }//end loginValidation

    private void loginCheck(){
        if(result==true){
            SharedPreferences sp2 = getSharedPreferences("session",MODE_PRIVATE);
            SharedPreferences.Editor ed2 = sp2.edit();
            ed2.putString("id",etId.getText().toString());
            ed2.commit();

            Intent intent = new Intent(LoginActivity.this,ListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }else{
            new AlertDialog.Builder(LoginActivity.this)
                    .setIconAttribute(android.R.attr.alertDialogIcon)
                    .setTitle("fail Login")
                    .setMessage("failed")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }


}//end class
