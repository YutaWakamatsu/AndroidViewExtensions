package jp.co.arsaga.extensions.view.application

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

class ApplicationViewModelUnDefinedException : Throwable()

@MainThread
inline fun <reified VM : ViewModel> Fragment.applicationViewModel(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null,
): Lazy<VM> = createViewModelLazy(VM::class, {
    requireActivity().application.let {
        if (it is ViewModelStoreOwner) it.viewModelStore
        else throw ApplicationViewModelUnDefinedException()
    }
},
    { defaultViewModelCreationExtras },
    factoryProducer ?: { requireActivity().defaultViewModelProviderFactory }
)