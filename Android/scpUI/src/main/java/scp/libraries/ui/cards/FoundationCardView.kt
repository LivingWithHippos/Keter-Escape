package scp.libraries.ui.cards

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import scp.libraries.ui.R

class FoundationCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val roleTextView: TextView
    private val clearanceTextView: TextView
    private val gradeTextView: TextView
    private val descriptionTextView: TextView
    private val profileImageView: ImageView

    private var profilePic: Drawable? = null
        set(value) {
            if (value == null) {
                profileImageView.visibility = View.INVISIBLE
            } else {
                profileImageView.setImageDrawable(value)
                profileImageView.visibility = View.VISIBLE
            }
            field = value
        }

    private var roleText: String = ""
        set(value) {
            roleTextView.text = value
            field = value
        }

    private var clearanceText: String = ""
        set(value) {
            clearanceTextView.text = value
            field = value
        }

    private var gradeText: String = ""
        set(value) {
            gradeTextView.text = value
            field = value
        }

    private var descriptionText: String = ""
        set(value) {
            descriptionTextView.text = value
            field = value
        }

    init {
        //todo: try with ViewBinding
        val view = View.inflate(context, R.layout.card_foundation_view_layout, this)
        roleTextView = view.findViewById(R.id.tv_role)
        clearanceTextView = view.findViewById(R.id.scp_clearance_body)
        gradeTextView = view.findViewById(R.id.scp_grade_body)
        descriptionTextView = view.findViewById(R.id.tv_card_body)
        profileImageView = view.findViewById(R.id.scp_card_header)

        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.FoundationCardView,
            defStyleAttr,
            defStyleRes
        )

        profilePic = a.getDrawable(R.styleable.FoundationCardView_rolePicture)
        roleText = a.getString(R.styleable.FoundationCardView_roleText) ?: roleText
        clearanceText = a.getString(R.styleable.FoundationCardView_clearanceText) ?: clearanceText
        gradeText = a.getString(R.styleable.FoundationCardView_gradeText) ?: gradeText
        descriptionText = a.getString(R.styleable.FoundationCardView_android_text)
            ?: descriptionText

        a.recycle()
    }
}