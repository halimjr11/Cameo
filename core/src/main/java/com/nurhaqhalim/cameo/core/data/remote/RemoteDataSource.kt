package com.nurhaqhalim.cameo.core.data.remote

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.storage.FirebaseStorage
import com.nurhaqhalim.cameo.core.data.remote.model.NowPlayingResponse
import com.nurhaqhalim.cameo.core.data.remote.model.PopularResponse
import com.nurhaqhalim.cameo.core.data.remote.model.TopRatedResponse
import com.nurhaqhalim.cameo.core.data.remote.model.UpcomingResponse
import com.nurhaqhalim.cameo.core.data.remote.service.ApiEndpoint
import com.nurhaqhalim.cameo.core.utils.safeApiCall
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.FileInputStream

class RemoteDataSource(
    private val apiEndpoint: ApiEndpoint,
    private val firebaseAnalytics: FirebaseAnalytics,
    private val firebaseAuth: FirebaseAuth,
    private val remoteConfig: FirebaseRemoteConfig,
    private val firebaseStorage: FirebaseStorage
) {



    suspend fun fetchNowPlaying(page: Int, language: String): NowPlayingResponse = safeApiCall {
        apiEndpoint.fetchNowPlaying(page, language)
    }

    suspend fun fetchPopular(page: Int, language: String): PopularResponse = safeApiCall {
        apiEndpoint.fetchPopular(page, language)
    }

    suspend fun fetchTopRated(page: Int, language: String): TopRatedResponse = safeApiCall {
        apiEndpoint.fetchTopRated(page, language)
    }

    suspend fun fetchUpcoming(page: Int, language: String): UpcomingResponse = safeApiCall {
        apiEndpoint.fetchUpcoming(page, language)
    }

    fun fetchLogin(email: String, password: String): Flow<Boolean> = callbackFlow {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            trySend(task.isSuccessful)
        }.addOnFailureListener {
            trySend(false)
        }
        awaitClose()
    }

    fun fetchRegister(email: String, password: String): Flow<Boolean> = callbackFlow {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            trySend(task.isSuccessful)
        }.addOnFailureListener {
            trySend(false)
        }
        awaitClose()
    }

    fun fetchUploadProfile(userProfileChangeRequest: UserProfileChangeRequest): Flow<Boolean> =
        callbackFlow {
            getCurrentUser()?.updateProfile(userProfileChangeRequest)
                ?.addOnCompleteListener { task ->
                    trySend(task.isSuccessful)
                }?.addOnFailureListener { error ->
                    trySend(false)
                }
            awaitClose()
        }

    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    fun fetchLogout() {
        firebaseAuth.signOut()
    }

    fun fetchUploadImage(fileStream: FileInputStream, fileName: String): Flow<String> =
        callbackFlow {
            val fileRef = firebaseStorage.reference.child("profile/$fileName")
            val uploadTask = fileRef.putStream(fileStream)
            uploadTask.addOnFailureListener {
                println("upin : error upload image ${it.message}")
                trySend("")
            }.addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener { uri ->
                    val urlDownload = uri.toString()
                    println("upin : file uri $uri")
                    trySend(urlDownload)
                }
            }
            awaitClose()
        }
}