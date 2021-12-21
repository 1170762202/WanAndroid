package com.zlx.module_login.register;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.zlx.module_base.base_fg.BaseMvvmFg;
import com.zlx.module_base.event.EventHandlers;
import com.zlx.module_login.BR;
import com.zlx.module_login.R;
import com.zlx.module_login.databinding.FgAccountRegisterBinding;
import com.zlx.module_network.widget.popwindow.PopUtil;

/**
 * FileName: AccountLoginFg
 * Created by zlx on 2020/9/21 14:54
 * Email: 1170762202@qq.com
 * Description: 账号注册
 */
public class AccountRegisterFg extends BaseMvvmFg<FgAccountRegisterBinding, AccountRegisterViewModel> {

    @Override
    protected void initViews() {
        super.initViews();
        binding.setEventHandlers(new RegisterEvent());
        binding.btnLogin.setText("注册");
        viewModel.registerLiveData.observe(this, userInfo -> {
            binding.btnLogin.success();
        });
    }

    @Override
    protected int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fg_account_register;
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }


    private void register() {
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
                viewModel.register(username, password);
            }
        }, 2000);
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.btnLogin.reset();
    }


    public class RegisterEvent extends EventHandlers {
        public void onLoginClick() {
            register();
        }

        public void onCloseClick() {
            NavHostFragment.findNavController(AccountRegisterFg.this).navigateUp();
        }

    }
}
