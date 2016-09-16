package com.acme.amazon.amazonpage.order;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;
import com.acme.amazon.AAConstant;
import com.acme.amazon.AAManager;
import com.acme.amazon.AAUtils;
import com.acme.amazon.listsupport.AAExpandableListAdapter;
import com.acme.amazon.orderrecord.R;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by ye1chen on 9/1/16.
 */
public class AaTransUpdatePage extends Activity implements View.OnClickListener,
        ExpandableListView.OnChildClickListener {

    private Button mFilePickBt;
    private static final int REQUEST_CODE_CSV = 7715;
    private boolean isReadData;
    private ExpandableListView mListView;
    private AAExpandableListAdapter mExpandAdapter;

    private ArrayList<ArrayList<ArrayList<TransactionNode>>> mExpandDataList;
    private ArrayList<ArrayList<TransactionNode>> mChildList;
    private ProgressDialog mProgressDialog;

    private static final String file_prex = "file://";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa_trans_update_page);
        mFilePickBt = (Button) findViewById(R.id.bt_file_picker);
        mFilePickBt.setOnClickListener(this);
        mListView = (ExpandableListView) findViewById(R.id.expand_lv);
        mExpandAdapter = new AAExpandableListAdapter(AAUtils.EXPAND_ADAPTER_TRANS);
        mListView.setAdapter(mExpandAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new QueryTransData().execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_file_picker:
                showChooser();
                break;
        }
    }

    private void setSignleLevelChildData() {
        if (mExpandDataList != null && mExpandDataList.size() != 0) {
            for (int i = 0; i < mExpandDataList.size(); i++) {
                if (mChildList.size() <= i) {
                    mChildList.add(new ArrayList<TransactionNode>());
                }
                for (int j = mExpandDataList.get(i).size() - 1; j >= 0; j--) {
                    for (int k = 0; k < mExpandDataList.get(i).get(j).size(); k++) {
                        mChildList.get(i).add(mExpandDataList.get(i).get(j).get(k));
                    }
                }
            }
        }
    }

    private void setExpViewData() {
        mChildList = new ArrayList<>();
        setSignleLevelChildData();
        mExpandAdapter.setTransListData(mExpandDataList, mChildList, this);
        mListView.setOnChildClickListener(this);
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
        String[] nodes = l.split("\",\"");
        nodes = AAUtils.stripLeadingAndTrailingQuotes(nodes);
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

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        return false;
    }

    /**
     * Create a dialog and show it user when the provisioning is going on.
     */
    private void showProgressBar(String message, boolean cancelable) {
        if(null == mProgressDialog) { //resolve the PLM ticket P160826-04769 (loading window keeps showing)
            mProgressDialog = new ProgressDialog(AaTransUpdatePage.this);
            mProgressDialog.setMessage(message);
            mProgressDialog.setCancelable(cancelable);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.show();
        }
    }

    /**
     * Make sure the Progress Dialog is not null and actually showing.
     */
    private void dismissProgressBar() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }


    private class QueryTransData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            showProgressBar("Loading", false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            mExpandDataList = AAUtils.sortTransOrderByDate(AAManager.getManager().getDB()
                    .getAllTransOrder(getContentResolver()));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setExpViewData();
            dismissProgressBar();
        }
    }
}
