package practicaltest04var04.eim.systems.cs.pub.ro.practicaltest01var04;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var04MainActivity extends AppCompatActivity {
    private int counter;
    private final int SECONDARY_CODE = 1;
    private boolean serviceState;
    private MessageBroadcastReceiver mbr = new MessageBroadcastReceiver();
    private IntentFilter ifilter = new IntentFilter();

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Mesaj", intent.getStringExtra("mesaj"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_main);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("counter")) {
                counter = savedInstanceState.getInt("counter");
                Toast.makeText(this, "Saved value " + counter, Toast.LENGTH_SHORT).show();
            }
            else {
                counter = 0;
            }
        }
        else {
            counter = 0;
        }

        serviceState = false;

        ifilter.addAction("PART");
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mbr, ifilter);
    }

    @Override
    public void onPause() {
        unregisterReceiver(mbr);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var04Service.class);
        stopService(intent);
        serviceState = false;
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("counter", counter);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey("counter")) {
            counter = savedInstanceState.getInt("counter");
        }
    }

    public void onClick(View view) {
        Button b = (Button) view;
        EditText bottom = (EditText) findViewById(R.id.bottom);
        String s;

        switch(b.getId()) {
            case R.id.tl:
            case R.id.tr:
            case R.id.ct:
            case R.id.bl:
            case R.id.br:
                s = bottom.getText().toString();
                if (s.length() != 0)
                    s += ", ";
                s += b.getText().toString();
                bottom.setText(s);
                ++counter;
                Toast.makeText(this, "" + counter, Toast.LENGTH_SHORT).show();

                if (counter > 5 && !serviceState) {
                    Toast.makeText(this, "Stated Service", Toast.LENGTH_SHORT).show();
                    serviceState = true;
                    Intent intent = new Intent(getApplicationContext(),
                            PracticalTest01Var04Service.class);
                    intent.putExtra("text", bottom.getText().toString());
                    getApplicationContext().startService(intent);
                    registerReceiver(mbr, ifilter);
                }

                break;
            case R.id.next:
                Intent intent = new Intent(getApplicationContext(),
                        PracticalTest01Var04SecondaryActivity.class);

                intent.putExtra("valoare", bottom.getText().toString());

                startActivityForResult(intent, SECONDARY_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        EditText text = (EditText) findViewById(R.id.bottom);
        if(requestCode == SECONDARY_CODE) {
            Toast.makeText(this, "Secondary activity returned " + resultCode, Toast.LENGTH_SHORT).show();
            counter = 0;
            text.setText("");
        }
    }
}
