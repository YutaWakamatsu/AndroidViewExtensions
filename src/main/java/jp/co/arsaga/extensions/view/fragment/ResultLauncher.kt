package jp.co.arsaga.extensions.view.fragment

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment


class ImagePicker(
    fragment: Fragment,
    uriCallback: (Uri?) -> Unit
) {
    private val resultLauncher = fragment.registerForActivityResult(
        ActivityResultContracts.GetContent(),
        uriCallback
    )

    fun pickImage() {
        resultLauncher.launch("image/*")
    }
}