package com.acme.amazon.amazonpage.order;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.acem.amazon.logging.Logging;
import com.acme.amazon.AAConstant;
import com.acme.amazon.AAManager;
import com.acme.amazon.orderrecord.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ye1chen on 9/1/16.
 */
public class AaTransUpdatePage extends Activity implements View.OnClickListener {

    private Button mFilePickBt;
    private static final int REQUEST_CODE_CSV = 7715;
    private boolean isReadData;

    private static final String file_prex = "file://";

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
            case R.id.bt_file_picker:
                showChooser();
                break;
        }
    }

    private void showChooser() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        Intent sIntent = new Intent(AAConstant.ACTION_FOLDER_PICKER);
        sIntent.putExtra("CONTENT_TYPE", "*/*");
        sIntent.addCategory(Intent.CATEGORY_DEFAULT);

        Intent chooserIntent;

        try {

            chooserIntent = Intent.createChooser(intent, getString(R.string.pick_your_file));
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                    new Intent[] { sIntent });
            startActivityForResult(chooserIntent, REQUEST_CODE_CSV);
        } catch (Exception ex) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CSV && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if (uri != null) {
                // Single URI return
                if (!isFileCSVtype(uri)) {
                    return;
                }
                try {
                    InputStream is = null;
                    if(uri.toString().contains(file_prex)) {
                        String realPath = uri.toString().replace(file_prex,"");
                        File fi = new File(realPath);
                        is = new FileInputStream(fi);
                    } else {
                        is = getContentResolver().openInputStream(uri);
                    }
                    if (is != null) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                        String line;
                        isReadData = false;
                        while ((line = reader.readLine()) != null) {
                            readCSVAaTransUpdate(line);
                        }
                        if(isReadData)
                            isReadData = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    ClipData clipData = data.getClipData();
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        String s = readData(clipData.getItemAt(i).getUri());
                    }
                }
            }
        }
    }

    private boolean isFileCSVtype(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            if (!result.contains(".csv")) {
                Toast.makeText(this, getString(R.string.toast_csv_file_only), Toast.LENGTH_LONG).show();
                return false;
            }
            cursor.close();
        }
        return true;
    }

    private String readData(Uri uri) {
        return uri.getPath();
    }


    private void readCSVAaTransUpdate(String line) {
        if (isReadData) {
            saveLineToDb(line);
        } else {
            if (isReachNameList(line)) {
                isReadData = true;
            }
        }
    }

    private boolean isReachNameList(String l) {
        return (l.contains(AAConstant.aa_tran_date) && l.contains(AAConstant.settlement_id) && l.contains(AAConstant.aa_type) && l.contains(AAConstant.order_id) && l.contains(AAConstant.sku)
                && l.contains(AAConstant.description) && l.contains(AAConstant.quantiy) && l.contains(AAConstant.marketPlace) && l.contains(AAConstant.fulfillment) && l.contains(AAConstant.order_city)
                && l.contains(AAConstant.order_state) && l.contains(AAConstant.order_postal) && l.contains(AAConstant.product_sales) && l.contains(AAConstant.shipping_credits) && l.contains(AAConstant.gift_wrap_credits)
                && l.contains(AAConstant.promotional_rebates) && l.contains(AAConstant.sales_tax_collected) && l.contains(AAConstant.selling_fees) && l.contains(AAConstant.fba_fees)
                && l.contains(AAConstant.other_transaction_fees) && l.contains(AAConstant.other) && l.contains(AAConstant.total));
    }

    private void saveLineToDb(String l) {
        String[] nodes = l.split(",");
        if(nodes.length !=  0) {
            TransactionNode node = new TransactionNode();
            node.setAa_tran_date(nodes[0]);
            node.setSettlement_id(nodes[1]);
            node.setType(nodes[2]);
            node.setOrder_id(nodes[3]);
            node.setSku(nodes[4]);
            node.setDescription(nodes[5]);
            node.setQuantiy(nodes[6]);
            node.setMarketPlace(nodes[7]);
            node.setFulfillment(nodes[8]);
            node.setOrder_city(nodes[9]);
            node.setOrder_state(nodes[10]);
            node.setOrder_postal(nodes[11]);
            node.setProduct_sales(nodes[12]);
            node.setShipping_credits(nodes[13]);
            node.setGift_wrap_credits(nodes[14]);
            node.setPromotional_rebates(nodes[15]);
            node.setSales_tax_collected(nodes[16]);
            node.setSelling_fees(nodes[17]);
            node.setFba_fees(nodes[18]);
            node.setOther_transaction_fees(nodes[19]);
            node.setOther(nodes[20]);
            node.setTotal(nodes[21]);
            if (AAManager.getManager().getDB().getTransOrder(getContentResolver(), node) == null) {
                AAManager.getManager().getDB().saveTransOrder(getContentResolver(), node);
            }
        }
    }
}
