package com.example.annaqin.valjaskola;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>  {
    private List<Skola> skolaList;
    private OnItemClickListener itemListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        itemListener=listener;
    }


    public ListAdapter(List<Skola> skolaList) {
        this.skolaList = skolaList;


    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.listview,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        Skola skola=skolaList.get(position);
        holder.textViewName.setText(skola.getName());
        holder.textViewKummun.setText(skola.getKummun());
        holder.textViewGmeriv.setText(Double.toString(skola.getGmeriv()));

        if (skola.getType().equals("Kommunal")){
            holder.textViewType.setText(R.string.Kommunal);
        } else if (skola.getType().equals("Enskild")){
            holder.textViewType.setText(R.string.Enskild);
        } else {
            holder.textViewType.setText("...");
        }


    }

    @Override
    public int getItemCount() {
        return skolaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewKummun;
        private TextView textViewType;
        private TextView textViewGmeriv;

        public ViewHolder(final View itemView) {
            super(itemView);
            textViewName=(TextView)itemView.findViewById(R.id.textView_SkolaName);
            textViewKummun=(TextView)itemView.findViewById(R.id.textView_kom);
            textViewType=(TextView)itemView.findViewById(R.id.textView_type);
            textViewGmeriv=(TextView)itemView.findViewById(R.id.textView_gmeriv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemListener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            itemListener.onItemClick(position);
                        }
                    }
                }
            });

        }


    }
}