package com.example.heroesofdota2.adapter;

import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heroesofdota2.Hero;
import com.example.heroesofdota2.MainActivity;
import com.example.heroesofdota2.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    List<String> heroes;
    List<Integer> heroes_id;

    public ListAdapter(List<Integer> heroes_id, List<String> heroes)
    {
        this.heroes = heroes;
        this.heroes_id = heroes_id;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        //holder.imageView.setImagegetResources().getDrawable(R.drawable.overviewicon_speed));
        holder.textView.setText(heroes.get(position));
    }

    @Override
    public int getItemCount() {
        return heroes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull final View view) {
            super(view);
            imageView = view.findViewById(R.id.item_pic);
            textView = view.findViewById(R.id.hero_name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), Hero.class);
                    intent.putExtra("id", heroes_id.get(getPosition()));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
