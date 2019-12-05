package showmethe.github.kframework.picture.luban;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * showmethe.github.kframework.picture.luban
 * cuvsu
 * 2019/4/6
 **/
public class LubanZip  {

    private  static LubanZip instant = null;

    private  String CACHEDIR = "";

    private static final int  DEFAULTIGNOR = 50;

    private static final CompressionPredicate predicate = path -> !(path.isEmpty() || path.toLowerCase().endsWith(".gif"));


    private LubanZip(Context context){
        CACHEDIR = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    }

    public static LubanZip get(Context context){
        if(instant == null){
            instant = new LubanZip(context);
        }
        return instant;
    }

    public void CPR(Context context,String inputPath,onComPressCallBack comPressCallBack){

        Luban.with(context).load(inputPath).ignoreBy(DEFAULTIGNOR).setTargetDir(CACHEDIR)
                .filter(predicate).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {
                comPressCallBack.onStart();
            }

            @Override
            public void onSuccess(File file) {

                comPressCallBack.onSuccess(file);
            }

            @Override
            public void onError(Throwable e) {
                comPressCallBack.onError(e);
            }

            @Override
            public void onFinish() {

            }
        }).launch();
    }


    public void CPRS(Context context, List<String> inputPaths, onFilesComPressCallBack onFilesComPressCallBack){
        ArrayList<File> files = new ArrayList<>();
        Luban.with(context).load(inputPaths).ignoreBy(DEFAULTIGNOR).setTargetDir(CACHEDIR)
                .filter(predicate).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {
                onFilesComPressCallBack.onStart();
            }

            @Override
            public void onSuccess(File file) {
                files.add(file);
                onFilesComPressCallBack.onSuccess(file);
            }

            @Override
            public void onError(Throwable e) {

                onFilesComPressCallBack.onError(e);
            }

            @Override
            public void onFinish() {
                //onFilesComPressCallBack.onFinish(files);
            }
        }).launch();
    }


    public interface onComPressCallBack{

        void  onStart();

        void onSuccess(File file);

        void onError(Throwable e);

    }


    public interface onFilesComPressCallBack{

        void  onStart();

        void onSuccess(File file);

        void onError(Throwable e);

    }

}
