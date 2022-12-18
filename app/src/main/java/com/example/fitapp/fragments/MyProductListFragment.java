package com.example.fitapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.fitapp.adapters.MyProductAdapter;
import com.example.fitapp.interfaces.MyProductInterface;
import com.example.fitapp.interfaces.SearchInterface;
import com.example.fitapp.remote.model.ResultsItem;
import com.example.fitapp.remote.modelProduct.Product;
import com.example.fitapp.viewModels.MainViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyProductListFragment extends Fragment {

    private Context context;

    private MainViewModel mainViewModel;

    private MyProductInterface myProductInterface;

    private MyProductAdapter myProductAdapter;
    RecyclerView searchProduct;

    private List<ResultsItem> items = new ArrayList<>();

    Map<String, Object> dataRef = new HashMap<>();
    List<String> lista = new ArrayList<>();

    FirebaseFirestore db;
    FirebaseUser currentUser;
    String uid;

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

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            uid = currentUser.getUid();
            Log.d("User", uid);
        }

        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        dataRef = document.getData();
                        Log.d("User", "DocumentSnapshot data: " + dataRef.get("my_products"));
                        lista = (List<String>) dataRef.get("my_products");
                    } else {
                        Log.d("User", "No such document");
                    }
                } else {
                    Log.d("User", "get failed with ", task.getException());
                }
            }
        });


        mainViewModel.getProductLiveData().observe(getViewLifecycleOwner(), new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                if(product != null && product.getNutrition() != null){
                    Intent intent = new Intent(context, AddProductToMeal.class);
                    startActivity(intent);
                    //Bundle i sprawdzenie nazwy tutaj a w activity
                    //mainViewModel.getProductLiveData().get....
                }
            }
        });

        myProductInterface = new MyProductInterface() {
            @Override
            public void onClick(String name) {
                mainViewModel.setNameProduct(uid+name);
                Intent intent = new Intent(context, AddProductToMeal.class);
                startActivity(intent);
            }
        };

        searchProduct = view.findViewById(R.id.search_results);
        myProductAdapter = new MyProductAdapter(context, lista,myProductInterface);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        searchProduct.setLayoutManager(manager);
        searchProduct.setAdapter(myProductAdapter);
    }
}
