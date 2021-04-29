package com.example.projet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class AlarmDialog extends DialogFragment {
    boolean[] selectedAlarms = new boolean[]{false,false,false};
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Alarm Setter").setMultiChoiceItems(R.array.choices, null, new
                DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        selectedAlarms[which] = isChecked;
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity().getApplicationContext(),"OK",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity().getApplicationContext(),"Cancel",Toast.LENGTH_LONG).show();
                    }
                });
        return builder.create();
    }
    public boolean[] getSelectedAlarms() {
        return selectedAlarms;
    }

    public void setSelectedAlarms(boolean[] pSelectedAlarms) {
        for(int i=0;i<3;i++){
            this.selectedAlarms[i] = pSelectedAlarms[i];
        }
    }
}
