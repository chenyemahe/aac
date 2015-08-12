
package com.acme.amazon.amazonpage;

import com.acme.amazon.AAManager;
import com.acme.amazon.AAProduct;
import com.acme.amazon.AAUtils;
import com.acme.amazon.orderrecord.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

public class ProductListDetailPage extends Activity implements OnClickListener{

    public static final String AMAZON_PRODUCT_ADD = "amazon_product_add";

    public static final String AMAZON_PRODUCT_VIEW = "amazon_product_view";

    public static final String INTENT_EXTRA_DETAIL_PAGE = "extra_detail_page";

    private String mPageType;

    private String mProductName;

    private TableLayout mViewLayout;

    private TableLayout mAddLayout;

    private AAProduct mProduct;

    private TextView mProduct_Name;

    private TextView mShop_comPrice;

    private TextView mMaFullPrice;

    private TextView mBVpoint;

    private TextView mBVtoDollar;

    private TextView mFbaPreFee;

    private TextView mFBAShipping;

    private TextView mAmazonRefFee;

    private TextView mAmazonBasePrice;

    private TextView mAmazonPricewithBV;

    private TextView mSalePriceOnAm;

    private TextView mProfit;

    private Button mSubmitBT;

    private EditText mProduct_Name_ED;

    private EditText mShop_comPrice_ED;

    private EditText mMaFullPrice_ED;

    private EditText mBVpoint_ED;

    private EditText mFbaPreFee_ED;

    private EditText mFBAShipping_ED;

    private EditText mSalePriceOnAm_ED;

    private TextView mBVtoDollar_ADD;

    private TextView mAmazonRefFee_ADD;

    private TextView mAmazonBasePrice_ADD;

    private TextView mAmazonPricewithBV_ADD;

    private TextView mProfit_ADD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa_prod_list_detal_page);
        mPageType = getIntent().getStringExtra(INTENT_EXTRA_DETAIL_PAGE);
        mViewLayout = (TableLayout) findViewById(R.id.tl_prod_view);
        mAddLayout = (TableLayout) findViewById(R.id.tl_prod_add);
        mSubmitBT = (Button) findViewById(R.id.submit);
        mSubmitBT.setOnClickListener(this);
        mProduct_Name_ED = (EditText) findViewById(R.id.product_name_ed);
        mProduct_Name = (TextView) findViewById(R.id.product_name);

        if (TextUtils.equals(mPageType, AMAZON_PRODUCT_VIEW)) {
            mProductName = getIntent().getStringExtra(AAUtils.INTENT_PRODUCT_NAME);
            if (mProduct_Name != null) {
                mProduct = AAManager.getManager().getDB()
                        .getAAProductByName(getContentResolver(), mProductName);
                if (mProduct != null) {
                    setViewPage();
                }
            }
        }
        if (TextUtils.equals(mPageType, AMAZON_PRODUCT_ADD)) {
            setAddPage();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setViewPage() {
        mProduct_Name = (TextView) findViewById(R.id.product_name);
        mShop_comPrice = (TextView) findViewById(R.id.tr_1_2);
        mMaFullPrice = (TextView) findViewById(R.id.tr_1_4);
        mBVpoint = (TextView) findViewById(R.id.tr_2_2);
        mBVtoDollar = (TextView) findViewById(R.id.tr_2_4);
        mFbaPreFee = (TextView) findViewById(R.id.tr_3_2);
        mFBAShipping = (TextView) findViewById(R.id.tr_3_4);
        mAmazonRefFee = (TextView) findViewById(R.id.tr_4_2);
        mAmazonBasePrice = (TextView) findViewById(R.id.tr_4_4);
        mAmazonPricewithBV = (TextView) findViewById(R.id.tr_5_2);
        mSalePriceOnAm = (TextView) findViewById(R.id.tr_5_4);
        mProfit = (TextView) findViewById(R.id.tr_6_2);

        mProduct_Name.setText(mProduct.getProductName());
        mShop_comPrice.setText(mProduct.getShop_comPrice());
        mMaFullPrice.setText(mProduct.getMaFullPrice());
        mBVpoint.setText(mProduct.getBVpoint());
        mBVtoDollar.setText(mProduct.getBVtoDollar());
        mFbaPreFee.setText(mProduct.getFbaPreFee());
        mFBAShipping.setText(mProduct.getFBAShipping());
        mAmazonRefFee.setText(mProduct.getAmazonRefFee());
        mAmazonBasePrice.setText(mProduct.getAmazonBasePrice());
        mAmazonPricewithBV.setText(mProduct.getAmazonPricewithBV());
        mSalePriceOnAm.setText(mProduct.getSalePriceOnAm());
        mProfit.setText(mProduct.getProfit());

        statusChangeView(View.VISIBLE);
        statusChangeAddView(View.GONE);
    }

    private void setAddPage() {
        mShop_comPrice_ED = (EditText) findViewById(R.id.tr2_1_2);
        mMaFullPrice_ED = (EditText) findViewById(R.id.tr2_1_4);
        mBVpoint_ED = (EditText) findViewById(R.id.tr2_2_2);
        mFbaPreFee_ED = (EditText) findViewById(R.id.tr2_3_2);
        mFBAShipping_ED = (EditText) findViewById(R.id.tr2_3_4);
        mSalePriceOnAm_ED = (EditText) findViewById(R.id.tr2_5_4);

        mBVtoDollar_ADD = (TextView) findViewById(R.id.tr2_2_4);
        mAmazonRefFee_ADD = (TextView) findViewById(R.id.tr2_4_2);
        mAmazonBasePrice_ADD = (TextView) findViewById(R.id.tr2_4_4);
        mAmazonPricewithBV_ADD = (TextView) findViewById(R.id.tr2_5_2);
        mProfit_ADD = (TextView) findViewById(R.id.tr2_6_2);

        mMaFullPrice_ED.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mAmazonBasePrice_ADD.setText(AAUtils.calAmazonBasePrice(mMaFullPrice_ED.getText()
                        .toString(), mFBAShipping_ED.getText().toString(), mFbaPreFee_ED.getText()
                        .toString()));
                mAmazonPricewithBV_ADD.setText(AAUtils.calAmazonPricewithBV(mMaFullPrice_ED
                        .getText().toString(), mFBAShipping_ED.getText().toString(), mFbaPreFee_ED
                        .getText().toString(), mBVtoDollar_ADD.getText().toString()));
            }
        });

        mBVpoint_ED.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mBVtoDollar_ADD.setText(AAUtils.calBVtoDollar(mBVpoint_ED.getText().toString()));
            }
        });

        mFbaPreFee_ED.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mAmazonBasePrice_ADD.setText(AAUtils.calAmazonBasePrice(mMaFullPrice_ED.getText()
                        .toString(), mFBAShipping_ED.getText().toString(), mFbaPreFee_ED.getText()
                        .toString()));
                mAmazonPricewithBV_ADD.setText(AAUtils.calAmazonPricewithBV(mMaFullPrice_ED
                        .getText().toString(), mFBAShipping_ED.getText().toString(), mFbaPreFee_ED
                        .getText().toString(), mBVtoDollar_ADD.getText().toString()));
            }
        });

        mFBAShipping_ED.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mAmazonBasePrice_ADD.setText(AAUtils.calAmazonBasePrice(mMaFullPrice_ED.getText()
                        .toString(), mFBAShipping_ED.getText().toString(), mFbaPreFee_ED.getText()
                        .toString()));
                mAmazonPricewithBV_ADD.setText(AAUtils.calAmazonPricewithBV(mMaFullPrice_ED
                        .getText().toString(), mFBAShipping_ED.getText().toString(), mFbaPreFee_ED
                        .getText().toString(), mBVtoDollar_ADD.getText().toString()));
            }
        });

        mSalePriceOnAm_ED.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mAmazonRefFee_ADD.setText(AAUtils.calAmazonRefFee(mSalePriceOnAm_ED.getText()
                        .toString()));
                mProfit_ADD.setText(AAUtils.calProfit(mAmazonBasePrice_ADD.getText().toString(),
                        mSalePriceOnAm_ED.getText().toString()));
            }
        });

        statusChangeView(View.GONE);
        statusChangeAddView(View.VISIBLE);
    }

    private void statusChangeAddView(int status) {
        mAddLayout.setVisibility(status);
        mSubmitBT.setVisibility(status);
        mProduct_Name_ED.setVisibility(status);
    }

    private void statusChangeView(int status) {
        mViewLayout.setVisibility(status);
        mProduct_Name.setVisibility(status);
    }

    private AAProduct getProdFromAddView() {
        AAProduct product = new AAProduct();
        product.setAmazonBasePrice(mAmazonBasePrice_ADD.getText().toString());
        product.setAmazonPricewithBV(mAmazonPricewithBV_ADD.getText().toString());
        product.setAmazonRefFee(mAmazonRefFee_ADD.getText().toString());
        product.setBVpoint(mBVpoint_ED.getText().toString());
        product.setBVtoDollar(mBVtoDollar_ADD.getText().toString());
        product.setFbaPreFee(mFbaPreFee_ED.getText().toString());
        product.setFBAShipping(mFBAShipping_ED.getText().toString());
        product.setMaFullPrice(mMaFullPrice_ED.getText().toString());
        product.setProductName(mProduct_Name_ED.getText().toString());
        product.setProfit(mProfit_ADD.getText().toString());
        product.setSalePriceOnAm(mSalePriceOnAm_ED.getText().toString());
        product.setShop_comPrice(mShop_comPrice_ED.getText().toString());
        return product;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.submit) {
            AAManager.getManager().getDB().saveAAProduct(getContentResolver(), getProdFromAddView());
            finish();
        }
    }
}
