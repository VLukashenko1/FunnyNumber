package com.vitaliy.funnynumber.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.vitaliy.funnynumber.App;
import com.vitaliy.funnynumber.FactDetails;
import com.vitaliy.funnynumber.R;
import com.vitaliy.funnynumber.Room.History;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private List<History> historyList;
    private final LifecycleOwner lifecycleOwner;

    public HistoryAdapter(Context context, List<History> historyList, LifecycleOwner lifecycleOwner){
        this.inflater = LayoutInflater.from(context);
        this.historyList = historyList;
        this.lifecycleOwner = lifecycleOwner;

        listUpdater();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameView.setText(historyList.get(position).fact);
        holder.number.setText(historyList.get(position).number);

        holder.itemView.setOnClickListener(view -> onElementClick(historyList.get(position)) );

    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView, number;
        ViewHolder(View view){
            super(view);

            nameView = view.findViewById(R.id.fact);
            number = view.findViewById(R.id.number);
        }
    }

    public static final String EXTRA_HISTORY = "FACT";
    void onElementClick(History history){
        System.out.println(history.toString());
        Intent intent = new Intent(inflater.getContext(), FactDetails.class);
        intent.putExtra(EXTRA_HISTORY, history);
        inflater.getContext().startActivity(intent);
        //todo Result activity);
    }

    private void listUpdater(){
        App.getInstance().getHistoryDAO().getAllLiveData().observe(lifecycleOwner, list -> {
            if (list == null) return;

            historyList.clear();
            historyList = list;
            notifyDataSetChanged();

        });
    }
}
