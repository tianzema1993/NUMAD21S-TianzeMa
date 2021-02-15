package edu.neu.madcourse.numad21s_tianzema;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.net.URL;
import java.util.ArrayList;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.ViewHolder> {
    private final ArrayList<LinkCard> linkCardList;
    private ItemClickListener listener;

    public LinkAdapter(ArrayList<LinkCard> linkCardList) {
        this.linkCardList = linkCardList;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.link_card, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinkCard currentLink = linkCardList.get(position);
        holder.editName.setText(currentLink.getName());
        holder.editLink.setText(currentLink.getUrl());
        if (currentLink.isEditable()) {
            holder.editName.setEnabled(true);
            holder.editLink.setFocusable(true);
            holder.editLink.setClickable(false);
            holder.imageButton.setEnabled(true);
            holder.imageButton.setColorFilter(Color.parseColor("#66f542"));
        } else {
            holder.editName.setEnabled(false);
            holder.editLink.setFocusable(false);
            holder.editLink.setClickable(true);
            holder.editLink.setTextColor(Color.parseColor("#0352fc"));
            holder.imageButton.setEnabled(false);
            holder.imageButton.setColorFilter(Color.parseColor("#A9A9A9"));
        }
    }

    @Override
    public int getItemCount() {
        return linkCardList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private EditText editName;
        private EditText editLink;
        private ImageButton imageButton;

        public ViewHolder(@NonNull View itemView, final ItemClickListener listener) {
            super(itemView);
            editName = itemView.findViewById(R.id.editName);
            editLink = itemView.findViewById(R.id.editLink);
            imageButton = itemView.findViewById(R.id.addLinkButton);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getLayoutPosition();
                        if (position != RecyclerView.NO_POSITION && linkCardList.get(position).isEditable()) {
                            String url = editLink.getText().toString();
                            if (isValid(url)) {
                                listener.addItemButtonClick(position, editName.getText().toString(), url);
                                editName.setEnabled(false);
                                editLink.setFocusable(false);
                                editLink.setClickable(true);
                                editLink.setTextColor(Color.parseColor("#0352fc"));
                                imageButton.setEnabled(false);
                                imageButton.setColorFilter(Color.parseColor("#A9A9A9"));
                                imageButton.setBackgroundColor(Color.parseColor("#A9A9A9"));
                                notifyDataSetChanged();
                                Snackbar.make(v, "Add a new item", Snackbar.LENGTH_SHORT)
                                        .setAction("Action", null).show();
                            } else {
                                Snackbar.make(v, "The URL format is incorrect!", Snackbar.LENGTH_SHORT)
                                        .setAction("Action", null).show();
                            }
                        }
                    }
                }
            });
            editLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION && !linkCardList.get(position).isEditable()) {
                        String url = editLink.getText().toString();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        v.getContext().startActivity(i);
                    }
                }
            });
        }
    }

    public boolean isValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
