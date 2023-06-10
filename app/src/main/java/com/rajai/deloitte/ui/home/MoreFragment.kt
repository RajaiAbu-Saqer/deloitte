package com.rajai.deloitte.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.rajai.deloitte.R
import com.rajai.deloitte.base_ui.BaseMainFragment
import com.rajai.deloitte.databinding.FragmentMoreBinding
import com.rajai.deloitte.ui.registration.RegistrationModel
import com.rajai.deloitte.utility.CryptoPrefsUtil
import com.rajai.deloitte.utility.DrawableTools

class MoreFragment : BaseMainFragment<FragmentMoreBinding>(), OnClickListener {
    override fun getViewBinding() = FragmentMoreBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        binding?.logout?.setOnClickListener(this)
        binding?.changeLanguage?.setOnClickListener(this)
        CryptoPrefsUtil.instance.apply {
            val email = getString(CryptoPrefsUtil.AUTHORIZATION)

            getObject(
                email ?: "",
                RegistrationModel::class.java
            )?.let {
                binding?.information?.text =
                    "${getString(R.string.nationality)}\n${it.nationality}\n\n" +
                            "${getString(R.string.email)}\n ${it.email} \n\n" +
                            "${getString(R.string.phone_number)}\n${it.phoneNumber}\n\n" +
                            "${getString(R.string.date_of_birth)}\n${it.dateOfBirth}"
                binding?.fullName?.text = it.firstName
                binding?.email?.text = it.email
                binding?.image?.let { imageView -> DrawableTools.setImageUrl(imageView, "") }
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding?.logout?.id -> logout()
            binding?.changeLanguage?.id -> {
                CryptoPrefsUtil.instance.changeLanguage()
                reCreateApp()
            }
        }
    }

    private fun logout() {
        CryptoPrefsUtil.instance.clearSessions()
        mainActivity.apply {
            reCreateApp()
        }
    }
}