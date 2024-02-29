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
import com.nurhaqhalim.cameo.databinding.FragmentLoginBinding
import com.nurhaqhalim.cameo.utils.toSpannableHyperLink
import com.nurhaqhalim.cameo.viewmodel.PreLoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment :
    BaseFragment<FragmentLoginBinding, PreLoginViewModel>(FragmentLoginBinding::inflate) {
    override val viewModel: PreLoginViewModel by viewModel()

    override fun initView() = with(binding) {
        val color = context?.let { ContextCompat.getColor(it, R.color.purple) }
        mtLogin.title = resources.getString(R.string.login_title)
        tilLoginEmail.hint = resources.getString(R.string.email_label)
        tilLoginPassword.hint = resources.getString(R.string.password_label)
        btnLogin.text = resources.getString(R.string.button_login)
        if (color != null) {
            tvLoginTnc.movementMethod = LinkMovementMethod()
            tvLoginTnc.text = resources.getString(R.string.not_have_account_yet)
                .toSpannableHyperLink(
                    isLogin = true,
                    resources.configuration.locales[0].language,
                    color
                ) {
                    findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                }
        }
    }

    override fun observeData() {
        with(viewModel) {
            validateLoginEmail.launchAndCollectIn(viewLifecycleOwner) { state ->
                state.onCreated {}
                    .onValue { isValid ->
                        binding.run {
                            tilLoginEmail.isErrorEnabled = isValid.not()
                            if (isValid) {
                                tilLoginEmail.error = null
                            } else {
                                tilLoginEmail.error = resources.getString(R.string.error_email)
                            }
                        }
                    }
            }
            validateLoginPassword.launchAndCollectIn(viewLifecycleOwner) { state ->
                state.onCreated {}
                    .onValue { isValid ->
                        binding.run {
                            tilLoginPassword.isErrorEnabled = isValid.not()
                            if (isValid) {
                                tilLoginPassword.error = null
                            } else {
                                tilLoginPassword.error =
                                    resources.getString(R.string.error_password)
                            }
                        }
                    }
            }
            validateLoginField.launchAndCollectIn(viewLifecycleOwner) { state ->
                state.onCreated {}
                    .onValue { isPass ->
                        binding.run {
                            tilLoginEmail.isErrorEnabled = isPass.not()
                            tilLoginPassword.isErrorEnabled = isPass.not()
                            if (isPass) {
                                val email = tietLoginEmail.text.toString().trim()
                                val password = tietLoginPassword.text.toString().trim()
                                doLogin(email, password)
                            } else {
                                tilLoginEmail.error = resources.getString(R.string.required_email)
                                tilLoginPassword.error =
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

    override fun initListener() = with(binding) {
        tietLoginEmail.doOnTextChanged { text, _, before, _ ->
            viewModel.validateLoginEmail(text.toString())
        }

        tietLoginPassword.doOnTextChanged { text, _, before, _ ->
            viewModel.validateLoginPassword(text.toString())
        }

        btnLogin.setOnClickListener {
            val email = tietLoginEmail.text.toString().trim()
            val password = tietLoginPassword.text.toString().trim()
            if (tilLoginEmail.isErrorEnabled.not() && tilLoginPassword.isErrorEnabled.not()) {
                viewModel.validateLoginField(email, password)
            } else {
                context?.let { ctx ->
                    CustomSnackbar.showSnackBar(
                        ctx,
                        binding.root,
                        resources.getString(R.string.field_error_message)
                    )
                }
            }
        }
    }

    fun doLogin(email: String, password: String) {
        viewModel.fetchLogin(email, password).launchAndCollectIn(viewLifecycleOwner) { success ->
            if (success) findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
    }

}