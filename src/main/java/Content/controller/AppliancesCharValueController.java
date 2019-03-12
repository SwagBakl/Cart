package Content.controller;

import Content.entity.AppliancesCharValue;
import Content.repository.AppCharsValueRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class AppliancesCharValueController {

    @Value("${upload.path}")
    private String uploadPath;

    final private AppCharsValueRepository appCharsRepo;

    public AppliancesCharValueController(AppCharsValueRepository appCharsRepo){
        this.appCharsRepo = appCharsRepo;
    }

    @GetMapping("/appchars/appCharValueList")
    public String electCharsList(@RequestParam(required = false, defaultValue = "") String filter, Model model){
        List<AppliancesCharValue> appCharValue;
        if(filter != null && !filter.isEmpty()){
            appCharValue = appCharsRepo.findBycharValue(filter);

        }else {
            appCharValue = appCharsRepo.findAll();
        }
        model.addAttribute("appCharValue", appCharValue);
        model.addAttribute("filter", filter);
        return "/appchars/appCharValueList";
    }


    @PostMapping("/appchars/appCharValueList")
    public String addTab(
            @Valid AppliancesCharValue appCharValue,
            BindingResult bindingResult,
            Model model) throws Exception{
        if (bindingResult.hasErrors()){
            Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                    fieldError -> fieldError.getField() + "Error",
                    FieldError::getDefaultMessage
            );
            Map<String, String> errorsMap = bindingResult.getFieldErrors().stream().collect(collector);
            model.mergeAttributes(errorsMap);
            return "/appchars/addAppValueChar";
        }
        appCharsRepo.save(appCharValue);
        List<AppliancesCharValue> appCharValueList = appCharsRepo.findAll();
        model.addAttribute("appCharValueList", appCharValueList);
        return "redirect:/appchars/appCharValueList";
    }

    @GetMapping("appchars/addAppValueChar")
    public String addTab(Model model){
        return "appchars/addAppValueChar";
    }

    @GetMapping("/appcharsValue/{appCharValue}")
    public String editTab(@PathVariable AppliancesCharValue appCharValue, Model model){
        model.addAttribute("appCharValue", appCharValue);
        List<AppliancesCharValue> appCharValues = appCharsRepo.findAll();
        model.addAttribute("appCharValues", appCharValues);
        return "/appchars/editAppValueChar";
    }

    @PostMapping("/appCharValue/save")
    public String savePc(
            @RequestParam String charValue,
            @RequestParam("id") AppliancesCharValue appCharValue, Model model) throws IOException {
        appCharValue.setCharValue(charValue);

        appCharsRepo.save(appCharValue);

        return "redirect:/appchars/appCharValueList";
    }

    @GetMapping("/appCharsValue/delete/{id}")
    public String deletePc(@PathVariable("id") AppliancesCharValue appCharValue){
        appCharsRepo.delete(appCharValue);
        return "redirect:/appchars/appCharValueList";
    }

}
