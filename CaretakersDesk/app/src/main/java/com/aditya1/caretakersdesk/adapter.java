package com.aditya1.caretakersdesk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class adapter  extends RecyclerView.Adapter<adapter.ComplaintViewHolder> {
    ArrayList<complaint> ComplaintList;
    Context context;


    public adapter(Context context, ArrayList<complaint> complaintList) {
        this.context = context;
        this.ComplaintList = complaintList;
    }

    @Override
    public ComplaintViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);

        return new ComplaintViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override

    public void onBindViewHolder(ComplaintViewHolder holder, int position) {
        //setting data to each complaint view
        complaint complaint = ComplaintList.get(position);

/**
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(v.getContext(), recycler_expand.class);
               v.getContext().startActivity(intent);
           }
       });
**/
        holder.tvtypeComplain.setText(complaint.getDepartment());
        holder.tvDate.setText(complaint.getTime());
        holder.tvstatus.setText(complaint.getStatus());
        if(Objects.equals(complaint.getStatus(), "Resolved"))
            holder.tvstatus.setTextColor(Color.parseColor("#008000"));

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
