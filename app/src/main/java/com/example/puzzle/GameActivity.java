package com.example.puzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

import java.util.Random;
public class GameActivity extends AppCompatActivity {


    public static ThreadLongClickImage currentThreadChangeImage = null;
    public static  ArrayList<ButtonImageController> listPuzzles = new ArrayList<>();
    public static int setChangeImage = 0;
    public static Integer currentLevel = 0;
    public static FragmentManager fragmentManager;
    public static Context context;
    String targetSound = "0";

    ArrayList<Integer> listDefaultCorners = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        context = getApplicationContext();
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            targetSound = extras.getString("targetSound");
            currentLevel = extras.getInt("level");
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

        int[] intArray = getArrayRandomNumbersForFiles();


        for (int i = 1; i <= 4; i++) {

            String filename = "puzzle_"+currentLevel+"/puzzle_"+ String.valueOf(intArray[i-1])+".png";

            int randomItem = getRandomInt(1, 3);

            ButtonImageController objPuzzle =  new ButtonImageController(
                    getApplicationContext(),
                    listImagePuzzles.get(i-1),
                    i,
                    listDefaultCorners.get(randomItem),
                    randomItem+1,
                    filename);

            listPuzzles.add(objPuzzle);
        }

        setImageAllItemsPuzzle();
    }


    public int[] getArrayRandomNumbersForFiles() {
        final int N = 4;
        ArrayList<Integer> arrayList = new ArrayList<>(N);
        Random random = new Random();

        while (arrayList.size() < N) {
            int i = random.nextInt(N) + 1;
            if (!arrayList.contains(i)) {
                arrayList.add(i);

            }
        }

        int[] randomArray = arrayList.stream().mapToInt(i -> i).toArray();
        return randomArray;

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

        if (sumValidate == 4) {

            DataController.init(context);
            Integer levels_in_memory = DataController.getProperty("levels");
            if (levels_in_memory <= currentLevel) {
                DataController.addProperty("levels", currentLevel+1);
            }


            CustomDialogFragment dialog = new CustomDialogFragment();

            Bundle bundle = new Bundle();
            bundle.putString("winner", "");
            dialog.setArguments(bundle);
            dialog.show(fragmentManager, "custom");
        }


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