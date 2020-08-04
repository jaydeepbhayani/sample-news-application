package anetos.software.byjuszyoin.ui.common.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import anetos.software.byjuszyoin.R

/**
 * * [ScrollChildSwipeRefreshLayout]
 *
 * Custom class created for custom [SwipeRefreshLayout] using [SwipeRefreshLayout].
 * @author
 * created by Jaydeep Bhayani on 30/07/2020
 */

class ScrollChildSwipeRefreshLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs) {

    var scrollUpChild: View? = null

    override fun canChildScrollUp() =
        scrollUpChild?.canScrollVertically(-1) ?: super.canChildScrollUp()

}

fun ScrollChildSwipeRefreshLayout.refresh() {
    findViewById<ScrollChildSwipeRefreshLayout>(this.id).apply {
        setColorSchemeColors(
            ContextCompat.getColor(context, R.color.colorBlue),
            ContextCompat.getColor(context, R.color.colorAccent),
            ContextCompat.getColor(context, R.color.colorPrimaryDark)
        )
    }
}