package jp.co.arsaga.extensions.view.baseLayout

import android.app.Dialog
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jp.co.arsaga.extensions.view.R


abstract class BaseBottomSheetDialogFragment: BottomSheetDialogFragment() {

    protected fun adjust(height: Int, isTransition: Boolean = true) {
        dialog?.findViewById<View>(R.id.design_bottom_sheet)
            ?.let { BottomSheetBehavior.from(it) }
            ?.setPeekHeight(height, isTransition)
    }

    override fun onCreateDialog(
        savedInstanceState: Bundle?
    ): Dialog = super.onCreateDialog(savedInstanceState).also { dialog ->
        dialog.setContentView(View(context))
    }
}