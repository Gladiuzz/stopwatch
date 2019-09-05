package com.example.pwpbjava3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button start, pause, reset, lap;
    long Millisecondtime, starttime, timebuff, updatetime = 0L;
    Handler handler;
    int seconds, minutes, milliseconds;
    ListView listView;
    String[] listelements = new String[]{};
    List<String> listelementsarraylist;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textview);
        start = (Button) findViewById(R.id.button);
        pause = (Button) findViewById(R.id.button2);
        reset = (Button) findViewById(R.id.button3);
        lap = (Button) findViewById(R.id.button4);
        listView = (ListView) findViewById(R.id.listview1);

        handler = new Handler();

        listelementsarraylist = new ArrayList<String>(Arrays.asList(listelements));

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listelementsarraylist);

        listView.setAdapter(adapter);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starttime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);

                reset.setEnabled(false);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timebuff += Millisecondtime;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Millisecondtime = 0L;
                starttime = 0L;
                timebuff = 0L;
                updatetime = 0L;
                seconds = 0;
                minutes = 0;
                milliseconds = 0;

                textView.setText("00:00:00");
                listelementsarraylist.clear();
                adapter.notifyDataSetChanged();
            }
        });

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listelementsarraylist.add(textView.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Millisecondtime = SystemClock.uptimeMillis() - starttime;
            updatetime = timebuff + Millisecondtime;
            seconds = (int) (updatetime / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            milliseconds = (int) (updatetime % 1000);

            textView.setText(""+minutes+":"
                    + String.format("%02d",seconds)+":"
                    + String.format("%03d", milliseconds));

            handler.postDelayed(this, 0);
        }
    };

}
