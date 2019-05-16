package showmethe.github.kframework.widget.citypicker.picker;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



public class utils {
    
    String cityJsonStr = "";


    public static void setBackgroundAlpha(Context mContext, float bgAlpha) {


        if (bgAlpha == 1f) {
            clearDim((Activity) mContext);
        }else{
            applyDim((Activity) mContext, bgAlpha);
        }
    }

    private static void applyDim(Activity activity, float bgAlpha) {
        ViewGroup parent = (ViewGroup) activity.getWindow().getDecorView().getRootView();

        Drawable dim = new ColorDrawable(Color.BLACK);
        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        dim.setAlpha((int) (255 * bgAlpha));
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.add(dim);
    }

    private static void clearDim(Activity activity) {

        ViewGroup parent = (ViewGroup) activity.getWindow().getDecorView().getRootView();

        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.clear();
    }
}
