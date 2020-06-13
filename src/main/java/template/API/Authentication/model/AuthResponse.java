package template.API.Authentication.model;

import lombok.Data;
import template.API.User.model.User;

@Data
public class AuthResponse {

    private User user;
    private String jwt;

    public AuthResponse(){}
    public AuthResponse(User user, String jwt){
        this.user = user;
        this.jwt = jwt;
    }

}
