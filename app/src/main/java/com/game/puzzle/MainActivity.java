package com.game.puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        Button button_start = findViewById(R.id.button_start);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, LevelsActivity.class);
                intent.putExtra("targetSound", "targetSound");
                setResult(Activity.RESULT_OK, intent);
                startActivityForResult(intent,101);
                onNewIntent (intent);
            }
        });

        Button restart = findViewById(R.id.button_restart);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomFragmentRestartGame dialog = new CustomFragmentRestartGame();

                Bundle bundle = new Bundle();
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "custom");
            }
        });
    }



}