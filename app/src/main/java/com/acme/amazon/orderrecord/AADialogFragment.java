package com.acme.amazon.orderrecord;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class AADialogFragment extends DialogFragment {

    private AADialogListener listener;
    private DatePicker mDatePicker;
    private String mDate;
    public static final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // create dialog
        View mView = LayoutInflater.from(getActivity()).inflate(R.layout.timepicker, null);
        setDate(mView);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(mView)
                .setPositiveButton(R.string.dialog_submit, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (listener != null) {
                            listener.onSubmitChange();
                        }
                        getActivity().finish();
                    }
                });
        AlertDialog myDialog = builder.create();
        return myDialog;
    }

    public void setListener(AADialogListener l) {
        listener = l;
    }

    private void setDate(View v) {
        mDatePicker = (DatePicker) v.findViewById(R.id.datePicker);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        mDate  = dateFormatter.format(c.getTime());
        mDatePicker.init(year, month, day, new OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mDate = dateFormatter.format(newDate.getTime());
            }
        });
    }

    public interface AADialogListener {
        void onSubmitChange();
    }

    public String getDate() {
        return mDate;
    }
}
