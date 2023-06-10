package com.rajai.deloitte.ui.registration

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import com.rajai.deloitte.R
import com.rajai.deloitte.base_ui.BaseMainFragment
import com.rajai.deloitte.databinding.DatePickerDialogBinding
import com.rajai.deloitte.databinding.FragmentSignupBinding
import com.rajai.deloitte.dialog.DatePickerBottomSheet
import com.rajai.deloitte.generic.GeneralBottomSheetDialog
import com.rajai.deloitte.utility.CryptoPrefsUtil
import com.rajai.deloitte.utility.ViewTools
import java.util.*

class SignupFragment : BaseMainFragment<FragmentSignupBinding>(), OnClickListener {
    override fun getViewBinding() = FragmentSignupBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding?.registration?.setOnClickListener(this)
        binding?.dateOfBirthEtx?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding?.registration?.id -> verifyFieldsAndRegistration()
            binding?.dateOfBirthEtx?.id -> datePickerDialog()
        }
    }

    private fun datePickerDialog() {
        object : DatePickerBottomSheet(mainActivity) {
            override fun onLayoutCreated(
                view: GeneralBottomSheetDialog<DatePickerDialogBinding>
            ) {
                super.onLayoutCreated(view)
                binding.apply {
                    datePicker.maxDate = Date().time
                }
            }

            override fun submitDate(date: String) {
                setDateText(date)
            }

            override fun getViewBinding() = DatePickerDialogBinding.inflate(layoutInflater)
        }.dismissible().cancellable().show()
    }

    private fun setDateText(date: String) {
        binding?.dateOfBirthEtx?.setText(date)
        removeDoBFocus()
    }

    private fun removeDoBFocus() {
        binding?.dateOfBirthEtx?.let { ViewTools.removeEditTextError(it) }
    }

    private fun verifyFieldsAndRegistration() {
        if (binding?.firstNameEtx?.let {
                ViewTools.firstLastNameMinLengthValidation(
                    it,
                    resources.getString(R.string.error_first_name_required),
                    resources.getString(R.string.error_service_length_of_first_last_is_less_thank_two_characters),
                    resources.getString(R.string.error_first_name_invalid)
                )
            } == true
        ) return
        binding?.emailEtx?.let { email ->
            if (ViewTools.isEmptyEditText(email, getString(R.string.error_email_required))) return
            if (ViewTools.isValidEmail(
                    email,
                    resources.getString(R.string.error_invalide_email_format)
                )
            ) return
        } ?: return
        if (binding?.nationalityEtx?.let {
                ViewTools.isEmptyEditText(it, getString(R.string.error_nationality_required))
            } == true)
            return

        binding?.phoneNumberEtx?.let { phoneNumber ->
            if (ViewTools.isEmptyEditText(
                    phoneNumber,
                    getString(R.string.error_required_phone_number)
                )
            ) return
            if (ViewTools.isValidPhoneNumber(
                    phoneNumber,
                    getString(R.string.error_invalid_phone_number)
                )
            ) return
        } ?: return
        if (binding?.dateOfBirthEtx?.let {
                ViewTools.isEmptyEditText(
                    it,
                    getString(R.string.error_gender_required)
                )
            } == true
        ) {
            Toast.makeText(
                mainActivity,
                getString(R.string.error_gender_required),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        binding?.passwordEtx?.let { password ->
            if (ViewTools.isEmptyEditText(
                    password,
                    getString(R.string.error_required_password)
                )
            ) return
            if (ViewTools.isEditTextCharLessThan(
                    password,
                    8,
                    getString(R.string.error_password_eight_digits)
                )
            ) return
            if (!ViewTools.isComplexPassword(
                    password,
                    getString(R.string.error_complix_password)
                )
            ) return
        } ?: return
        checkValidationSignup()
    }

    private fun checkValidationSignup(){
        CryptoPrefsUtil.instance.getObject(
            binding?.emailEtx?.text.toString(),
            RegistrationModel::class.java
        )?.let {
            Toast.makeText(mainActivity, getString(R.string.error_email_already_exists), Toast.LENGTH_SHORT).show()
        }?:signupSuccessfully()
    }
    private fun signupSuccessfully() {
        CryptoPrefsUtil.instance.apply {
            setValue(
                CryptoPrefsUtil.AUTHORIZATION,
                binding?.emailEtx?.text.toString()
            )
            setValue(
                binding?.emailEtx?.text.toString(),
                RegistrationModel(
                    firstName = binding?.firstNameEtx?.text.toString(),
                    email = binding?.emailEtx?.text.toString(),
                    nationality = binding?.nationalityEtx?.text.toString(),
                    phoneNumber = binding?.countryText?.text.toString() + binding?.phoneNumberEtx?.text?.toString(),
                    dateOfBirth = binding?.dateOfBirthEtx?.text.toString(),
                    password = binding?.passwordEtx?.text.toString()
                )
            )
        }
        navigateTo(RegistrationFragmentDirections.actionNavigationRegistrationToNavigationDashboard())
    }
}