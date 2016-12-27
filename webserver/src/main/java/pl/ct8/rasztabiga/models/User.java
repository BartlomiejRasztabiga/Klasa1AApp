package pl.ct8.rasztabiga.models;

import pl.ct8.rasztabiga.utils.SecurityUtils;

public class User {

    private int id;
    private String email;
    private String apiKey;
    private String name;
    private String surname;
    private SecurityUtils.Role role;

    public User(int id, String email, String apiKey, String name, String surname, String role) {
        this.id = id;
        this.email = email;
        this.apiKey = apiKey;
        this.name = name;
        this.surname = surname;
        switch (role) {
            case "user":
                this.role = SecurityUtils.Role.USER;
                break;
            case "admin":
                this.role = SecurityUtils.Role.ADMIN;
                break;
            default:
                this.role = null;
                break;
        }
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

    public SecurityUtils.Role getRole() {
        return role;
    }
}
