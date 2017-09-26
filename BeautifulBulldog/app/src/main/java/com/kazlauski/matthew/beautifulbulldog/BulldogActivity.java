package com.kazlauski.matthew.beautifulbulldog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class BulldogActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulldog);

        textView =(TextView) findViewById(R.id.textView);

        Bulldog bulldog = (Bulldog) getIntent().getSerializableExtra("bulldog");
        textView.setText(bulldog.getName());

    }
}
