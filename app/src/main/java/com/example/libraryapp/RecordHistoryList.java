package com.example.libraryapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RecordHistoryList extends ArrayAdapter<Issue> {

    private Activity context;
    List<Issue> issues;

    public RecordHistoryList(Activity context, List<Issue> issues) {
        super(context, R.layout.layout_record_history, issues);
        this.context = context;
        this.issues = issues;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_record_history, null, true);

        TextView textViewBookTitle = (TextView) listViewItem.findViewById(R.id.textViewBookTitle);
        TextView textViewIssueName = (TextView) listViewItem.findViewById(R.id.textViewIssueName);
        TextView textViewIssueDate = (TextView) listViewItem.findViewById(R.id.textViewIssueDate);
        TextView textViewReturnDate = (TextView) listViewItem.findViewById(R.id.textViewReturnDate);

        Issue issue = issues.get(position);
        textViewBookTitle.setText(issue.getBookTitle());
        textViewIssueName.setText(issue.getIssueName());
        textViewIssueDate.setText(issue.getIssueDate());
        textViewReturnDate.setText(issue.getReturnDate());

        return listViewItem;
    }

}
