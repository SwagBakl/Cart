package Content.controller;

import Content.entity.Fridge;
import Content.repository.FridgeRepository;
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
public class FridgeController {

    @Value("${upload.path}")
    private String uploadPath;

    final private FridgeRepository fridgeRepository;

    public FridgeController(FridgeRepository fridgeRepository) {
        this.fridgeRepository = fridgeRepository;
    }


    @GetMapping("/fridge/fridgeList")
    public String fridgeList(@RequestParam(required = false, defaultValue = "") String filter, Model model,
                           @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable){
        Page<Fridge> fridgeList;
        if(filter != null && !filter.isEmpty()){
            fridgeList = fridgeRepository.findByModel(filter, pageable);

        }else {
            fridgeList = fridgeRepository.findAll(pageable);
        }
        model.addAttribute("fridgeList", fridgeList);
        model.addAttribute("url", "/fridge/fridgeList");
        model.addAttribute("filter", filter);
        return "fridge/fridgeList";
    }

    @PostMapping("/fridge/fridgeList")
    public String fridgeTab(
            @Valid Fridge fridge,
            BindingResult bindingResult,
            @RequestParam("file") MultipartFile file,
            Model model) throws Exception{
        if (bindingResult.hasErrors() && file == null || file.getOriginalFilename().isEmpty()){
            Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                    fieldError -> fieldError.getField() + "Error",
                    FieldError::getDefaultMessage
            );
            Map<String, String> errorsMap = bindingResult.getFieldErrors().stream().collect(collector);
            model.mergeAttributes(errorsMap);
            return "/fridge/addFridge";
        }else {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFilename));
                fridge.setFilename(resultFilename);
            }
            fridgeRepository.save(fridge);}
        List<Fridge> fridgeList = fridgeRepository.findAll();
        model.addAttribute("fridgeList", fridgeList);
        return "redirect:/fridge/fridgeList";
    }

    @GetMapping("fridge/addFridge")
    public String addTab(Model model){
        return "fridge/addFridge";
    }

    @GetMapping("/fridge/{fridge}")
    public String editTab(@PathVariable Fridge fridge, Model model){
        model.addAttribute("fridge", fridge);
        return "fridge/editFridge";
    }

    @PostMapping("/fridge/save")
    public String savePc(
            @RequestParam String name,
            @RequestParam Double price,
            @RequestParam Integer quantity,
            @RequestParam String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam("id") Fridge fridge) throws IOException {
        fridge.setModel(name);
        fridge.setPrice(price);
        fridge.setQuantity(quantity);
        fridge.setDescription(description);
        if (file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            fridge.setFilename(resultFilename);
        }
        fridgeRepository.save(fridge);
        return "redirect:/fridge/fridgeList";
    }

    @GetMapping("/fridge/delete/{id}")
    public String deleteFridge(@PathVariable("id") Fridge fridge){
        fridgeRepository.delete(fridge);
        return "redirect:/fridge/fridgeList";
    }

}
