package showmethe.github.kframework.picture;

import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import showmethe.github.kframework.BR;

/**
 * showmethe.github.kframework.picture
 * cuvsu
 * 2019/4/5
 **/
public class LocalMedia implements Observable {

    private String path;

    private boolean check = false;
    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

    public LocalMedia(String path) {
        this.path = path;
    }

    @Bindable
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        notifyChange(BR.path);
    }

    @Bindable
    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean isCheck) {
        this.check = isCheck;
        notifyChange(BR.check);
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
