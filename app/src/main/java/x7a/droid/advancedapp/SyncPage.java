package x7a.droid.advancedapp;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SyncPage extends Fragment{
    private static final String BASE_URL_SYNC = "http://private-6020b-task42.apiary-mock.com";
    TextView tv_status, tv_values;
 AlertDialog.Builder alert;
 Fragment fragment;
 FragmentManager FM;
 FragmentTransaction FT;
 ProgressBar PB;
 DatabaseHelper DB;
 Cursor expenses, incomes;
 int clicked;
 private ProgressDialog SyncDialog;
// private List <String>expenses_value_List = new ArrayList<String>();


//    private String[] expenses = {
//            "id_exp","description_exp","amount_exp"};
//    private String [] incomes = {
//            "id_exp","description_inc","amount_inc"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sync_page, container, false);
        Button btnSync = (Button) view.findViewById(R.id.btnSync);
        tv_values = (TextView) view.findViewById(R.id.tv_values);
        tv_status = (TextView) view.findViewById(R.id.tv_respond);
        final ProgressBar PB = (ProgressBar) view.findViewById(R.id.sync_progress);
        final Button retryBtn = new Button(getActivity());
        final Button skipBtn = new Button(getActivity());
        setHasOptionsMenu(true);
        JSONObject JSON_obj_exp, JSON_obj_inc;
        JSONArray JSON_arr_exp, JSON_arr_inc;

        //Check Network
        KoneksiCek();

            DB = new DatabaseHelper(getContext());
            expenses = DB.getAllDataExpenses();
            incomes = DB.getAllDataIncomes();

            clicked = 0;
            //Retry Temporary
            retryBtn.setText("Retry");
            retryBtn.setTextColor(Color.parseColor("#1E824C"));
            retryBtn.setGravity(Gravity.CENTER);
            retryBtn.setBackgroundColor(Color.parseColor("#66CC99"));
            //Retry Temporary
            skipBtn.setText("Skip");
            skipBtn.setTextColor(Color.parseColor("#1E824C"));
            skipBtn.setGravity(Gravity.CENTER);
            skipBtn.setBackgroundColor(Color.parseColor("#66CC99"));

            ArrayList<String> expenses_value_List = new ArrayList<String>();
            ArrayList<String> incomes_value_List = new ArrayList<String>();
            Cursor curExpenses = DB.getAllDataExpenses();
            Cursor curIncomes = DB.getAllDataIncomes();


            //CONVERT
            String[] data_expenses;
            if (curExpenses != null) {
                while (curExpenses.moveToNext()) {
                    data_expenses = new String[3];
                    data_expenses[0] = Integer.toString(curExpenses.getInt(0));
                    data_expenses[1] = curExpenses.getString(1);
                    data_expenses[2] = Integer.toString(curExpenses.getInt(2));
                    expenses_value_List.add(String.valueOf(data_expenses[0]));
                    expenses_value_List.add(String.valueOf(data_expenses[1]));
                    expenses_value_List.add(String.valueOf(data_expenses[2]));
//                    Log.e("INFO EXPENSES LIST", String.valueOf(expenses_value_List));
                }
                curExpenses.close();
            }
            String[] data_incomes;
            if (curIncomes != null) {
                while (curIncomes.moveToNext()) {
                    data_incomes = new String[3];
                    data_incomes[0] = Integer.toString(curIncomes.getInt(0));
                    data_incomes[1] = curIncomes.getString(1);
                    data_incomes[2] = Integer.toString(curIncomes.getInt(2));
//                    Log.e("info incomes", data_incomes[1]);
                    incomes_value_List.add(String.valueOf(data_incomes[0]));
                    incomes_value_List.add(String.valueOf(data_incomes[1]));
                    incomes_value_List.add(String.valueOf(data_incomes[2]));
//                    Log.e("INFO INCOMES LIST", String.valueOf(incomes_value_List));
                }
                curIncomes.close();
            }
        String total_data_expenses, total_data_incomes;
        total_data_expenses = expenses_value_List.toString();
        total_data_incomes = incomes_value_List.toString();


//            tv_values.setText("EXPENSES = "+String.valueOf(expenses_value_List) +"\n" +"\n"
//                                +"INCOMES = "+String.valueOf(incomes_value_List));
        tv_values.setText(total_data_expenses +"\n" +total_data_incomes);

        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(KoneksiCek()!=true) {
                    Toast.makeText(getActivity(), "Check Your Network Connectivity!!!", Toast.LENGTH_SHORT).show();
                }else{
                    onSyncing();
                    }
                }
            });
        return view;
        }

    private boolean onSyncing() {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'H:mm:ssZ")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_SYNC)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            final SyncApi sync_transaction = retrofit.create(SyncApi.class);
            //implement sync data
            final SyncTransaction Sync = new SyncTransaction(expenses, incomes);
            //SYNC FAKE SERVER
            Call<SyncTransaction> call = sync_transaction.syncExpenses(Sync);
            call.enqueue(new Callback<SyncTransaction>() {
                @Override
                public void onResponse(Call<SyncTransaction> call, Response<SyncTransaction> response) {
                    final int status = response.code();
//                tv_status.setText(String.valueOf(status));

                    if (status == 404) {
                        onSynced();
                        tv_status.setText("SUCCESS SYNC VALUE = " + String.valueOf(status));
                        Typeface supercell = Typeface.createFromAsset(getContext().getAssets(), "fonts/Supercell.ttf");
                        tv_status.setTypeface(supercell);
                        SyncDialog.dismiss();
                    } else {
//                        Toast.makeText(getActivity(), "404, NOT FOUND", Toast.LENGTH_SHORT).show();
                        tv_status.setText("FAILED SYNC VALUE = " + String.valueOf(status));
                        Typeface supercell = Typeface.createFromAsset(getContext().getAssets(), "fonts/Supercell.ttf");
                        tv_status.setTypeface(supercell);
                        SyncDialog.dismiss();
                        String code = String.valueOf(status);
                        Log.e("CODE Values", code);
                        onFailed();
                    }

                }

                @Override
                public void onFailure(Call<SyncTransaction> call, Throwable t) {
                    tv_status.setText("Nilai FAILURE Response API = " + String.valueOf(t));
                    Log.d("Sync", "Sync Response = " + t.getMessage());
                }
            });

            // Sync Dialog
            int totalProgressTime = 100;
            SyncDialog = new ProgressDialog(getActivity());
            SyncDialog.setMessage("Syncing....");
            SyncDialog.setTitle("Checking Values");
            SyncDialog.setCancelable(false);
            SyncDialog.setIndeterminate(false);
            SyncDialog.setProgress(7);
//        SyncDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL SYNC", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getActivity(), "SYNC CANCELLED", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//                SyncDialog.dismiss();
//                refreshFragment();
//            }
//        });
            SyncDialog.show();
        return true;
    }

    private boolean onSynced() {
        Toast.makeText(getActivity(), "SYNC SUCCEED", Toast.LENGTH_SHORT).show();
        SyncDialog.dismiss();
        return false;
        }
    private boolean onFailed(){
        alert = new AlertDialog.Builder(getActivity());
        LinearLayout layoutD = new LinearLayout(getActivity());
        layoutD.setOrientation(LinearLayout.VERTICAL);
        layoutD.setPadding (5,5,5,5);
//        layoutD.setBackgroundColor(Color.parseColor("#3F51B5"));
//        layoutD.addView(retryBtn);
//        layoutD.addView(skipBtn);
        alert.setView(layoutD);
        //Alert Sync
        alert.setCancelable(false)
                .setTitle("Failed To Synchronize")
                .setMessage("Retry? Or Skip?")
                .setPositiveButton("Retry?", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Retry Synchronizing....", Toast.LENGTH_SHORT).show();
//                        dialog.cancel();//bugs
                        dialog.dismiss();
                        onSyncing();
                    }
                })
                .setNeutralButton("Skip!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Sync Skipped", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        refreshFragment();
                    }
                });
//           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getActivity(), "Sync Cancelled", Toast.LENGTH_SHORT).show();
////                dialog.dismiss();
////                SyncDialog.dismiss();
//                homeFragment();
//            }
//        });
        alert.show();
        return false;
    }

    private void refreshFragment() {
            fragment = new SyncPage();
            FM = getFragmentManager();
            FT = FM.beginTransaction();
            FT.replace(R.id.fragment_place, fragment);
            FT.commit();
            }

    private boolean KoneksiCek() {
        ConnectivityManager konek = (ConnectivityManager)getContext()
                                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = konek.getActiveNetworkInfo();
        if(netInfo !=null && netInfo.isConnectedOrConnecting()){
            Toast.makeText(getActivity(), "NETWORK OK", Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(getActivity(), "NO NETWORK", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void homeFragment() {
        fragment = new DashboardPage();
        FM = getFragmentManager();
        FT = FM.beginTransaction();
        FT.replace(R.id.fragment_place, fragment);
        FT.commit();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_sync, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}







//                Gson gson = new GsonBuilder()
//                        .setDateFormat("yyyy-MM-dd'T'H:mm:ssZ")
//                        .create();
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl(BASE_URL_REGISTER)
//                        .addConverterFactory(GsonConverterFactory.create(gson))
//                        .build();
//                final SyncApi sync_transaction = retrofit.create(SyncApi.class);
//                //implement sync data
//                final SyncTransaction Sync = new SyncTransaction(expenses,incomes);
//                Call<SyncTransaction> call = sync_transaction.saveExpenses(Sync);
//                call.enqueue(new Callback<SyncTransaction>() {
//                    @Override
//                    public void onResponse(Call<SyncTransaction> call, Response<SyncTransaction> response) {
//                        int status = response.code();
//                        tv_status.setText(String.valueOf(status));

//                        SyncDialog = new ProgressDialog(getActivity());
//                        SyncDialog.setMessage("Syncing..");
//                        SyncDialog.setTitle("Checking Values");
//                        SyncDialog.setIndeterminate(false);
//                        SyncDialog.setCancelable(true);
//                        SyncDialog.show();
//                        final int totalProgressTime = 100;

//                        if(String.valueOf(status).equals("210")){
//                            Toast.makeText(getActivity(), "SUCCEED, Synchronize To Server", Toast.LENGTH_SHORT).show();
//                        }else{
//                            onFailed();
////                            Toast.makeText(getActivity(), "FAILED, Try ReSync Again", Toast.LENGTH_SHORT).show();
//                        }
//                        refreshFragment();
//                    }
//
//                    @Override
//                    public void onFailure(Call<SyncTransaction> call, Throwable t) {
//                        tv_status.setText("Nilai Response API = "+String.valueOf(t));
//                        Log.d("Login", "Sync Response =" + t.getMessage());
//                    }

//        });
//    }


//    private void onFailed(){
//        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
//        LinearLayout layoutD = new LinearLayout(getActivity());
//        layoutD.setOrientation(LinearLayout.VERTICAL);
//        layoutD.setPadding (5,5,5,5);
//        layoutD.setBackgroundColor(Color.parseColor("#3F51B5"));
//        layoutD.addView(retryBtn);
//        layoutD.addView(skipBtn);
//        alert.setView(layoutD);
//        }
//        });


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



//JSON_arr_exp = new JSONArray();
//        curExpenses.moveToFirst();
//        while (curExpenses.moveToNext()){
//        JSON_obj_exp = new JSONObject();
//        try {
//        JSON_obj_exp.put("_id", curExpenses.getInt(Integer.parseInt("_id")));
//        JSON_obj_exp.put("description_exp", curExpenses.getInt(Integer.parseInt("description_exp")));
//        } catch (JSONException e) {
//        e.printStackTrace();
//        }
//
//        JSON_arr_exp.put(JSON_obj_exp);
//        }
//        JSON_obj_exp = new JSONObject();
//        JSON_arr_exp.put(JSON_arr_exp);
//        String strExp = JSON_obj_exp.toString();