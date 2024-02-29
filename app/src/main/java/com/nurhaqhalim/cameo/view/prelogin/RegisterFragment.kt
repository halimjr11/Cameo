package com.nurhaqhalim.cameo.view.prelogin

import android.text.method.LinkMovementMethod
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.nurhaqhalim.cameo.R
import com.nurhaqhalim.cameo.components.CustomSnackbar
import com.nurhaqhalim.cameo.core.base.BaseFragment
import com.nurhaqhalim.cameo.core.domain.state.onCreated
import com.nurhaqhalim.cameo.core.domain.state.onValue
import com.nurhaqhalim.cameo.core.utils.launchAndCollectIn
import com.nurhaqhalim.cameo.databinding.FragmentRegisterBinding
import com.nurhaqhalim.cameo.utils.toSpannableHyperLink
import com.nurhaqhalim.cameo.viewmodel.PreLoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterFragment :
    BaseFragment<FragmentRegisterBinding, PreLoginViewModel>(FragmentRegisterBinding::inflate) {
    override val viewModel: PreLoginViewModel by viewModel()

    override fun initView() {
        with(binding) {
            val color = context?.let { ContextCompat.getColor(it, R.color.purple) }
            mtRegister.title = resources.getString(R.string.register_title)
            tilRegisterEmail.hint = resources.getString(R.string.email_label)
            tilRegisterPassword.hint = resources.getString(R.string.password_label)
            btnRegister.text = resources.getString(R.string.button_register)
            if (color != null) {
                tvRegisterTnc.movementMethod = LinkMovementMethod()
                tvRegisterTnc.text =
                    resources.getString(R.string.already_have_account_yet).toSpannableHyperLink(
                        isLogin = false, resources.configuration.locales[0].language, color
                    ) {
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }
            }
        }
    }

    override fun observeData() {
        with(viewModel) {
            validateRegisterEmail.launchAndCollectIn(viewLifecycleOwner) { state ->
                state.onCreated {}
                    .onValue { isValid ->
                        binding.run {
                            tilRegisterEmail.isErrorEnabled = isValid.not()
                            if (isValid) {
                                tilRegisterEmail.error = null
                            } else {
                                tilRegisterEmail.error = resources.getString(R.string.error_email)
                            }
                        }
                    }
            }
            validateRegisterPassword.launchAndCollectIn(viewLifecycleOwner) { state ->
                state.onCreated {}
                    .onValue { isValid ->
                        binding.run {
                            tilRegisterPassword.isErrorEnabled = false
                            if (isValid) {
                                tilRegisterPassword.error = null
                            } else {
                                tilRegisterPassword.error =
                                    resources.getString(R.string.error_password)
                            }
                        }
                    }
            }
            validateRegisterField.launchAndCollectIn(viewLifecycleOwner) { state ->
                state.onCreated {}
                    .onValue { isPass ->
                        binding.run {
                            tilRegisterEmail.isErrorEnabled = isPass.not()
                            tilRegisterPassword.isErrorEnabled = isPass.not()
                            if (isPass) {
                                val email = tietRegisterEmail.text.toString().trim()
                                val password = tietRegisterPassword.text.toString().trim()
                                doRegister(email, password)
                            } else {
                                tilRegisterEmail.error =
                                    resources.getString(R.string.required_email)
                                tilRegisterPassword.error =
                                    resources.getString(R.string.required_password)
                                context?.let { ctx ->
                                    CustomSnackbar.showSnackBar(
                                        ctx,
                                        binding.root,
                                        resources.getString(R.string.required_message)
                                    )
                                }
                            }
                        }
                    }
            }
        }
    }

    override fun initListener() {
        with(binding) {
            tietRegisterEmail.doOnTextChanged { text, _, before, _ ->
                if (before.toString().isNotEmpty()) viewModel.validateRegisterEmail(text.toString())
            }

            tietRegisterPassword.doOnTextChanged { text, _, before, _ ->
                if (before.toString()
                        .isNotEmpty()
                ) viewModel.validateRegisterPassword(text.toString())
            }
            btnRegister.setOnClickListener {
                val email = tietRegisterEmail.text.toString().trim()
                val password = tietRegisterPassword.text.toString().trim()
                if (tilRegisterEmail.isErrorEnabled.not() && tilRegisterPassword.isErrorEnabled.not()) {
                    viewModel.validateRegisterField(email, password)
                } else {
                    context?.let { ctx ->
                        CustomSnackbar.showSnackBar(
                            ctx, binding.root, resources.getString(R.string.field_error_message)
                        )
                    }
                }
                viewModel.resetRegisterField()
            }
        }
    }

    fun doRegister(email: String, password: String) {
        viewModel.fetchRegister(email, password).launchAndCollectIn(viewLifecycleOwner) { success ->
            if (success) findNavController().navigate(R.id.action_registerFragment_to_profileFragment)
        }
    }

}