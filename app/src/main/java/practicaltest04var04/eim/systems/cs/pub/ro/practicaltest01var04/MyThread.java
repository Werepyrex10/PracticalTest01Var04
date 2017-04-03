package practicaltest04var04.eim.systems.cs.pub.ro.practicaltest01var04;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.StringTokenizer;

/**
 * Created by malvo.
 */

public class MyThread extends Thread {
    private Context context;
    private boolean running;
    private String text;

    public MyThread(Context context, String text) {
        this.context = context;
        this.text = text;
        running = true;
    }

    @Override
    public void run() {
        Log.d("MyThread", "Started");
        while(running) {
            sendParts();
            try {
                Thread.sleep(5000);
            } catch (Exception e) {}
        }
        Log.d("MyThread", "Stopped");
    }

    private void sendParts() {
        StringTokenizer st = new StringTokenizer(text, ",");
        String tok;
        while(st.hasMoreTokens()) {
            tok = st.nextToken();

            Intent intent = new Intent();
            intent.setAction("PART");
            intent.putExtra("mesaj", tok);
            context.sendBroadcast(intent);
        }
    }

    public void stopThread() {
        running = false;
    }
}
