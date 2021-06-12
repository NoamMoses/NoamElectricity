package com.noam.noamelectricity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TODOFragment extends Fragment {
    /*
     * Previously OtherFragment.java
     * */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] todo_list = {
                "Add more features",
                "Add more languages support, and improve existing",
                "Better design",
                "Organise & improve the code",
                "Better app name&logo",
                "Resistors\\Capacitors in series\\parallel calculator",
                "That's it.. pretty much.. Any ideas?\n"
        };
        /*
        * Padding bottom for the ListView will always put a space
        * \n will put a space just under the last item - looks better
        * But I think I will keep both - right now it is with padding bottom of 20dp in xml, and here one \n
        * */

        String[] atWork_list = {
                "Better design",
                "Adding French & Spanish",
                "Adding support for 6 bands resistor"
        };

        String[] knownBugs_list = {
                "Some quotes are a bit too big - fixed",
                "Bad GUI support when rotating the screen - fixed",
                "Two bugs were enough! Don't you think??"
        };

        ArrayAdapter<String> lv_todo_adapter = new ArrayAdapter<>(getContext(), R.layout.todo_list_items, todo_list),
                lv_atWork_adapter = new ArrayAdapter<>(getContext(), R.layout.todo_list_items, atWork_list),
                lv_knownBugs_adapter = new ArrayAdapter<>(getContext(), R.layout.todo_list_items, knownBugs_list);
        ListView lv_todo = view.findViewById(R.id.lv_todo),
                lv_atWork = view.findViewById(R.id.lv_atWork),
                lv_knownBugs = view.findViewById(R.id.lv_knownBugs);
        lv_todo.setAdapter(lv_todo_adapter);
        lv_atWork.setAdapter(lv_atWork_adapter);
        lv_knownBugs.setAdapter(lv_knownBugs_adapter);
    }
}
