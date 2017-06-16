package s2.navigation.com.view;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;


import s2.navigation.com.R;
import s2.navigation.com.model.AccidentVO;
import s2.navigation.com.model.RequestToServerDAO;


public class ListActivity extends AppCompatActivity {
    private ListView listView =null;
    private RequestToServerDAO reqDAO = new RequestToServerDAO();
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        new ListAsync().execute();
    }
    private void addList(ArrayList<AccidentVO> tempList){
        ArrayList<AccidentVO> voList = new ArrayList<>();
        Iterator<AccidentVO> iterator =tempList.iterator();
        while(iterator.hasNext()){
            AccidentVO tempVO = iterator.next();
            iterator.remove();
            String tempAddr = tempVO.convertAddr(tempVO.getLatitude(),
                    tempVO.getLongitude(),getApplicationContext());
            tempVO.setAddr(tempAddr);
            Log.i("atime>>", tempVO.getAtime());
//                String[] splitAtime = tempVO.getAtime().split("/");
//                String[] sd = splitAtime[0].split(".");
//                for (String x :sd) {Log.i("sd>>",x);}
//                String[] st = splitAtime[1].split(":");
//                for (String x :st) {Log.i("st>>",x);}
//                tempVO.setAtime(sd[0]+"년 "+sd[1]+"월 "+sd[2]+"일 "+st[0]+"시 "+st[1]+"분");
            voList.add(tempVO);
        }

        final MyAdapter myAdapter = new MyAdapter(ListActivity.this.getApplicationContext(),voList);
        listView = (ListView)findViewById(R.id.accilist);
        listView.setAdapter(myAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        myAdapter.printList();
    }

    private class ListAsync extends AsyncTask<String,String,ArrayList<AccidentVO>> { //사고지역을 네트워크로 가져와 마커로 뿌려주는 Async
        @Override
        protected void onPreExecute() { //실행 전처리
            super.onPreExecute();
        }

        @Override
        protected ArrayList<AccidentVO> doInBackground(String... params) { //실행
            ArrayList<AccidentVO> tempList = new ArrayList<>();
            try {
                tempList = reqDAO.getAccidentJson(); //네트워크에서 json을 가져와 리스트에  담음
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return tempList;
        }

        @Override
        protected void onPostExecute(ArrayList<AccidentVO> tempList) { //실행 후처리
            super.onPostExecute(tempList);
            addList(tempList); //핸들러를 실행
        }
    }//end class AddMarkAlertAsync

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_nomove,R.anim.anim_slide_out_bottom);
    }
}
