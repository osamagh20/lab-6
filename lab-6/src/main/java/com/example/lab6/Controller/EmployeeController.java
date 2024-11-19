package com.example.lab6.Controller;

import com.example.lab6.Model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    ArrayList<Employee> employees = new ArrayList<>();

    @GetMapping("/get")
    public ResponseEntity getEmployees() {
        return ResponseEntity.status(200).body(employees);
    }

    @PostMapping("/add")
    public ResponseEntity addEmployee(@RequestBody @Valid Employee employee, Errors errors) {
        if(errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        employees.add(employee);
        return ResponseEntity.status(200).body("Employee added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateEmployee(@PathVariable String id,@RequestBody @Valid Employee employee, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getID().equals(id)) {
                employees.set(i, employee);
            }
        }
        return ResponseEntity.status(200).body("Employee updated successfully");

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEmployee(@PathVariable String id) {
        for (int i = 0; i < employees.size(); i++) {
            if(employees.get(i).getID().equals(id)) {
                employees.remove(i);
            }
        }
        return ResponseEntity.status(200).body("employee deleted successfully");
    }

    @GetMapping("/get-by-position/{position}")
    public ResponseEntity searchByPosition(@PathVariable String position) {
        ArrayList<Employee> positions = new ArrayList<>();
        if(position.equalsIgnoreCase("supervisor") || position.equalsIgnoreCase("coordinator")) {
            for (int i = 0; i < employees.size(); i++) {
                if(employees.get(i).getPosition().equals(position)) {
                    positions.add(employees.get(i));
                }
            }
        }

        return ResponseEntity.status(200).body(positions);
    }

    @GetMapping("/search-by-age-range/{minAge}/{maxAge}")
    public ResponseEntity searchByAgeRange(@PathVariable @Valid int minAge, @PathVariable @Valid int maxAge) {
        ArrayList<Employee> ages = new ArrayList<>();
        for (int i = 0; i < employees.size(); i++) {
            if(employees.get(i).getAge() >= minAge && employees.get(i).getAge() <= maxAge) {
                ages.add(employees.get(i));
            }
        }
        return ResponseEntity.status(200).body(ages);
    }

    @GetMapping("/apply-annual-leave/{id}")
    public ResponseEntity applyAnnualLeave(@PathVariable String id) {

        for (int i = 0; i < employees.size(); i++) {
            if(employees.get(i).getID().equals(id) && employees.get(i).getOnLeave().equals(false)) {
                employees.get(i).setOnLeave(true);
                if(employees.get(i).getAnnualLeave() >= 1) {
                    employees.get(i).setAnnualLeave(employees.get(i).getAnnualLeave()-1);
                }
            }
        }
        return ResponseEntity.status(200).body("Employee applied annual leave successfully");
    }

    @GetMapping("/get-no-annual-leave")
    public ResponseEntity listOfNoEmployeesLeave() {
        ArrayList<Employee> noEmployeesLeave = new ArrayList<>();
        for (int i = 0; i < employees.size(); i++) {
            if(employees.get(i).getOnLeave().equals(false)) {
                noEmployeesLeave.add(employees.get(i));
            }
        }
        return ResponseEntity.status(200).body(noEmployeesLeave);
    }

    @PutMapping("/promote-employee/{id}")
    public ResponseEntity promoteEmployee(@PathVariable String id,@RequestBody @Valid Employee employee, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        for (int i = 0; i < employees.size(); i++) {
            if(employees.get(i).getID().equals(id)) {
                if(employee.getPosition().equals("supervisor")){
                    if(employees.get(i).getAge()>=30 && employee.getAge() >=30 ) {
                        if(employees.get(i).getOnLeave().equals(false)) {
                            employees.get(i).setPosition("supervisor");
                        }

                    }
                }


            }
        }
        return ResponseEntity.status(200).body("Employee promoted successfully");
    }

}
