package s2.navigation.com;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

/**
 * Created by kwj10 on 2017-06-08.
 */

public class SoundPoolController {
    SoundPool pool;
    public final int SOUND_BBOK = 1;
    public final int SOUND_START = 2;
    public final int SOUND_ALAERT = 3;
    public final int SOUND_NEAR100 = 100;
    public final int SOUND_NEAR300 = 300;

    int bbok,startNavi,near100,near300,accialert;
    boolean soundLoaded = false;

    public SoundPoolController(Context context){
        pool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        bbok = pool.load(context,R.raw.buttonsound,1);
        startNavi = pool.load(context,R.raw.startnavi,1);
        near100 = pool.load(context,R.raw.near100,1);
        near300 = pool.load(context,R.raw.near300,1);
        accialert = pool.load(context,R.raw.inccident,1);

        pool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                Log.i("soundLoad>>","id:"+sampleId+" status:"+status);
                soundLoaded = true;
            }
        });
    }

    public void playSound(int soundID){
        if(soundLoaded){
            switch(soundID){
                case SOUND_BBOK:
                    pool.play(bbok,0.5f,0.5f,0,0,1);
                    break;
                case SOUND_START:
                    pool.play(startNavi,1,1,0,0,1);
                    break;
                case SOUND_ALAERT:
                    pool.play(accialert,1,1,0,0,1);
                    break;
                case SOUND_NEAR100:
                    pool.play(near100,1,1,0,0,1);
                    break;
                case SOUND_NEAR300:
                    pool.play(near300,1,1,0,0,1);
                    break;

            }
        }
    }

    public void releasePool(){
        pool.release();
    }

}
