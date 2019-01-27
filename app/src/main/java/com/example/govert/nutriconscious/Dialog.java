package com.example.govert.nutriconscious;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

public class Dialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // get builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // get inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // inflate view
        View view = inflater.inflate(R.layout.dialog_height, null);

        // set view
        builder.setView(view);

        builder.setPositiveButton("update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}
