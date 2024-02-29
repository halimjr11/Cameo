package com.nurhaqhalim.cameo.view.prelogin

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nurhaqhalim.cameo.R
import com.nurhaqhalim.cameo.components.CustomSnackbar
import com.nurhaqhalim.cameo.core.base.BaseFragment
import com.nurhaqhalim.cameo.core.domain.state.onCreated
import com.nurhaqhalim.cameo.core.domain.state.onValue
import com.nurhaqhalim.cameo.core.utils.launchAndCollectIn
import com.nurhaqhalim.cameo.databinding.FragmentProfileBinding
import com.nurhaqhalim.cameo.utils.MediaHelper
import com.nurhaqhalim.cameo.utils.MediaHelper.convertBitmapToFile
import com.nurhaqhalim.cameo.utils.toCenterCrop
import com.nurhaqhalim.cameo.viewmodel.PreLoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileInputStream

class ProfileFragment :
    BaseFragment<FragmentProfileBinding, PreLoginViewModel>(FragmentProfileBinding::inflate) {
    override val viewModel: PreLoginViewModel by viewModel()
    private var fileName: String = ""
    private var fileUri: String = ""
    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showCamera()
            }
        }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { result ->
            val image = result as Bitmap

            context?.let { MediaHelper.saveFileToStorage(it, image) }
            context?.convertBitmapToFile(image)?.let { file -> retrieveFile(file) }
        }

    private val requestImagePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openGallery()
            }
        }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data ?: return@registerForActivityResult
                context?.let { ctx ->
                    MediaHelper.convertFileFromContentUri(ctx, imageUri)
                }?.let { file -> retrieveFile(file) }
            }
        }

    override fun initView() {
        with(binding) {
            mtProfile.title = resources.getString(R.string.profile_title)
            btnProfile.text = resources.getString(R.string.done_text)
            ivProfileImage.setImageResource(R.drawable.ic_person_outline)
        }
    }

    override fun observeData() {
        with(viewModel) {
            validateProfileName.launchAndCollectIn(viewLifecycleOwner) { state ->
                state.onCreated {}.onValue { isPass ->
                    binding.run {
                        tilProfileName.isErrorEnabled = isPass.not()
                        if (isPass) {
                            val name = tietProfileName.text.toString().trim()
                            doUpdateProfile(name, fileUri)
                        } else {
                            tilProfileName.error = getString(R.string.required_profile_name)
                            context?.let { ctx ->
                                CustomSnackbar.showSnackBar(
                                    ctx,
                                    binding.root,
                                    getString(R.string.required_name_message)
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
            ivProfileImage.setOnClickListener {
                showChooseMediaDialog()
            }

            btnProfile.setOnClickListener {
                val name = tietProfileName.text.toString().trim()
                viewModel.validateProfileName(name)
            }

        }
    }

    private fun showChooseMediaDialog() {
        val mediaSelector = arrayOf(
            resources.getString(R.string.choose_image_camera),
            resources.getString(R.string.choose_image_gallery)
        )
        val alertDialog = context?.let { MaterialAlertDialogBuilder(it) }
        alertDialog?.apply {
            setTitle(resources.getString(R.string.choose_image_title))
            setItems(mediaSelector) { _, item ->
                when (item) {
                    0 -> {
                        checkCameraPermission()
                    }

                    else -> {
                        checkGalleryPermission()
                    }
                }
            }
            show()
        }
    }

    private fun checkCameraPermission() {
        if (context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.CAMERA
                )
            } == PackageManager.PERMISSION_GRANTED
        ) {
            showCamera()
        } else {
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun showCamera() {
        cameraLauncher.launch(null)
    }

    private fun checkGalleryPermission() {
        if (context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_MEDIA_IMAGES
                )
            } == PackageManager.PERMISSION_GRANTED
        ) {
            openGallery() // Permission already granted
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestImagePermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                requestImagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun openGallery() {
        val intentGallery = Intent()
        intentGallery.action = Intent.ACTION_GET_CONTENT
        intentGallery.type = "image/*"

        val chooseImage =
            Intent.createChooser(intentGallery, resources.getString(R.string.choose_image_gallery))
        galleryLauncher.launch(chooseImage)
    }

    private fun retrieveFile(file: File) {
        binding.ivProfileImage.apply {
            toCenterCrop()
            load(file)
        }
        val image = File(file.absolutePath)
        fileName = image.name
        doUploadImage(FileInputStream(image), fileName)
    }

    private fun doUploadImage(fileInputStream: FileInputStream, fileName: String) {
        viewModel.fetchUploadImage(fileInputStream, fileName)
            .launchAndCollectIn(viewLifecycleOwner) { uriString ->
                fileUri = uriString
            }
    }

    private fun doUpdateProfile(name: String, imageUrl: String) {
        viewModel.fetchUploadProfile(name, imageUrl)
            .launchAndCollectIn(viewLifecycleOwner) { success ->
                if (success) {
                    viewModel.saveProfile()
                    findNavController().navigate(R.id.action_profileFragment_to_mainFragment)
                }
            }
    }


}