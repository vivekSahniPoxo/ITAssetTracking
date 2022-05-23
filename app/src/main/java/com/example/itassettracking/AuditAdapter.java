package com.example.itassettracking;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AuditAdapter extends RecyclerView.Adapter<AuditAdapter.viewholder>{
    List<AuditModel> list ;
     Context context;

    public AuditAdapter(List<AuditModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list, parent, false);
        return  new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        AuditModel model = list.get(position);

        holder.language.setText(model.getRemarks());

        holder.publisher.setText(model.getAuditDate());
        holder.head_title.setText(model.getAuditor());
        holder.head_subject.setText(model.getAuditId());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,AuditDetrails.class);
                i.putExtra("keyId",model.getAuditId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class viewholder extends RecyclerView.ViewHolder {
        TextView Subject, Title, publisher, author, edition, language, access_No, head_subject, head_title, expand, minimize;
        LinearLayout list_layout;
        CardView cardView, card_details;
        ConstraintLayout ListLayout;
TextView h1,h2,h3,h4;
         public viewholder(@NonNull View itemView) {
             super(itemView);


             ListLayout = itemView.findViewById(R.id.LayoutList);
             Subject = itemView.findViewById(R.id.Subject);
             Title = itemView.findViewById(R.id.Booktitle);
//             expand = itemView.findViewById(R.id.expand);
//             minimize = itemView.findViewById(R.id.minimize);
             list_layout = itemView.findViewById(R.id.list_layout);
             cardView = itemView.findViewById(R.id.cardView);
             card_details = itemView.findViewById(R.id.cardView_Details);
             publisher = itemView.findViewById(R.id.Publisher);
             author = itemView.findViewById(R.id.Authorname);
             edition = itemView.findViewById(R.id.Edition);
             language = itemView.findViewById(R.id.Language);
             access_No = itemView.findViewById(R.id.Access_No);
             head_subject = itemView.findViewById(R.id.Head_subject);
             head_title = itemView.findViewById(R.id.Head_Tilte);
             h1= itemView.findViewById(R.id.textView6);
             h2= itemView.findViewById(R.id.textView7);
             h3= itemView.findViewById(R.id.textView8);
             h4= itemView.findViewById(R.id.textView9);


         }
     }
}
