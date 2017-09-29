package com.example.stellar.escapeproject_stellar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by zhangxuan on 2016/12/18.
 */

public class PlayActivity extends Activity implements View.OnClickListener {
    private Button btn_click;
    private Button backBtn;
    private TypeTextView mResultText;
    private ImageView mainWin;
    private static final String TAG = "PlayActivity";
    private int mainImgPath;
    private int clickedRoom;
    private Bitmap bitmap;

    private FiniteStateMachine fsm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play);
        Log.d(TAG, "onCreate: ");
        // FSM init
        Intent intent = getIntent();
        int []state = intent.getIntArrayExtra("state");
        fsm = new FiniteStateMachine();
        fsm.init(state[0], state[1], state[2], state[3], state[4]);

        //img init
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        mainImgPath = intent.getIntExtra("mainImgPath", 0);
        bitmap = BitmapFactory.decodeResource(getResources(), mainImgPath, options);
        //mobile version
        mainWin = (ImageView) findViewById(R.id.playImage);
        //display version

        clickedRoom = intent.getIntExtra("clickedRoom", 0);
        /*
        switch (clickedRoom)
        {
            case 1:
                mainWin = (ImageView) findViewById(R.id.leftUpImg);
                break;
            case 2:
                mainWin = (ImageView) findViewById(R.id.middleUpImg);
                break;
            case 3:
                mainWin = (ImageView) findViewById(R.id.rightUpImg);
                break;
            case 4:
                mainWin = (ImageView) findViewById(R.id.leftDownImg);
                break;
            case 5:
                mainWin = (ImageView) findViewById(R.id.middleDownImg);
                break;
            case 6:
                mainWin = (ImageView) findViewById(R.id.rightDownImg);
                break;
        }
        */
        //mainWin = (ImageView) findViewById(R.id.playImage);
        //Log.d(TAG, "room clicked: " + clickedRoom);
        mainWin.setImageDrawable(new RoundImageDrawable(bitmap));
        //mainWin.getg



        // button and textline init
        //clickedRoom = intent.getIntExtra("clickedRoom", 0);
        btn_click = (Button) findViewById(R.id.startBtn);
        mResultText = ((TypeTextView) findViewById(R.id.contentText ));
        mResultText.setFocusable(false);
        backBtn = (Button) findViewById(R.id.back2MainBtn);
        backBtn.setVisibility(View.VISIBLE);
        if (clickedRoom != state[0]) {
            mResultText.setText(R.string.wrongRoomInfo);
            btn_click.setVisibility(View.INVISIBLE);
        }
        else {
            mResultText.setText(R.string.rightRoomInfo);
            btn_click.setVisibility(View.VISIBLE);
        }

        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=58ef6f8e");// + SpeechConstant.FORCE_LOGIN +"=true");//=584962eb");//585f3ad3

        btn_click.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        mainWin.setOnClickListener(this);
        mainWin.setClickable(false);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.startBtn)
            btnVoice();
        else if(v.getId() == R.id.back2MainBtn)
            backBtnEvent();
        //else if(v.getId() == R.id.playImage)
        //    resetEvent();
    }

    private void btnVoice() {
        RecognizerDialog dialog = new RecognizerDialog(this, null);
        //Log.d(TAG, "btnVoice: " + Boolean.toString(dialog == null));
        dialog.setParameter(SpeechConstant.LANGUAGE, "en_us");
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        mResultText.setText("");

        dialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                printResult(recognizerResult);
            }
            @Override
            public void onError(SpeechError speechError) {
                Log.d(TAG, "listener error");
            }
        });
        dialog.show();
        Toast.makeText(this, "Please Begin Speech ...", Toast.LENGTH_SHORT).show();
    }

    private void printResult(RecognizerResult results) {
        String text = parseIatResult(results.getResultString());
        if(!text.equals(".")) {
            // 自动填写地址
            //mResultText.append(text);

            //update fsm
            String fsmRet = text + fsm.update(text);
            mResultText.start(fsmRet);

            //show action
            int fsmState[] = fsm.getState();
            String key = "" + fsmState[2] + "_" + fsmState[3] + "_" + fsmState[4];
            Log.d("action",key);
            int value[] = MainActivity.searchTable.get(key);

            //update images
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
                bitmap = null;
            }
            System.gc();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            mainImgPath = value[clickedRoom];
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mainImgPath, options);
            mainWin.setImageDrawable(new RoundImageDrawable(bitmap));

            //update voice
            MainActivity.pool.load(this, value[0], 1);

            //if room changed
            if (clickedRoom != fsmState[0]) {
                btn_click.setVisibility(View.INVISIBLE);
            }

            //if bad end 1
            if (fsmState[0] == 2 && fsmState[1] == 6) {
                //mResultText.setText(R.string.BEInfo);
                btn_click.setVisibility(View.INVISIBLE);
                backBtn.setVisibility(View.INVISIBLE);
                mainWin.setClickable(true);
            }

            //if bad end 2
            if (fsmState[0] == 5 && fsmState[1] == 7) {
                //mResultText.setText(R.string.BEInfo);
                btn_click.setVisibility(View.INVISIBLE);
                backBtn.setVisibility(View.INVISIBLE);
                mainWin.setClickable(true);
            }

            //if happy end
            if (fsmState[0] == 5 && fsmState[1] == 11) {
                //mResultText.setText(R.string.BEInfo);
                btn_click.setVisibility(View.INVISIBLE);
                backBtn.setVisibility(View.INVISIBLE);
                mainWin.setClickable(true);
            }
        }
    }

    public static String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }

    private void backBtnEvent() {
        Intent intent = new Intent();
        int fsmState[] = fsm.getState();
        intent.putExtra("state", fsmState);
        Log.d(TAG, "new-state sent from Play to Main: " + Util.itIntArray(fsmState));
        setResult(1, intent);
        finish();
    }

    private void resetEvent() {
        Log.d(TAG, "resetEvent: PlayActivity");
        Intent intent = new Intent();
        int fsmState[] = new int[]{1,1,0,0,0};
        intent.putExtra("state", fsmState);
        setResult(1, intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK )
            backBtnEvent();

        return true;
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
}
