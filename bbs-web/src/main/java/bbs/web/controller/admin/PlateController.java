package bbs.web.controller.admin;

import bbs.core.data.Plate;
import bbs.core.data.User;
import bbs.core.persist.entity.UserPO;
import bbs.core.persist.service.PlateService;
import bbs.core.persist.service.UserService;
import bbs.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/plates")
public class PlateController extends BaseController {

    @Autowired
    private PlateService plateService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String list() {
        return "/admin/plates/list";
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<Plate> findPlates() {
        List<Plate> plates = plateService.findAll();
        return plates;
    }

    @GetMapping(value = {"/add/pid/{id}", "/add"})
    public String addPlate(@PathVariable(required = false) Long id, ModelMap model) {
        if (id != null) {
            Plate plate = plateService.findOne(id);
            model.put("plate", plate);
        }
        List<User> users = userService.findAllUsers();
        model.put("users", users);
        return "/admin/plates/view";
    }

    @GetMapping("/edit/{id}")
    public String editPlate(@PathVariable(required = false) Long id, ModelMap model) {
        Plate plate = plateService.findOne(id);
        List<User> users = userService.findAllUsers();
        model.put("plate", plate);
        model.put("users", users);
        return "/admin/plates/edit";
    }

    @PostMapping("/save")
    public String save(Plate plate) {
        Long managerId = plate.getManagerId();
        UserPO userPO = userService.findUserById(managerId);
        plateService.save(plate, userPO);
        return "redirect:/admin/plates/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(Long id) {
        plateService.delete(id);
        return "1";
    }

}
