package showmethe.github.kframework.widget.citypicker.picker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.jetbrains.annotations.NotNull;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;
import showmethe.github.kframework.R;
import showmethe.github.kframework.adapter.BaseRecyclerViewAdapter;
import showmethe.github.kframework.base.BaseActivity;
import showmethe.github.kframework.widget.citypicker.bean.ProvinceBean;



import static showmethe.github.kframework.widget.citypicker.picker.Const.INDEX_INVALID;


public class ProvinceAdapter extends BaseRecyclerViewAdapter<ProvinceBean, ProvinceAdapter.ViewHolder> {

    private int provinceIndex = INDEX_INVALID;

    public ProvinceAdapter(@NotNull BaseActivity<?, ?> context, @NotNull ObservableArrayList<ProvinceBean> mData) {
        super(context, mData);
    }


    public void updateSelectedPosition(int index) {
        int last = getSelectedPosition();
        this.provinceIndex = index;
        notifyItemChanged(index);
        notifyItemChanged(last);
    }

    public int getSelectedPosition() {
        return this.provinceIndex;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(inflateItemView(parent, R.layout.pop_citypicker_item));
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(getMData().get(position).getId());
    }


    @Override
    protected void bindDataToItemView(@NotNull ViewHolder holder, ProvinceBean item, int position) {
        holder.name.setText(item.getName());
        boolean checked = provinceIndex != INDEX_INVALID && getMData().get(provinceIndex).getId().equals(item.getId());
        holder.name.setEnabled(checked);
        holder.selectImg.setVisibility(checked ? View.VISIBLE : View.GONE);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView selectImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            selectImg = itemView.findViewById(R.id.selectImg);
        }
    }
}
