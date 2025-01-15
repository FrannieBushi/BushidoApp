package com.example.bushidoapp;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback{

    private HabitoAdapter habitoAdapter;

    public SwipeToDeleteCallback(HabitoAdapter habitoAdapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.habitoAdapter = habitoAdapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        if(direction == ItemTouchHelper.LEFT) {
            int position = viewHolder.getAdapterPosition();
            habitoAdapter.deleteItem(position);
        }

        else if(direction == ItemTouchHelper.RIGHT) {
            int position = viewHolder.getAdapterPosition();
            edit_habit(viewHolder.itemView.getContext(), position);
        }

    }

    private void edit_habit(Context context, int position) {

        Intent i = new Intent(context, EditarHabitoActivity.class);
        i.putExtra("index", position);
        context.startActivity(Intent.createChooser(i, "Compartir en")
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    
}
