package com.lolimanzgame.huarongroad;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import com.lolimanzgame.huarongroad.GameConfigurationsClass.Block;
import com.lolimanzgame.huarongroad.GameConfigurationsClass.Direction;
import static com.lolimanzgame.huarongroad.GameConfigurationsClass.blkShape;
import static com.lolimanzgame.huarongroad.GameConfigurationsClass.score_resource;

import static android.view.ViewGroup.*;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameMainActivity extends Activity
{
    private MediaPlayer mPlayer;
    private SoundPool mSound;
    private HashMap<Integer, Integer> mSoundPoolMap;

    private int winWidth;

    private ImageView[] mScore = new ImageView[4];
    private int score = 0;
    private ImageView[] blkViews = new ImageView[11];
    private Block blocks[] = new Block[11];

    private int set_id;
    private int[][] layout = new int[11][3];
    private int[][] move_able = new int[7][6];

    GestureDetector mGestureDetector;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            startActivity(new Intent(GameMainActivity.this, StartGameActivity.class));
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_main);

        mGestureDetector = new GestureDetector(this, new MyOnGestureListener());

        View mGameMainFrame = findViewById(R.id.id_game_main_frame);

        WindowManager wm = this.getWindowManager();
        winWidth = wm.getDefaultDisplay().getWidth();

        Intent intent = getIntent();
        set_id = Integer.valueOf(intent.getExtras().getString("set_id"));

        //initialize the all image views
        for (int i = GameConfigurationsClass.CAOCAO; i<=GameConfigurationsClass.ZU_4; i++) {
            blkViews[i] = findViewById(GameConfigurationsClass.block_layout_id_table[i]);
        }

        //initialize the score board
        findViewById(R.id.id_score).bringToFront();
        mScore[0] = findViewById(R.id.id_score_thousand_place);
        mScore[1] = findViewById(R.id.id_score_hundred_place);
        mScore[2] = findViewById(R.id.id_score_decade_place);
        mScore[3] = findViewById(R.id.id_score_unit_place);
        mScore[3].setImageResource(R.mipmap.n0);

        //Initialize the main game animation
        Button mReplayButton = findViewById(R.id.id_button_replay);
        Button mBackButton = findViewById(R.id.id_button_back);

        //initialize layout
        initialize_game_layout(set_id);

        //initialize sound player
        InitSounds();

        //set the touch listener
        mGameMainFrame.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                mGestureDetector.onTouchEvent(event);

                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("action down...","action down...");
                        break;

                    case MotionEvent.ACTION_MOVE:
                        Log.d("action move...","action move...");
                        break;

                    case MotionEvent.ACTION_UP:
                        Log.d("action up...","action up...");
                        break;
                }
                return true;
            }
        });

        mReplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();
                Intent replayIntent = new Intent(GameMainActivity.this, GameMainActivity.class);
                replayIntent.putExtra("set_id", String.valueOf(set_id));
                startActivity(replayIntent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();
                startActivity(new Intent(GameMainActivity.this, StartGameActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });
    }

    //initialize the game plate
    private void initialize_game_layout(int set_id)
    {
        ImageView gamePlate = findViewById(R.id.id_game_bg);
        LayoutParams params = gamePlate.getLayoutParams();
        params.width =winWidth;
        params.height= (int) (winWidth*10.6/8.6);
        gamePlate.setLayoutParams(params);

        //initialize the move-able steps.
        for (int row=0;row<7;row++) {
            for (int col=0;col<6;col++){
                move_able[row][col] = 0;
                if (row==0||row==6||col==0||col==5){
                    move_able[row][col] = -1;
                }
            }
        }

        //set id
        for (int i=0;i<11;i++){
            for (int j=0;j<3;j++) {
                layout[i][j] = GameConfigurationsClass.setTable[set_id][i][j];
            }
        }

        //initialize all blocks objects
        for (int blk_id = GameConfigurationsClass.CAOCAO; blk_id <= GameConfigurationsClass.ZU_4; blk_id++) {

            int row = layout[blk_id][0];
            int col = layout[blk_id][1];
            boolean isRotated = layout[blk_id][2] == 0 ? false : true;
            int blkWidth = isRotated?blkShape[blk_id][1]:blkShape[blk_id][0];
            int blkHeight = isRotated?blkShape[blk_id][0]:blkShape[blk_id][1];

            //produce all blocks
            blocks[blk_id] = new Block(blk_id, row, col, isRotated);

            move_able[row][col] = blk_id;
            move_able[row+blkHeight- 1][col] = blk_id;
            move_able[row][col+blkWidth- 1] = blk_id;
            move_able[row+blkHeight- 1][col+blkWidth- 1] = blk_id;
        }

        //initialize all block image view X,Y,W,H
        for(int blk_id=GameConfigurationsClass.CAOCAO;blk_id<=GameConfigurationsClass.ZU_4;blk_id++)
        {
            blkViews[blk_id].setImageResource(GameConfigurationsClass.block_resource_id_table[blk_id]);
            if (blocks[blk_id].isRotation())//false: normal true: rotated
            {
                blkViews[blk_id].setImageResource(GameConfigurationsClass.block_resource_r_id_table[blk_id]);
            }
            blkViews[blk_id].setX(blocks[blk_id].getBlkCoord(winWidth).x);
            blkViews[blk_id].setY(blocks[blk_id].getBlkCoord(winWidth).y);

            LayoutParams blockParams = blkViews[blk_id].getLayoutParams();
            blockParams.width = blocks[blk_id].getBlkCoord(winWidth).width;
            blockParams.height= blocks[blk_id].getBlkCoord(winWidth).height;
            blkViews[blk_id].setLayoutParams(blockParams);
        }
    }

    //locate the block with touch event.
    private int focus_block(int x, int y)
    {
        int blk_id = GameConfigurationsClass.DUMMY;
        int row;
        int col;

        //calculate the row & col
        if ( x<= (int)(winWidth*0.3/8.6) ||
                x >= (int)(winWidth*8.3/8.6) ||
                y <= (int)(winWidth*10.6/8.6*0.3/10.6) ||
                y >= (int)(winWidth*10.6/8.6*10.3/10.6))
        {
            return blk_id;
        }

        //row&col
        for (row=1;row<=5;)
        {
            if (y<=(int)((winWidth*(10.6/8.6)*(0.3/10.6))+(winWidth*10.6/8.6*10/10.6/5)*row)){break;}
            row++;
        }
        for (col=1;col<=4;)
        {
            if (x<=(int)((winWidth*0.3/8.6)+(winWidth*2/8.6)*col)){break;}
            col++;
        }

        //
        for (;blk_id<=GameConfigurationsClass.ZU_4;blk_id++)
        {
            if (layout[blk_id][0] == row) {
                if (layout[blk_id][1] == col) {
                    return blk_id;
                }
                else {
                    if (!blocks[blk_id].isRotation())
                    {
                        if(blkShape[blk_id][0] == 1) {
                            continue;
                        }
                        else if (blkShape[blk_id][0] == 2) {
                            if (layout[blk_id][1]+1 == col) {
                                return blk_id;
                            } else {
                                continue;
                            }
                        }
                    }
                    else {//is rotated
                        //todo
                        if(blkShape[blk_id][1] == 1) {
                            continue;
                        }
                        else if (blkShape[blk_id][1] == 2) {
                            if (layout[blk_id][1]+1 == col) {
                                return blk_id;
                            } else {
                                continue;
                            }
                        }
                    }
                }
            }
            else
            {
                if (layout[blk_id][1] == col) {
                    if (!blocks[blk_id].isRotation()) {
                        if(blkShape[blk_id][1] == 1) {
                            continue;
                        }
                        else if (blkShape[blk_id][1] == 2) {
                            if (layout[blk_id][0] + 1 == row) {
                                return blk_id;
                            } else {
                                continue;
                            }
                        }
                    }
                    else {//is rotated
                        //todo
                        if(blkShape[blk_id][0] == 1) {
                            continue;
                        }
                        else if (blkShape[blk_id][0] == 2) {
                            if (layout[blk_id][0] + 1 == row) {
                                return blk_id;
                            } else {
                                continue;
                            }
                        }
                    }
                }
                else {//judge if it is the bottom right corner of CAOCAO
                    if (layout[blk_id][0]+1 == row && layout[blk_id][1]+1 == col) {
                        if (blkShape[blk_id][0]==2 && blkShape[blk_id][1]==2) {
                            return GameConfigurationsClass.CAOCAO;
                        }
                        else {
                            continue;
                        }
                    }
                    else {
                        continue;
                    }
                }
            }
        }

        return GameConfigurationsClass.DUMMY;

    }

    private void move_block(final int id, Direction direction, int steps)
    {
        int fromX=blocks[id].getBlkCoord(winWidth).x;
        int DeltaX=0;
        int fromY=blocks[id].getBlkCoord(winWidth).y;
        int DeltaY=0;

        int row = layout[id][0];
        int col = layout[id][1];
        boolean isRotated = layout[id][2] == 0 ? false : true;
        int blkWidth = isRotated?blkShape[id][1]:blkShape[id][0];
        int blkHeight = isRotated?blkShape[id][0]:blkShape[id][1];

        switch (direction) {
            case RIGHT:
                DeltaX = (blocks[GameConfigurationsClass.ZU_1].getBlkCoord(winWidth).width)*steps;

                move_able[row][col+blkWidth+steps-1] = id;
                move_able[row+blkHeight-1][col+blkWidth+steps-1] = id;

                move_able[row][col] = 0;
                move_able[row+blkHeight-1][col] = 0;

                layout[id][1] = layout[id][1] + steps;
                blocks[id].mBlkPos.mCol = blocks[id].mBlkPos.mCol + steps;
                break;

            case LEFT:
                DeltaX = 0 - (blocks[GameConfigurationsClass.ZU_1].getBlkCoord(winWidth).width)*steps;
                move_able[row][col-steps] = id;
                move_able[row+blkHeight-1][col-steps] = id;

                move_able[row][col+blkWidth-1] = 0;
                move_able[row+blkHeight-1][col+blkWidth-1] = 0;

                layout[id][1] = layout[id][1] - steps;
                blocks[id].mBlkPos.mCol = blocks[id].mBlkPos.mCol - steps;
                break;

            case DOWN:
                DeltaY = (blocks[GameConfigurationsClass.ZU_1].getBlkCoord(winWidth).height) * steps;

                move_able[row+blkHeight+steps-1][col] = id;
                move_able[row+blkHeight+steps-1][col+blkWidth- 1] = id;

                move_able[row][col] = 0;
                move_able[row][col+blkWidth-1] = 0;

                layout[id][0] = layout[id][0] + steps;
                blocks[id].mBlkPos.mRow = blocks[id].mBlkPos.mRow + steps;
                break;

            case UP:
                DeltaY = 0 - (blocks[GameConfigurationsClass.ZU_1].getBlkCoord(winWidth).height) * steps;

                move_able[row-steps][col] = id;
                move_able[row-steps][col+blkWidth-1] = id;

                move_able[row+blkHeight-1][col] = 0;
                move_able[row+blkHeight-1][col+blkWidth-1] = 0;

                layout[id][0] = layout[id][0] - steps;
                blocks[id].mBlkPos.mRow = blocks[id].mBlkPos.mRow - steps;
                break;

            default:
                break;
        }
        ObjectAnimator animator;
        if (direction == Direction.RIGHT || direction == Direction.LEFT) {
            animator = ObjectAnimator.ofFloat(blkViews[id], "translationX", fromX, fromX + DeltaX);
        }else {// if(direction == Direction.DOWN || direction == Direction.UP) {
            animator = ObjectAnimator.ofFloat(blkViews[id], "translationY", fromY, fromY + DeltaY);
        }
        animator.setDuration(200*steps);
        animator.start();

        //update the score
        updateScore();

        //well dine!!!
        if (blocks[GameConfigurationsClass.CAOCAO].getBlkPos().mRow == 4 &&
                blocks[GameConfigurationsClass.CAOCAO].getBlkPos().mCol == 2) {
            Toast.makeText(GameMainActivity.this, "Well Done!!!",Toast.LENGTH_SHORT).show();
        }

        //layout log
        //for (int row=0;row<7;row++) {String s="";for (int col=0;col<6;col++)
        //{s=s+String.valueOf(move_able[row][col])+" ";}s=s+"\n";Log.d("--->",s);}
    }

    // 初始化声音
    private void InitSounds()
    {
        // 设置播放音效
        mPlayer = MediaPlayer.create(GameMainActivity.this, R.raw.bg_music);
        mPlayer.setLooping(true);
        mPlayer.start();

        // 第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        mSound = new SoundPool(6, AudioManager.STREAM_MUSIC, 100);
        mSoundPoolMap = new HashMap<>();
        mSoundPoolMap.put(GameConfigurationsClass.DIE, mSound.load(this, R.raw.die, 1));
        mSoundPoolMap.put(GameConfigurationsClass.HIT, mSound.load(this, R.raw.hit, 1));
        mSoundPoolMap.put(GameConfigurationsClass.POINT, mSound.load(this, R.raw.point, 1));
        mSoundPoolMap.put(GameConfigurationsClass.SWOOSH, mSound.load(this, R.raw.swoosh, 1));
        mSoundPoolMap.put(GameConfigurationsClass.WING, mSound.load(this, R.raw.wing, 1));
    }

    //soundPool播放
    //@param sound 播放第一个
    //@param loop 是否循环
    private void PlaySound(int sound, int loop)
    {
        AudioManager mgr = (AudioManager) this
                .getSystemService(Context.AUDIO_SERVICE);
        // 获取系统声音的当前音量
        float currentVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        // 获取系统声音的最大音量
        float maxVolume = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        // 获取当前音量的百分比
        float volume = currentVolume / maxVolume;

        // 第一个参数是声效ID,第二个是左声道音量，第三个是右声道音量，第四个是流的优先级，最低为0，
        // 第五个是是否循环播放，第六个播放速度(1.0 =正常播放,范围0.5 - 2.0)
        mSound.play(mSoundPoolMap.get(sound), volume, volume, 1, loop, 1f);
    }

    //
    private void updateScore()
    {
        score++;
        if (score < 10)
        {
            mScore[3].setImageResource(score_resource[score]);
        }
        else if (score >= 10 && score < 100)
        {
            mScore[2].setVisibility(View.VISIBLE);
            mScore[2].setImageResource(score_resource[score / 10]);
            mScore[3].setImageResource(score_resource[score % 10]);
        }
        else if (score >= 100 && score < 1000)
        {
            mScore[1].setVisibility(View.VISIBLE);
            mScore[2].setVisibility(View.VISIBLE);
            mScore[1].setImageResource(score_resource[score / 100]);
            mScore[2].setImageResource(score_resource[(score % 100) / 10]);
            mScore[3].setImageResource(score_resource[(score % 100) % 10]);
        }
        else if (score >= 1000 && score < 10000)
        {
            mScore[0].setVisibility(View.VISIBLE);
            mScore[1].setVisibility(View.VISIBLE);
            mScore[2].setVisibility(View.VISIBLE);
            mScore[0].setImageResource(score_resource[score / 1000]);
            mScore[1].setImageResource(score_resource[(score % 1000) / 100]);
            mScore[2].setImageResource(score_resource[((score % 1000) % 100) / 10]);
            mScore[3].setImageResource(score_resource[((score % 1000) % 100) % 10]);
        }

        PlaySound(GameConfigurationsClass.SWOOSH, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayer.stop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.stop();
    }

    class MyOnGestureListener extends SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }
        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("scroll works... ",
                        " x0: " + String.valueOf(e1.getRawX()) +
                            "  y0: " + String.valueOf(e1.getRawY()) +
                            "  x1: " + String.valueOf(e2.getRawX()) +
                            "  y1: " + String.valueOf(e2.getRawY()) +
                            "  Dx: " + String.valueOf(distanceX) +
                            "  Dy: " + String.valueOf(distanceY));

            int x0 = (int) e1.getRawX();
            int y0 = (int) e1.getRawY();
            //which block is touched
            int blk_id = focus_block(x0, y0);

            if (blk_id == GameConfigurationsClass.DUMMY) { return false; }

            int row = layout[blk_id][0];
            int col = layout[blk_id][1];
            boolean isRotated = layout[blk_id][2] == 0 ? false : true;
            int blkWidth = isRotated?blkShape[blk_id][1]:blkShape[blk_id][0];
            int blkHeight = isRotated?blkShape[blk_id][0]:blkShape[blk_id][1];

            int delta_x = (int)e2.getRawX() - x0;
            int delta_y = (int)e2.getRawY() - y0;

            if (Math.abs(delta_x) > Math.abs(delta_y)) {
                if (delta_x > 60) {
                    if (move_able[row][col+blkWidth] == 0 &&
                            move_able[row+blkHeight-1][col+blkWidth] == 0) {
                        move_block(blk_id, Direction.RIGHT, 1);
                    }
                } else if (delta_x < -60) {
                    if (move_able[row][col-1] == 0 &&
                            move_able[row+blkHeight-1][col-1] == 0) {
                        move_block(blk_id, Direction.LEFT, 1);
                    }
                }
            }
            else //Math.abs(delta_x) < Math.abs(delta_y)
            {
                if (delta_y > 60) {
                    if (move_able[row+blkHeight][col] == 0 &&
                            move_able[row+blkHeight][col+blkWidth-1] == 0) {
                        move_block(blk_id, Direction.DOWN, 1);
                    }
                } else if (delta_y < -60) {
                    if (move_able[row-1][col] == 0 &&
                            move_able[row-1][col+blkWidth-1] == 0) {
                        move_block(blk_id, Direction.UP, 1);
                    }
                }
            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("fling works... ",
                    " x0: " + String.valueOf(e1.getRawX()) +
                        "  y0: " + String.valueOf(e1.getRawY()) +
                        "  x1: " + String.valueOf(e2.getRawX()) +
                        "  y1: " + String.valueOf(e2.getRawY()) +
                        "  Dx: " + String.valueOf(velocityX) +
                        "  Dy: " + String.valueOf(velocityY));
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return false;
        }
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }
    }
}
