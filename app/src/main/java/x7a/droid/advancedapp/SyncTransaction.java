package x7a.droid.advancedapp;

import android.database.Cursor;

/**
 * Created by DroiD on 04/05/2016.
 */
public class SyncTransaction {
    private int id_exp;
    private int id_inc;
    private String description_exp;
    private String amount_exp;
    private String descriptions_inc;
    private String amount_inc;
    private String responses;


    public SyncTransaction(Cursor expenses, Cursor incomes) {
        return;
    }
    public int getId() {
        return id_exp;
    }
    public void setId(int id_exp) {
        this.id_exp = id_exp;
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
    public int getId_inc() {
        return id_inc;
    }
    public void setId_inc(int id_inc) {
        this.id_inc = id_inc;
    }
    public String getDescriptions_inc() {
        return descriptions_inc;
    }
    public void setDescriptions_inc(String descriptions_inc) {
        this.descriptions_inc = descriptions_inc;
    }
    public String getAmount_inc() {
        return amount_inc;
    }
    public void setAmount_inc(String amount_inc) {
        this.amount_inc = amount_inc;
    }
    public String getResponses() {
        return responses;
    }
    public void setResponses(String responses) {
        this.responses = responses;
    }
}

