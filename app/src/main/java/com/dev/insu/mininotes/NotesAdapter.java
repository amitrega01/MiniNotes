package com.dev.insu.mininotes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by amitr on 19.11.2017.
 */

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    // Store a member variable for the contacts
    private List<NoteModel> mNotes;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public NotesAdapter(Context context, List<NoteModel> contacts) {
        mNotes = contacts;
        mContext = context;

    }

    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_note, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(final NotesAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        NoteModel note = mNotes.get(position);

        // Set item views based on your views and data model
        TextView note_title = viewHolder.note_title;
        TextView note_content = viewHolder.note_content;
        TextView note_date = viewHolder.note_date;
        note_title.setText(note.getTitle());
        note_date.setText(note.getDate());
        note_content.setText(note.getContent());
        CardView card = viewHolder.cardView;
       
        card.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                //Toast.makeText(mContext, "asdas", Toast.LENGTH_SHORT).show();
                int pos = mNotes.size();
                Intent i = new Intent(mContext, AddNote.class);
                i.putExtra("ID", pos - (position));
                mContext.startActivity(i);
            }
        });


    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public CardView cardView;
        public TextView note_title;
        public TextView note_content;
        public TextView note_date;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card);
            note_title = (TextView) itemView.findViewById(R.id.note_title);
            note_content = (TextView) itemView.findViewById(R.id.note_content);
            note_date = (TextView) itemView.findViewById(R.id.note_date);

        }
    }

}