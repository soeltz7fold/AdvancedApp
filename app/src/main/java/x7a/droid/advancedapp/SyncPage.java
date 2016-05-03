package x7a.droid.advancedapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class SyncPage extends Fragment implements View.OnClickListener {
//    private OnFragmentInteractionListener mListener;
//
//    public SyncPage() {
//        // Required empty public constructor
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_sync_page, container, false);
        View view = inflater.inflate(R.layout.fragment_sync_page, container, false);
        Button btnSync = (Button) view.findViewById(R.id.btnSync);

        btnSync.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSync:
                Toast.makeText(getActivity(), "CLICKED", Toast.LENGTH_SHORT).show();

        //implements Interface for get all user
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'H:mm:ssZ")
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:3000")
                        .baseUrl("http://private-2efe7-soeltz7fold.apiary-mock.com")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                UserApi user_api = retrofit.create(UserApi.class);
        }
    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
