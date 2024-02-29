package com.nurhaqhalim.cameo.utils

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.nurhaqhalim.cameo.R
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object MediaHelper {

    fun String.toRequestBody() = this.toRequestBody("text/plain".toMediaType())
    fun File.toRequestBody() = this.asRequestBody("multipart/form-data".toMediaTypeOrNull())

    fun convertFileFromContentUri(context: Context, uri: Uri): File? {
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            inputStream?.use { input ->
                val file = createTemporaryFile(context)
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
                return file
            }
        } catch (e: Exception) {
            return null
        }
        return null
    }

    private fun createTemporaryFile(context: Context): File {
        val fileName = "temp_file"
        val directory = context.cacheDir
        return File.createTempFile(fileName, null, directory)
    }

    fun saveFileToStorage(context: Context, bitmap: Bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Use MediaStore API for Android 10+
            saveImageToMediaStore(context, bitmap)
        } else {
            // Use legacy approach for older versions
            saveImageToLegacyStorage(context, bitmap)
        }
    }

    private fun saveImageToMediaStore(context: Context, bitmap: Bitmap) {
        val contentResolver = context.contentResolver
        val appName = context.resources.getString(R.string.app_name)
        val locale = context.resources.configuration.locales[0]
        val time = locale.getCurrentTimeStamp()
        val filename = "${appName}-${time}.jpg"
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        val uri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values) ?: return
        try {
            val outputStream = contentResolver.openOutputStream(uri) ?: return
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.close()
            // Send intent to refresh gallery
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.data = uri
            context.sendBroadcast(intent)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun saveImageToLegacyStorage(context: Context, bitmap: Bitmap) {
        val directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) ?: return
        val appName = context.resources.getString(R.string.app_name)
        val locale = context.resources.configuration.locales[0]
        val time = locale.getCurrentTimeStamp()
        val filename = "${appName}-${time}.jpg"
        val file = File(directory, filename)
        try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.close()
            // Use MediaScanner to scan the file for gallery inclusion
            val mediaScannerIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            mediaScannerIntent.data = Uri.fromFile(file)
            context.sendBroadcast(mediaScannerIntent)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun handleImageUri(context: Context, uri: Uri) {
        when (val scheme = uri.scheme) {
            "content" -> {
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                    context.convertBitmapToFile(bitmap)
                } catch (e: IOException) {
                    // Handle error
                }
            }

            else -> {
                val inputStream = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()
                context.convertBitmapToFile(bitmap)
            }
        }
    }

    fun Context.convertBitmapToFile(bitmap: Bitmap): File? {
        val directory = this.filesDir
        val appName = resources.getString(R.string.app_name)
        val locale = resources.configuration.locales[0]
        val time = locale.getCurrentTimeStamp()
        val filename = "${appName}-${time}.jpg"
        val file = File(directory, filename)

        val outputStream = try {
            FileOutputStream(file)
        } catch (e: IOException) {
            return null
        }

        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: IOException) {
            return null
        }

        return file
    }

    fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}
