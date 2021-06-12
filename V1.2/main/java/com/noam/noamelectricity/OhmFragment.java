package com.noam.noamelectricity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.noam.noamelectricity.functions.helperFunctions;

import java.util.Locale;

public class OhmFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ohm, container, false);
    }

    float v = 0, i = 0, r = 0; // Not really useful, but just in case

    EditText volt_in, amp_in, res_in; // Inputs
    TextView volt_title, amp_title, res_title, feedback; // Titles - result is also used to indicate whether there is an error
    Button calculate;
    char choice = ' '; // Flag to know which parameter should be calculated
    int defaultTextColour; // To know what the default colour is - for when selecting a parameter (in selectCalc() function)

    void calc_ohm(char choice, EditText volt, EditText ampere, EditText resistance) {
        /*
        * calc_ohm
        * ---------
        * Parameters:
        *   char choice: Which parameter should be calculated.
        *       Possible values: v, i, r
        *   EditText v_p, i_p, r_p: EditText inputs
        * The function calculates the requested parameter and sets the feedback TextView to the result/error
        * */
        double result = 0.0;
        if (helperFunctions.inputIsEmpty(volt_in) || helperFunctions.inputIsEmpty(amp_in) || helperFunctions.inputIsEmpty(res_in)) {
            // One or more fields are empty/not valid
            result = 0.0;
            feedback.setText(R.string.err_emptyFields);
        } else {
            float v = Float.parseFloat(volt.getText().toString()),
                    i = Float.parseFloat(ampere.getText().toString()),
                    r = Float.parseFloat(resistance.getText().toString());
            switch (choice) {
                case 'v':
                    result = i * r;
                    break;
                case 'i':
                    result = v / r;
                    break;
                case 'r':
                    result = v / i;
                    break;
            }
        }
        // TODO check why is it with a warning
        feedback.setText(String.format(Locale.US, "%.2f %s", result, "Ω")); // Sets the feedback TextView to the result/error message
    }

    void selectCalc(char param) {
        /*
        * selectCalc
        * ------------
        * Parameters:
        *   char parm: which parameter was clicked.
        *       Each button click is a separated button, and calls this function independently - therefore it can pass an indicator of which button is it
        * Whenever the user clicks a field, and chooses it to be the requested parameter to be calculated
        * The function does:
        * 1) Sets all of the other inputs to be disabled
        * 2) Sets all of the other titles to be in a different colour
        * 3) Sets the "char choice" global variable to be either v/i/r - to indicate which variable should be calculated in calcOhm();
        * */
        volt_in.setEnabled(true);
        amp_in.setEnabled(true);
        res_in.setEnabled(true);
        volt_title.setTextColor(defaultTextColour);
        amp_title.setTextColor(defaultTextColour);
        res_title.setTextColor(defaultTextColour);
        // String chosenItem_colour = "#FF8000";
        String chosenItem_colour = "#FF7129";
        switch (param) {
            case 'v': // Voltage
                volt_in.setEnabled(false);
                volt_title.setTextColor(Color.parseColor(chosenItem_colour));
                choice = 'v';
                break;
            case 'i': // Ampere
                amp_in.setEnabled(false);
                amp_title.setTextColor(Color.parseColor(chosenItem_colour));
                choice = 'i';
                break;
            case 'r': // Resistance
                res_in.setEnabled(false);
                res_title.setTextColor(Color.parseColor(chosenItem_colour));
                choice = 'r';
                break;
            default: // Should never happen, since I am the one who passes the parameters, via the code, and not using user input.
                // Disabling everything, just in case. Setting the "char choice" global variable to "e", to indicate that there was an error.
                volt_in.setEnabled(false);
                amp_in.setEnabled(false);
                res_in.setEnabled(false);
                volt_title.setTextColor(Color.RED);
                amp_title.setTextColor(Color.RED);
                res_title.setTextColor(Color.RED);
                choice = 'e';
                break;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        volt_in = view.findViewById(R.id.ohm_input_v);
        amp_in = view.findViewById(R.id.ohm_input_i);
        res_in = view.findViewById(R.id.ohm_input_r);
        volt_title = view.findViewById(R.id.ohm_title_v);
        amp_title = view.findViewById(R.id.ohm_title_i);
        res_title = view.findViewById(R.id.ohm_title_r);
        feedback = view.findViewById(R.id.ohm_title_feedback);
        calculate = view.findViewById(R.id.ohm_button_calc);

        defaultTextColour = feedback.getCurrentTextColor(); // Saving the default text colour

        // Sets a default value to each parameter
        volt_in.setText("1");
        amp_in.setText("1");
        res_in.setText("1");

        calculate.setOnClickListener((View v)->{ // Calculates the selected parameter
            calc_ohm(choice, volt_in, amp_in, res_in);
        });

        if (volt_in != null && amp_in != null && res_in != null) {
            /*
            * This code runs when the view is created, and since I set the values to the EditTexts, it can't be null - but making sure, just in case.
            * However, there is no need to have else, that would be just useless
            * */
            volt_title.setOnClickListener((View v)->{
                selectCalc('v'); // Calculate the voltage
            });

            amp_title.setOnClickListener((View v)->{
                selectCalc('i'); // Calculate the ampere
            });

            res_title.setOnClickListener((View v)->{
                selectCalc('r'); // Calculate the resistance
            });

            // Simulate a click on "Volt" and "calculate", in order to set default values to start with
            volt_title.performClick();
            calculate.performClick();

            volt_in.addTextChangedListener(new TextWatcher() {
                /*
                * When the EditText is being changed.
                * There is a null check in the parent-if.
                * */
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!(volt_in.getText().toString().matches(""))) {
                        /*
                        * Making sure the input isn't empty. In case it is, it would be an else statement.
                        * But it is useless, since I don't need to take any action in case the input is empty - no feedback is needed.
                        * */
                        v = Float.parseFloat(s.toString());
                    }
                }
            });
            amp_in.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!(amp_in.getText().toString().matches(""))) {
                        i = Float.parseFloat(s.toString());
                    }
                }
            });
            res_in.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!(res_in.getText().toString().matches(""))) {
                        r = Float.parseFloat(s.toString());
                    }
                }
            });
        }
    }
}
