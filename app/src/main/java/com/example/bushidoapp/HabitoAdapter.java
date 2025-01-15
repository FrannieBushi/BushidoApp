package com.example.bushidoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bushidoapp.room.HabitoDao;

import java.util.ArrayList;
import java.util.List;

public class HabitoAdapter extends RecyclerView.Adapter<HabitoAdapter.HabitoViewHolder> {

    private List<Habito> habitoList;
    private HabitoDao habitoDao;
    Context context;

    public HabitoAdapter(List<Habito> habitoList, HabitoDao habitoDao, Context context) {
        this.habitoList = habitoList;
        this.habitoDao = habitoDao;
        this.context = context;
    }

    @NonNull
    @Override
    public HabitoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habito_item, parent, false);
        return new HabitoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitoViewHolder holder, int position) {
        Habito habito = habitoList.get(position);
        holder.txtName.setText(habito.getNombre());
        holder.txtType.setText(habito.getTipo());
        holder.txtDuracion.setText("Duración: " + habito.getDuracion());
    }

    @Override
    public int getItemCount() {
        return habitoList.size();
    }

    // Método para actualizar la lista de hábitos
    public void updateHabitoList(List<Habito> newHabitos) {
        this.habitoList = newHabitos;
        notifyDataSetChanged();  // Notificar al adaptador que la lista ha cambiado
    }

    // ViewHolder para el adapter
    public static class HabitoViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtType, txtDuracion;

        public HabitoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtType = itemView.findViewById(R.id.txtType);
            txtDuracion = itemView.findViewById(R.id.txtDuracion);
        }
    }

    public void deleteItem(int position) {
        Habito habito = habitoList.get(position);
        habitoList.remove(position); // Eliminar el ítem de la lista
        notifyItemRemoved(position); // Notificar al adaptador que un ítem ha sido eliminado
        Toast.makeText(context, "Habito eliminado", Toast.LENGTH_SHORT).show();

        // Eliminar el ítem de la base de datos
        new Thread(new Runnable() {
            @Override
            public void run() {
                habitoDao.delete(habito);
            }
        }).start();
    }


}
