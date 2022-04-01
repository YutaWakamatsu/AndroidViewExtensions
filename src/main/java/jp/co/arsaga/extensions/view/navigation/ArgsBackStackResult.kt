package jp.co.arsaga.extensions.view.navigation

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController


fun <T> Fragment.setBackStackResult(key: String, value: T) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, value)
}

fun <T> Fragment.getBackStackResultLiveData(key: String): LiveData<T>? {
    return findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)
}

fun <T> Fragment.getBackStackResult(key: String): T? {
    return findNavController().currentBackStackEntry?.savedStateHandle?.get(key)
}

fun <T> Fragment.getBackStackResultOnce(owner: LifecycleOwner, key: String, onChanged: (T) -> Unit) {
    getBackStackResultLiveData<T>(key)?.observe(owner) {
        onChanged.invoke(it)
        findNavController().currentBackStackEntry?.savedStateHandle?.remove<T>(key)
    }
}