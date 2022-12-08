package jp.co.arsaga.extensions.view.external

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.DONUT)
fun openChrome(
    context: Context,
    url: String
) {
    openWeb(context, url, "com.android.chrome")
}

@RequiresApi(Build.VERSION_CODES.DONUT)
fun openWeb(
    context: Context,
    url: String,
    packageName: String?,
) {
    Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        packageName?.run { setPackage(this) }
    }.let {
        runCatching {
            context.startActivity(it)
        }.onFailure { exception ->
            if (exception is ActivityNotFoundException) {
                it.setPackage(null)
                context.startActivity(it)
            }
        }
    }
}

fun openLocalPdfList(
    context: Context
) {
    Intent(Intent.ACTION_GET_CONTENT)
        .setType("application/pdf")
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        .run { context.startActivity(this) }
}