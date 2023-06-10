package com.rajai.deloitte.ui.registration

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import com.rajai.deloitte.R
import com.rajai.deloitte.base_ui.BaseMainFragment
import com.rajai.deloitte.databinding.FragmentSigninBinding
import com.rajai.deloitte.utility.CryptoPrefsUtil
import com.rajai.deloitte.utility.ViewTools

class SigninFragment : BaseMainFragment<FragmentSigninBinding>(), OnClickListener {
    override fun getViewBinding() = FragmentSigninBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding?.login?.setOnClickListener(this)
        binding?.changeLanguage?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding?.login?.id -> verifyFieldsAndLogin()
            binding?.changeLanguage?.id -> {
                CryptoPrefsUtil.instance.changeLanguage()
                reCreateApp()
            }
        }
    }

    private fun verifyFieldsAndLogin() {
        binding?.emailEtx?.let { email ->
            if (ViewTools.isEmptyEditText(email, getString(R.string.error_email_required))) return
            if (ViewTools.isValidEmail(
                    email,
                    resources.getString(R.string.error_invalide_email_format)
                )
            ) return
        } ?: return

        binding?.passwordEtx?.let { password ->
            if (ViewTools.isEmptyEditText(
                    password,
                    getString(R.string.error_required_password)
                )
            ) return
        } ?: return
        checkValidationLogin()
    }

    private fun checkValidationLogin() {
        CryptoPrefsUtil.instance.getObject(
            binding?.emailEtx?.text.toString(),
            RegistrationModel::class.java
        )?.let {
            if (binding?.emailEtx?.text.toString()
                    .equals(it.email, ignoreCase = false) && binding?.passwordEtx?.text?.toString()
                    .equals(it.password)
            )
                signInSuccessfully()
            else invalidLogIn()
        } ?: invalidLogIn()
    }

    private fun invalidLogIn() {
        Toast.makeText(mainActivity, getString(R.string.invalid_login), Toast.LENGTH_SHORT).show()
    }

    private fun signInSuccessfully() {
        CryptoPrefsUtil.instance.setValue(
            CryptoPrefsUtil.AUTHORIZATION,
            binding?.emailEtx?.text.toString()
        )
        navigateTo(RegistrationFragmentDirections.actionNavigationRegistrationToNavigationDashboard())
    }
}