package userLogin;

import java.util.HashMap;

public class AccountManager {
    private HashMap<String, String> accounts;

    public AccountManager(){
        accounts = new HashMap<>();
        accounts.put("Patrick", "1234");
        accounts.put("Molly", "FloPup");
    }

    public boolean accountExists(String username){
        return accounts.containsKey(username);
    }

    public boolean isPassword(String username, String password){
        if(accounts.containsKey(username)) {
            return password.equals(accounts.get(username));
        }
        return false;
    }

    public boolean createAccount(String username, String password){
        if(accountExists(username)){
            return false;
        }
        accounts.put(username, password);
        return true;
    }
}
