import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.teamwss.websoso.R

class CustomSnackBar(private val view: View) {
    private val snackBar: Snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)
    private val customView: View
    private val tvCustomSnackBar: TextView
    private val ivCustomSnackBar: ImageView

    init {
        val inflater = LayoutInflater.from(view.context)
        customView = inflater.inflate(R.layout.custom_snack_bar, null)
        tvCustomSnackBar = customView.findViewById(R.id.tvCustomSnackBar)
        ivCustomSnackBar = customView.findViewById(R.id.ivCustomSnackBar)

        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout
        snackBarLayout.setBackgroundColor(ContextCompat.getColor(view.context, android.R.color.transparent))
        snackBarLayout.addView(customView, 0)
    }

    fun setText(text: String): CustomSnackBar {
        tvCustomSnackBar.text = text
        return this
    }

    fun setIcon(drawable: Drawable): CustomSnackBar {
        ivCustomSnackBar.background = drawable
        return this
    }

    fun show() {
        snackBar.show()
    }

    companion object {
        fun make(view: View): CustomSnackBar {
            return CustomSnackBar(view)
        }
    }
}