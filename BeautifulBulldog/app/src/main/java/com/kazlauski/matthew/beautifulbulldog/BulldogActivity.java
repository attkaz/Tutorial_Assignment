package com.kazlauski.matthew.beautifulbulldog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import io.realm.Realm;

public class BulldogActivity extends AppCompatActivity {

    private TextView textView_ab;
    private Button button_ab;
    private Spinner spinner_ab;
    private ImageView imageView_ab;

    private Realm realm;
    private User owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulldog);

        textView_ab =(TextView) findViewById(R.id.textView_ab);
        spinner_ab =(Spinner) findViewById(R.id.spinner_ab);
        imageView_ab =(ImageView) findViewById(R.id.imageView_ab);
        button_ab =(Button) findViewById(R.id.button_ab);

        String username = (String) getIntent().getStringExtra("username");


        realm = Realm.getDefaultInstance();
        owner = realm.where(User.class).equalTo("username", username).findFirst();

        String id = (String) getIntent().getStringExtra("bulldog");

        final Bulldog bulldog = realm.where(Bulldog.class).equalTo("id", id).findFirst();
        textView_ab.setText(bulldog.getName());

        if(bulldog.getImage() != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(bulldog.getImage(), 0, bulldog.getImage().length);
            imageView_ab.setImageBitmap(bmp);
        }
        textView_ab.setText(bulldog.getName());

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("0");
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        arrayList.add("4");
        arrayList.add("5");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner_ab.setAdapter(adapter);

        spinner_ab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button_ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.executeTransaction(new Realm.Transaction()
                {
                    @Override
                    public void execute(Realm realm)
                    {
                        Vote vote = new Vote();
                        vote.setOwner(owner);
                        vote.setRating(Integer.valueOf(spinner_ab.getSelectedItem().toString()));
                        bulldog.appendVote(vote);

                        finish();
                    }
                });
            }
        });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the Realm instance.
        realm.close();
    }



}
