package com.example.fitapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitapp.R;
import com.example.fitapp.adapters.SearchProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment {

    private Context context;

    private SearchProductAdapter searchProductAdapter;
    RecyclerView searchProduct;

    private List<String> items = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        items.clear();
        items.add("Masło");
        items.add("Mleko");
        items.add("Chleb");
        items.add("Jajka");
        items.add("Sok");
        items.add("Jajka");
        items.add("Sok");
        items.add("Jajka");
        items.add("Sok");
        items.add("Jajka");
        items.add("Sok");

        searchProduct = view.findViewById(R.id.search_results);
        searchProductAdapter = new SearchProductAdapter(context, items);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        searchProduct.setLayoutManager(manager);
        searchProduct.setAdapter(searchProductAdapter);
    }
}
