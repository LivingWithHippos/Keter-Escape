package scp.libraries.ui.cards

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import scp.libraries.ui.R
import scp.libraries.ui.utilities.*
import scp.libraries.ui.views.SCPSecondaryClassView

class SCPCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val containmentView: SCPSecondaryClassView
    private val riskView: SCPSecondaryClassView
    private val disruptionView: SCPSecondaryClassView
    private val clearanceLevelTitleView: TextView
    private val clearanceLevelDescriptionView: TextView
    private val scpNumberView: TextView
    private val scpDescriptionView: TextView

    private var containmentClassValue: Int = -1
        set(value) {
            //todo: add remaining classes and -1
            when(value){
                CONTAINMENT_CLASS_SAFE -> setupSCPClassView(
                    containmentView,
                    SCP_VIEW_MODE_CONTAINMENT,
                    context.getString(R.string.scp_containment),
                    context.getString(R.string.scp_containment_safe),
                    context.getColor(R.color.safe),
                    context.getColor(R.color.safe_secondary),
                    context.getDrawable(R.drawable.scp_containment_safe_icon) )

                CONTAINMENT_CLASS_EUCLID -> setupSCPClassView(
                    containmentView,
                    SCP_VIEW_MODE_CONTAINMENT,
                    context.getString(R.string.scp_containment),
                    context.getString(R.string.scp_containment_euclid),
                    context.getColor(R.color.euclid),
                    context.getColor(R.color.euclid_secondary),
                    context.getDrawable(R.drawable.scp_containment_euclid_icon) )

                CONTAINMENT_CLASS_KETER -> setupSCPClassView(
                    containmentView,
                    SCP_VIEW_MODE_CONTAINMENT,
                    context.getString(R.string.scp_containment),
                    context.getString(R.string.scp_containment_keter),
                    context.getColor(R.color.keter),
                    context.getColor(R.color.keter_secondary),
                    context.getDrawable(R.drawable.scp_containment_keter_icon) )

            }
            field = value
        }


    private var disruptionClassValue: Int = -1
        set(value) {
            //todo: add remaining classes and -1
            when(value){
                DISRUPTION_CLASS_DARK -> setupSCPClassView(
                    disruptionView,
                    SCP_VIEW_MODE_DISRUPTION,
                    context.getString(R.string.scp_disruption),
                    context.getString(R.string.scp_disruption_dark),
                    context.getColor(R.color.dark),
                    context.getColor(R.color.dark_secondary),
                    context.getDrawable(R.drawable.scp_disruption_dark_icon),
                    DISRUPTION_CLASS_DARK )

                DISRUPTION_CLASS_VLAM -> setupSCPClassView(
                    disruptionView,
                    SCP_VIEW_MODE_DISRUPTION,
                    context.getString(R.string.scp_disruption),
                    context.getString(R.string.scp_disruption_vlam),
                    context.getColor(R.color.vlam),
                    context.getColor(R.color.vlam_secondary),
                    context.getDrawable(R.drawable.scp_disruption_vlam_icon),
                    DISRUPTION_CLASS_VLAM )

            }
            field = value
        }

    private var riskClassValue: Int = -1
        set(value) {
            //todo: add remaining classes and -1
            when(value){
                RISK_CLASS_NOTICE -> setupSCPClassView(
                    riskView,
                    SCP_VIEW_MODE_RISK,
                    context.getString(R.string.scp_risk),
                    context.getString(R.string.scp_risk_notice),
                    context.getColor(R.color.notice),
                    context.getColor(R.color.notice_secondary),
                    context.getDrawable(R.drawable.scp_risk_notice_icon),
                    RISK_CLASS_NOTICE )

                RISK_CLASS_CAUTION -> setupSCPClassView(
                    riskView,
                    SCP_VIEW_MODE_RISK,
                    context.getString(R.string.scp_risk),
                    context.getString(R.string.scp_risk_caution),
                    context.getColor(R.color.caution),
                    context.getColor(R.color.caution_secondary),
                    context.getDrawable(R.drawable.scp_risk_caution_icon),
                    RISK_CLASS_CAUTION )

            }
            field = value
        }

    private var clearanceLevelValue: Int = -1
        set(value) {
            //todo: add remaining classes and -1
            //todo: add number of lines
            when(value){
                CLEARANCE_LEVEL_UNRESTRICTED -> {
                    clearanceLevelTitleView.text = context.getString(R.string.scp_clearance_level,CLEARANCE_LEVEL_UNRESTRICTED)
                    clearanceLevelDescriptionView.text = context.getString(R.string.scp_clearance_level_unrestricted)
                }
                CLEARANCE_LEVEL_RESTRICTED -> {
                    clearanceLevelTitleView.text = context.getString(R.string.scp_clearance_level,CLEARANCE_LEVEL_RESTRICTED)
                    clearanceLevelDescriptionView.text = context.getString(R.string.scp_clearance_level_restricted)
                }
            }
            field = value
        }

    private var scpNumberText: String = ""
        set(value) {
            scpNumberView.text = value
            field = value
        }

    private var scpDescriptionText: String = ""
        set(value) {
            scpDescriptionView.text = value
            field = value
        }

    private fun setupSCPClassView(view: SCPSecondaryClassView,
                                  mode: Int,
                                  classTitle: String,
                                  classBody: String,
                                  darkColor: Int,
                                  lightColor: Int,
                                  classPic: Drawable?,
                                  classNumber: Int = -1
    ) {
        view.modeID = mode
        view.classTitleText = classTitle
        view.classBodyText = classBody
        view.darkFillColor = darkColor
        view.lightFillColor = lightColor
        view.classPic = classPic
        view.classNumberText = classNumber.toString()
    }

    init {
        val view = View.inflate(context, R.layout.card_scp_view_layout, this)

        containmentView = view.findViewById(R.id.containment)
        disruptionView = view.findViewById(R.id.disruption)
        riskView = view.findViewById(R.id.risk)
        clearanceLevelTitleView = view.findViewById(R.id.tv_level_title)
        clearanceLevelDescriptionView = view.findViewById(R.id.tv_level_description)
        scpNumberView = view.findViewById(R.id.tv_item)
        scpDescriptionView = view.findViewById(R.id.scp_card_body)


        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SCPCardView,
            defStyleAttr,
            defStyleRes
        )

        containmentClassValue = a.getInteger(R.styleable.SCPCardView_containment, containmentClassValue)
        disruptionClassValue = a.getInteger(R.styleable.SCPCardView_disruption, disruptionClassValue)
        riskClassValue = a.getInteger(R.styleable.SCPCardView_risk, riskClassValue)
        clearanceLevelValue = a.getInteger(R.styleable.SCPCardView_clearance, clearanceLevelValue)
        scpNumberText = a.getString(R.styleable.SCPCardView_scpNumber) ?: scpNumberText
        scpDescriptionText = a.getString(R.styleable.SCPCardView_scpDescription) ?: scpDescriptionText

    }
}