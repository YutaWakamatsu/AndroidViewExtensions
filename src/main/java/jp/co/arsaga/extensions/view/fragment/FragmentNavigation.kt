package jp.co.arsaga.extensions.view.fragment

import android.content.Intent
import androidx.fragment.app.Fragment

fun Fragment.activityRecreate(intentDecorator: Intent.() -> Intent = { this }) {
    requireActivity().run {
        val intent = intent
        finish()
        startActivity(intentDecorator(intent))
    }
}