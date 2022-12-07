package com.example.fitapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import com.example.fitapp.interfaces.SearchInterface;
import com.example.fitapp.remote.model.ResultsItem;
import com.example.fitapp.remote.model.Search;
import com.example.fitapp.viewModels.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductListFragment extends Fragment {

    private Context context;

    private MainViewModel mainViewModel;

    private SearchProductAdapter searchProductAdapter;
    RecyclerView searchProduct;

    private List<ResultsItem> items = new ArrayList<>();

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

        mainViewModel.getAutoCompleteMealsData().observe(getViewLifecycleOwner(), new Observer<Search>() {
            @Override
            public void onChanged(Search search) {
                if(search!= null && search.getResults()!=null && search.getResults().size()> 0){
                    items.clear();
                    items.addAll(search.getResults());
                    searchProductAdapter.notifyDataSetChanged();
                }
            }
        });

        SearchInterface searchInterface = new SearchInterface() {
            @Override
            public void onClick(ResultsItem item) {
                Log.d("RV",item.getId() + " " );
            }
        };

        searchProduct = view.findViewById(R.id.search_results);
        searchProductAdapter = new SearchProductAdapter(context, items, searchInterface);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        searchProduct.setLayoutManager(manager);
        searchProduct.setAdapter(searchProductAdapter);
    }
}
