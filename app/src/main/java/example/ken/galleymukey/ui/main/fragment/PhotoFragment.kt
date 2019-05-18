package example.ken.galleymukey.ui.main.fragment

import android.os.Bundle
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.PhotoWallBean
import example.ken.galleymukey.databinding.FragmentPhotoBinding
import example.ken.galleymukey.ui.main.adapter.PhotoAdapter
import example.ken.galleymukey.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.fragment_galley.*
import kotlinx.android.synthetic.main.fragment_photo.*
import showmethe.github.kframework.base.BaseFragment

/**
 * example.ken.galleymukey.ui.main.fragment
 * cuvsu
 * 2019/5/18
 **/
class PhotoFragment : BaseFragment<FragmentPhotoBinding, MainViewModel>() {



    val list = ObservableArrayList<PhotoWallBean>()
    lateinit var adapter : PhotoAdapter
    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)
    override fun getViewId(): Int = R.layout.fragment_photo

    override fun onBundle(bundle: Bundle) {
    }

    override fun observerUI() {



    }


    override fun init(savedInstanceState: Bundle?) {

        refresh.setColorSchemeResources(R.color.colorPrimaryDark)

        for(i in 1..20){
            list.add(PhotoWallBean())
        }
        adapter = PhotoAdapter(context,list)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)


    }

    override fun initListener() {
    }
}