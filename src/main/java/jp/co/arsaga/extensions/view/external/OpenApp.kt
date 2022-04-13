package jp.co.arsaga.extensions.view.external

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment


@SuppressLint("QueryPermissionsNeeded")
fun Fragment.callPhone(phoneNumber: String?) {
    phoneNumber ?: return
    phoneNumber.filter { it.isDigit() }
        .let { Uri.parse("tel:$it") }
        .let { Intent(Intent.ACTION_DIAL).apply { data = it } }
        .takeIf { it.resolveActivity(requireActivity().packageManager) != null }
        ?.let { startActivity(it) }
}

@SuppressLint("QueryPermissionsNeeded")
@RequiresApi(Build.VERSION_CODES.DONUT)
fun Fragment.jumpMap(address: String?) {
    address ?: return
    Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=$address"))
        .apply { setPackage("com.google.android.apps.maps") }
        .takeIf { it.resolveActivity(requireActivity().packageManager) != null }
        ?.let { startActivity(it) }
}