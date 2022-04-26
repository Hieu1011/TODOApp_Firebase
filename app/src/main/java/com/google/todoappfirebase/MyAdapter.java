package com.google.todoappfirebase;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    final ArrayList<Task> listTask;

    public MyAdapter (ArrayList<Task> listTask) {
        this.listTask = listTask;
    }

    @Override
    public int getCount() {
        return listTask.size();
    }

    @Override
    public Object getItem(int i) {
        return listTask.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View viewTask;
        if (convertView == null)
            viewTask = View.inflate(parent.getContext(), R.layout.task, null);
        else viewTask = convertView;

        //Bind sữ liệu phần tử vào View
        Task task = (Task) getItem(position);
        ((TextView) viewTask.findViewById(R.id.textViewTitle)).setText(task.getTitle());
        ((TextView) viewTask.findViewById(R.id.textViewStart)).setText( task.getStart());
        ((TextView) viewTask.findViewById(R.id.textViewEnd)).setText( task.getEnd());
        viewTask.findViewById(R.id.buttonDelete);

        return viewTask;
    }
}

