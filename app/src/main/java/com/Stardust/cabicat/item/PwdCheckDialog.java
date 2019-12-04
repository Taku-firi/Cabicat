package com.Stardust.cabicat.item;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.Stardust.cabicat.R;

public class PwdCheckDialog extends Dialog implements View.OnClickListener {

    private TextView mPwdDialog;
    private EditText mEtPwdReal;
    private ImageView mEt1, mEt2, mEt3, mEt4, mEt5, mEt6;

    public interface PasswordCallback{
        void callback(String password);
    }

    public PasswordCallback mPasswordCallback;

    public PwdCheckDialog(Context context){
        super(context, R.style.Dialog_PasswordStyle);
        initDialog();
    }

    void initDialog(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_passcheck);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        findViewById(R.id.dialog_close).setOnClickListener(this);
        mEtPwdReal = (EditText)findViewById(R.id.dialog_et_pwd_real);
        mEtPwdReal.addTextChangedListener(new PasswordEditChangedListener(mEtPwdReal));

        mEt1 = (ImageView)findViewById(R.id.et_pwd_1);
        mEt2 = (ImageView)findViewById(R.id.et_pwd_2);
        mEt3 = (ImageView)findViewById(R.id.et_pwd_3);
        mEt4 = (ImageView)findViewById(R.id.et_pwd_4);
        mEt5 = (ImageView)findViewById(R.id.et_pwd_5);
        mEt6 = (ImageView)findViewById(R.id.et_pwd_6);
    }

    private void requestFocus(){
        mEtPwdReal.requestFocus();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }


    //reset
    public void clearPasswordText(){
        mEtPwdReal.setText("");
        requestFocus();
    }


    //set callback
    public void setPasswordCallback(PasswordCallback passwordCallback){
        this.mPasswordCallback = passwordCallback;
    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.dialog_close:
                dismiss();
                break;
        }
    }


    private class PasswordEditChangedListener implements TextWatcher{
        private EditText mEditText;

        public PasswordEditChangedListener(EditText editText){
            this.mEditText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after){}

        @Override
        public void onTextChanged (CharSequence s, int start, int before, int count){}

        @Override
        public void afterTextChanged(Editable s){
            String ret = s.toString().trim();
            if (mEditText.getId()== R.id.dialog_et_pwd_real){
                char[] arr = ret.toCharArray();

                // set black dot (pretend the password)
                for (int i = 0; i < arr.length; i++){
                    switch (i){
                        case 0:
                            mEt1.setImageResource(R.mipmap.ic_dialog_dot);
                            break;
                        case 1:
                            mEt2.setImageResource(R.mipmap.ic_dialog_dot);
                            break;
                        case 2:
                            mEt3.setImageResource(R.mipmap.ic_dialog_dot);
                            break;
                        case 3:
                            mEt4.setImageResource(R.mipmap.ic_dialog_dot);
                            break;
                        case 4:
                            mEt5.setImageResource(R.mipmap.ic_dialog_dot);
                            break;
                        case 5:
                            mEt6.setImageResource(R.mipmap.ic_dialog_dot);
                            break;
                        default:
                            break;
                    }
                }
                clearTextView(arr.length, mEt1, mEt2, mEt3, mEt4, mEt5, mEt6);

                //auto submission
                if (arr.length == 6){
                    if (mPasswordCallback != null)
                        mPasswordCallback.callback(String.valueOf(arr));
                }

            }
        }

        // when delete pressed, clean the black dot
        void clearTextView(int length, ImageView... editTexts){
            for (int i = 0; i < 6; i++ ){
                if(i > length - 1){
                    editTexts[i].setImageResource(0);
                }
            }
        }

    }

}




