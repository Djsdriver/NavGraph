package com.example.navgraph.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.PermissionChecker
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope

import androidx.navigation.fragment.findNavController
import androidx.test.runner.permission.PermissionRequester
import com.example.navgraph.R
import com.example.navgraph.data.db.PlaylistEntity
import com.example.navgraph.databinding.FragmentMainBinding
import com.example.navgraph.presention.ui.PlaylistViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val playlistViewModel by viewModel<PlaylistViewModel>()
    private var imagePath: String = ""
    private var _uri: Uri? = null
    private var uriOfImage: Uri? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMainBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    @SuppressLint("StringFormatInvalid")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                //обрабатываем событие выбора пользователем фотографии
                if (uri != null) {
                    binding.imageCover.setImageURI(uri)
                    uriOfImage = uri
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
        //по нажатию на кнопку pickImage запускаем photo picker
        binding.imageCover.setOnClickListener {

            //по нажатию на кнопку pickImage запускаем photo picker
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }


        binding.buttonSave.setOnClickListener {
            val message = try {
                if (_uri == null) {
                    addPlaylist()
                } else {
                    saveImageToPrivateStorage(_uri!!, addPlaylist().toString())
                }
                findNavController().popBackStack()
                this.resources.getString(R.string.playlist_created, binding.editName.text)
            } catch (e: Exception) {
                "error"
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }


            //uriOfImage?.let { saveImageToPrivateStorage(uri = it, nameOfFile = filepath) }
        }

        /*val controller= findNavController()
        binding.buttonSave.setOnClickListener { controller.navigate(R.id.fragment2) }*/


    private fun addPlaylist() {
        val name = binding.editName.text.toString()
        val description = binding.editDesc.text.toString()


        playlistViewModel.addPlaylist(PlaylistEntity(
            name = name,
            description = description,
            imagePath = playlistViewModel.generateImageNameForStorage(),
            trackCount = 0
        ))

    }


    private fun saveImageToPrivateStorage(uri: Uri, nameOfImage: String) {
        val filePath =
            File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),"my_album")
        if (!filePath.exists()) {
            filePath.mkdirs()
        }

        val file = File(filePath, nameOfImage)
        val inputStream = requireActivity().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
    }

    /*private fun saveImageToPrivateStorage(uri: Uri) {
        val filePath =
            File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "myalbum")
        if (!filePath.exists()) {
            filePath.mkdirs()
        }
        val file = File(filePath, "cover_${System.currentTimeMillis()}.jpg")
        imagePath = file.path
        val inputStream = requireActivity().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
    }*/


}
