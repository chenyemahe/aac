
package com.acme.amazon.amazonpage.productlist;

import com.acme.amazon.amazonpage.fba.FbaShippingReportPage;
import com.acme.amazon.orderrecord.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ProductInventory extends Activity implements OnClickListener {

    private Button FBA_Shipping_Rec;

    private Button Product_List;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.prodcut_inventor_page);
        FBA_Shipping_Rec = (Button) findViewById(R.id.fba_shipping_rec);
        Product_List = (Button) findViewById(R.id.product_list);
        FBA_Shipping_Rec.setOnClickListener(this);
        Product_List.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fba_shipping_rec:
                startActivity(new Intent(this, FbaShippingReportPage.class));
                break;
            case R.id.product_list:
                startActivity(new Intent(this, ProductListPage.class));
                break;
            default:
                break;
        }
    }

}
