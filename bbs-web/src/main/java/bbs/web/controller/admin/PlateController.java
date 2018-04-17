package bbs.web.controller.admin;

import bbs.core.data.Plate;
import bbs.core.persist.service.PlateService;
import bbs.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin/plates")
public class PlateController extends BaseController{

    @Autowired
    private PlateService plateService;

    @GetMapping("/list")
    public String list(){
        return "/admin/plates/list";
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<Plate> findPlates(){
        List<Plate> plates = plateService.findAll();
        return plates;
    }

    @GetMapping("/add")
    public String add(){
        return "/admin/plates/view";
    }

    @GetMapping("/add/pid/{id}")
    public String addPlate(@PathVariable(required = false) Long id, ModelMap model){
        Plate plate = plateService.findOne(id);
        model.put("plate",plate);
        return "/admin/plates/view";
    }


}
