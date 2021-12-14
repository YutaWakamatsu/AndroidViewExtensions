package jp.co.arsaga.extensions.view

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun NavDirections.popBackStack(fragment: Fragment, isInclusive: Boolean = true) {
    fragment.findNavController().popBackStack(actionId, isInclusive)
}
fun NavDirections.navigate(fragment: Fragment, navigateAnimation: NavigateAnimationType? = null) {
    fragment.findNavController().navigate(this, animateNavOptionsFactory(navigateAnimation))
}
fun NavDirections.navigate(activity: Activity, navigateAnimation: NavigateAnimationType? = null) {
    activity.getNavController()?.navigate(this, animateNavOptionsFactory(navigateAnimation))
}
