package jp.co.arsaga.extensions.view

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import timber.log.Timber

fun NavDirections.popBackStack(
    fragment: Fragment,
    isInclusive: Boolean = true,
    preAction: () -> Unit = {},
    onError: (Throwable) -> Unit = { Timber.e(it) },
) {
    preAction()
    runCatching {
        fragment.findNavController().popBackStack(actionId, isInclusive)
    }.onFailure(onError)
}

fun NavDirections.navigate(
    fragment: Fragment,
    navigateAnimation: NavigateAnimationType? = null,
    preAction: () -> Unit = {},
    onError: (Throwable) -> Unit = {},
) {
    preAction()
    runCatching {
        fragment.findNavController().navigate(this, animateNavOptionsFactory(navigateAnimation))
    }.onFailure(onError)
}

fun NavDirections.navigate(
    activity: Activity,
    navigateAnimation: NavigateAnimationType? = null,
    preAction: () -> Unit = {},
    onError: (Throwable) -> Unit = {},
) {
    preAction()
    runCatching {
        activity.getNavController()?.navigate(this, animateNavOptionsFactory(navigateAnimation))
    }.onFailure(onError)
}
