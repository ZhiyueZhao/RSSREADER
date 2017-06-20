package com.example.zhiyue.RSSpro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class firstActivity extends AppCompatActivity {

    private EditText edtUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        edtUri = (EditText)findViewById(R.id.edtUri);
    }

    public void startOtherActivity(View view) {

        //1. create an intent object
        Intent intent = new Intent(this, MainActivity.class);
        String sUri = edtUri.getText().toString();
        //add some extras (data) to the intent (to pass to the other activity)
        intent.putExtra("uri", sUri);
        //startActivity or startActivityForResult
        startActivity(intent);
    }
}
