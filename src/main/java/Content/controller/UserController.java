package Content.controller;

import Content.entity.Role;
import Content.entity.User;
import Content.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    final private UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/addUser")
    public String userForm(){
        return "addUser";
    }

    @PostMapping("/addUser")
    public String addUser(User user, Model model){
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null){
            model.addAttribute("message", "This username has already existed.");
            return "addUser";
        }

        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        userRepository.save(user);
        return "redirect:/userList";
    }

    @GetMapping("/userList")
    public String userList(Model model){
        Iterable<User> userList = userRepository.findAll();
        model.addAttribute("userList", userList);
        return "userList";
    }

    @GetMapping("/user/{user}")
    public String editUser(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "editUser";
    }

    @PostMapping("/user")
    public String saveUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Map<String, String> form,
            @RequestParam("id") User user){

        user.setUsername(username);
        user.setPassword(password);
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()){
            if (roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
        return "redirect:/userList";
    }

}
