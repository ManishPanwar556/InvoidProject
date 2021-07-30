package com.example.invoidproject.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.invoidproject.databinding.ActivityMainBinding
import com.example.invoidproject.utils.Constants
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var storageRef: StorageReference
    private val intentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                /**  catch the file uri as a result **/
                storageRef = FirebaseStorage.getInstance().reference
                val uri = result.data?.data
                if (uri != null) {
                    /** start a new activity UploadingActivity and pass the file uri through intents **/
                    val intent = Intent(this, UploadingActivity::class.java)
                    intent.putExtra(Constants.URI, uri.toString())
                    startActivity(intent)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.uploadFileBtn.setOnClickListener {
            /**start an implicit intent to browse all your files using your default file explorer **/
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            intentLauncher.launch(intent)
        }

    }
}