package com.example.sudhasri.tictactoe;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.sudhasri.tictactoe.data.DifficultyLevel;

/**
 * Created by sudhasri on 26/8/16.
 */
public class Utils
{
    public interface DialogClickInterface
    {
        public void onFirstButtonClick();
    }

    public interface ChoiceClickListener extends DialogClickInterface
    {
        public void onSecondButtonClick();
        public void onCancelClick(DialogInterface dialogInterface);
    }

    public interface DifficultySelectedListener
    {
        public void onDifficultySelected(DifficultyLevel difficultyLevel);
    }

    public static AlertDialog showWinDialog(Context context, String title, String message, final DialogClickInterface listener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null)
                        {
                            listener.onFirstButtonClick();
                        }
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;
        dialog.show();

        return dialog;

    }



    public static AlertDialog showChoosePlayerDialog(Context context, final ChoiceClickListener choiceClickListener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        builder.setTitle(context.getString(R.string.choose_player))
                .setMessage(context.getString(R.string.choose_player_msg))
                .setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (choiceClickListener != null)
                        {
                            choiceClickListener.onFirstButtonClick();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(context.getString(R.string.no_dialog), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (choiceClickListener != null)
                        {
                            choiceClickListener.onSecondButtonClick();
                        }
                        dialog.dismiss();
                    }
                });

        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

    public static AlertDialog showPlayModeDialog(Context context, final DifficultySelectedListener clickLiInterface)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        LayoutInflater inflater = LayoutInflater.from(context);
        View root = inflater.inflate(R.layout.spinner_layout, null);

        builder.setTitle(context.getString(R.string.play_mode))
                .setView(root)
                .setCancelable(false);
        final AlertDialog dialog = builder.create();


        Button easy = (Button) root.findViewById(R.id.easy);
        Button medium = (Button) root.findViewById(R.id.medium);
        Button difficult = (Button) root.findViewById(R.id.difficult);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId())
                {
                    case R.id.easy:
                        if (clickLiInterface != null)
                        clickLiInterface.onDifficultySelected(DifficultyLevel.EASY);
                        break;
                    case R.id.medium:
                        clickLiInterface.onDifficultySelected(DifficultyLevel.MEDIUM);
                        break;
                    case R.id.difficult:
                        clickLiInterface.onDifficultySelected(DifficultyLevel.HARD);
                        break;
                }
                dialog.dismiss();

            }
        };

        easy.setOnClickListener(clickListener);
        medium.setOnClickListener(clickListener);
        difficult.setOnClickListener(clickListener);
        dialog.show();
        return dialog;
    }
}
