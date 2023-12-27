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

public class GameActivity extends AppCompatActivity {

    ThreadLongClickImage currentThreadChangeImage = null;
    public int setChangeImage = 0;
    public class ThreadLongClickImage extends Thread {

        public Boolean active;

        public String localFileName = "";
        public ImageView localImageView;
        public Integer localIdButton;


        ThreadLongClickImage(ImageView imageView,Integer idButton, String currentUseFileNameImage){
            imageView.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.ADD);
            localImageView = imageView;
            active = true;
            localFileName = currentUseFileNameImage;
            localIdButton = idButton;
        }
        public void run() {

            while(active) {
                try {
                    this.sleep(400);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            localImageView.setColorFilter(Color.alpha(1));

        }

        void disable(){
            active = false;
        }

        public Boolean getActive(){
            return active;
        }
        public String getCurrentFileName(){
            return localFileName;
        }

        public ImageView getCurrentImageView(){
            return localImageView;
        }

        public Integer getIdButton(){
            return localIdButton;
        }
    }

    String currentLevel = "1";
    String targetSound = "0";
    Integer corner_puzzle_1 = 90;
    Integer corner_puzzle_2 = 90;
    Integer corner_puzzle_3 = 90;
    Integer corner_puzzle_4 = 90;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            targetSound = extras.getString("targetSound");
            currentLevel = extras.getString("level");

        }

        currentThreadChangeImage = new ThreadLongClickImage(new ImageView(this),0,"");
        currentThreadChangeImage.disable();

        String filename1 = "puzzle_"+currentLevel+"/puzzle_1.png";
        String filename2 = "puzzle_"+currentLevel+"/puzzle_2.png";
        String filename3 = "puzzle_"+currentLevel+"/puzzle_3.png";
        String filename4 = "puzzle_"+currentLevel+"/puzzle_4.png";

        ImageView partPuzzle_1 = findViewById(R.id.image_puzzle_1);
        ImageView partPuzzle_2 = findViewById(R.id.image_puzzle_2);
        ImageView partPuzzle_3 = findViewById(R.id.image_puzzle_3);
        ImageView partPuzzle_4 = findViewById(R.id.image_puzzle_4);

        initButtonImage(partPuzzle_1,1, filename1);
        initButtonImage(partPuzzle_2,2, filename2);
        initButtonImage(partPuzzle_3,3, filename3);
        initButtonImage(partPuzzle_4,4, filename4);

    }


    private void initButtonImage(ImageView imageView, int idButton, String filename ) {

        setImage (imageView, filename);

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                ThreadLongClickImage myThread = new ThreadLongClickImage(imageView, idButton, filename);
                myThread.start();
                currentThreadChangeImage = myThread;
                return false;
            }

        });

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (currentThreadChangeImage!=null) {
                    if (currentThreadChangeImage.getActive()==false) {
                        int currentCorner = 0;
                        if (idButton==1){
                            corner_puzzle_1 = corner_puzzle_1+90;
                            currentCorner = corner_puzzle_1;
                        } else if (idButton==2) {
                            corner_puzzle_2=corner_puzzle_2+90;
                            currentCorner = corner_puzzle_2;
                        }else if (idButton==3) {
                            corner_puzzle_3=corner_puzzle_3+90;
                            currentCorner = corner_puzzle_3;
                        }else if (idButton==4) {
                            corner_puzzle_4=corner_puzzle_4+90;
                            currentCorner = corner_puzzle_4;
                        }
                        imageView.animate().rotation(currentCorner);
                    } else {

                        if (setChangeImage==1){

                            currentThreadChangeImage.disable();
//                            setImage (currentThreadChangeImage.getCurrentImageView(), filename);
//                            setImage (imageView, currentThreadChangeImage.getCurrentFileName());

                            initButtonImage(currentThreadChangeImage.getCurrentImageView(),
                                    currentThreadChangeImage.getIdButton(),
                                    filename);

                            initButtonImage(imageView,
                                    idButton,
                                    currentThreadChangeImage.getCurrentFileName());



                            setChangeImage = 0;
                        } else {
                            setChangeImage++;
                        }



                    }
                }

            }
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