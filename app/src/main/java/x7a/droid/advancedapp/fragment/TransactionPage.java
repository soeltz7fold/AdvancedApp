package x7a.droid.advancedapp.fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import x7a.droid.advancedapp.DatabaseHelper;
import x7a.droid.advancedapp.R;

public class TransactionPage extends Fragment implements View.OnClickListener{
    DatabaseHelper DB;
    String get_amount_exp, get_desc_exp, get_desc_inc, get_amount_inc;
    com.github.clans.fab.FloatingActionButton clear_exp, clear_inc;
    EditText desc_exp, amount_exp, desc_inc, amount_inc;
    TextView tv_result_exp, tv_result_inc;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_transaction, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        return inflater.inflate(R.layout.fragment_transaction_page, container, false);
    View view = inflater.inflate(R.layout.fragment_transaction_page, container, false);
//        return view;
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        toolbar.setTitle("Transaction");
//        toolbar.setNavigationIcon(R.drawable.ic_menu_camera);
//        setSupportActionBar(toolbar);


    //init widget
        Button btn_add_exp = (Button) view.findViewById(R.id.btnAddExpenses);
        Button btn_add_inc = (Button) view.findViewById(R.id.btnAddIncome);
//        Button fab_trans = (Button) view.findViewById(R.id.fab_trans);
        desc_exp = (EditText) view.findViewById(R.id.desc_add_expenses);
        amount_exp = (EditText) view.findViewById(R.id.amount_add_expenses);
        desc_inc = (EditText) view.findViewById(R.id.desc_new_income);
        amount_inc = (EditText) view.findViewById(R.id.amount_new_income);
        tv_result_exp = (TextView) view.findViewById(R.id.tv_result_expenses);
        tv_result_inc = (TextView) view.findViewById(R.id.tv_result_income);
        clear_exp = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.transaction_fab1);
        clear_inc = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.transaction_fab2);

        //Capture This
        btn_add_exp.setOnClickListener(this);
        btn_add_inc.setOnClickListener(this);
        desc_exp.setOnClickListener(this);
        amount_exp.setOnClickListener(this);
        clear_exp.setOnClickListener(this);
        clear_inc.setOnClickListener(this);
//        fab_trans.setOnClickListener(this);
        DB = new DatabaseHelper(getContext());
        DB.list_expenses();
        DB.list_incomes();
        return view;
    }

    @Override
    public void onClick(View v) {
        // So we will make
        switch (v.getId()) {
            case R.id.btnAddExpenses:
                try {
                    get_amount_exp = amount_exp.getText().toString();
                    get_desc_exp = desc_exp.getText().toString();
                    boolean result = DB.save_expanses(get_desc_exp, get_amount_exp);
                    if (result)
                        Toast.makeText(getActivity(), "New Expenses Inputted", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "New Expenses Failed", Toast.LENGTH_SHORT).show();
                    //Display Value
                    String total_expenses = get_desc_exp + Integer.parseInt(get_amount_exp);
                    tv_result_exp.setText("New Expenses = " +" " +total_expenses);
                    desc_exp.setText("");
                    amount_exp.setText("");
                    Typeface supercell = Typeface.createFromAsset(getContext().getAssets(), "fonts/Supercell.ttf");
                    tv_result_exp.setTypeface(supercell);
                } catch (Exception e) {
                }
                break;

            case R.id.btnAddIncome:
                Typeface supercell = Typeface.createFromAsset(getResources().getAssets(), "fonts/Supercell.ttf");
                try {
                    get_desc_inc = desc_inc.getText().toString();
                    get_amount_inc = amount_inc.getText().toString();
                    boolean result = DB.save_income(get_desc_inc, get_amount_inc);
                    if (result)
                        Toast.makeText(getActivity(), "New Income Succeed", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "New Income Succeed", Toast.LENGTH_SHORT).show();
                    //Display Value
                    String total_income = get_desc_inc + Integer.parseInt(get_amount_inc);
                    tv_result_inc.setText("New Income = " +" " +total_income);
                    desc_inc.setText("");
                    amount_inc.setText("");
                    supercell = Typeface.createFromAsset(getContext().getAssets(), "fonts/Supercell.ttf");
                    tv_result_inc.setTypeface(supercell);
                } catch (Exception e) {
                }
                break;
            case R.id.transaction_fab1:
                desc_exp.setText("");
                amount_exp.setText("");
                tv_result_exp.setText("EXPENSES Again?");
                supercell = Typeface.createFromAsset(getResources().getAssets(), "fonts/Supercell.ttf");
                tv_result_exp.setTypeface(supercell);
//                Toast.makeText(getActivity(), "Clicked Clear EXPENSES", Toast.LENGTH_SHORT).show();
                break;
            case R.id.transaction_fab2:
                desc_inc.setText("");
                amount_inc.setText("");
                tv_result_inc.setText("INCOMES Again?");
                supercell = Typeface.createFromAsset(getResources().getAssets(), "fonts/Supercell.ttf");
                tv_result_inc.setTypeface(supercell);
//                Toast.makeText(getActivity(), "Clicked Clear INCOMES", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }


//            case R.id.desc_add_expenses:
//                EditText desc = (EditText) getView().findViewById(R.id.desc_add_expenses);
//                desc.setText("");
//                Toast.makeText(getActivity(), "Input Sesuatu Atuh", Toast.LENGTH_SHORT).show();
//                return true;
//
//            case R.id.amount_add_expenses:
//                EditText amount = (EditText) getView().findViewById(R.id.amount_add_expenses);
//                amount.setText("");
//                Toast.makeText(getActivity(), "Input Atuh Sesuatu", Toast.LENGTH_SHORT).show();
//                return true;

