package com.example.puzzle;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.ImageView;

public class ThreadLongClickImage extends Thread  {

    public Boolean active;
    public String localFileName = "";
    public ImageView localImageView;
    public Integer localIdButton;

    ButtonImageController localButtonImage;


    ThreadLongClickImage( ButtonImageController buttonImageController,
                          Context context,
                          ImageView imageView,
                          Integer idButton,
                          String currentUseFileNameImage) {

        localButtonImage = buttonImageController;

        imageView.setColorFilter(context.getResources().getColor(R.color.red), PorterDuff.Mode.ADD);
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

    void disable() {
        active = false;
    }

    public Boolean getActive() {
        return active;
    }
    public String getCurrentFileName() {
        return localFileName;
    }

    public ImageView getCurrentImageView() {
        return localImageView;
    }

    public Integer getIdButton(){
        return localIdButton;
    }
}
