package com.zlx.module_login.login;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.zlx.module_base.base_fg.BaseMvvmFg;
import com.zlx.module_login.BR;
import com.zlx.module_login.R;
import com.zlx.module_login.R2;
import com.zlx.module_login.databinding.FgAccountLoginBinding;
import com.zlx.module_network.widget.popwindow.PopUtil;

import butterknife.OnClick;

/**
 * FileName: AccountLoginFg
 * Created by zlx on 2020/9/21 14:54
 * Email: 1170762202@qq.com
 * Description: 账号登录
 */
public class AccountLoginFg extends BaseMvvmFg<FgAccountLoginBinding, AccountLoginViewModel> {

    @Override
    protected void initViews() {
        super.initViews();
        viewModel.loginLiveData.observe(this, userInfo -> {
            binding.btnLogin.reset();
        });
    }

    @Override
    protected int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fg_account_login;
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }


    private void login() {
        String username = binding.etAccount.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            PopUtil.show("请输入账号");
            return;
        }
        String password = binding.etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            PopUtil.show("请输入密码");
            return;
        }

        binding.btnLogin.startAnim();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewModel.login(username, password);
            }
        }, 2000);

    }

    @Override
    public void onStop() {
        super.onStop();
        binding.btnLogin.reset();
    }

    @OnClick(R2.id.btnLogin)
    public void onViewClicked() {
        login();

    }

    @OnClick(R2.id.ivCha)
    public void cha() {
        getActivity().finish();
    }

    @OnClick(R2.id.tvRegister)
    public void register() {
        NavHostFragment.findNavController(this).navigate(R.id.action_fragment_register);
    }
}
