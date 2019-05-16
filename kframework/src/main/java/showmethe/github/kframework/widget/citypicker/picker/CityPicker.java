package showmethe.github.kframework.widget.citypicker.picker;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import showmethe.github.kframework.R;
import showmethe.github.kframework.adapter.BaseRecyclerViewAdapter;
import showmethe.github.kframework.base.BaseActivity;
import showmethe.github.kframework.util.ToastFactory;
import showmethe.github.kframework.widget.citypicker.bean.CityBean;
import showmethe.github.kframework.widget.citypicker.bean.DistrictBean;
import showmethe.github.kframework.widget.citypicker.bean.ProvinceBean;
import showmethe.github.kframework.widget.citypicker.helper.CityParseHelper;

/**
 * showmethe.github.kframework.widget.citypicker.picker
 * cuvsu
 * 2019/3/18
 **/
public class CityPicker extends DialogFragment {

    private BaseActivity<?,?> context;
    private RadioGroup rg;
    private RadioButton rb1,rb2,rb3;
    private RadioButton[] radioButtons;
    private RecyclerView rv;
    private CityParseHelper parseHelper;
    private ProvinceAdapter mProvinceAdapter;
    private CityAdapter mCityAdapter;
    private AreaAdapter mAreaAdapter;
    private TextView tvConfirm,tvCancel;
    private ProvinceBean provinceBean;
    private CityBean cityBean;
    private DistrictBean  districtBean;
    private ObservableArrayList<ProvinceBean> provinceList = new ObservableArrayList<>();
    private ObservableArrayList<CityBean> cityList = new ObservableArrayList<>();
    private ObservableArrayList<DistrictBean> areaList = new ObservableArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (BaseActivity<?, ?>) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

       Dialog dialog = new Dialog(context);
       View view  = View.inflate(context, R.layout.dialog_city_picker,null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);

        Window window = dialog.getWindow();
        if(window!=null){
            DisplayMetrics dm = new DisplayMetrics();
            window.getWindowManager().getDefaultDisplay().getMetrics(dm);
            window.setLayout(dm.widthPixels,window.getAttributes().height);
            window.setBackgroundDrawable(new ColorDrawable(0x00000000));
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.AnimBottom);
        }

        init(view);
        initData();
        initAdapter();


        return  dialog;
    }




    private void init(View view) {
        rg = view.findViewById(R.id.rg);
        rb1 = view.findViewById(R.id.rb1);
        rb2= view.findViewById(R.id.rb2);
        rb3 = view.findViewById(R.id.rb3);
        radioButtons = new RadioButton[]{rb1,rb2,rb3};
        tvConfirm = view.findViewById(R.id.tvConfirm);
        tvCancel = view.findViewById(R.id.tvCancel);
        rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        updateIsClickable();
    }



    private void initData() {
        if (parseHelper == null) {
            parseHelper = new CityParseHelper();
        }
        if (parseHelper.getProvinceBeanArrayList().isEmpty()) {
            parseHelper.initData(context);
        }

        provinceList.addAll(parseHelper.getProvinceBeanArrayList());

    }



    private BaseRecyclerViewAdapter.OnItemClickListener provinceListener = new BaseRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(@NotNull View view, int position) {
            provinceBean = mProvinceAdapter.getMData().get(position);
            mProvinceAdapter.updateSelectedPosition(position);
            updateTab(0,provinceBean.getName());
            if(mCityAdapter == null){
                mCityAdapter = new CityAdapter(context,provinceBean.getCityList());
                mCityAdapter.setOnItemClickListener(cityListener);
            }else {
                mCityAdapter.setMData(provinceBean.getCityList());
            }
            rv.setAdapter(mCityAdapter);
        }
    };


    private BaseRecyclerViewAdapter.OnItemClickListener cityListener = new BaseRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(@NotNull View view, int position) {
            cityBean = mCityAdapter.getMData().get(position);
            mCityAdapter.updateSelectedPosition(position);

            updateTab(1,cityBean.getName());
            if(mAreaAdapter == null){
                mAreaAdapter = new AreaAdapter(context,cityBean.getCityList());
                mAreaAdapter.setOnItemClickListener(areaListener);
            }else {
                mAreaAdapter.setMData(cityBean.getCityList());
            }
            rv.setAdapter(mAreaAdapter);

        }
    };


    private BaseRecyclerViewAdapter.OnItemClickListener areaListener = new BaseRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(@NotNull View view, int position) {
            districtBean = mAreaAdapter.getMData().get(position);
            updateTab(2,districtBean.getName());
            mAreaAdapter.updateSelectedPosition(position);

        }
    };



    private void initAdapter() {
        mProvinceAdapter = new ProvinceAdapter( context,provinceList);
        rv.setAdapter(mProvinceAdapter);

        mProvinceAdapter.setOnItemClickListener(provinceListener);


        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onCityPickListener!=null){
                    onCityPickListener.onCityPick(provinceBean,cityBean,districtBean);
                }
                dismiss();
            }
        });

        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv.setAdapter(mProvinceAdapter);
                if(mAreaAdapter!=null){
                    mAreaAdapter.updateSelectedPosition(-1);
                }
               if(mCityAdapter!=null){
                   mCityAdapter.updateSelectedPosition(-1);
               }
                rv.smoothScrollToPosition(mProvinceAdapter.getSelectedPosition());
                clearTab(0);
            }
        });




        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv.setAdapter(mCityAdapter);
                if(mAreaAdapter!=null){
                    mAreaAdapter.updateSelectedPosition(-1);
                }
                rv.smoothScrollToPosition(mCityAdapter.getSelectedPosition());
                clearTab(1);
            }
        });

        rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv.setAdapter(mAreaAdapter);
                rv.smoothScrollToPosition(mAreaAdapter.getSelectedPosition());
            }
        });




    }

    private onCityPickListener onCityPickListener;

    public void setOnCityPickListener(CityPicker.onCityPickListener onCityPickListener) {
        this.onCityPickListener = onCityPickListener;
    }

    public interface onCityPickListener{
        void onCityPick( ProvinceBean provinceBean ,CityBean cityBean,DistrictBean  districtBean);
    }



    private void updateTab(int position,String s){
        radioButtons[position].setText(s);
        updateIsClickable();
    }


    private void clearTab(int size){
        for(int i =0;i<radioButtons.length;i++){
            if(i>size){
                radioButtons[i].setChecked(false);
                radioButtons[i].setClickable(false);
                radioButtons[i].setText("");
            }
        }
    }


   private void updateIsClickable(){
        for(int i =0;i<radioButtons.length;i++){
            if(radioButtons[i].getText().toString().isEmpty()){
                radioButtons[i].setClickable(false);
                radioButtons[i].setChecked(false);
            }else {
                radioButtons[i].setClickable(true);
                radioButtons[i].setChecked(true);
            }
        }

   }



}
