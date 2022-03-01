package jp.co.arsaga.extensions.view.navigation

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
abstract class AbstractViewAnalyticsEventListener<ViewAnalyticsType : Enum<ViewAnalyticsType>>
    : Application.ActivityLifecycleCallbacks,
    NavController.OnDestinationChangedListener {

    private val graspedFragment = mutableMapOf<String, ViewAnalyticsType?>()

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        if (p0 is FragmentActivity) p0.supportFragmentManager.registerFragmentLifecycleCallbacks(
            object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentAttached(
                    fm: FragmentManager,
                    f: Fragment,
                    context: Context
                ) {
                    super.onFragmentAttached(fm, f, context)
                    initialLogging(f)
                }
            }, true
        )
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination) {
            is FragmentNavigator.Destination -> destination.className
            is DialogFragmentNavigator.Destination -> destination.className
            else -> null
        }?.let { fragmentClassName ->
            graspedFragment[fragmentClassName]?.let {
                fetchLoggingLog(it)
            }
        }
    }

    private fun initialLogging(fragment: Fragment) {
        graspedFragment.getOrPut(fragment::class.java.name) {
            fragmentToEventMapping(fragment)?.also {
                fetchLoggingLog(it)
            }
        }
    }

    private fun fetchLoggingLog(viewAnalyticsType: ViewAnalyticsType) {
        Timber.d("LoggingViewEvent::${viewAnalyticsType.name}")
        fetchLogging(viewAnalyticsType)
    }

    abstract fun fetchLogging(viewAnalyticsType: ViewAnalyticsType)

    abstract fun fragmentToEventMapping(fragment: Fragment): ViewAnalyticsType?

    override fun onActivityStarted(p0: Activity) {}
    override fun onActivityResumed(p0: Activity) {}
    override fun onActivityPaused(p0: Activity) {}
    override fun onActivityStopped(p0: Activity) {}
    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {}
    override fun onActivityDestroyed(p0: Activity) {}
}
