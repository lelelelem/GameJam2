package nativeUtil;

import nativeUtils.NativeInterface;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class NativeSupport implements NativeInterface{
    
    private Context context;
  
    public NativeSupport(Context context){
        this.context = context;
    }
    
    public void showDialog(final OnResponseListener responseListener){
        new AlertDialog.Builder(context).setTitle("Connection Error").setMessage("Connection Failed!").setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                responseListener.doAction();
            }
        }).show();
    }

}
