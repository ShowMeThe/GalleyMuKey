package showmethe.github.kframework.widget.citypicker.helper;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.databinding.ObservableArrayList;
import showmethe.github.kframework.util.assetFile.AssetFile;
import showmethe.github.kframework.widget.citypicker.AssestConstant;
import showmethe.github.kframework.widget.citypicker.bean.CityBean;
import showmethe.github.kframework.widget.citypicker.bean.DistrictBean;
import showmethe.github.kframework.widget.citypicker.bean.ProvinceBean;


public class CityParseHelper {
    
    /**
     * 省份数据
     */
    private ObservableArrayList<ProvinceBean> mProvinceBeanArrayList = new ObservableArrayList<>();
    
    /**
     * 城市数据
     */
    private ObservableArrayList<ArrayList<CityBean>> mCityBeanArrayList;
    
    /**
     * 地区数据
     */
    private ObservableArrayList<ObservableArrayList<ObservableArrayList<DistrictBean>>> mDistrictBeanArrayList;
    
    private ProvinceBean[] mProvinceBeenArray;
    
    private ProvinceBean mProvinceBean;
    
    private CityBean mCityBean;
    
    private DistrictBean mDistrictBean;
    
    //    private CityConfig config;
    
    /**
     * key - 省 value - 市
     */
    private Map<String, CityBean[]> mPro_CityMap = new HashMap<String, CityBean[]>();
    
    /**
     * key - 市 values - 区
     */
    private Map<String, DistrictBean[]> mCity_DisMap = new HashMap<String, DistrictBean[]>();
    
    /**
     * key - 区 values - 邮编
     */
    private Map<String, DistrictBean> mDisMap = new HashMap<String, DistrictBean>();
    
    public ObservableArrayList<ProvinceBean> getProvinceBeanArrayList() {
        return mProvinceBeanArrayList;
    }
    
    public void setProvinceBeanArrayList(ObservableArrayList<ProvinceBean> provinceBeanArrayList) {
        mProvinceBeanArrayList = provinceBeanArrayList;
    }
    
    public ArrayList<ArrayList<CityBean>> getCityBeanArrayList() {
        return mCityBeanArrayList;
    }
    
    public void setCityBeanArrayList(ObservableArrayList<ArrayList<CityBean>> cityBeanArrayList) {
        mCityBeanArrayList = cityBeanArrayList;
    }
    
    public ObservableArrayList<ObservableArrayList<ObservableArrayList<DistrictBean>>> getDistrictBeanArrayList() {
        return mDistrictBeanArrayList;
    }
    
    public void setDistrictBeanArrayList(ObservableArrayList<ObservableArrayList<ObservableArrayList<DistrictBean>>> districtBeanArrayList) {
        mDistrictBeanArrayList = districtBeanArrayList;
    }
    
    public ProvinceBean[] getProvinceBeenArray() {
        return mProvinceBeenArray;
    }
    
    public void setProvinceBeenArray(ProvinceBean[] provinceBeenArray) {
        mProvinceBeenArray = provinceBeenArray;
    }
    
    public ProvinceBean getProvinceBean() {
        return mProvinceBean;
    }
    
    public void setProvinceBean(ProvinceBean provinceBean) {
        mProvinceBean = provinceBean;
    }
    
    public CityBean getCityBean() {
        return mCityBean;
    }
    
    public void setCityBean(CityBean cityBean) {
        mCityBean = cityBean;
    }
    
    public DistrictBean getDistrictBean() {
        return mDistrictBean;
    }
    
    public void setDistrictBean(DistrictBean districtBean) {
        mDistrictBean = districtBean;
    }
    
    public Map<String, CityBean[]> getPro_CityMap() {
        return mPro_CityMap;
    }
    
    public void setPro_CityMap(Map<String, CityBean[]> pro_CityMap) {
        mPro_CityMap = pro_CityMap;
    }
    
    public Map<String, DistrictBean[]> getCity_DisMap() {
        return mCity_DisMap;
    }
    
    public void setCity_DisMap(Map<String, DistrictBean[]> city_DisMap) {
        mCity_DisMap = city_DisMap;
    }
    
    public Map<String, DistrictBean> getDisMap() {
        return mDisMap;
    }
    
    public void setDisMap(Map<String, DistrictBean> disMap) {
        mDisMap = disMap;
    }
    
    public CityParseHelper() {
        
    }
    
    /**
     * 初始化数据，解析json数据
     */
    public void initData(Context context) {
        
        String cityJson = AssetFile.Companion.getJson(context, AssestConstant.CITY_DATA);
        Type type = new TypeToken<ObservableArrayList<ProvinceBean>>() {
        }.getType();

        mProvinceBeanArrayList = new Gson().fromJson(cityJson, type);
        
        if (mProvinceBeanArrayList == null || mProvinceBeanArrayList.isEmpty()) {
            return;
        }
        
        mCityBeanArrayList = new ObservableArrayList<>();
        mDistrictBeanArrayList = new ObservableArrayList<>();
        
        //*/ 初始化默认选中的省、市、区，默认选中第一个省份的第一个市区中的第一个区县
        if (mProvinceBeanArrayList != null && !mProvinceBeanArrayList.isEmpty()) {
            mProvinceBean = mProvinceBeanArrayList.get(0);
            List<CityBean> cityList = mProvinceBean.getCityList();
            if (cityList != null && !cityList.isEmpty() && cityList.size() > 0) {
                mCityBean = cityList.get(0);
                List<DistrictBean> districtList = mCityBean.getCityList();
                if (districtList != null && !districtList.isEmpty() && districtList.size() > 0) {
                    mDistrictBean = districtList.get(0);
                }
            }
        }
        
        //省份数据
        mProvinceBeenArray = new ProvinceBean[mProvinceBeanArrayList.size()];
        
        for (int p = 0; p < mProvinceBeanArrayList.size(); p++) {
            
            //遍历每个省份
            ProvinceBean itemProvince = mProvinceBeanArrayList.get(p);

            //每个省份对应下面的市
            ArrayList<CityBean> cityList = itemProvince.getCityList();
            
            //当前省份下面的所有城市
            CityBean[] cityNames = new CityBean[cityList.size()];
            
            //遍历当前省份下面城市的所有数据
            for (int j = 0; j < cityList.size(); j++) {
                cityNames[j] = cityList.get(j);
                
                //当前省份下面每个城市下面再次对应的区或者县
                List<DistrictBean> districtList = cityList.get(j).getCityList();
                if (districtList == null) {
                    break;
                }
                DistrictBean[] distrinctArray = new DistrictBean[districtList.size()];
                
                for (int k = 0; k < districtList.size(); k++) {
                    
                    // 遍历市下面所有区/县的数据
                    DistrictBean districtModel = districtList.get(k);
                    
                    //存放 省市区-区 数据
                    mDisMap.put(itemProvince.getName() + cityNames[j].getName() + districtList.get(k).getName(),
                            districtModel);
                    
                    distrinctArray[k] = districtModel;
                    
                }
                // 市-区/县的数据，保存到mDistrictDatasMap
                mCity_DisMap.put(itemProvince.getName() + cityNames[j].getName(), distrinctArray);
                
            }
            
            // 省-市的数据，保存到mCitisDatasMap
            mPro_CityMap.put(itemProvince.getName(), cityNames);
            
            mCityBeanArrayList.add(cityList);
            
            //只有显示三级联动，才会执行
            ObservableArrayList<ObservableArrayList<DistrictBean>> array2DistrictLists = new ObservableArrayList<>();
            
            for (int c = 0; c < cityList.size(); c++) {
                CityBean cityBean = cityList.get(c);
                array2DistrictLists.add(cityBean.getCityList());
            }
            mDistrictBeanArrayList.add(array2DistrictLists);
            
            //            }
            //赋值所有省份的名称
            mProvinceBeenArray[p] = itemProvince;
            
        }
        
    }
    
}
