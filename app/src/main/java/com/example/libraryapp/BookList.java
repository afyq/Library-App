package com.example.libraryapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BookList  extends ArrayAdapter<Book> {
    private Activity context;
    List<Book> books;

    public BookList(Activity context, List<Book> books) {
        super(context, R.layout.layout_book, books);
        this.context = context;
        this.books = books;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_book, null, true);

        TextView textViewBookTitle = (TextView) listViewItem.findViewById(R.id.textViewBookTitle);
        TextView textViewBookWriter = (TextView) listViewItem.findViewById(R.id.textViewBookWriter);
        TextView textViewBookPublisher = (TextView) listViewItem.findViewById(R.id.textViewBookPublisher);
        TextView textViewBookGenre = (TextView) listViewItem.findViewById(R.id.textViewBookGenre);
        TextView textViewBookPage = (TextView) listViewItem.findViewById(R.id.textViewBookPage);
        TextView textViewAvailability = (TextView) listViewItem.findViewById(R.id.textViewAvailability);

        Book book = books.get(position);
        textViewBookTitle.setText(book.getBookTitle());
        textViewBookWriter.setText(book.getBookWriter());
        textViewBookPublisher.setText(book.getBookPublisher());
        textViewBookGenre.setText(book.getBookGenre());
        textViewBookPage.setText(String.valueOf(book.getBookPage()));
        textViewAvailability.setText(String.valueOf(book.getBookAvailability()));

        return listViewItem;
    }

}