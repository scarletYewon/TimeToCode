package com.kmu.timetocode.login;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.kmu.timetocode.R;

public class CustomProgressDialog extends Dialog {
    public CustomProgressDialog(Context context) {
        super(context);
        setCanceledOnTouchOutside(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
    }
}
