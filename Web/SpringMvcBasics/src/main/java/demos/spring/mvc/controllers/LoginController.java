package demos.spring.mvc.controllers;

import demos.spring.mvc.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
@RequestMapping("/demos")
public class LoginController {

    @RequestMapping(value = "/login1.htm", method = GET)
    public @ResponseBody
    String demo1() {
        System.out.println("Demo 1 method called");
        return "<h2>You are now logged in...</h2>";
    }

    @RequestMapping(value = "/login2.htm", method = GET)
    public @ResponseBody
    String demo2(@RequestParam("username") String username,
                 @RequestParam("password") String password) {
        System.out.println("Demo 2 method called");
        return "<h2>Welcome back: " + username + " with password " + password + "</h2>";
    }

    @RequestMapping(value = "/login3.htm", method = GET)
    public void demo3(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Demo 3 method called");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        response.setContentType("text/html");
        PrintWriter writer;
        try {
            writer = response.getWriter();
            writer.println("<h2>Welcome back: " + username + " with password " + password + "</h2>");
        } catch (IOException ex) {
            throw new IllegalStateException("IO Failure: " + ex);
        }
    }

    @RequestMapping(value = "/login4.htm", method = GET)
    public @ResponseBody
    String demo4(User theUser) {
        System.out.println("Demo 4 method called");
        return "<h2>Welcome back: " + theUser.getUsername() + " with password " + theUser.getPassword() + "</h2>";
    }

    @RequestMapping(value = "/login5.htm", method = POST)
    public @ResponseBody
    String demo5(User theUser) {
        System.out.println("Demo 5 method called");
        return "<h2>Welcome back: " + theUser.getUsername() + " with password " + theUser.getPassword() + "</h2>";
    }

    @RequestMapping(value = "/{username}/{password}/login6.htm", method = GET)
    public @ResponseBody
    String demo6(@PathVariable("username") String username, @PathVariable("password") String password) {
        System.out.println("Demo 6 method called");
        return "<h2>Welcome back: " + username + " with password " + password + "</h2>";
    }

    @RequestMapping(value = "/login7.htm", method = GET)
    public @ResponseBody
    String demo7(@RequestHeader("Accept") String acceptHeader) {
        System.out.println("Demo 7 method called");
        return "<h2>Value of accept header is: <span style='color:blue'>" + acceptHeader + "</span></h2>";
    }

    @RequestMapping(value = "/login8.htm", method = GET)
    public @ResponseBody
    String demo8(@CookieValue("JSESSIONID") String sessionCookie) {
        System.out.println("Demo 8 method called");
        return "<h2>Value of session ID cookie is: <span style='color:blue'>" + sessionCookie + "</span></h2>";
    }

    @RequestMapping(value = "/login9.htm", method = POST)
    public @ResponseBody
    String demo9(@RequestBody String theBody) {
        System.out.println("Demo 9 method called");
        return "<h2>Value of body is: <span style='color:blue'>" + theBody + "</span></h2>";
    }

    @RequestMapping(value = "/login10.htm", method = POST)
    public String demo10(User theUser, Model model) {
        System.out.println("Demo 10 method called");
        if (theUser.getUsername().equalsIgnoreCase("dave")) {
            String message = String.format("Welcome back %s", theUser.getUsername());
            model.addAttribute("msg", message);
            return "loginSuccess";
        }
        model.addAttribute("msg", "Unknown username or password");
        return "loginFailure";
    }
}
