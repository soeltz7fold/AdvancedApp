package x7a.droid.advancedapp.models;

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
        private String id_exp;
        public String description_exp;
        public String amount_exp;
        public String responses;
        public String id_inc;
        public String descriptrion_inc;
        public String amount_inc;


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
            return id_exp;
        }
        public void setExpenses_id(String id_exp) {
            this.id_exp = id_exp;
        }
        public String getResponses() {
            return responses;
        }
        public void setResponses(String response) {
            this.responses = response;
        }
        public String getAmount_inc() {
            return amount_inc;
        }
        public void setAmount_inc(String amount_inc) {
            this.amount_inc = amount_inc;
        }
        public String getDescriptrion_inc() {
            return descriptrion_inc;
        }
        public void setDescriptrion_inc(String descriptrion_inc) {
            this.descriptrion_inc = descriptrion_inc;
        }
        public String getId_inc() {
            return id_inc;
        }
        public void setId_inc(String id_inc) {
            this.id_inc = id_inc;
        }
        public String getId_exp() {
            return id_exp;
        }
        public void setId_exp(String id_exp) {
            this.id_exp = id_exp;
        }
    }
}

