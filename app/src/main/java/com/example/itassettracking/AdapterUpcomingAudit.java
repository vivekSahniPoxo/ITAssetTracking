package com.example.itassettracking;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterUpcomingAudit extends RecyclerView.Adapter<AdapterUpcomingAudit.MyviewHolder> {
    List<AuditModel> list;
    Context c;

    public AdapterUpcomingAudit(List<AuditModel> list, Context c) {
        this.list = list;
        this.c = c;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.list, parent, false);
        return new MyviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        AuditModel model = list.get(position);
////        holder.Subject.setText(model.getAuditId());
////        holder.author.setText(model.getAuditDate());
////        holder.edition.setText(model.getAuditor());
        String currentString = model.getAuditDate();
        String[] separated = currentString.split("T");
        // this will contain "Fruit"


        String date1 = separated[0];


        holder.language.setText(model.getRemarks());
        holder.publisher.setText(date1);
        holder.head_title.setText(model.getAuditor());
        holder.head_subject.setText(model.getAuditId());
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        holder.head_subject.setTextColor(Color.parseColor("#000000"));
        holder.head_title.setTextColor(Color.parseColor("#000000"));
        holder.publisher.setTextColor(Color.parseColor("#000000"));
        holder.language.setTextColor(Color.parseColor("#000000"));
        holder.ListLayout.setBackgroundColor(Color.parseColor("#C6CFEF"));
        holder.h4.setTextColor(Color.parseColor("#000000"));
        holder.h3.setTextColor(Color.parseColor("#000000"));
        holder.h2.setTextColor(Color.parseColor("#000000"));
        holder.h1.setTextColor(Color.parseColor("#000000"));
        holder.h1.setText("Audit ID :");
        holder.h2.setText("Auditor :");
        holder.h3.setText("Audit Date :");
        holder.h4.setText("Remark :");




        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (currentDate.matches(date1)) {
                    Intent i = new Intent(c, AuditDetrails.class);
                    i.putExtra("keyId", model.getAuditId());
                    c.startActivity(i);
//                } else {
//                    Toast.makeText(c, "You can't Audit Today...", Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView Subject, Title, publisher, author, edition, language, access_No, head_subject, head_title, expand, minimize;
        LinearLayout list_layout;
        CardView cardView, card_details;
        ConstraintLayout ListLayout;
        TextView h1,h2,h3,h4;


        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            ListLayout = itemView.findViewById(R.id.LayoutList);
            Subject = itemView.findViewById(R.id.Subject);
            Title = itemView.findViewById(R.id.Booktitle);
//            expand = itemView.findViewById(R.id.expand);
//            minimize = itemView.findViewById(R.id.minimize);
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
