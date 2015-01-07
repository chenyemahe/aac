package com.acme.amazon.orderrecord;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class AddNewOrder extends Activity implements OnClickListener{

    private AutoCompleteTextView mItemACT;
    private EditText mItemQuantity;
    private Button mItemAddBT;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_order);
        setLayoutView();
    }
    
    private void setLayoutView(){
        mItemACT = (AutoCompleteTextView) findViewById(R.id.act_add_item);
        ArrayAdapter<String> sAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.list_of_item));
        mItemACT.setAdapter(sAdapter);
        mItemQuantity = (EditText) findViewById(R.id.ed_quantity);
        mItemAddBT = (Button) findViewById(R.id.bt_add_item);
        mItemAddBT.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.bt_add_item:
            
            break;

        default:
            break;
        }
    }
}
