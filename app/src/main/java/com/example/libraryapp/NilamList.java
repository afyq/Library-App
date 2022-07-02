package com.example.libraryapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NilamList extends ArrayAdapter<Nilam> {
    private Activity context;
    List<Nilam> nilams;

    public NilamList(Activity context, List<Nilam> nilams) {
        super(context, R.layout.layout_nilam, nilams);
        this.context = context;
        this.nilams = nilams;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_nilam, null, true);

        TextView textViewBookTitle = (TextView) listViewItem.findViewById(R.id.textViewBookTitle);
        TextView textViewBookWriter = (TextView) listViewItem.findViewById(R.id.textViewBookWriter);
        TextView textViewBookPublisher = (TextView) listViewItem.findViewById(R.id.textViewBookPublisher);
        TextView textViewBookLanguage = (TextView) listViewItem.findViewById(R.id.textViewBookLanguage);
        TextView textViewBookYearPublish = (TextView) listViewItem.findViewById(R.id.textViewBookYearPublish);
        TextView textViewToRM = (TextView) listViewItem.findViewById(R.id.textViewToRM);

        Nilam nilam = nilams.get(position);
        textViewBookTitle.setText(nilam.getBookTitle());
        textViewBookWriter.setText(nilam.getBookWriter());
        textViewBookPublisher.setText(nilam.getBookPublisher());
        textViewBookLanguage.setText(nilam.getBookLanguage());
        textViewBookYearPublish.setText(String.valueOf(nilam.getBookYearPublish()));
        textViewToRM.setText(nilam.getBookToRM());

        return listViewItem;
    }
}
