package pl.ct8.rasztabiga.models;

import pl.ct8.rasztabiga.utils.SecurityUtils;

import java.util.List;

public class User {

    private int id;
    private String email;
    private String apiKey;
    private String name;
    private String surname;
    private List<SecurityUtils.Role> roleList;

    public User(int id, String email, String apiKey, String name, String surname, List<SecurityUtils.Role> roleList) {
        this.id = id;
        this.email = email;
        this.apiKey = apiKey;
        this.name = name;
        this.surname = surname;
        this.roleList = roleList;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public List<SecurityUtils.Role> getRoleList() {
        return roleList;
    }
}
