package com.zikozee.springboot.thymeleafdemo.controller;

import com.zikozee.springboot.thymeleafdemo.entity.Employee;
import com.zikozee.springboot.thymeleafdemo.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {

   private EmployeeService employeeService;

   //since we have one service autowired is optional , else we need specify Autowired(value= "theService")
   public EmployeeController(EmployeeService theEmployeeService){
       employeeService = theEmployeeService;
   }

    // add mapping for "/list
    @GetMapping("/")
    public String listEmployees(Model theModel){
       //get employees from database
       List<Employee> theEmployees = employeeService.findAll();

        // add to the spring model
        theModel.addAttribute("employees", theEmployees);

        return "employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel){

       //create model attribute to bind form data
        Employee theEmployee = new Employee();

        theModel.addAttribute("employee", theEmployee);

        return "employees/employee-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel){

        // get the employee from the service
        Employee theEmployee = employeeService.findById(theId);

        //set employee as a model attribute to pre-populate  the form
        theModel.addAttribute("employee", theEmployee);

        // send over to our form
        return "employees/employee-form";
    }

    @GetMapping("/delete")
    public String showFormForDelete(@RequestParam("employeeId") int theId){

        // delete the employee
        employeeService.deleteById(theId);

        // redirect to /employee/list
        return "redirect:/";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee){

       // save the Employee
        employeeService.save(theEmployee);

        // use a redirect to prevent duplicate submissions
        return "redirect:/";
    }

}
