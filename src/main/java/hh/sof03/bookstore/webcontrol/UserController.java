package hh.sof03.bookstore.webcontrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hh.sof03.bookstore.domain.SignupForm;
import hh.sof03.bookstore.domain.User;
import hh.sof03.bookstore.domain.UserRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/webcontrol")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    // Shows login page
    @RequestMapping(value="/login")
    public String login() {	
        return "login";
    }


    // Shows new signup page
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String addUser(Model model) {
        model.addAttribute("signupform", new SignupForm());
        return "signup";
    }


    // Saves user signup if all requirements are met
@RequestMapping(value = "/saveuser", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()) {
            if(signupForm.getPassword().equals(signupForm.getPasswordCheck())) {
                String pwd = signupForm.getPassword();
                BCryptPasswordEncoder cryptEncoder = new BCryptPasswordEncoder();
                String hashPwd = cryptEncoder.encode(pwd);
            
                User newUser = new User();
                newUser.setPasswordHash(hashPwd);
                newUser.setUsername(signupForm.getUsername());
                newUser.setEmail(signupForm.getEmail());
                newUser.setRole("USER");

                if(userRepository.findByEmail(signupForm.getEmail()) == null) {

                if(userRepository.findByUsername(signupForm.getUsername()) == null) {
                    userRepository.save(newUser);
                }
                else {
                    bindingResult.rejectValue("username", "err.username", "Username is already in use.");
                    return "signup";
                } 
                } else {
                    bindingResult.rejectValue("email", "err.email", "Email is already in use.");
                    return "signup";
                }
            } 
            else {
                bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords do not match.");
                return "signup";
            }
        }
        else {
            return "signup";
        }
        return "redirect:/login";        
    }

}
