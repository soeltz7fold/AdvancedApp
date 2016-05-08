package x7a.droid.advancedapp;

import android.database.Cursor;

/**
 * Created by DroiD on 04/05/2016.
 */
public class SyncTransaction {
    public SyncTransaction(Cursor expenses, Cursor incomes) {
        return;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription_exp() {
        return description_exp;
    }

    public void setDescription_exp(String description_exp) {
        this.description_exp = description_exp;
    }

    public String getAmount_exp() {
        return amount_exp;
    }

    public void setAmount_exp(String amount_exp) {
        this.amount_exp = amount_exp;
    }

    private int id;
    private String description_exp;
    private String amount_exp;

}

