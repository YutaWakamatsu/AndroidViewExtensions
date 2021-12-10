package jp.co.arsaga.extensions.view.baseLayout

import android.app.Dialog
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jp.co.arsaga.extensions.view.R


abstract class BaseBottomSheetDialogFragment: BottomSheetDialogFragment() {

    protected abstract val adjustPeekHeight: Int

    override fun onCreateDialog(
        savedInstanceState: Bundle?
    ): Dialog = super.onCreateDialog(savedInstanceState).also { dialog ->
        dialog.setContentView(View(context))
        BottomSheetBehavior.from(dialog.findViewById(R.id.design_bottom_sheet)).apply {
            setPeekHeight(adjustPeekHeight)
        }
    }
}