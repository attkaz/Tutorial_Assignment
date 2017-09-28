package com.kazlauski.matthew.beautifulbulldog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;

import io.realm.Realm;

public class AltNewBulldogActivity extends Activity {

    private EditText editText_anb;
    private EditText editText2_anb;
    private Button button_anb;
    private ImageButton imageButton_anb;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alt_new_bulldog);


        editText_anb = (EditText) findViewById(R.id.editText_anb);
        editText2_anb = (EditText) findViewById(R.id.editText2_anb);
        imageButton_anb = (ImageButton) findViewById(R.id.imageButton_anb);
        button_anb = (Button) findViewById(R.id.button_anb);

        realm = Realm.getDefaultInstance();


        imageButton_anb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }
            }
        });

        button_anb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(!editText_anb.getText().toString().matches("")
            && !editText2_anb.getText().toString().matches("")
            && imageButton_anb.getDrawable() != null) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Bulldog bulldog = new Bulldog();
                        bulldog.setAge(editText2_anb.getText().toString());
                        bulldog.setName(editText_anb.getText().toString());
                        bulldog.setId(realm.where(Bulldog.class).findAllSorted("id").last().getId() + 1);
                        BitmapDrawable image = (BitmapDrawable) imageButton_anb.getDrawable();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        image.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageInByte = baos.toByteArray();
                        bulldog.setImage(imageInByte);
                        realm.copyToRealm(bulldog);
                        finish();


                    }
                });

                }

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton_anb.setImageBitmap(imageBitmap);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the Realm instance.
        realm.close();
    }
}

