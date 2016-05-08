package x7a.droid.advancedapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DroiD on 04/05/2016.
 */
public class SyncTransactions {
    @SerializedName("SyncTransactions")
    public List<SyncItem> SyncTransactions;
    public List<SyncItem> getSyncTransactions(){return SyncTransactions;}

    public void setSyncTransactions(List<SyncItem> syncTransactions) {
        SyncTransactions = syncTransactions;
    }
    public SyncTransactions(List<SyncItem>SyncTransactions){this.SyncTransactions = SyncTransactions;}

    public class SyncItem {
        private String expenses_id;
        public String description_exp;
        public String amount_exp;


        public String getAmount_exp() {
            return amount_exp;
        }

        public void setAmount_exp(String amount_exp) {
            this.amount_exp = amount_exp;
        }

        public String getDescription_exp() {
            return description_exp;
        }

        public void setDescription_exp(String description_exp) {
            this.description_exp = description_exp;
        }

        public String getExpenses_id() {
            return expenses_id;
        }

        public void setExpenses_id(String expenses_id) {
            this.expenses_id = expenses_id;
        }
    }
}

