package com.fantasyapps.darmahealthcare.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.fantasyapps.darmahealthcare.R;
import com.fantasyapps.darmahealthcare.listeners.YesNoCallback;


public class CustomDialog implements View.OnClickListener {
    YesNoCallback callback;
    Dialog dialog;

    public void showDialog(Activity activity, String title, String msg, YesNoCallback callback) {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_yes_no);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        this.callback = callback;

        TextView text1 = (TextView) dialog.findViewById(R.id.tvTitle);
        text1.setText(title);
        TextView text2 = (TextView) dialog.findViewById(R.id.tvMessage);
        text2.setText(msg);

        Button dialogBtn_no = (Button) dialog.findViewById(R.id.btnNegative);
        Button dialogBtn_yes = (Button) dialog.findViewById(R.id.btnPositive);

        dialogBtn_yes.setOnClickListener(this);
        dialogBtn_no.setOnClickListener(this);

        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPositive:
                if (callback != null)
                    callback.onYes();
                dialog.dismiss();
                break;

            case R.id.btnNegative:
                if (callback != null)
                    callback.onNo();
                dialog.dismiss();
                break;
        }
    }
}
