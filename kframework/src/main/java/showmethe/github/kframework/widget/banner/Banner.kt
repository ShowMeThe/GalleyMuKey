package showmethe.github.kframework.widget.banner

import android.content.Context
import android.content.res.TypedArray
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout

import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import androidx.viewpager2.widget.ViewPager2

import java.util.ArrayList

import showmethe.github.kframework.R


/**
 * showmethe.github.kframework.widget.banner
 * cuvsu
 * 2019/5/8
 */
class Banner @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

    private val imageList = ObservableArrayList<Any>()
    private var viewPager: ViewPager2? = null
    lateinit var adapter: BannerViewAdapter
    private var dotTabView: DotTabView? = null
    private var autoPlay = false
    private var smoothType = true
    private var selectColor: Int = 0
    private var unselectColor: Int = 0
    private var selectRadius: Int = 0
    private var unSelectRadius: Int = 0
    private var count = 0
    private var currentItem: Long = 0
    private var delayTime = TIME
    private var showIndicator = true
    var mMinHeight = 0f
    private var mMaxHeight = 0f
    private val mHandler = Handler(Looper.getMainLooper())



    private val task = object : Runnable {
        override fun run() {
            if (count > 1 && autoPlay) {
                val current = (currentItem % count).toInt()
                if (smoothType) {
                    viewPager!!.setCurrentItem(current, true)
                }
                currentItem++
                mHandler.postDelayed(this, delayTime.toLong())
            }
        }
    }


    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {


        initType(context, attrs)

        val view = LayoutInflater.from(context).inflate(layoutId, null)
        viewPager = view.findViewById(R.id.viewPager)
        dotTabView = view.findViewById(R.id.dot)

        if(showIndicator){
            dotTabView?.visibility = View.VISIBLE
        }

        adapter = BannerViewAdapter(context, imageList)
        viewPager?.adapter = adapter
        viewPager?.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager?.offscreenPageLimit = 5

        viewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                currentItem = position.toLong()
                onPagerListener?.invoke(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        adapter.setOnImageLoader(object : BannerViewAdapter.onImageLoader {
            override fun display(url: Any, imageView: ImageView) {
                loader?.apply {
                    imageView.maxHeight = mMaxHeight.toInt()
                    imageView.minimumHeight = mMinHeight.toInt()
                    invoke(url, imageView)
                }
            }
        })

        adapter.setOnItemClickListener { view, position ->
            onPageClick?.invoke(position)
        }

        addViewInLayout(view, -1, DEFAULT_LAYOUT_PARAMS, true)
    }

    private fun initType(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.Banner)
        autoPlay = array.getBoolean(R.styleable.Banner_autoPlay, true)
        selectColor = array.getColor(R.styleable.Banner_selected_color, ContextCompat.getColor(context, R.color.colorAccent))
        unselectColor = array.getColor(R.styleable.Banner_unselected_color, ContextCompat.getColor(context, R.color.white_85))
        mMinHeight = array.getDimension(R.styleable.Banner_imageMinHeight,resources.getDimension(R.dimen.px600dp))
        mMaxHeight = array.getDimension(R.styleable.Banner_imageMaxHeight,resources.getDimension(R.dimen.px2500dp))
        selectRadius = array.getDimension(R.styleable.Banner_select_radius, 16f).toInt()
        unSelectRadius = array.getDimension(R.styleable.Banner_unSelect_radius, 15f).toInt()
        delayTime = array.getInt(R.styleable.Banner_delayTime, TIME)
        showIndicator = array.getBoolean(R.styleable.Banner_showIndicator,true)
        array.recycle()
    }


    var onPagerListener: ((position : Int)->Unit)? = null

    fun setOnPagerLisnener(onPagerListener: ((position : Int)->Unit)){
        this.onPagerListener = onPagerListener
    }

    var onPageClick: ((position:Int)->Unit)? = null

    fun setOnPageClickListner(onPageClick : ((position:Int)->Unit)){
        this.onPageClick = onPageClick
    }


        fun setCurrentPosition(position : Int){
        if(position<=imageList.size-1){
            currentItem = position.toLong()
            viewPager?.post {
                viewPager!!.setCurrentItem(position,false)
            }
        }
    }


    fun play() {
        autoPlay = true
        currentItem = 1
        if (imageList.size > 1) {
            mHandler.removeCallbacks(task)
            mHandler.postDelayed(task, delayTime.toLong())
        }
    }

    fun stopPlay() {
        mHandler.removeCallbacks(task)
    }


    private fun resumePlay() {
        autoPlay = true
        count = imageList.size
        mHandler.removeCallbacks(task)
        mHandler.postDelayed(task, delayTime.toLong())

    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (autoPlay && imageList.size > 1) {
            play()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopPlay()
    }



    var downX = 0f
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if(!autoPlay) return super.dispatchTouchEvent(ev)

        val action = ev.action
        when(action){
            MotionEvent.ACTION_UP,MotionEvent.ACTION_CANCEL,MotionEvent.ACTION_OUTSIDE -> {
                val current = (currentItem % (count)).toInt()
                if(downX - ev.x < 0 && current == 0){
                    viewPager?.post {
                        viewPager?.setCurrentItem(imageList.size - 1 ,true)
                    }
                }else if(downX - ev.x > 0 && current == imageList.size - 1){
                    viewPager?.post {
                        viewPager?.setCurrentItem(0 ,true)
                    }
                }
                if(autoPlay){
                    resumePlay()
                }
            }
            MotionEvent.ACTION_DOWN -> {
                stopPlay()
                downX = ev.x
                currentItem = viewPager?.currentItem!!.toLong()
            }
        }
        return super.dispatchTouchEvent(ev)
    }


    var loader :((url: Any, imageView: ImageView)->Unit)? = null


    fun setOnImageLoader(loader: (url: Any, imageView: ImageView)->Unit) {
        this.loader = loader
    }

    fun addList(arrayList: ArrayList<*>) {
        imageList.clear()
        imageList.addAll(arrayList)
        if(showIndicator){
            dotTabView!!.setIndicatorRadius(selectRadius, unSelectRadius)
            dotTabView!!.setViewPager2(viewPager, imageList.size, 10, selectColor, unselectColor)
        }
        count = imageList.size
    }

    companion object {

        private val layoutId = R.layout.banner_layout
        private val DEFAULT_LAYOUT_PARAMS = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT)
        val TIME = 3000
    }

}
