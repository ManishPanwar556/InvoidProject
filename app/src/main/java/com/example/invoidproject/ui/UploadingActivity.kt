package com.example.invoidproject.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.invoidproject.R
import com.example.invoidproject.databinding.ActivityUploadingBinding
import com.example.invoidproject.utils.Constants
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class UploadingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadingBinding
    private val storageRef by lazy {
        FirebaseStorage.getInstance().reference
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUploadingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        /** get the uri passed through intent **/
        val uriString = intent.getStringExtra(Constants.URI)
        if (uriString != null) {
            /** upload file to firebase storage **/
            val uri = Uri.parse(uriString)
            val docsRef = storageRef.child("document/${uri.lastPathSegment}")
            val task = docsRef.putFile(uri)
            task.addOnSuccessListener {
                binding.linearProgressBar.visibility = View.GONE
                binding.lottieAnimation.setAnimation(R.raw.uploading_completed)
                binding.lottieAnimation.playAnimation()
                lifecycleScope.launch {
                    Toast.makeText(
                        this@UploadingActivity,
                        "File Uploaded successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    /** add a delay of 2 seconds to delay finishing of activity **/
                    delay(2000)
                    finish()
                }

            }.addOnFailureListener {
                Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show()
            }.addOnProgressListener {
                if (it.totalByteCount.toInt() != 0) {
                    binding.linearProgressBar.progress =
                        ((100.0 * it.bytesTransferred) / it.totalByteCount).toInt()
                }
            }
        }
    }


}