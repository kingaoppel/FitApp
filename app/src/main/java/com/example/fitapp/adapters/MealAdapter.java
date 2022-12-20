package com.example.fitapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitapp.MyProduct;
import com.example.fitapp.R;
import com.example.fitapp.interfaces.OnMealAdapterItemClickInterface;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {

    private Context context;
    private List<MyProduct> items;
    private OnMealAdapterItemClickInterface onMealAdapterItemClickInterface;

    public MealAdapter(Context context, List<MyProduct> items, OnMealAdapterItemClickInterface onMealAdapterItemClickInterface) {
        this.context = context;
        this.items = items;
        this.onMealAdapterItemClickInterface = onMealAdapterItemClickInterface;
    }

    @NonNull
    @Override
    public MealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealAdapter.ViewHolder holder, int position) {
        String str = items.get(position).getName();
        holder.textView.setText(str);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        private ImageView plus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_searchItemName);
            plus = itemView.findViewById(R.id.but_addProducttoMeal);

            plus.setVisibility(View.GONE);

//            plus.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(view.getContext(), AddProducktActivity.class);
//                    view.getContext().startActivity(intent);
//                }
//            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMealAdapterItemClickInterface.onMealItemClick(items.get(getAdapterPosition()));
                }
            });
        }
    }
}
