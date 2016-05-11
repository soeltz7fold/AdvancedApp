package x7a.droid.advancedapp.models;

/**
 * Created by DroiD on 20/04/2016.
 */
public class User {
    private int id;
    private String email;
    private String password;
    private String token_authentication;

    public User(String email, String password, String token_authentication){
    this.email = email;
    this.password = password;
    this.token_authentication = token_authentication;
    }


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
