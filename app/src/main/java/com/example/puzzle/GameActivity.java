package com.example.puzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

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

import java.util.Random;
public class GameActivity extends AppCompatActivity {


    public static ThreadLongClickImage currentThreadChangeImage = null;
    public static  ArrayList<ButtonImageController> listPuzzles = new ArrayList<>();

    public static int setChangeImage = 0;
    public static String currentLevel = "1";
    public static FragmentManager fragmentManager;
    String targetSound = "0";

    ArrayList<Integer> listDefaultCorners = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            targetSound = extras.getString("targetSound");
            currentLevel = extras.getString("level");

        }

        listPuzzles = new ArrayList<>();
        fragmentManager = getSupportFragmentManager();
        listDefaultCorners.add(90);
        listDefaultCorners.add(180);
        listDefaultCorners.add(270);
        listDefaultCorners.add(360);

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


        for (int i = 1; i <= 4; i++) {

            String filename = "puzzle_"+currentLevel+"/puzzle_"+ String.valueOf(i)+".png";

            int randomItem = getRandomInt(1, 3);

            ButtonImageController objPuzzle =  new ButtonImageController(
                    getApplicationContext(),
                    listImagePuzzles.get(i-1),
                    i,
                    listDefaultCorners.get(randomItem),
                    randomItem+1,
                    filename);

            System.out.println(" [" + String.valueOf(i) + "] ");
            System.out.println("randomItem: " + String.valueOf(randomItem));
            System.out.println("listDefaultCorners.get(randomItem)" + String.valueOf(listDefaultCorners.get(randomItem)));

            listPuzzles.add(objPuzzle);
        }

        setImageAllItemsPuzzle();
    }

    public static void setImageAllItemsPuzzle() {

        for (int i = 0; i < listPuzzles.size(); i++) {
            ButtonImageController objPuzzle = listPuzzles.get(i);
            objPuzzle.initButtonImage();
        }

    }

    public static void checkWinner() {

        int sumValidate = 0;

        for (int i = 0; i < listPuzzles.size(); i++) {

            ButtonImageController objPuzzle = listPuzzles.get(i);

            String shortFileName = getShortFileName(objPuzzle.localFileName,objPuzzle.localFileName.length()-12,objPuzzle.localFileName.length() );

            if (i==0 & objPuzzle.localCorner == 360 & shortFileName.equals("puzzle_1.png")) {
                sumValidate++;
            } if (i==1 & objPuzzle.localCorner == 360 & shortFileName.equals("puzzle_2.png")) {
                sumValidate++;
            } if (i==2 & objPuzzle.localCorner == 360 & shortFileName.equals("puzzle_3.png"))  {
                sumValidate++;
            } if (i==3 & objPuzzle.localCorner == 360 & shortFileName.equals("puzzle_4.png")) {
                sumValidate++;
            }
        }

        System.out.println("sumValidate: " + String.valueOf(sumValidate));
        if (sumValidate == 4) {

            CustomDialogFragment dialog = new CustomDialogFragment();

            Bundle bundle = new Bundle();
            bundle.putString("winner", "");
            dialog.setArguments(bundle);

            dialog.show(fragmentManager, "custom");
        }


    }

    public void winPlayer() {
        DataController.init (getApplicationContext());
        DataController.addProperty("level","2");
    }

    public static String getShortFileName(String filename, int start, int end) {
        char[] dst=new char[end - start];
        filename.getChars(start, end, dst, 0);
        return String.valueOf(dst);
    }

    public int getRandomInt(int min, int max) {

        int diff = max - min;
        Random random = new Random();
        int i = random.nextInt(diff + 1);
        i += min;

        return  i;

    }
}