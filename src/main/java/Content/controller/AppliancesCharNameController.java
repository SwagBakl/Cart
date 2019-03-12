package Content.controller;

import Content.entity.AppliancesCharName;
import Content.repository.AppCharsNameRepository;
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
public class AppliancesCharNameController {

    @Value("${upload.path}")
    private String uploadPath;

    final private AppCharsNameRepository appCharsRepo;

    public AppliancesCharNameController(AppCharsNameRepository appCharsRepo){
        this.appCharsRepo = appCharsRepo;
    }

    @GetMapping("/appchars/appCharNameList")
    public String electCharsList(@RequestParam(required = false, defaultValue = "") String filter, Model model){
        List<AppliancesCharName> appCharName;
        if(filter != null && !filter.isEmpty()){
            appCharName = appCharsRepo.findBycharName(filter);

        }else {
            appCharName = appCharsRepo.findAll();
        }
        model.addAttribute("appCharName", appCharName);
        model.addAttribute("filter", filter);
        return "/appchars/appCharNameList";
    }


    @PostMapping("/appchars/appCharNameList")
    public String addTab(
            @Valid AppliancesCharName appCharName,
            BindingResult bindingResult,
            Model model) throws Exception{
        if (bindingResult.hasErrors()){
            Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                    fieldError -> fieldError.getField() + "Error",
                    FieldError::getDefaultMessage
            );
            Map<String, String> errorsMap = bindingResult.getFieldErrors().stream().collect(collector);
            model.mergeAttributes(errorsMap);
            return "/appchars/addAppNameChar";
        }
        appCharsRepo.save(appCharName);
        List<AppliancesCharName> appCharNameList = appCharsRepo.findAll();
        model.addAttribute("appCharNameList", appCharNameList);
        return "redirect:/appchars/appCharNameList";
    }

    @GetMapping("/appchars/addAppNameChar")
    public String addTab(Model model){
        return "appchars/addAppNameChar";
    }

    @GetMapping("/appchars/{appCharName}")
    public String editTab(@PathVariable AppliancesCharName appCharName, Model model){
        model.addAttribute("appCharName", appCharName);
        List<AppliancesCharName> appCharNames = appCharsRepo.findAll();
        model.addAttribute("appCharNames", appCharNames);
        return "/appchars/editAppNameChar";
    }

    @PostMapping("/appChar/save")
    public String savePc(
            //@RequestParam String charName,
            @Valid AppliancesCharName appCharName, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()){
            Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                    fieldError -> fieldError.getField() + "Error",
                    FieldError::getDefaultMessage
            );
            Map<String, String> errorsMap = bindingResult.getFieldErrors().stream().collect(collector);
            model.mergeAttributes(errorsMap);
            return "/appchars/editAppNameChar";
        }
        appCharName.setCharName(appCharName.getCharName());

        appCharsRepo.save(appCharName);

        return "redirect:/appchars/appCharNameList";
    }

    @GetMapping("/appchars/delete/{id}")
    public String deletePc(@PathVariable("id") AppliancesCharName appCharName){
        appCharsRepo.delete(appCharName);
        return "redirect:/appchars/appCharNameList";
    }

}
