package com.example.puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.io.IOException;
import java.io.InputStream;

public class GameActivity extends AppCompatActivity {

    Integer currentLevel = 1;
    Integer corner_puzzle_1 = 90;
    Integer corner_puzzle_2 = 90;
    Integer corner_puzzle_3 = 90;
    Integer corner_puzzle_4 = 90;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ImageView partPuzzle_1 = findViewById(R.id.image_puzzle_1);
        String filename1 = "puzzle_1/puzzly_1_1.png";
        setImage (partPuzzle_1, filename1);

        ImageView partPuzzle_2 = findViewById(R.id.image_puzzle_2);
        String filename2 = "puzzle_1/puzzly_1_2.png";
        setImage (partPuzzle_2, filename2);

        ImageView partPuzzle_3 = findViewById(R.id.image_puzzle_3);
        String filename3 = "puzzle_1/puzzly_1_3.png";
        setImage (partPuzzle_3, filename3);

        ImageView partPuzzle_4 = findViewById(R.id.image_puzzle_4);
        String filename4 = "puzzle_1/puzzly_1_4.png";
        setImage (partPuzzle_4, filename4);

        partPuzzle_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corner_puzzle_1=corner_puzzle_1+90;
                partPuzzle_1.animate().rotation(corner_puzzle_1);            }
        });

        partPuzzle_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corner_puzzle_2=corner_puzzle_2+90;
                partPuzzle_2.animate().rotation(corner_puzzle_2);            }
        });

        partPuzzle_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corner_puzzle_3 = corner_puzzle_3+90;

                partPuzzle_3.animate().rotation(corner_puzzle_3);            }
        });

        partPuzzle_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corner_puzzle_4=corner_puzzle_4+90;

                partPuzzle_4.animate().rotation(corner_puzzle_4);            }
        });

    }



    private void setImage (ImageView imageView, String filename) {
        try(InputStream inputStream = getApplicationContext().getAssets().open(filename)){
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(drawable);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}