package showmethe.github.kframework.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.Window

import showmethe.github.kframework.R
import showmethe.github.kframework.widget.animView.BallRotationProgressBar
import showmethe.github.kframework.widget.animView.RectScaleAnim

@WindowParam(gravity = Gravity.CENTER,noAnim = true)
class DialogLoading : SimpleDialogFragment() {

    internal var progressbar: BallRotationProgressBar? = null
    override fun build(savedInstanceState: Bundle?) {
        buildDialog {
            R.layout.dialog_loading_layout
        }
    }
}
