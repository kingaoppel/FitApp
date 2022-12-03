package com.example.fitapp.adapters;
import android.content.Context;
import android.content.Intent;
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

import java.util.List;

public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductAdapter.ViewHolder> {

    private Context context;
    private List<String> items;

    public SearchProductAdapter(Context context, List<String> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public SearchProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_item,parent,false);
        return new ViewHolder(view);
    }
    

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String str = items.get(position);
        holder.textView.setText(str);
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
            plus = itemView.findViewById(R.id.but_addProducttoMeal);

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), AddProductToMeal.class);
                    view.getContext().startActivity(intent);
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(context,textView.getText(), Toast.LENGTH_SHORT).show();
//                    searchProductAdapterInterface.getInfo(items.get(getAdapterPosition()));
                    if(linearLayout.isShown()){
                        linearLayout.setVisibility(View.GONE);
                    }
                    else{
                        linearLayout.setVisibility(View.VISIBLE);
                    }

                }
            });
        }
    }
}
