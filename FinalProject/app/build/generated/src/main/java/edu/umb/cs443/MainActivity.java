package edu.umb.cs443;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView ScoreBoard, GameStarting;
    private ImageView pac, blue, gold, red;
    private int HeightofScreen, WidthofScreen;
    private int HeightofFrame;
    private int SizeofPac;
    private float pacY;
    private float blueX, blueY;
    private float goldX, goldY;
    private float redX, redY;
    private int result;
    private Timer clock = new Timer();
    private Handler handle = new Handler();
    private boolean GetHit = false;
    private boolean GetStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScoreBoard = findViewById(R.id.scoreboard);
        GameStarting = findViewById(R.id.GameStarting);
        pac = findViewById(R.id.pac);
        blue = findViewById(R.id.blue);
        gold = findViewById(R.id.gold);
        red = findViewById(R.id.red);

        WindowManager managerwindow = getWindowManager();
        Display showing = managerwindow.getDefaultDisplay();
        Point point = new Point();
        showing.getSize(point);

        WidthofScreen = point.x;
        HeightofScreen = point.y;

        blue.setX(-80.0f);
        blue.setY(-80.0f);
        gold.setX(-80.0f);
        gold.setY(-80.0f);
        red.setX(-80.0f);
        red.setY(-80.0f);

        ScoreBoard.setText("You Scored: " + result);
    }

    public void Positioning() {

        CheckingHit();

        blueX -= 13;
        if(blueX < 0){
            blueX = WidthofScreen + 25;
            blueY = (float)Math.floor(Math.random()*(HeightofFrame - blue.getHeight()));
        }
        blue.setX(blueX);
        blue.setY(blueY);

        goldX -= 17;
        if(goldX < 0){
            goldX = WidthofScreen + 15;
            goldY = (float)Math.floor(Math.random()*(HeightofFrame - gold.getHeight()));
        }
        gold.setX(goldX);
        gold.setY(goldY);

        redX -= 25;
        if(redX < 0){
            redX = WidthofScreen + 2000;
            redY = (float)Math.floor(Math.random()*(HeightofFrame - red.getHeight()));
        }
        red.setX(redX);
        red.setY(redY);

        if (GetHit) {

            pacY -= 25;
        } else {

            pacY += 25;
        }

        if (pacY < 0) pacY = 0;
        if (pacY > HeightofFrame - SizeofPac) pacY = HeightofFrame - SizeofPac;

        pac.setY(pacY);

        ScoreBoard.setText("You Scored: " + result);
    }

    public void CheckingHit() {

        float blueCenterX = blueX + blue.getWidth() / 3.0f;
        float blueCenterY = blueY + blue.getHeight() / 3.0f;

        if (0 <= blueCenterX && blueCenterX <= SizeofPac &&
                pacY <= blueCenterY && blueCenterY <= pacY + SizeofPac) {
            blueX = -105.0f;
            result += 5;
        }

        float goldCenterX = goldX + gold.getWidth() / 3.0f;
        float goldCenterY = goldY + gold.getHeight() / 3.0f;

        if (0 <= goldCenterX && goldCenterX <= SizeofPac &&
                pacY <= goldCenterY && goldCenterY <= pacY + SizeofPac) {
            goldX = -105.0f;
            result += 10;
        }

        float redCenterX = redX + red.getWidth() / 3.0f;
        float redCenterY = redY + red.getHeight() / 3.0f;

        if (0 <= redCenterX && redCenterX <= SizeofPac &&
                pacY <= redCenterY && redCenterY <= pacY + SizeofPac) {

            if (clock != null){
                clock.cancel();
                clock = null;
            }

            Intent intent = new Intent(getApplicationContext(), EndActivity.class);
            intent.putExtra("Your Score", result);
            startActivity(intent);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!GetStart) {
            GetStart = true;

            FrameLayout frameLayout = findViewById(R.id.frame);
            HeightofFrame = frameLayout.getHeight();

            pacY = pac.getY();
            SizeofPac = pac.getHeight();
            GameStarting.setVisibility(View.GONE);

            clock.schedule(new TimerTask() {
                @Override
                public void run() {
                    handle.post(new Runnable() {
                        @Override
                        public void run() {
                            Positioning();
                        }
                    });
                }
            }, 0, 25);

        } else {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                GetHit = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                GetHit = false;
            }
        }
        return super.onTouchEvent(event);
    }
}