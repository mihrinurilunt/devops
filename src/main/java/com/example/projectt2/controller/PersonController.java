package com.example.projectt2.controller;

import com.example.projectt2.entity.Person;
import com.example.projectt2.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class PersonController {
    @Autowired
    private PersonService service;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/person_register")
    public String personRegister() {
        return "personRegister";
    }

    @GetMapping("/person")
    public ModelAndView getAllPerson() {
        List<Person> list = service.getAllPerson();
        ModelAndView mav = new ModelAndView("allPerson");
        mav.addObject("person", list);
        mav.addObject("timestamp", System.currentTimeMillis()); // Eklenen timestamp, cache kontrolü için
        return mav;
    }

    @PostMapping("/save")
    public String addPerson(@RequestParam("file") MultipartFile file,
                            @RequestParam("name") String name,
                            @RequestParam("address") String address,
                            Person p, Model model) {
        p.setName(name);
        p.setAddress(address);
        try {
            service.save(p, file);
            return "redirect:/person"; // Başarılı olduğunda kişilerin listesine yönlendir.
        } catch (IOException e) {
            model.addAttribute("error", "Kişi eklenirken bir hata oluştu: " + e.getMessage());
            return "personRegister"; // Hata durumunda aynı sayfaya geri dön, hata mesajı ile birlikte.
        }
    }

    @RequestMapping("/deletePerson/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        service.deleteById(id);
        return "redirect:/person";
    }

    @RequestMapping("/editPerson/{id}")
    public String editPerson(@PathVariable("id") int id, Model model) {
        Person b = service.getPersonById(id);
        model.addAttribute("person", b);
        return "personEdit";
    }
}
