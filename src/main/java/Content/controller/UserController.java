package Content.controller;

import Content.Service.UserValidator;
import Content.entity.Role;
import Content.entity.User;
import Content.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
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
        return "user/addUser";
    }

    @PostMapping("/addUser")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model){

        if (user.getUsername() != null && user.getPassword() != null && !user.getPassword().equals(user.getVerPassword())){
            model.addAttribute("passwordError", "Пароли не совпадают.");
            return "/user/addUser";
        }
        if (bindingResult.hasErrors()){
            Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                    fieldError -> fieldError.getField() + "Error",
                    FieldError::getDefaultMessage
            );
            Map<String, String> errorsMap = bindingResult.getFieldErrors().stream().collect(collector);
            model.mergeAttributes(errorsMap);
            model.addAttribute("user", user);
            return "/user/addUser";
        }else {



            User userFromDb = userRepository.findByUsername(user.getUsername());
            if (userFromDb != null) {
                model.addAttribute("message", "This username has already existed.");
                return "/user/addUser";
            }

            user.setRoles(Collections.singleton(Role.USER));
            user.setActive(true);
            userRepository.save(user);
        }
        return "redirect:/user/userList";
    }

    @GetMapping("/user/userList")
    public String userList(Model model){
        Iterable<User> userList = userRepository.findAll();
        model.addAttribute("userList", userList);
        return "/user/userList";
    }

    @GetMapping("/user/{user}")
    public String editUser(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "/user/editUser";
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
        return "redirect:/user/userList";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") User user){
        userRepository.delete(user);
        return "redirect:/user/userList";
    }

}
