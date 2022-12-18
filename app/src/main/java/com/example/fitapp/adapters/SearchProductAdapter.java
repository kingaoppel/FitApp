package com.example.fitapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitapp.R;
import com.example.fitapp.activity.AddProducktActivity;
import com.example.fitapp.activity.AddProductToMeal;
import com.example.fitapp.interfaces.SearchInterface;
import com.example.fitapp.remote.model.ResultsItem;

import java.util.List;

public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductAdapter.ViewHolder> {

    private Context context;
    private List<ResultsItem> items;
    private SearchInterface searchInterface;

    public SearchProductAdapter(Context context, List<ResultsItem> items, SearchInterface searchInterface) {
        this.context = context;
        this.items = items;
        this.searchInterface = searchInterface;
    }

    @NonNull
    @Override
    public SearchProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultsItem str = items.get(position);
        holder.textView.setText(str.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        private LinearLayout linearLayout;
        private ImageView plus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_searchItemName);
            linearLayout = itemView.findViewById(R.id.additionalInfoLinLayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    searchInterface.onClick(items.get(getAdapterPosition()));
                }
            });
        }
    }
}
