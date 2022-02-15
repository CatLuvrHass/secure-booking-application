package app.controller;

import app.repository.UserRepository;
import app.model.User;
import app.exception.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("")
    public String showIndexPage() {
        return "index";
    }

    @GetMapping("/register")
    public String startRegistration(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register_attempt")
    public String registerAttempt(@ModelAttribute("user") User newUser) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
//        newUser.setPassword(encodedPassword);
        System.out.println("Saving user ");
        System.out.println("Name " + newUser.getName());
        System.out.println("Last Name " + newUser.getSurname());
        System.out.println("PPSN " +newUser.getPpsn());
        System.out.println("DOB " +newUser.getDob());
        System.out.println("email " +newUser.getEmail());
        System.out.println("address " +newUser.getAddress());
        System.out.println("encrypted password " +newUser.getPassword());
        System.out.println("Phone num " +newUser.getPhone());
        userRepository.save(newUser);
        System.out.println("User saved");
        return "registered_successfully";
    }

    // Test connection to frontend
    @RequestMapping("/welcome")
    public String welcome(){
        return "Welcome!";
    }
  /*https://www.codejava.net/frameworks/spring-boot/user-registration-and-login-tutorial*/
    @RequestMapping("/login")
    public String login(){
        // Take username and password
        // Check against database
        // Login status= true
        // Redirect to my details page
        return "Welcome!";
    }

    @RequestMapping("/logout")
    public String logout(){
        // Login status= false
        // Redirect to home page
        return "Welcome!";
    }

    // Get All users
    /* Convert this to Admin only w frontend view OR delete before submission */
    @GetMapping("/showAll")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/createNew")
    public User newUser(@Valid @RequestBody User newUser)  {
        // Error check if user already exists - column unique now in model

        return userRepository.save(newUser);
    }

    // Get a Single User
    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") Long userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
