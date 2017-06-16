package s2.navigation.com.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import s2.navigation.com.R;
import s2.navigation.com.SoundPoolController;

public class ButtonActivity extends AppCompatActivity {
    private Button btnNavi,btnCall,btnMng,btnInfo;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_button);

        btnNavi = (Button) findViewById(R.id.btn_navi);
        btnCall = (Button) findViewById(R.id.btn_call);
        btnMng = (Button) findViewById(R.id.btn_mng);
        btnInfo = (Button) findViewById(R.id.btn_info);

        btnNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pool.playSound(pool.SOUND_BBOK);
                startActivity(new Intent(ButtonActivity.this,MapsActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_top,R.anim.anim_nomove);

            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pool.playSound(pool.SOUND_BBOK);
                startActivity(new Intent(ButtonActivity.this,CallActivity.class));
                overridePendingTransition(R.anim.anim_open_scale,R.anim.anim_close_scale);
            }
        });

        btnMng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pool.playSound(pool.SOUND_BBOK);
                startActivity(new Intent(ButtonActivity.this,ListActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_bottom,R.anim.anim_nomove);

            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pool.playSound(pool.SOUND_BBOK);
                startActivity(new Intent(ButtonActivity.this,WebViewActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_bottom,R.anim.anim_nomove);
            }
        });
    }
}
