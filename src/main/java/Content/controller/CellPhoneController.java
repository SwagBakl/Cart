package Content.controller;

import Content.entity.CellPhone;
import Content.repository.CellPhoneCharsRepository;
import Content.repository.CellPhoneRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
public class CellPhoneController {

    @Value("${upload.path}")
    private String uploadPath;

    final private CellPhoneRepository cpRepository;
    final private CellPhoneCharsRepository cpCharsRepository;

    public CellPhoneController(CellPhoneRepository cpRepository, CellPhoneCharsRepository cpCharsRepository) {
        this.cpRepository = cpRepository;
        this.cpCharsRepository = cpCharsRepository;
    }

    @GetMapping("/cphone/phoneList")
    public String pcList(@RequestParam(required = false, defaultValue = "")String filter, Model model,
                         @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable){
        Page<CellPhone> cpList;
        if(filter != null && !filter.isEmpty()){
            cpList = cpRepository.findByModel(filter, pageable);

        }else {
            cpList = cpRepository.findAll(pageable);
        }
        model.addAttribute("url", "/cphone/phoneList");
        model.addAttribute("cpList", cpList);
        model.addAttribute("filter", filter);
        return "/cphone/phoneList";
    }


    @PostMapping("/cphone/phoneList")
    public String addPhone(@Valid CellPhone cellPhone,
                        BindingResult bindingResult,
                        @RequestParam("file") MultipartFile file,
                        Model model) throws IOException {
        if (bindingResult.hasErrors() && file == null || file.getOriginalFilename().isEmpty()){
            Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                    fieldError -> fieldError.getField() + "Error",
                    FieldError::getDefaultMessage
            );
            Map<String, String> errorsMap = bindingResult.getFieldErrors().stream().collect(collector);
            model.mergeAttributes(errorsMap);
            model.addAttribute("cellPhone", cellPhone);
            return "/cphone/addCP";
        }else {

        if (file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            cellPhone.setFilename(resultFilename);
        }
        cpRepository.save(cellPhone);
        }
        List<CellPhone> cpList = cpRepository.findAll();
        model.addAttribute("cpList", cpList);
        return "redirect:/cphone/phoneList";
    }

    @GetMapping("/cphone/{cellPhone}")
    public String editCPhone(@PathVariable CellPhone cellPhone, Model model){
        model.addAttribute("cellPhone", cellPhone);
        return "cphone/editPhone";
    }

    @GetMapping("cphone/addCP")
    public String addTab(Model model){
        return "cphone/addCP";
    }

    @PostMapping("/cphone/save")
    public String saveCPhone(
            @RequestParam String name,
            @RequestParam Integer price,
            @RequestParam Integer quantity,
            @RequestParam String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam("id") CellPhone cellPhone) throws IOException {
        cellPhone.setModel(name);
        cellPhone.setPrice(price);
        cellPhone.setQuantity(quantity);
        cellPhone.setDescription(description);
        if (file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            cellPhone.setFilename(resultFilename);
        }
        cpRepository.save(cellPhone);
        return "redirect:/cphone/phoneList";
    }

    @GetMapping("/cphone/delete/{id}")
    public String deletePc(@PathVariable("id") CellPhone cellPhone){
        cpRepository.delete(cellPhone);
        return "redirect:/cphone/phoneList";
    }
}
