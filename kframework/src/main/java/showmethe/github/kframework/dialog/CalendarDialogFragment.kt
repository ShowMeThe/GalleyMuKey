package showmethe.github.kframework.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.*
import showmethe.github.kframework.dialog.adapter.YearAdapter
import kotlinx.android.synthetic.main.dialog_canlendar.view.*
import showmethe.github.kframework.R
import java.lang.Exception
import java.util.*

/**
 * example.ken.dialog
 *
 * 2019/5/28
 **/
class CalendarDialogFragment : DialogFragment() {

    lateinit var mContext: Context
    val list = ObservableArrayList<String>()
    lateinit var adapter: YearAdapter
    val instant = Calendar.getInstance(Locale.CHINA)
    var day = 0
    var month = 0
    var year = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(mContext)
        val view = View.inflate(mContext, R.layout.dialog_canlendar, null)
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(true)

        if (dialog.window != null) {
            val window = dialog.window
            val dm = DisplayMetrics()
            window?.apply {
                windowManager.defaultDisplay.getMetrics(dm)
                setLayout(dm.widthPixels, window.attributes.height)
                setBackgroundDrawable(ColorDrawable(0x00000000))
                setGravity(Gravity.CENTER)
                setWindowAnimations(R.style.LeftRightAnim)
            }
        }

        list.clear()
        for(i in 1990..2100){
            list.add("${i}")
        }

        view?.apply {

            val shapeAppearanceModel = ShapeAppearanceModel()
            shapeAppearanceModel.setTopLeftCorner(CornerFamily.CUT,45)
            val drawable = MaterialShapeDrawable(shapeAppearanceModel)
            drawable.fillColor = ContextCompat.getColorStateList(context,R.color.white)
            cardView.background = drawable

            adapter = YearAdapter(mContext,list)
            rv.adapter = adapter
            rv.layoutManager = LinearLayoutManager(mContext,RecyclerView.VERTICAL,false)

            if(arguments!=null){
                arguments?.apply {
                    year = getInt("year")
                    month = getInt("month")
                    day = getInt("day")
                    adapter.currentPos = year - 1990
                }
            }else if(year == 0|| day ==0 || month == 0){
                year = instant.get(Calendar.YEAR)
                day = instant.get(Calendar.DAY_OF_MONTH)
                month = instant.get(Calendar.MONTH)+1
                adapter.currentPos = instant.get(Calendar.YEAR) - 1990
            }

            instant.set(Calendar.YEAR,year)
            instant.set(Calendar.MONTH,month-1)
            instant.set(Calendar.DAY_OF_MONTH,day)
            calendar.date = instant.timeInMillis


            tvYear.text = year.toString()
            tvDate.text = "$month/${day}"

            adapter.notifyDataSetChanged()
            rv.smoothScrollToPosition(adapter.currentPos)

            calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
                this@CalendarDialogFragment.month  = month+1
                this@CalendarDialogFragment.day = dayOfMonth
                this@CalendarDialogFragment.year = year
                tvYear.text = year.toString()
                tvDate.text = "$dayOfMonth/${month+1}"

            }


            adapter.setOnItemClickListener { view, position ->
                val pos = adapter.currentPos
                adapter.currentPos = position
                adapter.notifyItemChanged(pos)
                adapter.notifyItemChanged(adapter.currentPos)

                year = list[adapter.currentPos].toInt()
                tvYear.text = year.toString()

                instant.set(Calendar.YEAR,year)
                instant.set(Calendar.MONTH,month-1)
                instant.set(Calendar.DAY_OF_MONTH,day)
                calendar.date = instant.timeInMillis

                rv.smoothScrollToPosition(adapter.currentPos)
            }

            rg.setOnCheckedChangeListener { group, checkedId ->
                when(checkedId){
                    tvYear.id ->{
                        rv.visibility = View.VISIBLE
                        calendar.visibility = View.INVISIBLE
                    }

                    tvDate.id ->{
                        rv.visibility = View.INVISIBLE
                        calendar.visibility = View.VISIBLE
                    }
                }
            }


            btnDone.setOnClickListener {
                onDatePickDialog?.invoke(day,month,year)
                dialog.dismiss()
            }

        }

        return dialog
    }

    var onDatePickDialog:((day:Int,month:Int,year:Int)->Unit)? = null

    fun setOnDatePickDialogListener(onDatePickDialog:((day:Int,month:Int,year:Int)->Unit)){
        this.onDatePickDialog = onDatePickDialog
    }


    fun setDefaultDate(day:Int,month:Int,year:Int){
        val bundle = Bundle()
        bundle.putInt("day",day)
        bundle.putInt("month",month)
        bundle.putInt("year",year)
        arguments = bundle
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            if(!isAdded){
                super.show(manager, tag)
            }
        }catch (e :Exception){

        }
    }

}