package customview.banner

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.homework2.R
import com.example.homework2.databinding.BannerBinding

class BannerView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: BannerBinding

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.banner, this, true)
        binding = BannerBinding.bind(this)
        initializeAttributes(attrs, defStyleAttr, defStyleRes)
    }

    private fun initializeAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.BannerView, defStyleAttr, defStyleRes)
        val titleText = typedArray.getString(R.styleable.BannerView_title)
        setTitleText(titleText)
        val subTitleText = typedArray.getString(R.styleable.BannerView_subtitle)
        setSubtitleText(subTitleText)
        val titlesColor = typedArray.getColor(R.styleable.BannerView_titlesColor, Color.BLACK)
        setTitlesColor(titlesColor)
        val backgroundBanner =
            typedArray.getColor(R.styleable.BannerView_backgroundView, Color.WHITE)
        setBackgroundBanner(backgroundBanner)
        val image = typedArray.getResourceId(R.styleable.BannerView_image, R.drawable.ic_logo)
        setImageLogo(image)
        val imageWidth = typedArray.getInt(R.styleable.BannerView_imageWidth, 70)
        val imageHeight = typedArray.getInt(R.styleable.BannerView_imageHeight, 70)
        setImageSize(imageWidth, imageHeight)

        typedArray.recycle()
    }

    fun setTitleText(text: String?) {
        binding.tvTitle.text = text ?: ""
    }

    fun setSubtitleText(text: String?) {
        binding.tvSubtitle.text = text ?: ""
    }

    fun setTitlesColor(color: Int) {
        binding.tvTitle.setTextColor(color)
        binding.tvSubtitle.setTextColor(color)
    }

    fun setBackgroundBanner(color: Int) {
        binding.cvBanner.setCardBackgroundColor(color)
    }

    fun setImageLogo(image: Int) {
        binding.ivLogo.setImageResource(image)
    }

    fun setImageSize(imageWidth: Int, imageHeight: Int) {
        val params = binding.ivLogo.layoutParams
        params.width = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            imageWidth.toFloat(), resources.displayMetrics
        ).toInt()
        params.height = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            imageHeight.toFloat(), resources.displayMetrics
        ).toInt()
        binding.ivLogo.layoutParams = params
    }

    fun updateBannerView(bannerModel: BannerModel) {
        with(bannerModel) {
            setImageLogo(image)
            setImageSize(imageWith, imageHeight)
            setTitleText(title)
            setSubtitleText(subtitle)
            setTitlesColor(titlesColor)
            setBackgroundBanner(background)
        }
    }
}