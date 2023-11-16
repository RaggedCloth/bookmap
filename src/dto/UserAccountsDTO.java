package dto;

import java.io.Serializable;
import java.util.ArrayList;

import entity.UserAccountsBean;

public class UserAccountsDTO implements Serializable{
    private ArrayList<UserAccountsBean> userList;
    
    public UserAccountsDTO() {
        userList = new ArrayList<UserAccountsBean>();
    }

    public void add(UserAccountsBean ub) {
        userList.add(ub);
    }

    public UserAccountsBean get(int i) {
        return userList.get(i);
    }

    public int size() {
        return userList.size();
    }
}
