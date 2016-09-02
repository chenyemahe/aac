package com.acme.amazon.amazonpage.order;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.acme.amazon.orderrecord.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ye1chen on 9/1/16.
 */
public class AaTransUpdatePage extends Activity implements View.OnClickListener{

    private Button mFilePickBt;
    private static final int REQUEST_CODE = 7715;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa_trans_update_page);
        mFilePickBt = (Button) findViewById(R.id.bt_file_picker);
        mFilePickBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_file_picker :
                showChooser();
                break;
        }
    }

    private void showChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if(uri != null) {
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    if (is != null) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String l = line;
                            line
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    ClipData clipData = data.getClipData();
                    for(int i = 0; i < clipData.getItemCount(); i++) {
                        String s = readData(clipData.getItemAt(i).getUri());
                    }
                }
            }
        }
    }

    private String readData(Uri uri) {
        return uri.getPath();
    }
}
