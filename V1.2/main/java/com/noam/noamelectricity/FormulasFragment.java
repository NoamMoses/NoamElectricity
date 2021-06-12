package com.noam.noamelectricity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FormulasFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_formulas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView ohm_title = view.findViewById(R.id.ohm_title),
                watt_title = view.findViewById(R.id.watt_title),
                resistors_title = view.findViewById(R.id.resistors_title),
                capacitors_title = view.findViewById(R.id.capacitors_title),
                capacitorsSeries_title = view.findViewById(R.id.capacitorsSeries_title),
                capacitorsParallel_title = view.findViewById(R.id.capacitorsParallel_title),
                resistorsSeries_title = view.findViewById(R.id.resistorsSeries_title),
                resistorsParallel_title = view.findViewById(R.id.resistorsParallel_title);

        /*
        * Init the titles - using a template of $1%s:, and then all I need to do is to specify the string
        * - If I would simply "build a string" like str+":", then it gives me a convention-warning, asks me to use resources instead
        * */

        /* Owm and watt titles */
        ohm_title.setText(String.format(getResources().getString(R.string.colon_template), getResources().getString(R.string.ohm)));
        watt_title.setText(String.format(getResources().getString(R.string.colon_template), getResources().getString(R.string.watt)));
        resistors_title.setText(String.format(getResources().getString(R.string.colon_template), getResources().getString(R.string.resistors)));
        capacitors_title.setText(String.format(getResources().getString(R.string.colon_template), getResources().getString(R.string.capacitors)));

        /* Resistors and capacitors titles */
        capacitorsSeries_title.setText(String.format(getResources().getString(R.string.seriesConnect_template), getResources().getString(R.string.capacitors)));
        capacitorsParallel_title.setText(String.format(getResources().getString(R.string.parallelConnect_template), getResources().getString(R.string.capacitors)));
        resistorsSeries_title.setText(String.format(getResources().getString(R.string.seriesConnect_template), getResources().getString(R.string.resistors)));
        resistorsParallel_title.setText(String.format(getResources().getString(R.string.parallelConnect_template), getResources().getString(R.string.resistors)));
    }
}
