package com.example.dclassics_books;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StoreCarouselAdapter extends RecyclerView.Adapter<StoreCarouselAdapter.CarouselViewHolder> {

    private List<Integer> imageList;

    public StoreCarouselAdapter(List<Integer> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stores_carousel, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        int realPosition = position % imageList.size();
        holder.imageView.setImageResource(imageList.get(realPosition));
    }

    @Override
    public int getItemCount() {
        if (imageList == null || imageList.isEmpty()) {
            return 0;
        }

        return Integer.MAX_VALUE;
    }

    public static class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.store_image);
        }
    }
}