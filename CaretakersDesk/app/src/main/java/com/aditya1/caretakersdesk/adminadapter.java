package com.aditya1.caretakersdesk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adminadapter  extends RecyclerView.Adapter<adminadapter.ComplaintViewHolder> {
    ArrayList<complaint> ComplaintList;
    Context context;


    public adminadapter(Context context, ArrayList<complaint> complaintList) {
        this.context = context;
        this.ComplaintList = complaintList;
    }

    @Override
    public ComplaintViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_admin, parent, false);

        return new ComplaintViewHolder(view);
    }

    @Override

    public void onBindViewHolder(ComplaintViewHolder holder, int position) {
        //setting data to each complaint view
        complaint complaint = ComplaintList.get(position);
        holder.tvtypeComplain.setText(complaint.getDepartment());
        holder.tvDate.setText(complaint.getTime());
        holder.tvstatus.setText(complaint.getStatus());
        holder.tvdescription.setText(complaint.getDescription_problem());


    }

    @Override
    public int getItemCount() {
        return ComplaintList.size();
    }

    public class ComplaintViewHolder extends RecyclerView.ViewHolder {
        TextView tvtypeComplain, tvdescription, tvstatus, tvDate;


        public ComplaintViewHolder(View itemView) {
            super(itemView);
            tvtypeComplain = itemView.findViewById(R.id.tvd);
            tvdescription= itemView.findViewById(R.id.tvdes);
            tvstatus = itemView.findViewById(R.id.tvstat);
            tvDate=itemView.findViewById(R.id.tvts);

        }

    }
}
