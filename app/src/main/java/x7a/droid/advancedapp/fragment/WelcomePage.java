package x7a.droid.advancedapp.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import x7a.droid.advancedapp.R;

/**
 * Created by DroiD on 28/04/2016.
 */
public class WelcomePage extends Fragment {

    SharedPreferences getSharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SharedPreferences get_shared_preference = getSharedPreferences("authentication", MODE_PRIVATE);
//        Toast.makeText(getActivity(), "Welcome : "+get_shared_preference.getString("email",""), Toast.LENGTH_SHORT).show();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        return view;
    }

}
