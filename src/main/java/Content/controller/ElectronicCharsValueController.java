package Content.controller;

import Content.entity.ElectronicCharactsValue;
import Content.repository.ElectronicCharsValueRepository;
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
public class ElectronicCharsValueController {

    @Value("${upload.path}")
    private String uploadPath;

    final private ElectronicCharsValueRepository electronicCharsRepo;

    public ElectronicCharsValueController(ElectronicCharsValueRepository electronicCharsRepo){
        this.electronicCharsRepo = electronicCharsRepo;
    }

    @GetMapping("/elchars/elCharValueList")
    public String electCharsList(@RequestParam(required = false, defaultValue = "") String filter, Model model){
        List<ElectronicCharactsValue> elCharValue;
        if(filter != null && !filter.isEmpty()){
            elCharValue = electronicCharsRepo.findBycharValue(filter);

        }else {
            elCharValue = electronicCharsRepo.findAll();
        }
        model.addAttribute("elCharValue", elCharValue);
        model.addAttribute("filter", filter);
        return "/elchars/elCharValueList";
    }


    @PostMapping("/elchars/elCharValueList")
    public String addTab(
            @Valid ElectronicCharactsValue elCharValue,
            BindingResult bindingResult,
            Model model) throws Exception{
        if (bindingResult.hasErrors()){
            Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                    fieldError -> fieldError.getField() + "Error",
                    FieldError::getDefaultMessage
            );
            Map<String, String> errorsMap = bindingResult.getFieldErrors().stream().collect(collector);
            model.mergeAttributes(errorsMap);
            return "/elchars/addEIValueChar";
        }
        electronicCharsRepo.save(elCharValue);
        List<ElectronicCharactsValue> elCharValueList = electronicCharsRepo.findAll();
        model.addAttribute("elCharValueList", elCharValueList);
        return "redirect:/elchars/elCharValueList";
    }

    @GetMapping("elchars/addElValueChar")
    public String addTab(Model model){
        return "elchars/addEIValueChar";
    }

    @GetMapping("/charsValue/{elCharValue}")
    public String editTab(@PathVariable ElectronicCharactsValue elCharValue, Model model){
        model.addAttribute("elCharValue", elCharValue);
        List<ElectronicCharactsValue> elCharValues = electronicCharsRepo.findAll();
        model.addAttribute("elCharValues", elCharValues);
        return "/elchars/editEIValueChar";
    }

    @PostMapping("/elCharValue/save")
    public String savePc(
            @RequestParam String charValue,
            @RequestParam("id") ElectronicCharactsValue elCharValue, Model model) throws IOException {
        elCharValue.setCharValue(charValue);

        electronicCharsRepo.save(elCharValue);

        return "redirect:/elchars/elCharValueList";
    }

    @GetMapping("/elCharsValue/delete/{id}")
    public String deletePc(@PathVariable("id") ElectronicCharactsValue elCharValue){
        electronicCharsRepo.delete(elCharValue);
        return "redirect:/elchars/elCharValueList";
    }
}
