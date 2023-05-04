package com.digitcreativestudio.alarmapp;


import android.content.DialogInterface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog extends AppCompatDialogFragment {

    public android.app.Dialog onCreateDialog(Bundle savedInstanceState, Ringtone ringtone) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Some_info")
                .setMessage("Будильник!")
                .setPositiveButton("Выключить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ringtone.stop();
                    }
                });
        return builder.create();

    }
}
