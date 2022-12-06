package jp.co.arsaga.extensions.view.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.yalantis.ucrop.UCrop


interface ResultLauncher {
    fun pickData()
}

sealed class DataPicker(
    fragment: Fragment,
    uriCallback: (Uri?) -> Unit,
) : ResultLauncher {
    private val resultLauncher = fragment.registerForActivityResult(
        ActivityResultContracts.GetContent(),
        uriCallback
    )

    protected abstract val input: String?
    override fun pickData() {
        resultLauncher.launch(input)
    }
}

class Picker(
    fragment: Fragment,
    uriCallback: (Uri?) -> Unit,
) : DataPicker(fragment, uriCallback) {
    override val input: String = "image/*"
}

fun Fragment.imageCrop(
    saveUri: Context.() -> Uri,
    config: Context.(UCrop.Options) -> Unit,
    callback: (Uri?) -> Unit,
    resultLauncher: ((Uri?) -> Unit) -> ResultLauncher,
): ResultLauncher {
    val cropLauncher = registerForActivityResult(CropImage(saveUri, config)) {
        callback(it)
    }
    return resultLauncher {
        it ?: return@resultLauncher
        cropLauncher.launch(it)
    }
}

private class CropImage(
    private val saveUri: Context.() -> Uri,
    private val config: Context.(UCrop.Options) -> Unit,
) : ActivityResultContract<Uri, Uri?>() {

    override fun createIntent(
        context: Context,
        input: Uri,
    ): Intent = UCrop.of(input, saveUri(context)).apply {
        UCrop.Options()
            .also { config(context, it) }
            .let { withOptions(it) }
    }.getIntent(context)

    override fun parseResult(
        resultCode: Int, intent: Intent?,
    ): Uri? = intent
        ?.takeIf { resultCode == Activity.RESULT_OK }
        ?.let { UCrop.getOutput(it) }
}