package edu.umb.cs443;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        TextView ShowScore = findViewById(R.id.scoreboard);
        TextView ShowHigh = findViewById(R.id.showHigh);

        int result = getIntent().getIntExtra("Your Score", 0);
        ShowScore.setText("Your score: " + result);

        SharedPreferences sharedPreferences = getSharedPreferences("DATA_GAME", Context.MODE_PRIVATE);
        int ScoreHigh = sharedPreferences.getInt("SCORE_HIGH", 0);

        if (result > ScoreHigh) {
            SharedPreferences.Editor editThis = sharedPreferences.edit();
            editThis.putInt("SCORE_HIGH", result);
            editThis.apply();

            ShowHigh.setText("Your High Score: " + result);

        } else {
            ShowHigh.setText("Your High Score: " + ScoreHigh);
        }
    }

    public void reStart(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}