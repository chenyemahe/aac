package com.acme.amazon;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.acme.amazon.orderrecord.AAMainOrderListPage;
import com.acme.amazon.orderrecord.R;

import java.util.ArrayList;

/**
 * Created by ye1chen on 9/21/16.
 */
public class PermissionDialogPage extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> permissions = new ArrayList<String>();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        ActivityCompat.requestPermissions(this,
                permissions.toArray(new String[permissions.size()]), 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(permissions.length==0){
            return;
        }

        boolean permissionGranted = true;
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                permissionGranted = false;
                break;
            }
        }

        if (permissionGranted) {
            this.finish();
        } else {
            showDialog();
        }
    }

    void showDialog() {
        DialogFragment newFragment = MyPermFileReadDialogFragment.newInstance(
                R.string.permission_deny_title);
        newFragment.show(getFragmentManager(), "dialog");
    }
    public static class MyPermFileReadDialogFragment extends DialogFragment {

        public static MyPermFileReadDialogFragment newInstance(int title) {
            MyPermFileReadDialogFragment frag = new MyPermFileReadDialogFragment();
            Bundle args = new Bundle();
            args.putInt("title", title);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int title = getArguments().getInt("title");

            return new AlertDialog.Builder(getActivity())
                    .setTitle(title)
                    .setMessage(R.string.permission_deny_close)
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    startActivity(new Intent(getActivity(), AAMainOrderListPage.class));
                                    getActivity().finish();
                                }
                            }
                    )
                    .create();
        }
    }
}
