package showmethe.github.kframework.widget.citypicker.Interface;


import org.jetbrains.annotations.NotNull;

import showmethe.github.kframework.widget.citypicker.bean.CityBean;
import showmethe.github.kframework.widget.citypicker.bean.DistrictBean;
import showmethe.github.kframework.widget.citypicker.bean.ProvinceBean;

public abstract class OnCityItemClickListener {

    /**
     * 当选择省市区三级选择器时，需要覆盖此方法
     * @param province
     * @param city
     * @param district
     */
    public void onSelected(@NotNull  ProvinceBean province,@NotNull  CityBean city, @NotNull DistrictBean district) {
        
    }
    
    /**
     * 取消
     */
    public void onCancel() {
        
    }
}
