package jp.co.arsaga.extensions.view

enum class NavigateAnimationType(
    val enterAnimationId: Int = -1,
    val exitAnimationId: Int = -1,
    val popEnterAnimationId: Int = -1,
    val popExitAnimationId: Int = -1
) {
    XML,
    SLIDE(
        enterAnimationId = R.anim.slide_in_enter_animator,
        exitAnimationId = R.anim.slide_in_exit_animator,
        popEnterAnimationId = R.anim.slide_out_enter_animator,
        popExitAnimationId = R.anim.slide_out_exit_animator
    ),
    POP_UP(
        enterAnimationId = R.anim.pop_up_in_enter_animator,
        exitAnimationId = R.anim.pop_up_in_exit_animator,
        popEnterAnimationId = R.anim.pop_up_out_enter_animator,
        popExitAnimationId = R.anim.pop_up_out_exit_animator
    ),
    FADE(
        enterAnimationId = android.R.anim.fade_in,
        exitAnimationId = android.R.anim.fade_out,
        popEnterAnimationId = android.R.anim.fade_in,
        popExitAnimationId = android.R.anim.fade_out
    )
}