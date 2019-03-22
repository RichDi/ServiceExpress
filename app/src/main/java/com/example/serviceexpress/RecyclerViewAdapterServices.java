package com.example.serviceexpress;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class RecyclerViewAdapterServices extends RecyclerView.Adapter<RecyclerViewAdapterServices.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<Servicios> lista_servicios;

    public RecyclerViewAdapterServices(ArrayList<Servicios> lista_servicios) {
        this.lista_servicios = lista_servicios;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.service_item,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        viewHolder.service_name.setText(lista_servicios.get(i).nombre);
        viewHolder.service_category.setText(lista_servicios.get(i).categoria);
        viewHolder.service_open_hours.setText(lista_servicios.get(i).hora_apertura + " - " + lista_servicios.get(i).hora_clausura);
        viewHolder.service_location.setText(lista_servicios.get(i).getUbicacion().getAddress());
        viewHolder.service_no_services.setText(String.valueOf(lista_servicios.get(i).no_servicios));

        switch (lista_servicios.get(i).getCategoria()){
            case "Plomeria":
                viewHolder.parent_card.setBackgroundResource(R.drawable.bg_fontaneria);
                break;
            case "Electricista":
                viewHolder.parent_card.setBackgroundResource(R.drawable.bg_electricista);
                break;
            case "Cerrajeria":
                viewHolder.parent_card.setBackgroundResource(R.drawable.bg_cerrajeria);
                break;
            case "Carpinteria":
                viewHolder.parent_card.setBackgroundResource(R.drawable.bg_carpinteria);
                break;
            case "Jardineria":
                viewHolder.parent_card.setBackgroundResource(R.drawable.bg_jardineria);
                break;
        }

        viewHolder.parent_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.context, service_detail.class);
                intent.putExtra("Service", (Serializable) lista_servicios.get(i));
                viewHolder.context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return lista_servicios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final Context context;

        TextView service_name;
        TextView service_category;
        TextView service_open_hours;
        TextView service_location;
        TextView service_no_services;
        LinearLayout parent_card;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            parent_card = itemView.findViewById(R.id.parent_card);
            service_name = itemView.findViewById(R.id.textView8);
            service_category= itemView.findViewById(R.id.textView9);
            service_open_hours = itemView.findViewById(R.id.textView10);
            service_location = itemView.findViewById(R.id.textView11);
            service_no_services = itemView.findViewById(R.id.textView12);

        }
    }

}
