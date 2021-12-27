package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.model.User;
import web.service.RestService;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    @Autowired
    private RestService restService;

    @GetMapping("/users")
    public void getUsers() {
        System.out.println(restService.getUsers());

        String first = restService.addUser(new User(3L, "James", "Brown", (byte) 25));
        String second = restService.editUser(new User(3L, "Thomas", "Shelby", (byte) 26));
        String third = restService.deleteUser();

        System.out.println(first + second + third);
    }
}
