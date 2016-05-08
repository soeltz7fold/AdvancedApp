package x7a.droid.advancedapp;


import android.graphics.Typeface;
import android.os.Bundle;
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

public class TransactionPage extends Fragment implements View.OnClickListener{
    DatabaseHelper DB;
    String get_amount_exp, get_desc_exp, get_desc_inc, get_amount_inc;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.main_2, menu);
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


    //init
        Button btn_add_exp = (Button) view.findViewById(R.id.btnAddExpenses);
        Button btn_add_inc = (Button) view.findViewById(R.id.btnAddIncome);
//        Button fab_trans = (Button) view.findViewById(R.id.fab_trans);
        EditText desc_add_exp = (EditText) view.findViewById(R.id.desc_add_expenses);
        EditText amount_add_exp = (EditText) view.findViewById(R.id.amount_add_expenses);

        //capture
        btn_add_exp.setOnClickListener(this);
        btn_add_inc.setOnClickListener(this);
        desc_add_exp.setOnClickListener(this);
        amount_add_exp.setOnClickListener(this);
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
                    EditText desc_exp = (EditText) getView().findViewById(R.id.desc_add_expenses);
                    EditText amount_exp = (EditText) getView().findViewById(R.id.amount_add_expenses);
                    TextView tv_result = (TextView) getView().findViewById(R.id.tv_new_expenses);
                    get_amount_exp = amount_exp.getText().toString();
                    get_desc_exp = desc_exp.getText().toString();
                    boolean result = DB.save_expanses(get_desc_exp, get_amount_exp);
                    if (result)
                        Toast.makeText(getActivity(), "New Expenses Inputted", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "New Expenses Failed", Toast.LENGTH_SHORT).show();
                    //Display Value
                    String total_expenses = get_desc_exp + Integer.parseInt(get_amount_exp);
                    tv_result.setText("New Expenses = " +" " +total_expenses);
                    desc_exp.setText("");
                    amount_exp.setText("");
                    Typeface supercell = Typeface.createFromAsset(getContext().getAssets(), "fonts/Supercell.ttf");
                    tv_result.setTypeface(supercell);
                } catch (Exception e) {
                }
                break;

            case R.id.btnAddIncome:
                try {
                    EditText desc_inc = (EditText) getView().findViewById(R.id.desc_new_income);
                    EditText amount_inc = (EditText) getView().findViewById(R.id.amount_new_income);
                    TextView tv_result = (TextView) getView().findViewById(R.id.tv_new_income);
                    get_desc_inc = desc_inc.getText().toString();
                    get_amount_inc = amount_inc.getText().toString();
                    boolean result = DB.save_income(get_desc_inc, get_amount_inc);
                    if (result)
                        Toast.makeText(getActivity(), "New Income Succeed", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "New Income Succeed", Toast.LENGTH_SHORT).show();
                    //Display Value
                    String total_income = get_desc_inc + Integer.parseInt(get_amount_inc);
                    tv_result.setText("New Income = " +" " +total_income);
                    desc_inc.setText("");
                    amount_inc.setText("");
                    Typeface supercell = Typeface.createFromAsset(getContext().getAssets(), "fonts/Supercell.ttf");
                    tv_result.setTypeface(supercell);
                } catch (Exception e) {
                }
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

