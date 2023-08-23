package com.example.navgraph.fragments

import android.Manifest
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

    private var uriOfImage: Uri? = null
    private val requester = PermissionRequester()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMainBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

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
            viewLifecycleOwner.lifecycleScope.launch {
                requester.requestPermissions(
                ).collect { result ->
                    when(result) {
                        is PermissionChecker.PermissionResult -> {} // Пользователь дал разрешение, можно продолжать работу

                        is PermissionChecker.PermissionResult.Denied -> {

                            Toast.makeText(
                                requireContext(),
                                "Разрешение требуется, что вы могли загружать обложки для своих плейлистов",
                                Toast.LENGTH_SHORT
                            ).show()

//                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                            intent.data= Uri.fromParts("package", context?.packageName ?: "", null)
//                            context?.startActivity(intent)

                        }// Пользователь отказал в предоставлении разрешения

                        is PermissionResult.Cancelled -> {

                            Toast.makeText(requireContext(), "Canceled", Toast.LENGTH_SHORT).show()

                        }// Запрос на разрешение отменён
                    }
                }
            }

            val granted = requester.areGranted(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )

            if (granted) {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            
        }

        binding.buttonSave.setOnClickListener {

            val filepath = if (uriOfImage != null) "${binding.editName} cover_${System.currentTimeMillis()}.jpg" else ""

            val playlist = PlaylistEntity(
                name = binding.editName.text.toString(),
                description = binding.editDesc.text.toString(),
                imagePath = filepath,
                trackCount = 0
            )

            viewLifecycleOwner.lifecycleScope.launch {
                playlistViewModel.addPlaylist(playlist)
            }
            uriOfImage?.let { saveImageToPrivateStorage(uri = it, nameOfFile = filepath) }
        }

        /*val controller= findNavController()
        binding.buttonSave.setOnClickListener { controller.navigate(R.id.fragment2) }*/

        

    }


    private fun saveImageToPrivateStorage(uri: Uri, nameOfFile: String) {
        //создаём экземпляр класса File, который указывает на нужный каталог
        val filePath = File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "myalbum")
        //создаем каталог, если он не создан
        if (!filePath.exists()){
            filePath.mkdirs()
        }
        //создаём экземпляр класса File, который указывает на файл внутри каталога
        val file = File(filePath, nameOfFile)

        // создаём входящий поток байтов из выбранной картинки
        val inputStream = requireActivity().contentResolver.openInputStream(uri)
        // создаём исходящий поток байтов в созданный выше файл
        val outputStream = FileOutputStream(file)
        // записываем картинку с помощью BitmapFactory
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)

        Log.d("debage", "Сохранено в по адресу ${file.absolutePath}")
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
