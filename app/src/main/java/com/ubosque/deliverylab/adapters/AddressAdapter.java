package com.ubosque.deliverylab.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ubosque.deliverylab.R;
import com.ubosque.deliverylab.database.UserAddress;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private List<UserAddress> localDataSet;

    private Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtAddressItem;

        public ViewHolder(View view, AddressAdapter addressAdapter) {
            super(view);
            // Define click listener for the ViewHolder's View
            ConstraintLayout constraintLayout = (ConstraintLayout) view.findViewById(R.id.address_item_layout);
            txtAddressItem = (TextView) view.findViewById(R.id.txtUserAddressItem);

            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                    int position = viewHolder.getPosition();
                    UserAddress userAddress = addressAdapter.localDataSet.get(position);
                    Log.i("DeliveryLab", "Selected address is " + userAddress.getAddress());
                    //view.getContext().startActivity(new Intent(view.getContext().getApplicationContext(), StoreInfoActivity.class));
                }
            });
        }

        public TextView getTextViewAddressItem() {
            return txtAddressItem;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public AddressAdapter(Context context, List<UserAddress> dataSet) {
        this.context = context;
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.address_item_row, viewGroup, false);

        return new ViewHolder(view, this);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.i("DeliveryLab", "On bind view holder called " + localDataSet.get(position).getAddress());
        viewHolder.getTextViewAddressItem().setText(localDataSet.get(position).getAddress());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

