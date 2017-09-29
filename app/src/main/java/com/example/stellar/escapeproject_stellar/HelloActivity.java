package com.example.stellar.escapeproject_stellar;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class HelloActivity extends AppCompatActivity implements View.OnClickListener{
    private Button play_btn;
    private Button continue_btn;
    private int[] state;
    private boolean hasStarted;

    private static final String TAG = "HelloActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        //隐藏标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hello);

        Intent intent = getIntent();
        if (intent.getIntArrayExtra("state") == null)
        {
            //Log.d(TAG, "intent == null");
            state = new int[]{1,1,0,0,0};
            hasStarted = false;
        }
        else
        {
            state = intent.getIntArrayExtra("state");
            hasStarted = true;
        }

        play_btn = (Button) findViewById(R.id.enterPlayBtn);
        play_btn.setOnClickListener(this);
        continue_btn = (Button) findViewById(R.id.continuePlayBtn);
        continue_btn.setOnClickListener(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.hide();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Log.d(TAG, "HelloActivity hasStarted: " + hasStarted);
        if(id == R.id.enterPlayBtn)
        {
            Log.d(TAG, "HelloActivity onClick: new");
            Intent intent = new Intent(HelloActivity.this, CGActivity.class);
            state = new int[]{1,1,0,0,0};
            hasStarted = true;
            intent.putExtra("state", state);
            Log.d(TAG, "new-state sent from Hello to CG: " + Util.itIntArray(state));
            startActivityForResult(intent, 1);
        }
        else if (id == R.id.continuePlayBtn)
        {
            Log.d(TAG, "HelloActivity onClick: continue");
            if (hasStarted)
            {
                Intent intent = new Intent(HelloActivity.this, MainActivity.class);
                intent.putExtra("state", state);
                Log.d(TAG, "con-state sent from Hello to Main: " + Util.itIntArray(state));
                startActivityForResult(intent, 1);
            }
            else
            {
                Toast.makeText(HelloActivity.this, "You haven't started the game yet!", Toast.LENGTH_SHORT).show();
            }
        }
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case 1:
                if (resultCode == RESULT_OK)
                {
                    state = data.getIntArrayExtra("state");
                    Log.d(TAG, "state got from Pause to Hello: " + Util.itIntArray(state));
                }
                break;
            default:
        }
    }
*/
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
