package controllers;

import com.ashutosh.springsecurity.models.ApiResponse;
import com.ashutosh.springsecurity.models.LoginModel;
import com.ashutosh.springsecurity.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public ApiResponse saveUser(@RequestBody LoginModel loginModel) throws Exception{
        return new ApiResponse(true, loginService.doLogin(loginModel));
    }

}
