package cn.piesat.sanitation.util;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;


/**
 * Created by sen.luo on 2018/6/25.
 */

public class DialogUtils {

/*    *//**
     * 带编辑框的DiaLog
     * @param
     * @param
     *//*
    public static void   showEditDialog(final Context context , final GetEditContent getEditContent){

       *//* final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setView(editText);
        builder.setView(view);
        builder.create();*//*

        final Dialog dialog =new Dialog(context, R.style.common_alert_dialog);
        View view =View.inflate(context,R.layout.geometry_edit_dialog,null);
//        Button btCancel =view.findViewById(R.id.cancel_button);
        Button btConfirm =view.findViewById(R.id.confirm_button);
       final EditText  etGemoetry=view.findViewById(R.id.etGeometry);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etGemoetry.getText().toString())){
                    ToastUtil.showShort(context,"内容不得为空");
                    return;
                }
                getEditContent.callBackContent(etGemoetry.getText().toString());

                dialog.dismiss();
            }
        });

     *//*   btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*//*
        dialog.show();

*//*        builder.setNegativeButton("确定",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(editText.getText().toString())){
                    ToastUtil.showShort(context,"内容不得为空");
                    return;
                }

                getEditContent.callBackContent(editText.getText().toString());
            }
        });
        builder.setPositiveButton("取消" ,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });*//*

    }*/


    /**
     * 带编辑框的DiaLog  Int
     * @param context
     * @param title
     * @param getEditContent
     */
    public static void  showEditDialog(Context context, String title, EditText editText, final GetEditContent getEditContent){

//        final EditText editText =new EditText(context);
      /*  if (isInt){
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }*/
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setView(editText);
        builder.setNegativeButton("确定",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getEditContent.callBackContent(editText.getText().toString());
            }
        });
        builder.setPositiveButton("取消" ,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();

    }


    public interface GetEditContent{
        void callBackContent(String content);
    }


    /**
     * 只有确认按钮的Dialog
     * @param context
     * @param message
     * @param negativeClickListener
     */
    public static void onlyConfirmDialog(Context context, String message, DialogInterface.OnClickListener negativeClickListener){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setNegativeButton("确定", negativeClickListener);
        builder.create().show();
    }



    public static void onlyConfirmDialog(Context context, String title, String message, DialogInterface.OnClickListener negativeClickListener){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("确定", negativeClickListener);
        builder.create().show();
    }

    public static void generalDialog(Context context, String message, DialogInterface.OnClickListener negativeClickListener){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("确定", negativeClickListener);
        builder.create().show();
    }


    public static void generaCloselDialog(Context context, String message, DialogInterface.OnClickListener positiveClickListener, DialogInterface.OnClickListener negativeClickListener){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setPositiveButton("不，退出", positiveClickListener);
        builder.setNegativeButton("重新开始", negativeClickListener);
        builder.create().show();
    }


    public static void listDiaLog(Context context, String[] items, DialogInterface.OnClickListener negativeClickListener){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setItems(items,negativeClickListener);
        builder.create().show();
    }




}
