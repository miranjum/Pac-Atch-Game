package edu.umb.cs443;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BeginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);

        Button button1 = findViewById(R.id.button1);

        button1.setOnClickListener(v -> {

            Intent intent1 = new Intent(this, HowTo.class);

            startActivity(intent1);
        });

    }

    public void beginStart(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}