package Content.controller;

import Content.entity.ElectronicCharactsName;
import Content.repository.ElectronicCharsNameRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class ElectronicCharsNameController {

    @Value("${upload.path}")
    private String uploadPath;

    final private ElectronicCharsNameRepository electronicCharsRepo;

    public ElectronicCharsNameController(ElectronicCharsNameRepository electronicCharsRepo){
        this.electronicCharsRepo = electronicCharsRepo;
    }

    @GetMapping("/elchars/elCharNameList")
    public String electCharsList(@RequestParam(required = false, defaultValue = "") String filter, Model model){
        List<ElectronicCharactsName> elCharName;
        if(filter != null && !filter.isEmpty()){
            elCharName = electronicCharsRepo.findBycharName(filter);

        }else {
            elCharName = electronicCharsRepo.findAll();
        }
        model.addAttribute("elCharName", elCharName);
        model.addAttribute("filter", filter);
        return "/elchars/elCharNameList";
    }


    @PostMapping("/elchars/elCharNameList")
    public String addTab(
                         @Valid ElectronicCharactsName elCharName,
                         BindingResult bindingResult,
                         Model model) throws Exception{
        if (bindingResult.hasErrors()){
            Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                    fieldError -> fieldError.getField() + "Error",
                    FieldError::getDefaultMessage
            );
            Map<String, String> errorsMap = bindingResult.getFieldErrors().stream().collect(collector);
            model.mergeAttributes(errorsMap);
            return "/elchars/addElNameChar";
        }
        electronicCharsRepo.save(elCharName);
        List<ElectronicCharactsName> elCharNameList = electronicCharsRepo.findAll();
        model.addAttribute("elCharNameList", elCharNameList);
        return "redirect:/elchars/elCharNameList";
    }

    @GetMapping("elchars/addElNameChar")
    public String addTab(Model model){
        return "elchars/addElNameChar";
    }

    @GetMapping("/chars/{elCharName}")
    public String editTab(@PathVariable ElectronicCharactsName elCharName, Model model){
        model.addAttribute("elCharName", elCharName);
        List<ElectronicCharactsName> elCharNames = electronicCharsRepo.findAll();
        model.addAttribute("elCharNames", elCharNames);
        return "/elchars/editElNameChar";
    }

    @PostMapping("/elChar/save")
    public String savePc(
            //@RequestParam String charName,
            @Valid ElectronicCharactsName elCharName, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()){
            Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                    fieldError -> fieldError.getField() + "Error",
                    FieldError::getDefaultMessage
            );
            Map<String, String> errorsMap = bindingResult.getFieldErrors().stream().collect(collector);
            model.mergeAttributes(errorsMap);
            return "/elchars/editElNameChar";
        }
        elCharName.setCharName(elCharName.getCharName());

        electronicCharsRepo.save(elCharName);

        return "redirect:/elchars/elCharNameList";
    }

    @GetMapping("/chars/delete/{id}")
    public String deletePc(@PathVariable("id") ElectronicCharactsName elCharName){
        electronicCharsRepo.delete(elCharName);
        return "redirect:/elchars/elCharNameList";
    }

}
