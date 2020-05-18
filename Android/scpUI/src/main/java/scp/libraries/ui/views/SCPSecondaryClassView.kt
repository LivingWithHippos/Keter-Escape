package scp.libraries.ui.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginEnd
import scp.libraries.ui.R
import scp.libraries.ui.utilities.SCP_VIEW_MODE_CONTAINMENT
import scp.libraries.ui.utilities.setIntColor

//todo: rename to SCPClassView
class SCPSecondaryClassView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {


    private val rootLayout: ConstraintLayout
    private val classBackgroundImageView: ImageView
    private val barView: View
    private val classNumberTextView: TextView
    private val classTitleTextView: TextView
    private val classBodyTextView: TextView
    private val circleImageView: ImageView
    private val classImageView: ImageView

    private var backgroundPic: Drawable? = null
        set(value) {
            if (value == null) {
                val defaultBackground: Drawable? = context.getDrawable(R.drawable.scp_classes_shape)
                if (defaultBackground == null)
                    classBackgroundImageView.visibility = View.INVISIBLE
                else
                    classBackgroundImageView.setImageDrawable(defaultBackground)
            } else {
                classBackgroundImageView.setImageDrawable(value)
                classBackgroundImageView.visibility = View.VISIBLE
            }
            field = value
        }

    //todo: maybe remove this, it can be changed using a different drawable for backgroundPic
    private var backgroundFillColor: Int = Color.parseColor("#0c0c0c")
        set(value) {
            when (val drawable = classBackgroundImageView.drawable) {
                is ShapeDrawable -> drawable.paint.color = value
                is ColorDrawable -> drawable.color = value
            }
            field = value
        }

    var darkFillColor: Int = Color.parseColor("#0c0c0c")
        set(value) {
            barView.setBackgroundColor(value)
            when (val drawable = circleImageView.drawable) {
                is ShapeDrawable -> drawable.paint.color = value
                is ColorDrawable -> drawable.color = value
                is GradientDrawable -> drawable.setIntColor(value)
            }
            field = value
        }

    var lightFillColor: Int = Color.parseColor("#0c0c0c")
        set(value) {
            rootLayout.setBackgroundColor(value)
            field = value
        }

    private var circlePic: Drawable? = null
        set(value) {
            if (value == null) {
                val defaultBackground: Drawable? = context.getDrawable(R.drawable.scp_class_circle)
                if (defaultBackground == null)
                    circleImageView.visibility = View.INVISIBLE
                else
                    circleImageView.setImageDrawable(defaultBackground)
            } else {
                circleImageView.setImageDrawable(value)
                circleImageView.visibility = View.VISIBLE
            }
            field = value
        }

    var classPic: Drawable? = null
        set(value) {
            if (value == null) {
                classImageView.visibility = View.INVISIBLE
            } else {
                classImageView.setImageDrawable(value)
                classImageView.visibility = View.VISIBLE
            }
            field = value
        }

    var classNumberText: String = ""
        set(value) {
            if (value.isBlank())
                classNumberTextView.visibility = View.INVISIBLE
            else {
                classNumberTextView.text = value
                classNumberTextView.visibility = View.VISIBLE
            }
            field = value
        }

    var classTitleText: String = ""
        set(value) {
            classTitleTextView.text = value
            field = value
        }

    var classBodyText: String = ""
        set(value) {
            classBodyTextView.text = value
            field = value
        }

    var modeID: Int = 1
        set(value) {

            when(value){
                SCP_VIEW_MODE_CONTAINMENT -> {
                    classBackgroundImageView.visibility = View.INVISIBLE
                    classNumberTextView.visibility = View.INVISIBLE
                    val circleSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80f, resources.displayMetrics)
                    val marginSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics)
                    val strokeSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4f, resources.displayMetrics)
                    val params = circleImageView.layoutParams as MarginLayoutParams
                    params.width = circleSize.toInt()
                    params.height = circleSize.toInt()
                    params.marginEnd = marginSize.toInt()
                    //todo: stroke not working
                    when (val drawable = circleImageView.drawable) {
                        is GradientDrawable -> drawable.setStroke(strokeSize.toInt(),Color.parseColor("#0c0c0c"))
                    }
                    val picSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70f, resources.displayMetrics)
                    classImageView.layoutParams.height = picSize.toInt()
                    classImageView.layoutParams.width = picSize.toInt()
                }
            }
            field = value
        }

    init {
        val view = View.inflate(context, R.layout.scp_secondary_class_view, this)

        rootLayout = view.findViewById(R.id.root_layout)
        barView = view.findViewById(R.id.iv_bar)
        classTitleTextView = view.findViewById(R.id.tv_class_title)
        classBodyTextView = view.findViewById(R.id.tv_class_body)
        classBackgroundImageView = view.findViewById(R.id.iv_class_background)
        classNumberTextView = view.findViewById(R.id.tv_class_text)
        circleImageView = view.findViewById(R.id.iv_class_circle)
        classImageView = view.findViewById(R.id.iv_class)

        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SCPSecondaryClassView,
            defStyleAttr,
            defStyleRes
        )
        //todo: add automatic generation from a variable type and its values  CLASS_DISRUPTION_VLAM
        // or CLASS_RISK_NOTICE etc. Here or on the SCP ID view.
        backgroundPic = a.getDrawable(R.styleable.SCPSecondaryClassView_backgroundPicture)
        classNumberText = a.getString(R.styleable.SCPSecondaryClassView_classNumber)
            ?: classNumberText
        classTitleText = a.getString(R.styleable.SCPSecondaryClassView_classTitleText)
            ?: classTitleText
        classBodyText = a.getString(R.styleable.SCPSecondaryClassView_classBodyText)
            ?: classBodyText
        circlePic = a.getDrawable(R.styleable.SCPSecondaryClassView_circlePicture)
        classPic = a.getDrawable(R.styleable.SCPSecondaryClassView_classPicture)

        backgroundFillColor = a.getColor(R.styleable.SCPSecondaryClassView_backgroundColor, backgroundFillColor)
        darkFillColor = a.getColor(R.styleable.SCPSecondaryClassView_darkColor, darkFillColor)
        lightFillColor = a.getColor(R.styleable.SCPSecondaryClassView_lightColor, lightFillColor)

        modeID = a.getInteger(R.styleable.SCPSecondaryClassView_mode, modeID)

        a.recycle()
    }
}