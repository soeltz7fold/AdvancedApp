package x7a.droid.advancedapp;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


    //init
        Button btn_add_exp = (Button) view.findViewById(R.id.btnAddExpenses);
        Button btn_add_inc = (Button) view.findViewById(R.id.btnAddIncome);
//        Button fab_trans = (Button) view.findViewById(R.id.fab_trans);
        EditText desc_add_exp = (EditText) view.findViewById(R.id.desc_add_expenses);
        EditText amount_add_exp = (EditText) view.findViewById(R.id.amount_add_expenses);

        //capture
        btn_add_exp.setOnClickListener(this);
        btn_add_inc.setOnClickListener(this);
//        fab_trans.setOnClickListener(this);
        desc_add_exp.setOnClickListener(this);
        amount_add_exp.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        // So we will make
        switch (v.getId()) {
            case R.id.btnAddExpenses:
                try {
                    EditText desc = (EditText) getView().findViewById(R.id.desc_add_expenses);
                    EditText amount = (EditText) getView().findViewById(R.id.amount_add_expenses);
                    TextView result = (TextView) getView().findViewById(R.id.tv_new_expenses);
                    String expenses = desc.getText().toString()
                            + Integer.parseInt(amount.getText().toString());
                    result.setText("Nilai Expenses = " + expenses);
                    Typeface supercell = Typeface.createFromAsset(getContext().getAssets(), "fonts/Supercell.ttf");
                    result.setTypeface(supercell);

                } catch (Exception e) {
                    Toast.makeText(getActivity(), "ADD EXPANSES CLICKED", Toast.LENGTH_SHORT).show();
                }
                break;
//            case R.id.fab_trans:
//                EditText desc = (EditText) getView().findViewById(R.id.desc_add_expenses);
//                EditText amount = (EditText) getView().findViewById(R.id.amount_add_expenses);
//                TextView result = (TextView) getView().findViewById(R.id.tv_new_expenses);
//                desc.setText("");
//                amount.setText("");
//                result.setText("Wanna Try Again?");
//                Typeface supercell = Typeface.createFromAsset(getContext().getAssets(), "fonts/Supercell.ttf");
//                result.setTypeface(supercell);
//                break;

//            case R.id.btnAddIncome:
//            Toast.makeText(getActivity(), "ADD INCOME CLICKED", Toast.LENGTH_SHORT).show();
//        break;
//
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

