package com.puzzlygame.puzzle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class CustomFragmentRestartGame extends DialogFragment {

    String winner = "";
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            winner = bundle.getString("winner");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle(winner)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Вы уверены что хотите перезагрузить игру?")
                .setPositiveButton("Удалить прохождение", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Переход
                        dismiss();
                        DataController.init(MainActivity.context);
                        DataController.clearAll();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Переход
                        dismiss();

                    }
                })
                .create();
    }



}