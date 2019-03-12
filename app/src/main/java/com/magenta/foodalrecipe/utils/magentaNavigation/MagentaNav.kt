package com.magenta.foodalrecipe.utils.magentaNavigation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MenuInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.MenuRes
import androidx.appcompat.view.menu.MenuBuilder
import androidx.cardview.widget.CardView
import com.codebox.lib.android.utils.screenHelpers.ScreenUtils
import com.magenta.foodalrecipe.R
import com.magenta.foodalrecipe.utils.magentaNavigation.helpers.get
import com.magenta.foodalrecipe.utils.magentaNavigation.helpers.getDim
import com.magenta.foodalrecipe.utils.magentaNavigation.model.ItemsModel
import com.magenta.foodalrecipe.utils.magentaNavigation.onClick.OnNavItemClicked
import com.magenta.foodalrecipe.utils.magentaNavigation.onClick.cardBackgroundAnimator
import com.magenta.foodalrecipe.utils.magentaNavigation.onClick.loadDefaultActionState
import kotlin.math.max
import kotlin.math.roundToInt

//A new stylish Navigation
@Suppress("MemberVisibilityCanBePrivate")
class MagentaNav : LinearLayout {
    var defaultNavColor: Int = Color.WHITE
    var navColor = Color.parseColor("#970067")
    var accentDefaultColor: Int = Color.parseColor("#707070")
    var accentActiveColor: Int = Color.WHITE
    var isTextSingleLine: Boolean = false
    var defaultPosition = 0
    private var attribute: AttributeSet? = null
    private val screenUtils = ScreenUtils(context)
    private var navItems = mutableListOf<ItemsModel>()
    private val cardViews = mutableListOf<CardView>()
    private var isAnimatingNav = false

    private lateinit var cardNavParams: ViewGroup.LayoutParams
    private val textNavParams =
        LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).defaultMargin()

    var cardNavRadius = screenUtils.dp(8f)
    var cardNavElevation = screenUtils.dp(1f)
    var onClickElevation = screenUtils.dp(6f)
    var marginBetweenItems = 4f
    var navTextSize: Float = 21f
        set(value) {
            field = screenUtils.pixelsToSp(value)
        }

    var itemWidth = LayoutParams.MATCH_PARENT
    var itemHeight = LayoutParams.WRAP_CONTENT
    var imgNavScale = 1f
    var isBoldFont = true

    private var itemClickListener: OnNavItemClicked? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        attribute = attrs
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        attribute = attrs
        init()
    }

    private fun init() {
        orientation = LinearLayout.HORIZONTAL
        attributeBuilder()
        itemBuilder()
    }

    fun createNav(items: MutableList<ItemsModel>) {
        navItems = items
        itemBuilder()
    }
    //Navigation

    fun createNav(@MenuRes menuRes: Int) {
        menuBuilder(menuRes)
        itemBuilder()
    }

    private fun attributeBuilder() {
        if (attribute == null) {
            return
        }
        val ta = context.obtainStyledAttributes(attribute, R.styleable.MagentaNav)
        val menuRes = ta.getResourceId(R.styleable.MagentaNav_menuRes, 0)
        defaultNavColor = ta.getColor(R.styleable.MagentaNav_defaultColor, defaultNavColor)
        navColor = ta.getColor(R.styleable.MagentaNav_navColor, navColor)
        accentActiveColor = ta.getColor(R.styleable.MagentaNav_accentActiveColor,accentActiveColor)
        marginBetweenItems = ta.getDimension(R.styleable.MagentaNav_marginBetweenItems, marginBetweenItems)
        cardNavRadius = ta.getDimension(R.styleable.MagentaNav_navRadius, cardNavRadius)
        cardNavElevation = ta.getDimension(R.styleable.MagentaNav_navElevation, cardNavElevation)
        onClickElevation = ta.getDimension(R.styleable.MagentaNav_onClickItemElevation, onClickElevation)
        //secondary element
        navTextSize = ta.getDimension(R.styleable.MagentaNav_text_size, navTextSize)
        isTextSingleLine = ta.getBoolean(R.styleable.MagentaNav_textSingleLine, isTextSingleLine)
        isBoldFont = ta.getBoolean(R.styleable.MagentaNav_boldFont, isBoldFont)

        imgNavScale = ta.getFloat(R.styleable.MagentaNav_scaleImg, imgNavScale)
        itemWidth = ta.getDim(R.styleable.MagentaNav_itemWidth, itemWidth).toInt()
        itemHeight = ta.getDim(R.styleable.MagentaNav_itemHeight, itemHeight).toInt()

        defaultPosition = ta.getInteger(R.styleable.MagentaNav_default_position, defaultPosition)
        ta.recycle()

        if (menuRes != 0) menuBuilder(menuRes)
    }

    @SuppressLint("RestrictedApi")
    private fun menuBuilder(@MenuRes menuRes: Int) {
        navItems.clear()
        val menu = MenuBuilder(context)
        MenuInflater(context).inflate(menuRes, menu)

        for (p in 0 until menu.size()) {
            val item = menu.getItem(p)
            navItems.add(ItemsModel(item.title.toString(), item.icon, item.itemId))
        }
    }

    private fun itemBuilder() {
        val parenItemParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, itemHeight, 1f).defaultMargin()
        cardNavParams = LinearLayout.LayoutParams(itemWidth, LayoutParams.WRAP_CONTENT).defaultMargin().apply {
            gravity = Gravity.CENTER_HORIZONTAL
        }
        for (pos in 0 until navItems.size) {
            val item = navItems[pos]
            val cardNavItem = navCard(item.drawable)
            cardViews.add(cardNavItem)

            val navItemParent = LinearLayout(context).apply {
                this.orientation = LinearLayout.VERTICAL
                addView(cardNavItem, cardNavParams)
                addView(navText(item.title), textNavParams)
                //  setBackgroundColor(Color.RED)
            }
            addView(navItemParent, parenItemParams)
            /*if (pos == defaultPosition) {
                animateNavItem(pos)
            }*/
            cardNavItem.setOnClickListener {
                if (!isAnimatingNav) {
                    isAnimatingNav = true

                    itemClickListener?.onMagentaNavItemClicked(item.itemID, pos)

                    (context as? OnNavItemClicked)?.onMagentaNavItemClicked(item.itemID, pos)

                    animateNavItem(pos)

                }
            }

        }
    }


    fun animateNavItem(position: Int){

        val parentItem = this[position] as LinearLayout
        parentItem.cardBackgroundAnimator(
            500,
            navColor,
            defaultNavColor,
            accentActiveColor,
            accentDefaultColor
        ) {
            isAnimatingNav = false
            cardViews[position].cardElevation = onClickElevation
        }
        defaultState(position)
    }

    private fun defaultState(position: Int){
        this@MagentaNav.loadDefaultActionState(
            cardViews[position],
            cardNavElevation,
            accentDefaultColor,
            defaultNavColor
        )
    }

    private fun getMaxWidth(): Int {
        var maxTextSize = 0f

        for (i in 0 until navItems.size) {
            val perCharSize = navItems[i].title.length * navTextSize
            if (maxTextSize < perCharSize)
                maxTextSize = perCharSize
        }
        return max((maxTextSize + screenUtils.dp(10f)).roundToInt(), itemWidth)
    }

    private fun navCard(childImg: Drawable) = CardView(context).apply {
        this.setCardBackgroundColor(defaultNavColor)
        this.layoutParams = cardNavParams
        this.radius = cardNavRadius
        this.cardElevation = cardNavElevation
        this.addView(navImage(childImg))
    }

    private fun navImage(img: Drawable): RelativeLayout {
        val params = RelativeLayout.LayoutParams(itemWidth, RelativeLayout.LayoutParams.WRAP_CONTENT)

        return RelativeLayout(context).apply {
            val imgV = ImageView(context).apply {
                setImageDrawable(img)
                setColorFilter(accentDefaultColor)
            }
            params.addRule(RelativeLayout.CENTER_IN_PARENT)
            params.setMargins(0, screenUtils.dp(15f).toInt(), 0, screenUtils.dp(15f).toInt())
            imgV.layoutParams = params
            scaleX = imgNavScale
            scaleY = imgNavScale

            addView(imgV, params)
        }

    }

    private fun navText(title: String) = TextView(context).apply {
        gravity = Gravity.CENTER_HORIZONTAL
        setSingleLine(isTextSingleLine)
        layoutParams = textNavParams
        textSize = navTextSize
        text = title

        if (isBoldFont)
            typeface = Typeface.DEFAULT_BOLD

    }

    fun callOnClickNavigation(position: Int) {
        cardViews[position].callOnClick()
    }

    private fun LayoutParams.defaultMargin(): LayoutParams {
        this.setMargins(
            marginBetweenItems.roundToInt(),
            screenUtils.dp(10f).toInt(),
            marginBetweenItems.roundToInt(),
            0
        )
        return this
    }


    internal var TextView.sizeInSp: Float
        get() = screenUtils.pixelsToSp(textSize)
        set(value) = setTextSize(TypedValue.COMPLEX_UNIT_SP, value)

    fun doOnItemClick(listener: OnNavItemClicked) {
        itemClickListener = listener
    }
}


