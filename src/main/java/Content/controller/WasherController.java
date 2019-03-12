package Content.controller;

import Content.entity.Washer;
import Content.repository.WasherRepository;
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
public class WasherController {

    @Value("${upload.path}")
    private String uploadPath;

    final private WasherRepository washerRepository;

    public WasherController(WasherRepository washerRepository) {
        this.washerRepository = washerRepository;
    }

    @GetMapping("washer/washerList")
    public String washerList(@RequestParam(required = false, defaultValue = "") String filter, Model model,
                         @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable){
        Page<Washer> washerList;
        if(filter != null && !filter.isEmpty()){
            washerList = washerRepository.findByModel(filter, pageable);

        }else {
            washerList = washerRepository.findAll(pageable);
        }
        model.addAttribute("url", "/washer/washerList");
        model.addAttribute("washerList", washerList);
        model.addAttribute("filter", filter);
        return "/washer/washerList";
    }

    @PostMapping("washer/washerList")
    public String addWasher(@Valid Washer washer,
                        BindingResult bindingResult,
                        @RequestParam("file") MultipartFile file,
                        Model model) throws IOException {
        if (bindingResult.hasErrors() && file == null || file.getOriginalFilename().isEmpty()){
            Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                    fieldError -> fieldError.getField() + "Error",
                    FieldError::getDefaultMessage
            );
            Map<String, String> errorsMap = bindingResult.getFieldErrors().stream().collect(collector);
            String error = "Файл не выбран";
            model.addAttribute("error", error);
            model.mergeAttributes(errorsMap);
            return "/washer/addWasher";
        }else {
            if (file != null && !file.getOriginalFilename().isEmpty()){
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()){
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFilename));
                washer.setFilename(resultFilename);
            }
            washerRepository.save(washer);}
        List<Washer> washerList = washerRepository.findAll();
        model.addAttribute("washerList", washerList);
        return "redirect:/washer/washerList";
    }

    @GetMapping("washer/addWasher")
    public String addWasher(Model model){
        return "washer/addWasher";
    }

    @GetMapping("washer/{washer}")
    public String editWasher(@PathVariable Washer washer, Model model){
        model.addAttribute("washer", washer);
        return "washer/editWasher";
    }

    @PostMapping("washer/save")
    public String saveWasher(@RequestParam String name,
                         @RequestParam Double price,
                         @RequestParam Integer quantity,
                         @RequestParam String description,
                         @RequestParam("file") MultipartFile file,
                         @RequestParam("id") Washer washer) throws IOException {
        washer.setModel(name);
        washer.setPrice(price);
        washer.setQuantity(quantity);
        washer.setDescription(description);
        if (file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            washer.setFilename(resultFilename);
        }
        washerRepository.save(washer);
        return "redirect:/washer/washerList";
    }

    @GetMapping("/washer/delete/{id}")
    public String deletePc(@PathVariable("id") Washer washer){
        washerRepository.delete(washer);
        return "redirect:/washer/washerList";
    }

}
