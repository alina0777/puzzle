package com.puzzlygame.puzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class LevelsActivity extends AppCompatActivity {


    public static Activity activity;
    TableLayout levelsTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        activity = this;

        DataController.init(getApplicationContext());

        //DataController.clearAll();

        Integer currentLevelInMemory = DataController.getProperty("levels");

        levelsTable = findViewById(R.id.tableLevels);

        int levels_iterator = 1;
        for (int i = 0; i < levelsTable.getChildCount(); i++) {
            TableRow row = (TableRow) levelsTable.getChildAt(i);

            for (int j = 0; j < row.getChildCount(); j++) {
                AppCompatButton button = (AppCompatButton)row.getChildAt(j);

                if (levels_iterator > currentLevelInMemory) {
                    button.setText("");
                    button.setBackgroundResource(R.drawable.padlock_with_back);
                } else {
                    button.setText(String.valueOf(levels_iterator));
                    int finalLevels_iterator = levels_iterator;

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(LevelsActivity.this, GameActivity.class);
                            intent.putExtra("level", finalLevels_iterator);
                            setResult(Activity.RESULT_OK, intent);
                            startActivityForResult(intent,101);
                            onNewIntent (intent);
                        }
                    });
                }

                levels_iterator++;
            }

        }

        Button backMenu = findViewById(R.id.backfromlevels);
        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}