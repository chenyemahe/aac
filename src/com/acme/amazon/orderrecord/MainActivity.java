package com.acme.amazon.orderrecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.acme.amazon.AAConstant;
import com.acme.amazon.listsupport.AAListViewAdapter;

public class MainActivity extends ActionBarActivity implements OnClickListener{
    private ListView mListView;
    private Button mAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView1);
        mListView.setAdapter(new AAListViewAdapter(this,R.layout.aalistitem,AAConstant.ADAPTER_ORIDER_LIST));
        mAdd = (Button) findViewById(R.id.bt_add);
        mAdd.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.edit_product_list) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.bt_add:
            startActivity(new Intent(this, AddNewOrder.class));
            break;

        default:
            break;
        }
        
    }
}
