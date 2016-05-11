package x7a.droid.advancedapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.zip.Inflater;

/**
 * Created by DroiD on 11/05/2016.
 */
public class FabPage extends Fragment implements View.OnClickListener{

com.github.clans.fab.FloatingActionButton fab1, fab2;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fabpage, container, false);

//        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.custom_fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "CUSTOM", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        fab1 = (com.github.clans.fab.FloatingActionButton)view.findViewById(R.id.custom_fab1);
        fab2 = (com.github.clans.fab.FloatingActionButton)view.findViewById(R.id.custom_fab2);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.custom_fab1:
                Toast.makeText(getActivity(), "Custom ONE Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.custom_fab2:
                Toast.makeText(getActivity(), "Custom TWO Clicked", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
