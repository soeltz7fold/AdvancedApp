package x7a.droid.advancedapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DroiD on 20/04/2016.
 */
public class Users {
    @SerializedName("users")
    public List<UserItem> users;
    public List<UserItem> getUsers(){
        return users;
    }

    public void setUsers(List<UserItem> users) {
        this.users = users;
    }
    public Users(List<UserItem>users){
        this.users = users;
    }

    public class UserItem{
        private int id;
        public String email;
        public String password;
        public String token_authentication;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getToken_authentication() {
            return token_authentication;
        }

        public void setToken_authentication(String token_authentication) {
            this.token_authentication = token_authentication;
        }
    }

}
