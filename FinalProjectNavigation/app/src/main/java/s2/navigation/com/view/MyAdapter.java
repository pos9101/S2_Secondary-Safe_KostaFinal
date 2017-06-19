package s2.navigation.com.view;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import s2.navigation.com.R;
import s2.navigation.com.SoundPoolController;
import s2.navigation.com.model.AccidentVO;

/**
 * Created by Administrator on 2017-03-08.
 */

public class MyAdapter extends BaseAdapter implements View.OnClickListener {


    private AccidentVO vo;
    private Context myContext;
    private TextView txtAcciNum;
    private TextView txtAddr;
    private TextView txtAtime;
    private ArrayList<AccidentVO> voList;
    private SoundPoolController pool;

    public MyAdapter(Context context, ArrayList<AccidentVO> voList) {

        myContext = context;
        this.voList = voList;
        pool = new SoundPoolController(myContext);
    }

    public void printList() {
        for (AccidentVO x : voList) {
            Log.i("MyAdapter>>", "addr: " + x.getAddr() + " num: " + x.getAcciNum() + " atime: " + x.getAtime());
        }


    }

    @Override
    public int getCount() {
        return voList.size();
    }

    @Override
    public AccidentVO getItem(int position) {
        return voList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {

        if (v == null) {
            for (AccidentVO x : voList) {
                Log.i("MyAdapter>>>>>", x.getAddr() + ":" + x.getAtime() + ":" + x.getAcciNum());
            }
            v = ((LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.customlayout, null);
        }
        vo = getItem(position);
        if (v != null) {
            txtAddr = (TextView) v.findViewById(R.id.list_addr);
            txtAtime = (TextView) v.findViewById(R.id.list_atime);
            txtAcciNum = (TextView) v.findViewById(R.id.list_acci);
            txtAddr.setOnClickListener(this);
            txtAtime.setOnClickListener(this);
            txtAcciNum.setOnClickListener(this);


            if (vo != null) {
                txtAddr.setText(vo.getAddr());
                txtAtime.setText(vo.getAtime());
                txtAcciNum.setText(vo.getAcciNum());

                txtAddr.setTag(vo);
                txtAtime.setTag(vo);
                txtAcciNum.setTag(vo);
               /* txtmol.setTag(vo);
                txtform.setTag(vo);*/
            }//if vo not null
        }//if v not null


        return v;
    }//end getView

    @Override
    public void onClick(View v) {
        pool.playSound(pool.SOUND_BBOK);
        AccidentVO clickItem = (AccidentVO) v.getTag();
        Intent intent = new Intent(myContext.getApplicationContext(), HandingActivity.class);
        intent.putExtra("addr", clickItem.getAddr());
        intent.putExtra("atime", clickItem.getAtime());
        intent.putExtra("accinum", clickItem.getAcciNum());
        intent.putExtra("status", clickItem.getStatus());
        intent.setFlags(intent.FLAG_ACTIVITY_NO_HISTORY);
//            myContext.startActivity(intent);
        PendingIntent pendintent = PendingIntent.getActivity(myContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        try {
            pendintent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }


}//end class
