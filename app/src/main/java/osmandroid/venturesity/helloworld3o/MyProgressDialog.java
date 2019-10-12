package osmandroid.venturesity.helloworld3o;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class MyProgressDialog {

    private AlertDialog dialog;

    public MyProgressDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        dialog = builder.create();
    }


    void showPDiialog()
    {
        dialog.show();
    }

    void dismissPDialog()
    {
        if(dialog.isShowing())
            dialog.dismiss();
    }
}
