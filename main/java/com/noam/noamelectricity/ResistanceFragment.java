package com.noam.noamelectricity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import com.noam.noamelectricity.functions.helperFunctions;

public class ResistanceFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_resistance, container, false);
    }

    List<String> colours_list = new ArrayList<>(), // List of all of the colours values
            multipliers_list = new ArrayList<>(), // List of all of the multipliers values
            tolerance_list = new ArrayList<>(), // List of all of the tolerances values
            resistor = new ArrayList<>(), // The resistor - each index is a parameter - values from the spinner lists
            resistor_values = new ArrayList<>(); // The resistor actual values - without the word
    Spinner band1, band2, band3, band4; // The spinner lists
    TextView result; // The result
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        band1 = view.findViewById(R.id.spin_band1);
        band2 = view.findViewById(R.id.spin_band2);
        band3 = view.findViewById(R.id.spin_band3);
        band4 = view.findViewById(R.id.spin_band4);
        result = view.findViewById(R.id.res_result);

        // Init the lists so I won't get out of index error
        resistor_values.add("1");
        resistor_values.add("1");
        resistor_values.add("1");
        resistor_values.add("1");
        resistor.add("1");
        resistor.add("1");
        resistor.add("1");
        resistor.add("1");


        // Init the colours of the first two bands - same values
        colours_list.add("Black 0");
        colours_list.add("Brown 1");
        colours_list.add("Red 2");
        colours_list.add("Orange 3");
        colours_list.add("Yellow 4");
        colours_list.add("Green 5");
        colours_list.add("Blue 6");
        colours_list.add("Violet 7");
        colours_list.add("Grey 8");
        colours_list.add("White 9");
        // Init the multiplier values
        multipliers_list.add("Black 1");
        multipliers_list.add("Brown 10");
        multipliers_list.add("Red 100");
        multipliers_list.add("Orange 1K");
        multipliers_list.add("Yellow 10K");
        multipliers_list.add("Green 100K");
        multipliers_list.add("Blue 1M");
        multipliers_list.add("Violet 10M");
        multipliers_list.add("Grey 100M");
        multipliers_list.add("White 1G");
        multipliers_list.add("Gold 0.1");
        multipliers_list.add("Silver 0.01");
        // Init the tolerance values
        tolerance_list.add("Brown 1%");
        tolerance_list.add("Red 2%");
        tolerance_list.add("Green 0.5%");
        tolerance_list.add("Blue 0.25%");
        tolerance_list.add("Violet 0.1%");
        tolerance_list.add("Grey 0.05%");
        tolerance_list.add("Gold 5%");
        tolerance_list.add("Silver 10%");
        // TODO If I want to use ohm sign-symbol,then it is here Ω

        // Setting listeners to this class
        band1.setOnItemSelectedListener(this);
        band2.setOnItemSelectedListener(this);
        band3.setOnItemSelectedListener(this);
        band4.setOnItemSelectedListener(this);
        Context context = getContext();
        if (context != null) { // To check that the context isn't null - I need to think what to do in case it is null
            ArrayAdapter<String> adapter_colours = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, colours_list),
                    adapter_multipliers = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, multipliers_list),
                    adapter_tolerance = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, tolerance_list);
            band1.setAdapter(adapter_colours);
            band2.setAdapter(adapter_colours);
            band3.setAdapter(adapter_multipliers);
            band4.setAdapter(adapter_tolerance);
        }
    }

    double basicNumber = 0.0, // The of the first two digits - for example 2,0=20
            multiplierNum = 0.0, // The multiplier value as
            basicNumber_multiplied = 0.0; // The basic number multiplied by the multiplier
    String result_txt = ""; // To build the string of the result
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*
        * Whenever I choose something, from any list
        * - There isn't any else statement, since it can't happen
        * */
        if (parent.getId() == R.id.spin_band1) { // First spinner list
            Log.d("CLICKED", "First band, colour chose is " + colours_list.get(position));
            resistor.set(0, colours_list.get(position));
            resistor_values.set(0, colours_list.get(position).split(" ")[1]);
        } else if (parent.getId() == R.id.spin_band2) { // Second spinner list
            Log.d("CLICKED", "Second band, colour chose is " + colours_list.get(position));
            resistor.set(1, colours_list.get(position));
            resistor_values.set(1, colours_list.get(position).split(" ")[1]);
        } else if (parent.getId() == R.id.spin_band3) { // Fourth spinner list
            Log.d("CLICKED", "Third band, colour chose is " + multipliers_list.get(position));
            resistor.set(2, multipliers_list.get(position));
            resistor_values.set(2, multipliers_list.get(position).split(" ")[1]);
        } else if (parent.getId() == R.id.spin_band4) { // Fifth spinner list
            Log.d("CLICKED", "Fourth band, colour chose is " + tolerance_list.get(position));
            resistor.set(3, tolerance_list.get(position));
            resistor_values.set(3, tolerance_list.get(position).split(" ")[1]);
        }
        basicNumber = Integer.parseInt(resistor_values.get(0) + resistor_values.get(1));
        switch (resistor_values.get(2)) { // Getting the multiplier - convert "1k" to "1000"
            case "1":
                multiplierNum = 1;
                break;
            case "10":
                multiplierNum = 10;
                break;
            case "100":
                multiplierNum = 100;
                break;
            case "1K":
                multiplierNum = 1000;
                break;
            case "10K":
                multiplierNum = 10000;
                break;
            case "100K":
                multiplierNum = 100000;
                break;
            case "1M":
                multiplierNum = 1000000;
                break;
            case "10M":
                multiplierNum = 10000000;
                break;
            case "100M":
                multiplierNum = 100000000;
                break;
            case "1G":
                multiplierNum = 1000000000;
                break;
            case "0.1":
                multiplierNum = 0.1;
                break;
            case "0.01":
                multiplierNum = 0.01;
                break;
        }
        basicNumber_multiplied = basicNumber*multiplierNum;
        result_txt = helperFunctions.analyseDot(basicNumber_multiplied, true, true);
        result.setText(String.format(getResources().getString(R.string.ohm_result), result_txt, resistor_values.get(3)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d("CLICKED", "Nothing selected? the parent is " + parent.getId());
    }
}