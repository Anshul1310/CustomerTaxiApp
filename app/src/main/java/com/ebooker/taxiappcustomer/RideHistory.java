package com.ebooker.taxiappcustomer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebooker.taxiappcustomer.adapters.ride_history_adapter;
import com.ebooker.taxiappcustomer.modals.ride_history_modal;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class RideHistory extends Fragment {

   private View view;
   private RecyclerView recyclerView;
   private ride_history_adapter adapter;
   private ArrayList<ride_history_modal> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_ride_history, container, false);
        recyclerView=view.findViewById(R.id.ride_history_recycler);
        arrayList=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new ride_history_adapter(getContext(),arrayList);
        arrayList.add(new ride_history_modal("","","",""));
        arrayList.add(new ride_history_modal("","","",""));
        arrayList.add(new ride_history_modal("","","",""));
        arrayList.add(new ride_history_modal("","","",""));
        arrayList.add(new ride_history_modal("","","",""));
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        return view;
    }
}