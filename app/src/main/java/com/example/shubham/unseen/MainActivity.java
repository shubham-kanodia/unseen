package com.example.shubham.unseen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.InputStream;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextToSpeech ts;
    String txt_val;
    int result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ts = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS){
                    result = ts.setLanguage(Locale.UK);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please update your device to avail this feature.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void TTS(View view){
        switch(view.getId()){
            case R.id.submit_btn:
                try{
                    InputStream is = getAssets().open("read.txt");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    txt_val = new String(buffer);
                    ts.speak(txt_val, TextToSpeech.QUEUE_FLUSH,null);
                }
                catch(IOException e){

                }
                break;
            case R.id.stop_btn:
                if(ts!=null){
                    ts.stop();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(ts!=null){
            ts.stop();
            ts.shutdown();
        }
    }
}
