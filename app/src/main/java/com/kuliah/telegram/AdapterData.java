package com.kuliah.telegram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {
    private List<DataModel> listdata;
    private LayoutInflater inflater;
    public AdapterData(Context context, List<DataModel> listdata) {
        this.listdata = listdata;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_data, parent, false);
        return new HolderData(view);
    }
    public static class DataModel {
        private String title;
        private String lastmessage;
        private int imageresource;
        public DataModel(String title, String lastmessage, int imageresource) {
            this.title = title;
            this.lastmessage = lastmessage;
            this.imageresource = imageresource;
        }
        public String getLastMessage() {
            return lastmessage;
        }
        public String getTitle() {
            return title;
        }
        public int getImageResource() {
            return imageresource;
        }
    }
    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModel data = listdata.get(position);
        holder.txtData.setText(data.getTitle());
        holder.txtDeskripsi.setText(data.getLastMessage());
        holder.imageView.setImageResource(data.getImageResource());
    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }
    public class HolderData extends RecyclerView.ViewHolder{
        TextView txtData;
        TextView txtDeskripsi;
        ImageView imageView;
        public HolderData(@NonNull View itemView) {
            super(itemView);

            txtData = itemView.findViewById(R.id.namakontak);
            txtDeskripsi = itemView.findViewById(R.id.deskripsi);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}