package s2.navigation.com.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import s2.navigation.com.R;
import s2.navigation.com.SoundPoolController;
import s2.navigation.com.model.RequestToServerDAO;

public class HandingActivity extends AppCompatActivity {

    private TextView textAddr;
    private TextView textAtime;
    private TextView textAcci;
    private RadioGroup radioGroup;
    private RadioButton rbYes;
    private RadioButton rbNo;
    private Button saveBtn;
    private String addr="";
    private String atime="";
    private String accinum="";
    private String status="";
    private SoundPoolController pool;

    @Override
    protected void onStart() {
        super.onStart();
        pool = new SoundPoolController(getApplicationContext());
    }

    @Override
    protected void onStop() {
        super.onStop();
        pool.releasePool();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getFromList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_nomove);
        setContentView(R.layout.activity_handing);
        textAddr = (TextView)findViewById(R.id.address);
        textAtime = (TextView)findViewById(R.id.atime);
        textAcci = (TextView)findViewById(R.id.accinum);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        rbNo = (RadioButton) findViewById(R.id.rb_no);
        rbYes = (RadioButton) findViewById(R.id.rb_yes);
        saveBtn = (Button) findViewById(R.id.save);
        final RequestToServerDAO reqDAO= new RequestToServerDAO();

        getFromList();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_no :
                        status = "occured";
                        break;

                    case R.id.rb_yes :
                        status = "solved";
                        break;
                }// end switch
            }
        });



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pool.playSound(pool.SOUND_BBOK);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        reqDAO.postData(accinum,status);
                    }
                }).start();
//                Toast.makeText(new HandingActivity().getApplicationContext(),"전송되었습니다.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void getFromList(){
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        Log.i("HandlingActivity","bunndle>>>>"+b);
        if(b!=null){
            if(b.getString("addr")!=null) {
                addr= b.getString("addr");
                atime = b.getString("atime");
                accinum= b.getString("accinum");
                status=b.getString("status");
                Log.i("HandlingActivity>>",addr +" "+atime +" " + accinum+" "+status);

                textAddr.setText(addr);
                textAtime.setText(atime);
                textAcci.setText(accinum);
                if (status.equals("occured")){
                    rbNo.setChecked(true);
                }else{
                    rbYes.setChecked(true);
                }
            }//if formula!=null
        }//if b!=null
    }//end getFromList

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.anim_nomove,R.anim.anim_slide_out_right);
    }


}
