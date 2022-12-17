package com.example.fitapp.fragments;

import static java.lang.Double.valueOf;

import android.content.Context;
import android.content.Intent;
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
import com.example.fitapp.activity.AddProductToMeal;
import com.example.fitapp.adapters.SearchProductAdapter;
import com.example.fitapp.interfaces.SearchInterface;
import com.example.fitapp.remote.model.ResultsItem;
import com.example.fitapp.remote.model.Search;
import com.example.fitapp.remote.modelProduct.NutrientsItem;
import com.example.fitapp.remote.modelProduct.Product;
import com.example.fitapp.viewModels.MainViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductListFragment extends Fragment {

    private Context context;
    private MainViewModel mainViewModel;
    private SearchProductAdapter searchProductAdapter;
    RecyclerView searchProduct;
    private List<ResultsItem> items = new ArrayList<>();
    private List<NutrientsItem> itemsNutri = new ArrayList<>();

    FirebaseFirestore db;

    String s_name;
    Double s_cal;
    Double s_pro;
    Double s_fat;
    Double s_carbo;
    Double s_qua = 100.0;

    private NutrientsItem nutrientsItem;

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
                mainViewModel.fetchProductInfobyId(String.valueOf(item.getId()));
            }
        };

        mainViewModel.getProductLiveData().observe(getViewLifecycleOwner(), new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                if(product != null && product.getNutrition() != null){

                    whiteNewProduct();

                    Intent intent = new Intent(context, AddProductToMeal.class);
                    intent.putExtra("NAME", product.getName().toString());
                    startActivity(intent);

                    //Bundle i sprawdzenie nazwy tutaj a w activity
                    //mainViewModel.getProductLiveData().get....
                }
            }
        });

        searchProduct = view.findViewById(R.id.search_results);
        searchProductAdapter = new SearchProductAdapter(context, items, searchInterface);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        searchProduct.setLayoutManager(manager);
        searchProduct.setAdapter(searchProductAdapter);
    }

    private void whiteNewProduct() {

        db = FirebaseFirestore.getInstance();
        s_name = mainViewModel.getProductLiveData().getValue().getName();

        DocumentReference docRef = db.collection("products").document(s_name);

        itemsNutri = mainViewModel.getProductLiveData().getValue().getNutrition().getNutrients();
        nutrientsItem = itemsNutri.stream().filter(s -> s.getName().equals("Calories")).findFirst().orElse(null);
        if (nutrientsItem != null) {
            s_cal = nutrientsItem.getAmount();
        }
        nutrientsItem = itemsNutri.stream().filter(s -> s.getName().equals("Protein")).findFirst().orElse(null);
        if (nutrientsItem != null) {
            s_pro = nutrientsItem.getAmount();
        }
        nutrientsItem = itemsNutri.stream().filter(s -> s.getName().equals("Fat")).findFirst().orElse(null);
        if (nutrientsItem != null) {
            s_fat = nutrientsItem.getAmount();
        }
        nutrientsItem = itemsNutri.stream().filter(s -> s.getName().equals("Carbohydrates")).findFirst().orElse(null);
        if (nutrientsItem != null) {
            s_carbo = nutrientsItem.getAmount();
        }

        if (s_cal >= 0 && s_pro >= 0 && s_fat >= 0 && s_carbo >= 0 && s_name.length() > 2) {
            com.example.fitapp.Product product = new com.example.fitapp.Product(s_name, null, s_cal, s_pro, s_fat, s_carbo, s_qua);
            Map<String, Object> productValues = product.toMap();
            db.collection("products").document(s_name)
                    .set(productValues)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("AddData", "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("AddData", "Error writing document", e);
                        }
                    });
        }
    }
}
