package com.lolimanzgame.huarongroad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class StartGameActivity extends Activity {

    int set_id = 0;

    private static boolean isExit = false;
    static Handler mExit_Handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mExit_Handler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start_game);

        View mStartGameFrame = findViewById(R.id.id_start_game_frame);
        Button btn1 = findViewById(R.id.id_hdlm);
        Button btn2 = findViewById(R.id.id_ggzj);
        Button btn3 = findViewById(R.id.id_sxbt);
        Button btn4 = findViewById(R.id.id_jzzc);
        Button btn5 = findViewById(R.id.id_xycc);

        final Intent EntryIntent = new Intent(StartGameActivity.this, GameMainActivity.class);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_id = GameConfigurationsClass.HDLM;
                EntryIntent.putExtra("set_id",String.valueOf(set_id));
                finish();
                startActivity(EntryIntent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_id = GameConfigurationsClass.GGZJ;
                EntryIntent.putExtra("set_id",String.valueOf(set_id));
                finish();
                startActivity(EntryIntent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_id = GameConfigurationsClass.SXBT;
                EntryIntent.putExtra("set_id",String.valueOf(set_id));
                finish();
                startActivity(EntryIntent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_id = GameConfigurationsClass.JZZC;
                EntryIntent.putExtra("set_id",String.valueOf(set_id));
                finish();
                startActivity(EntryIntent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_id = GameConfigurationsClass.XYCC;
                EntryIntent.putExtra("set_id",String.valueOf(set_id));
                finish();
                startActivity(EntryIntent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
