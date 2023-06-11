package com.example.mivueloapp.Funcionalidades;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.os.Vibrator;
import com.example.mivueloapp.R;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "¡Es hora de despertar!", Toast.LENGTH_LONG).show();

        // Reproducir tono de alarma
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        if (ringtone != null) {
            ringtone.play();
        }

        // Vibrar
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            long[] pattern = {0, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000}; // Esperar 0 segundos y vibrar durante 1 segundo, repetido 9 veces
            vibrator.vibrate(pattern, -1);
        }
        Toast.makeText(context, "¡Es hora de despertar!", Toast.LENGTH_LONG).show();
    }
}

