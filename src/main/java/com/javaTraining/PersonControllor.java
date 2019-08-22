package com.javaTraining;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class PersonControllor {

    @Autowired
    private PersonDao personDao;

    @RequestMapping(value="/",method = RequestMethod.GET)
    public ModelAndView listPerson(ModelAndView model) throws IOException {
        List<Person> listPerson = personDao.list();
        model.addObject("listPerson", listPerson);
        model.setViewName("home");

        return model;
    }

    @RequestMapping(value = "/newContact", method = RequestMethod.GET)
    public ModelAndView newPerson(ModelAndView modelAndView){
        Person newperson = new Person();
        modelAndView.addObject("person", newperson);
        modelAndView.setViewName("PersonForm");
        return modelAndView;
    }

    @RequestMapping(value = "/savePerson", method = RequestMethod.POST)
    public ModelAndView savePerson(@ModelAttribute Person person) {
        personDao.saveOrUpdate(person);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/deletePerson", method = RequestMethod.GET)
    public ModelAndView deletePerson(HttpServletRequest request) {
        int personId = Integer.parseInt(request.getParameter("id"));
        personDao.delete(personId);
        return new ModelAndView("redirect:/");
    }


}
