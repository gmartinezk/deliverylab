package com.ubosque.deliverylab.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ubosque.deliverylab.R;
import com.ubosque.deliverylab.model.Store;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    private List<Store> localDataSet;

    private Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtStoreName;
        private final TextView txtStoreDescription;
        private final TextView txtCategory;
        private final ImageView storeImageView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            ConstraintLayout constraintLayout = (ConstraintLayout) view.findViewById(R.id.store_item_layout);
            txtStoreName = (TextView) view.findViewById(R.id.txtStoreName);
            txtStoreDescription = (TextView) view.findViewById(R.id.txtStoreDescription);
            txtCategory = (TextView) view.findViewById(R.id.txtCategory);
            storeImageView = (ImageView) view.findViewById(R.id.imageViewStore);

            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                    //int position = viewHolder.getAbsoluteAdapterPosition();
                    //Log.i("DeliveryLab", "absolute position" + position);
                    //view.getContext().startActivity(new Intent(view.getContext().getApplicationContext(), StoreInfoActivity.class));
                }
            });
        }

        public TextView getTextViewStoreName() {
            return txtStoreName;
        }

        public TextView getTextViewStoreDescription() {
            return txtStoreDescription;
        }

        public TextView getTextViewCategory() {
            return txtCategory;
        }

        public ImageView getStoreImageView() { return storeImageView; }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public StoreAdapter(Context context, List<Store> dataSet) {
        this.context = context;
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.store_item_row, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextViewStoreName().setText(localDataSet.get(position).getName());
        viewHolder.getTextViewStoreDescription().setText(localDataSet.get(position).getDescription());
        viewHolder.getTextViewCategory().setText(localDataSet.get(position).getCategory());
        ImageView storeImageView = viewHolder.getStoreImageView();

        String url = localDataSet.get(position).getLogoUrl();

        Glide.with(context)
                .load(url)
                .centerCrop()
                .skipMemoryCache(true)
                .into(storeImageView);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

