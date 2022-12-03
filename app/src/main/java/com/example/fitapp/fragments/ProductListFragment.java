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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitapp.R;
import com.example.fitapp.adapters.SearchProductAdapter;
import com.example.fitapp.viewModels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment {

    private Context context;

    private MainViewModel mainViewModel;

    private SearchProductAdapter searchProductAdapter;
    RecyclerView searchProduct;

    private List<String> items = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

        this.mainViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel.getAutoCompleteMealsData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                if(strings!= null && strings.size() > 0){
                    items.clear();
                    items.addAll(strings);
                    searchProductAdapter.notifyDataSetChanged();
                }
            }
        });

        searchProduct = view.findViewById(R.id.search_results);
        searchProductAdapter = new SearchProductAdapter(context, items);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        searchProduct.setLayoutManager(manager);
        searchProduct.setAdapter(searchProductAdapter);
    }
}
