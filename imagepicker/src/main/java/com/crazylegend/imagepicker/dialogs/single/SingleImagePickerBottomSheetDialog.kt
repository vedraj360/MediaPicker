package com.crazylegend.imagepicker.dialogs.single

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.invoke
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.crazylegend.core.gone
import com.crazylegend.core.viewBinding.viewBinding
import com.crazylegend.imagepicker.adapters.single.ImagesAdapter
import com.crazylegend.imagepicker.contracts.SinglePickerContracts
import com.crazylegend.imagepicker.databinding.FragmentGalleryLayoutBinding
import com.crazylegend.imagepicker.images.ImagesVM
import com.crazylegend.imagepicker.listeners.onImagePicked
import com.crazylegend.imagepicker.picker.SingleImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class SingleImagePickerBottomSheetDialog : BottomSheetDialogFragment(), SinglePickerContracts {

    override var onImagePicked: onImagePicked? = null
    override val binding by viewBinding(FragmentGalleryLayoutBinding::bind)
    override val imagesVM by viewModels<ImagesVM>()
    override val askForStoragePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            imagesVM.loadImages()
        } else {
            Log.e(SingleImagePicker.javaClass.name, "PERMISSION DENIED")
            dismissAllowingStateLoss()
        }
    }


    override val imagesAdapter by lazy {
        ImagesAdapter {
            onImagePicked?.forImage(it)
            dismissAllowingStateLoss()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        askForStoragePermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        binding.close.gone()
        binding.gallery.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = imagesAdapter
        }

        imagesVM.images.observe(viewLifecycleOwner) {
            imagesAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onImagePicked = null
    }

}