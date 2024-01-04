package com.game.puzzle;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class ButtonImageController {
    int localIdButton = 0;
    int localCorner = 0;
    String localFileName = "";
    ImageView localImageView;
    Context localContext;

    ButtonImageController ( Context context,
                            ImageView imageView,
                            int idButton,
                            int corner,
                            int idCorner,
                            String fileName) {

        localImageView = imageView;
        localIdButton = idButton;
        localCorner = corner;
        localFileName = fileName;
        localContext = context;

    }

    public void initButtonImage() {

        setImage();
        localImageView.setRotation(localCorner);

        localImageView.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {

                ThreadLongClickImage myThread = new ThreadLongClickImage(
                        getObj(),
                        localContext,
                        localImageView,
                        localIdButton,
                        localFileName);

                myThread.start();
                GameActivity.currentThreadChangeImage = myThread;

                return false;
            }
        });

        localImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (GameActivity.currentThreadChangeImage!=null) {
                    if (GameActivity.currentThreadChangeImage.getActive()==false) {

                        localCorner = localCorner + 90;

                        if (localCorner > 360) {
                            localCorner = 0;
                            localImageView.setRotation(localCorner);
                            localCorner=localCorner+90;

                        }

                        localImageView.animate().rotation(localCorner);
                        GameActivity.checkWinner();
                    } else {

                        if (GameActivity.setChangeImage==1){

                            GameActivity.currentThreadChangeImage.disable();

                            changeImagesInPuzzle(GameActivity.currentThreadChangeImage.localIdButton,
                                    localIdButton);


                            GameActivity.setChangeImage = 0;

                            GameActivity.checkWinner();
                        } else {
                            GameActivity.setChangeImage++;
                        }
                    }
                }

            }
        });
    }

    public ButtonImageController getObj() {
        return this;
    }

    public void changeImagesInPuzzle(int fromPuzzle, int toPuzzle){

        System.out.println("changeImagesInPuzzle: from_" + String.valueOf(fromPuzzle) + "_to_" +String.valueOf(toPuzzle));

        ButtonImageController objFrom = GameActivity.listPuzzles.get(fromPuzzle-1);
        ButtonImageController objTo = GameActivity.listPuzzles.get(toPuzzle-1);

        String bFileName = objFrom.localFileName;

        objFrom.setLocalFileName(objTo.localFileName);

        objTo.setLocalFileName(bFileName);

        GameActivity.setImageAllItemsPuzzle();
    }
    public void setImage () {

        try (
            InputStream inputStream = localContext.getAssets().open(localFileName) ) {
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            localImageView.setImageDrawable(drawable);
            localImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLocalImageView(ImageView imageView) {
        localImageView = imageView;
    }

    public void setLocalFileName(String fileName) {
        localFileName = fileName;
    }
}
