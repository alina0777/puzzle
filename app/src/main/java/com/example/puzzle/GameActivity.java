package com.example.puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {


    public static ThreadLongClickImage currentThreadChangeImage = null;
    public static  ArrayList<ButtonImageController> listPuzzles = new ArrayList<>();

    public static int setChangeImage = 0;
    public static String currentLevel = "1";
    String targetSound = "0";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            targetSound = extras.getString("targetSound");
            currentLevel = extras.getString("level");

        }

        currentThreadChangeImage = new ThreadLongClickImage(null,this,
                new ImageView(this),
                0,"");

        currentThreadChangeImage.disable();

        ImageView partPuzzle_1 = findViewById(R.id.image_puzzle_1);
        ImageView partPuzzle_2 = findViewById(R.id.image_puzzle_2);
        ImageView partPuzzle_3 = findViewById(R.id.image_puzzle_3);
        ImageView partPuzzle_4 = findViewById(R.id.image_puzzle_4);

        ArrayList<ImageView> listImagePuzzles = new ArrayList<>();

        listImagePuzzles.add(partPuzzle_1);
        listImagePuzzles.add(partPuzzle_2);
        listImagePuzzles.add(partPuzzle_3);
        listImagePuzzles.add(partPuzzle_4);

        for (int i = 1; i<=4; i++) {

            String filename = "puzzle_"+currentLevel+"/puzzle_"+ String.valueOf(i)+".png";

            ButtonImageController objPuzzle =  new ButtonImageController(
                    getApplicationContext(),
                    listImagePuzzles.get(i-1),
                    i,
                    90,
                    filename);

            listPuzzles.add(objPuzzle);
        }
        setImageAllItemsPuzzle();
    }

    public static void setImageAllItemsPuzzle() {
        System.out.println("setImageAllItemsPuzzle");

        for (int i=0; i<listPuzzles.size();i++) {
            ButtonImageController objPuzzle = listPuzzles.get(i);

            System.out.println("i= " + String.valueOf(i));
            System.out.println("localFileName= " + String.valueOf(objPuzzle.localFileName));

            objPuzzle.initButtonImage();
        }

    }

    public void winPlayer() {
        DataController.init (getApplicationContext());
        DataController.addProperty("level","2");
    }
}