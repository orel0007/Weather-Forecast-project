package main.controllers;

import javax.annotation.Resource;
import javax.validation.Valid;

import main.beans.Client;
import main.repo.Massage;
import main.repo.User;
import main.repo.MassageRepository;
import main.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {
    /* we need it so  inject the User repo bean */
    @Autowired
    private UserRepository repository;
    private UserRepository getRepo() {
        return repository;
    }

    /*Massages repository*/
    @Autowired
    private MassageRepository msgRepository;
    private MassageRepository getRepoMsg() { return msgRepository; }

    /*ClientBean singleton for hold data per each user */
    @Resource(name = "ClientBean")
    public Client clientBean;


    @GetMapping("/readme")
    public String goReadMe() {
        return "readme";
    }

    /**
     * check if user alrready login and insert this page- logout, otherwise go login page.
     * @param user
     * @param model
     * @return
     */
    @GetMapping("/")
    public String main(User user, Model model) {
        if(clientBean.getLogin())
            return "redirect:/loggedIn/logout";
        return "login";
    }

    /**
     * This function: add user to userList, if had problem in the connecting move to page error,
     *      userName taken- back to login, otherwise add user to the list and move to chatRoom.
     * @param user
     * @param result
     * @param model
     * @return page
     */
    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "error";
        }
        if(getRepo().findByUserName(user.getUserName()) != null){
            model.addAttribute("error", "error-there is have user with that name already");
            return "login";
        }
        clientBean.setData(user.getUserName());
        user.setTime();
        getRepo().save(user);
        return "redirect:/loggedIn/chat";

    }

    /**
     * This function: add models and move to the chatRoom
     * @param model
     * @return chatRoom page
     */
    @GetMapping("/loggedIn/chat")
    public String moveChat(Model model) {
        model.addAttribute("users", getRepo().findAll());
        model.addAttribute("currUser",  clientBean.getUserName());
        return "chatRoom";
    }

    /**
     * This function: add massages to the dataBase sql
     * @param msg
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/loggedIn/addMsg")
    public String addMsg(@Valid Massage msg, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "error";
        }
        msg.setUserName(clientBean.getUserName());
        getRepoMsg().save(msg);
        model.addAttribute("users", getRepo().findAll());
        model.addAttribute("currUser",  clientBean.getUserName());
        return "chatRoom";
    }

    /**
     * setTimeStamp of the user
     */
    @GetMapping(value="/loggedIn/setTimeSet")
    public @ResponseBody
    void setTimers() {
        updateUserStampTime();
    }

    /**
     * @return user list json
     */
    @GetMapping(value="/loggedIn/getJsonUsers")
    public @ResponseBody
    List<User> getAllUsers() {
        return getRepo().findAll();
    }

    /**
     * update if user disconnected
     * @return massages list json
     */
    @GetMapping(value="/loggedIn/getJsonMassages")
    public @ResponseBody
    List<Massage> getAllMsg() {
        checkUsers();
        return getRepoMsg().findTop5ByOrderByIdDesc();
    }

    /**
     * check if user unconnected.
     */
    public synchronized void checkUsers() {
        updateUserStampTime();
        check();
    }

    /**
     * update Timestamp to  user sql TimeStamp
     */
    private void updateUserStampTime(){
        User u = new User();
        u = getRepo().findByUserName(clientBean.getUserName());
        u.setTime();
        getRepo().save(u);
    }


    /**
     * logout and disconnect from user lists
     * @return to login page
     */
    @GetMapping("/loggedIn/logout")
    public String handleLogout() {
        clientBean.setLogin(false);
        getRepo().deleteById(getRepo().findByUserName(clientBean.getUserName()).getId());
        return "redirect:/";
    }

    /*** @return go to searchInChat page*/
    @GetMapping("/loggedIn/search")
    public String search() {
            return "searchInChat";
    }

    /**
     * This function: check all users if they connect to the server in the last 10 secends, otherwise delete the user
     * from User sql list
     */
    public void check() {
        List<User> e = getRepo().findAll();
        for(int i = 0; i<e.size(); i++) {
            if (System.currentTimeMillis() - getRepo().findByUserName(e.get(i).getUserName()).getTime() > 10000) {
                System.out.println("delete "+e.get(i).getUserName());
                getRepo().deleteById(getRepo().findByUserName(e.get(i).getUserName()).getId());
            }
        }
    }

    /**
     *This function: search userName massages
     * @return json with the requested userName massages
     */
    @GetMapping (value = "/loggedIn/searchUserMsg/{userName}")//change to search for the given user insert
    public @ResponseBody
    List<Massage> getUserMsg(@PathVariable("userName") String userName) {
        List<Massage> e = getRepoMsg().findByUserName(userName);
        return e;
    }

    /***
     * This function: search substring inside massages
     * @return json of massages that contains requsted substring
     */
    @GetMapping (value = "/loggedIn/searchMsgContain/{str}")
    public @ResponseBody
    List<Massage> getSearchMsg(@PathVariable("str") String str) {
        List<Massage> e = getRepoMsg().findAll();
        ArrayList<Massage> d = new ArrayList<Massage>();;
        for(int i=0;i<e.size();i++){
            if(e.get(i).getMsg().contains(str))
                d.add(e.get(i));
        }
        return d;
    }
}

