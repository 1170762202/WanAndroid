package com.zlx.module_web.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.zlx.module_web.R;
import com.zlx.module_web.R2;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebDialogFg extends DialogFragment {

    private String url;

    public WebDialogFg(String url) {
        this.url = url;
    }

    public static WebDialogFg newInstance(String url) {
        WebDialogFg fragment = new WebDialogFg(url);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fg_web, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true); //点击边际可消失
        dialog.getWindow().setWindowAnimations(R.style.dialog_animation);
//        dialog.getWindow().setLayout(100, ViewGroup.LayoutParams.WRAP_CONTENT);
        //width & height
//        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 1), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @OnClick({R2.id.tvOpen, R2.id.llCancel,R2.id.parent})
    public void onViewClick(View view) {

        if (view.getId() == R.id.tvOpen) {
            if (!TextUtils.isEmpty(url) && (url.startsWith("http") || url.startsWith("https"))) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
            dismiss();
        } else if (view.getId() == R.id.llCancel || view.getId()==R.id.parent) {
            dismiss();
        }
    }
}
