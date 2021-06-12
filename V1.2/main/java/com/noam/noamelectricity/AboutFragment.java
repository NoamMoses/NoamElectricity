package com.noam.noamelectricity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class AboutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView emailClickable = view.findViewById(R.id.appAbout_email);

        emailClickable.setOnClickListener((View v)->{
            Toast.makeText(view.getContext(), "Opening Gmail App", Toast.LENGTH_LONG).show();

            /* Opens gmail */
            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

            final String defaultEmailData_sendTo = "noam1moses.apps@gmail.com",
                    defaultEmailData_subject = "App Review | YOUR APP IS INCREDIBLE!!!!",
                    defaultEmailData_content = "Hello there!\nI didn't know you will be here!";
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{defaultEmailData_sendTo});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, defaultEmailData_subject);
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, defaultEmailData_content);

            final PackageManager pm = requireContext().getPackageManager();
            final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
            ResolveInfo best = null;
            for (final ResolveInfo info : matches) {
                if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail")) {
                    best = info;
                }
            }
            if (best != null) {
                emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
            }
            startActivity(emailIntent);
        });
    }
}