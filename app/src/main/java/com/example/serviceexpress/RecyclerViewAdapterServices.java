package com.example.serviceexpress;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.service_name.setText(lista_servicios.get(i).nombre);
        viewHolder.service_category.setText(lista_servicios.get(i).categoria);
        viewHolder.service_open_hours.setText(lista_servicios.get(i).hora_apertura + "-" + lista_servicios.get(i).hora_clausura);
        viewHolder.service_location.setText(lista_servicios.get(i).ubicacion.toString());
        viewHolder.service_no_services.setText(lista_servicios.get(i).no_servicios);
    }

    @Override
    public int getItemCount() {
        return lista_servicios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout parentLayout;
        ImageView image;
        TextView service_name;
        TextView service_category;
        TextView service_open_hours;
        TextView service_location;
        TextView service_no_services;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.service_layout);

            image = itemView.findViewById(R.id.imageView3);
            service_name = itemView.findViewById(R.id.textView8);
            service_category= itemView.findViewById(R.id.textView9);
            service_open_hours = itemView.findViewById(R.id.textView10);
            service_location = itemView.findViewById(R.id.textView11);
            service_no_services = itemView.findViewById(R.id.textView12);

        }
    }

}
