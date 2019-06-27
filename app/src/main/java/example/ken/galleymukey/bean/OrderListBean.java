package example.ken.galleymukey.bean;

import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import example.ken.galleymukey.BR;

public class OrderListBean implements Observable {

    private int orderId = 0;
    private int orderGoodsId = 0;
    private  String coverImg = "";
    private  double totalPrice = 0.0;
    private   int totalCount = 0;
    private String goodsName = "";
    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

    @Bindable
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
        notifyChange(example.ken.galleymukey.BR.goodsName);
    }

    @Bindable
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
        notifyChange(example.ken.galleymukey.BR.orderId);
    }

    @Bindable
    public int getOrderGoodsId() {
        return orderGoodsId;
    }

    public void setOrderGoodsId(int orderGoodsId) {
        this.orderGoodsId = orderGoodsId;
        notifyChange(example.ken.galleymukey.BR.orderGoodsId);
    }

    @Bindable
    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
        notifyChange(example.ken.galleymukey.BR.coverImg);
    }

    @Bindable
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
        notifyChange(example.ken.galleymukey.BR.totalPrice);
    }

    @Bindable
    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        notifyChange(example.ken.galleymukey.BR.totalCount);
    }

    private synchronized void notifyChange(int propertyId) {
        if (propertyChangeRegistry == null) {
            propertyChangeRegistry = new PropertyChangeRegistry();
        }
        propertyChangeRegistry.notifyChange(this, propertyId);
    }

    @Override
    public synchronized void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        if (propertyChangeRegistry == null) {
            propertyChangeRegistry = new PropertyChangeRegistry();
        }
        propertyChangeRegistry.add(callback);

    }

    @Override
    public synchronized void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        if (propertyChangeRegistry != null) {
            propertyChangeRegistry.remove(callback);
        }
    }
}
