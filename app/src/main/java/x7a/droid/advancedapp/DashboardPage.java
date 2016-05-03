package x7a.droid.advancedapp;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class DashboardPage extends Fragment {
    TextView txtDesc_exp, txtAmount_exp, title_exp, title_inc;
    ListView lv_exp;
    DatabaseHelper DB;
    private View view;
    Cursor expenses, incomes;
    int clicked;
    Fragment fragment = null;
    FragmentManager FM;
    FragmentTransaction FT;
    AlertDialog.Builder alert;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_page, container, false);
//        if (view==null) {
//            view = inflater.inflate(R.layout.fragment_dashboard_page, container, false);
//        }else{
//            ViewGroup parent = (ViewGroup)view.getParent();
//            parent.removeView(view);

        ListView lv_expanses_obj = (ListView) view.findViewById(R.id.lv_expenses);
        ListView lv_incomes_obj = (ListView) view.findViewById(R.id.lv_incomes);
        TextView expTotal = (TextView)view.findViewById(R.id.tv_total_expenses);
        TextView incTotal = (TextView)view.findViewById(R.id.tv_total_income);
        TextView balTotal = (TextView)view.findViewById(R.id.tv_balance);

        final EditText descriptIn = new EditText(getActivity());
        final EditText amountIn = new EditText(getActivity());
        final Button update = new Button(getActivity());

        DB = new DatabaseHelper(getContext());
        expenses = DB.list_expenses();
        incomes = DB.list_incomes();
//        title_exp.setOnClickListener(this);
//        title_inc.setOnClickListener(this);

        clicked =0;
        //Description Temporary
        descriptIn.setInputType(InputType.TYPE_CLASS_TEXT);
        descriptIn.setHint("Description");
        descriptIn.setTextColor(Color.parseColor("#c0c0c0"));
        descriptIn.setHintTextColor(Color.parseColor("#3F51B5"));
        descriptIn.setEnabled(false);
        descriptIn.setGravity(Gravity.CENTER);
        //Amount Temporary
        amountIn.setInputType(InputType.TYPE_CLASS_NUMBER);
        amountIn.setHint("Amount");
        amountIn.setTextColor(Color.parseColor("#c0c0c0"));
        amountIn.setHintTextColor(Color.parseColor("#3F51B5"));
        amountIn.setGravity(Gravity.CENTER);
        amountIn.setEnabled(false);
        //Update Temporary
        update.setText("Edit");
        update.setTextColor(Color.parseColor("#1E824C"));
        update.setGravity(Gravity.CENTER);
        update.setBackgroundColor(Color.parseColor("#66CC99"));


        // CURSOR ADAPTER INCOMES
        SimpleCursorAdapter AdapterExp = new SimpleCursorAdapter(view.getContext(),
                R.layout.custom_data_list, expenses,
                new String[] {"description_exp","amount_exp"},
                new int[]{ R.id.data_list_desc, R.id.data_list_amount},0);
        lv_expanses_obj.setAdapter(AdapterExp);
        lv_expanses_obj.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                expenses.moveToPosition(position);
                final int rowId = expenses.getInt(expenses.getColumnIndexOrThrow("_id"));
                final String descriptionTemp = expenses.getString(expenses.getColumnIndex("description_exp"));
                final String amountTemp = expenses.getString(expenses.getColumnIndex("amount_exp"));
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                descriptIn.setText(descriptionTemp);
                amountIn.setText(amountTemp);

        LinearLayout layoutD = new LinearLayout(getActivity());
        layoutD.setOrientation(LinearLayout.VERTICAL);
        layoutD.setPadding (5,5,5,5);
        layoutD.setBackgroundColor(Color.parseColor("#3F51B5"));
        layoutD.addView(descriptIn);
        layoutD.addView(amountIn);
        layoutD.addView(update);
        alert.setView(layoutD);

                //UPDATE METHOD
                update.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if(clicked==0){
                        Toast.makeText(getActivity(), "Edit Data", Toast.LENGTH_SHORT).show();
                        descriptIn.setEnabled(true);
                        amountIn.setEnabled(true);
                        amountIn.setTextColor(Color.parseColor("#E74C3C"));
                        descriptIn.setTextColor(Color.parseColor("#E74C3C"));
                        clicked = 1;
                    }else if (clicked==1){
                        Toast.makeText(getActivity(), "Edit Success", Toast.LENGTH_SHORT).show();
                        descriptIn.setEnabled(false);
                        amountIn.setEnabled(false);
                        descriptIn.setTextColor(Color.parseColor("#c0c0c0"));
                        amountIn.setTextColor(Color.parseColor("#c0c0c0"));//
                        clicked=0;
                        }
                    }
                });
            //ALERT EXPENSES
            alert.setCancelable(false)
                .setTitle("Data Expanses")
                .setMessage("Update Data? Or Delete To Remove : ")
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
                        refreshFragment();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("DELETE", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id_del = DB.delete_expenses(String.valueOf(rowId));
                        Toast.makeText(getActivity(), "Data Deleted", Toast.LENGTH_SHORT).show();
                        refreshFragment();
                        dialog.dismiss();
                    }
                })
                .setNeutralButton("Update", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Data Updated", Toast.LENGTH_SHORT).show();
                        DB.update_expenses(String.valueOf(rowId), descriptIn.getText().toString(),
                                amountIn.getText().toString());
                        refreshFragment();
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });


        // CURSOR ADAPTER INCOMES
        SimpleCursorAdapter AdapterInc = new SimpleCursorAdapter(getActivity(),
                R.layout.custom_data_list, incomes,
                new String[]{"description_inc", "amount_inc"},
                new int[]{R.id.data_list_desc, R.id.data_list_amount}, 0);
        lv_incomes_obj.setAdapter(AdapterInc);
        lv_incomes_obj.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                expenses.moveToPosition(position);
                final int rowId = incomes.getInt(incomes.getColumnIndexOrThrow("_id"));
                final String descriptionTemp = incomes.getString(incomes.getColumnIndex("description_inc"));
                final String amountTemp = incomes.getString(incomes.getColumnIndex("amount_inc"));
                alert = new AlertDialog.Builder(getActivity());
                descriptIn.setText(descriptionTemp);
                amountIn.setText(amountTemp);

                LinearLayout LayoutD = new LinearLayout(getActivity());
                LayoutD.setOrientation(LinearLayout.VERTICAL);
                LayoutD.setPadding(5,5,5,5);
                LayoutD.setBackgroundColor(Color.parseColor("#3F51B5"));
                LayoutD.addView(descriptIn);
                LayoutD.addView(amountIn);
                LayoutD.addView(update);
                alert.setView(LayoutD);

                update.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                if(clicked==0){
                    Toast.makeText(getActivity(), "Edit Data", Toast.LENGTH_SHORT).show();
                    descriptIn.setEnabled(true);
                    amountIn.setEnabled(true);
                    descriptIn.setTextColor(Color.parseColor("#E74C3C"));
                    amountIn.setTextColor(Color.parseColor("#E74C3C"));
                    clicked=1;
                }else if (clicked==1){
                    Toast.makeText(getActivity(), "Edit Succeed", Toast.LENGTH_SHORT).show();
                    descriptIn.setEnabled(true);
                    amountIn.setEnabled(true);
                    descriptIn.setTextColor(Color.parseColor("#c0c0c0"));
                    amountIn.setTextColor(Color.parseColor("c0c0c0"));
                    clicked=0;
                        }
                    }
                });
                //ALERT INCOMES
                alert.setCancelable(false)
                        .setTitle("Data Incomes")
                        .setMessage("Update Data? Or Delete To Remove : ")
                        .setPositiveButton("Cancel", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
                                refreshFragment();
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("DELETE", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id_inc = DB.delete_income(String.valueOf(rowId));
                                Toast.makeText(getActivity(), "Data Deleted", Toast.LENGTH_SHORT).show();
                                refreshFragment();
                                dialog.dismiss();
                            }
                        })
                        .setNeutralButton("Update", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "Data Updated", Toast.LENGTH_SHORT).show();
                                DB.update_income(String.valueOf(rowId), descriptIn.getText().toString(),
                                        amountIn.getText().toString());
                                refreshFragment();
                                dialog.dismiss();
                            }
                        });
                alert.show();
            }
        });

        //CALCULATION
        int sumExp =0;
        while (expenses.moveToNext()){
            sumExp += expenses.getInt(expenses.getColumnIndex("amount_exp"));
        }
        expTotal.setText("TOTAL EXPENSES = Rp."+String.valueOf(sumExp));

        int sumInc = 0;
        while (incomes.moveToNext()){
            sumInc += incomes.getInt(incomes.getColumnIndex("amount_inc"));
        }
        incTotal.setText("INCOMES TOTAL = Rp."+String.valueOf(sumInc));

        balTotal.setText("RP. "+String.valueOf(sumInc-sumExp));
        Typeface supercell = Typeface.createFromAsset(getContext().getAssets(), "fonts/Supercell.ttf");
        balTotal.setTypeface(supercell);
        DB.close();
        return view;
    }

//Refresh Fragment
    private void refreshFragment() {
    fragment = new DashboardPage();
    FM = getFragmentManager();
    FT = FM.beginTransaction();
    FT.replace(R.id.fragment_place, fragment);
    FT.commit();
        }
    }




//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//    }
//
//    private void alert_message(String title, String message) {
//        AlertDialog.Builder build = new AlertDialog.Builder(getContext());
//        build.setCancelable(true);
//        build.setTitle(title);
//        build.setMessage(message);
//        build.show();
//    }
//    Cursor expenses = DB.list_expenses();
//if (expenses.getCount() == 0) {
//        alert_message("Message", "No Data Expenses Found");
//        return view;
//        }
//        //Append
//        StringBuffer buffer = new StringBuffer();
//        while (expenses.moveToNext()) {
////            buffer.append("id : " + expenses.getString(0) + "\n");
////            buffer.append("description_exp : " + expenses.getString(1) + "\n");
////            buffer.append("amount_exp : " + expenses.getString(2) + "\n");
//
//        txtDesc_exp.append(expenses.getString(1) +"    " +expenses.getString(2) + "\n");
////            txtDesc_exp.append("Amount = " +expenses.getString(2) + "\n");
//        }
//        //show
////        alert_message("List Expenses", buffer.toString());
//        return view;

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            //SAVED
//            case R.id.tv_expenses_title:
//                Cursor expenses = DB.list_expenses();
//                if (expenses.getCount() == 0) {
//                    alert_message("Message", "No Data Expenses Found");
//                return;
//                }
//                //Append
//                StringBuffer buffer_exp = new StringBuffer();
//                while (expenses.moveToNext()) {
//                    buffer_exp.append("id : " + expenses.getString(0) + "\n");
//                    buffer_exp.append("description_exp : " + expenses.getString(1) + "\n");
//                    buffer_exp.append("amount_exp : " + expenses.getString(2) + "\n");
//                }
//                //show
//                Toast.makeText(getActivity(), "CLICK", Toast.LENGTH_SHORT).show();
//                alert_message("List Expenses", buffer_exp.toString());
//                break;
//            case R.id.tv_income_title:
//                Cursor income = DB.list_expenses();
//                if (income.getCount() == 0) {
//                    alert_message("Message", "No Data Income Found");
//                return;
//                }
//                //Append
//                StringBuffer buffer_inc = new StringBuffer();
//                while (income.moveToNext()) {
//                    buffer_inc.append("id : " + income.getString(0) + "\n");
//                    buffer_inc.append("description_inc : " + income.getString(1) + "\n");
//                    buffer_inc.append("amount_inc : " + income.getString(2) + "\n");
//                    //show
//                    Toast.makeText(getActivity(), "CLICK", Toast.LENGTH_SHORT).show();
//                    alert_message("List Expenses", buffer_inc.toString());
//                    break;
//                }
//
//            }
//        }