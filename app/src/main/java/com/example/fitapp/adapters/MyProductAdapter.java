package com.example.fitapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitapp.R;
import com.example.fitapp.interfaces.MyProductInterface;
import com.example.fitapp.remote.model.ResultsItem;

import java.text.BreakIterator;
import java.util.List;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.ViewHolder> {
    private Context context;
    private List<String> items;
    private MyProductInterface myProductInterface;

    public MyProductAdapter(Context context, List<String> items, MyProductInterface myProductInterface) {
        this.context = context;
        this.items = items;
        this.myProductInterface = myProductInterface;
    }

    public MyProductAdapter() {
    }

    @NonNull
    @Override
    public MyProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyProductAdapter.ViewHolder holder, int position) {
        String str = items.get(position);
        holder.textView.setText(str);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_searchItemName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myProductInterface.onClick(items.get(getAdapterPosition()));
                }
            });
        }
    }
}
