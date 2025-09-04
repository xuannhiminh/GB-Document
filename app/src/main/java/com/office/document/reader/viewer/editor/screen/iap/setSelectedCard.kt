import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView

fun setSelectedCard(
    selectedCard: CardView,
    otherCard: CardView
) {
    val borderWidth = 1f.dpToPx(selectedCard.context).toInt()
    val cornerRadius = 16f.dpToPx(selectedCard.context)

    // Gradient border (FB9400 -> FFAB38)
    val borderDrawable = GradientDrawable(
        GradientDrawable.Orientation.LEFT_RIGHT,
        intArrayOf(
            Color.parseColor("#FB9400"),
            Color.parseColor("#FFAB38")
        )
    ).apply {
        this.cornerRadius = cornerRadius
    }

    // Inner background (không đổi, chỉ nền phẳng)
    val innerDrawable = GradientDrawable().apply {
        setColor(Color.parseColor("#4D3946")) // nền cũ
        this.cornerRadius = cornerRadius - borderWidth
    }

    val layerDrawable = LayerDrawable(arrayOf(borderDrawable, innerDrawable)).apply {
        // Inset innerDrawable để lộ viền
        setLayerInset(1, borderWidth, borderWidth, borderWidth, borderWidth)
    }

    val unselectedBackground = GradientDrawable().apply {
        this.cornerRadius = cornerRadius
        setColor(Color.parseColor("#4D3946")) // nền phẳng
    }

    // Gán background
    selectedCard.background = layerDrawable
    otherCard.background = unselectedBackground
}


// Extension function: convert dp to px
fun Float.dpToPx(context: Context): Float {
    return this * context.resources.displayMetrics.density
}