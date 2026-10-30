package com.example.employeemanager.controller;

import com.example.employeemanager.model.Employee;
import com.example.employeemanager.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Show
    @GetMapping
    public String listEmployees(Model model, @RequestParam(required = false) String keyword) {
        model.addAttribute("listEmployees", employeeService.getAllEmployees(keyword));
        model.addAttribute("employee", new Employee());
        model.addAttribute("keyword", keyword);
        return "employees";
    }

    //  (Create)
    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listEmployees", employeeService.getAllEmployees(null));
            return "employees";
        }
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    // (Edit form)
    @GetMapping("/edit/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "edit-employee";
    }

    // (Update)
    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") Long id, @Valid @ModelAttribute("employee") Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            employee.setId(id);
            return "edit-employee";
        }
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    // (Delete)
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable(value = "id") Long id) {
        this.employeeService.deleteEmployeeById(id);
        return "redirect:/employees";
    }
}