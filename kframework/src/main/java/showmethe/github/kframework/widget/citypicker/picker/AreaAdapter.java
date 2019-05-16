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
import showmethe.github.kframework.widget.citypicker.bean.DistrictBean;


import static showmethe.github.kframework.widget.citypicker.picker.Const.INDEX_INVALID;


public class AreaAdapter extends BaseRecyclerViewAdapter<DistrictBean, AreaAdapter.ViewHolder> {



    private int districtIndex = INDEX_INVALID;

    public AreaAdapter(@NotNull BaseActivity<?, ?> context, @NotNull ObservableArrayList<DistrictBean> mData) {
        super(context, mData);
    }


    public int getSelectedPosition() {
        return this.districtIndex;
    }

    public void updateSelectedPosition(int index) {
        int last = getSelectedPosition();
        this.districtIndex = index;
        notifyItemChanged(index);
        notifyItemChanged(last);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflateItemView(parent, R.layout.pop_citypicker_item));
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(getMData().get(position).getId());
    }



    @Override
    protected void bindDataToItemView(@NotNull ViewHolder holder, DistrictBean item, int position) {
        holder.name.setText(item.getName());
        boolean checked = districtIndex != INDEX_INVALID && getMData().get(districtIndex).getId().equals(item.getId());
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
