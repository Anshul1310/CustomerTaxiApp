package com.ebooker.taxiappcustomer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ebooker.taxiappcustomer.R;
import com.ebooker.taxiappcustomer.ReceiptFragment;
import com.ebooker.taxiappcustomer.modals.ride_history_modal;

import java.util.ArrayList;

public class ride_history_adapter extends RecyclerView.Adapter<ride_history_adapter.viewholder> {

    private Context context;
    private ArrayList<ride_history_modal> arrayList;

    public ride_history_adapter(Context context, ArrayList<ride_history_modal> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewholder(LayoutInflater.from(context).inflate(R.layout.layout_your_trips, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, new ReceiptFragment())
                        .commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        private TextView addressFrom, adressTo, status, date;
        private ConstraintLayout constraintLayout;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.your_trips_constraint);
        }
    }
}
