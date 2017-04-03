package practicaltest04var04.eim.systems.cs.pub.ro.practicaltest01var04;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PracticalTest01Var04Service extends Service {
    private MyThread tid = null;
    public PracticalTest01Var04Service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String text = intent.getStringExtra("text");
        tid = new MyThread(this, text);
        tid.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        tid.stopThread();
    }
}
