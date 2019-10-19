package vavkhan.com.sanagostar.views.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import vavkhan.com.sanagostar.models.AddressListModel;
import vavkhan.com.sanagostar.databinding.ItemAddresListBinding;
import vavkhan.com.sanagostar.views.BaseAdapter;

public class AddressListAdapter extends BaseAdapter<AddressListAdapter.AddressListViewHolder, AddressListModel> {

    List<AddressListModel> leaveModels;
    private final ListClick<AddressListModel> leaveListCallBack;

    public AddressListAdapter(ListClick<AddressListModel> callBack){
        leaveModels = new ArrayList<>();
        this.leaveListCallBack = callBack;
    }
    @Override
    public void setData(List<AddressListModel> drawerUserModels) {
        this.leaveModels = drawerUserModels;
        notifyDataSetChanged();
    }

    public void addData(List<AddressListModel> drawerUserModels){
        this.leaveModels.addAll(drawerUserModels);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AddressListAdapter.AddressListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AddressListAdapter.AddressListViewHolder.create(LayoutInflater.from(parent.getContext()), parent, leaveListCallBack);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressListAdapter.AddressListViewHolder holder, int position) {
        holder.onBind(leaveModels.get(position));

    }

    @Override
    public int getItemCount() {
        if (leaveModels != null)
            return leaveModels.size();
        return 0;
    }

    static class AddressListViewHolder extends RecyclerView.ViewHolder {

        public static AddressListAdapter.AddressListViewHolder create(LayoutInflater inflater, ViewGroup parent, ListClick<AddressListModel> callback) {
            ItemAddresListBinding itemMovieListBinding = ItemAddresListBinding.inflate(inflater, parent, false);
            return new AddressListAdapter.AddressListViewHolder(itemMovieListBinding, callback);
        }

        ItemAddresListBinding binding;

        public AddressListViewHolder(ItemAddresListBinding binding, ListClick<AddressListModel> callback) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v ->
                    callback.onListClicked(binding.getAddress(), binding.nameTxt));
        }

        public void onBind(AddressListModel addressListModel) {
            binding.setAddress(addressListModel);
            binding.executePendingBindings();
        }
    }
}
