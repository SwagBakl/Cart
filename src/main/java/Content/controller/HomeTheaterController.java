package Content.controller;

import Content.entity.HomeTheater;
import Content.repository.HomeTheaterRepository;
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
public class HomeTheaterController  {

    @Value("${upload.path}")
    private String uploadPath;

    final private HomeTheaterRepository htRepository;

    public HomeTheaterController(HomeTheaterRepository htRepository) {
        this.htRepository = htRepository;
    }


    @GetMapping("/hometheater/htList")
    public String tabsList(@RequestParam(required = false, defaultValue = "") String filter, Model model,
                           @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable){
        Page<HomeTheater> htList;
        if(filter != null && !filter.isEmpty()){
            htList = htRepository.findByModel(filter, pageable);

        }else {
            htList = htRepository.findAll(pageable);
        }
        model.addAttribute("htList", htList);
        model.addAttribute("url", "/hometheater/htList");
        model.addAttribute("filter", filter);
        return "/hometheater/htList";
    }

    @PostMapping("/hometheater/htList")
    public String addTab(@Valid HomeTheater ht,
                         BindingResult bindingResult,
                         @RequestParam("file") MultipartFile file,
                         Model model) throws Exception{
        if (bindingResult.hasErrors() && file == null || file.getOriginalFilename().isEmpty()){
            Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                    fieldError -> fieldError.getField() + "Error",
                    FieldError::getDefaultMessage
            );
            Map<String, String> errorsMap = bindingResult.getFieldErrors().stream().collect(collector);
            String error = "Файл не выбран";
            model.addAttribute("error", error);
            model.mergeAttributes(errorsMap);
            return "/hometheater/addHT";
        }else {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFilename));
                ht.setFilename(resultFilename);
            }
            htRepository.save(ht);}
        List<HomeTheater> htList = htRepository.findAll();
        model.addAttribute("htList", htList);
        return "redirect:/hometheater/htList";
    }

    @GetMapping("/hometheater/addHT")
    public String addTab(Model model){
        return "hometheater/addHT";
    }

    @GetMapping("/hometheater/{ht}")
    public String editTab(@PathVariable HomeTheater ht, Model model){
        model.addAttribute("ht", ht);
        return "hometheater/editHT";
    }

    @PostMapping("/hometheater/save")
    public String savePc(
            @RequestParam String name,
            @RequestParam Double price,
            @RequestParam Integer quantity,
            @RequestParam String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam("id") HomeTheater ht) throws IOException {
        ht.setModel(name);
        ht.setPrice(price);
        ht.setQuantity(quantity);
        ht.setDescription(description);
        if (file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            ht.setFilename(resultFilename);
        }
        htRepository.save(ht);
        return "redirect:/hometheater/htList";
    }

    @GetMapping("/hometheater/delete/{id}")
    public String deletePc(@PathVariable("id") HomeTheater homeTheater){
        htRepository.delete(homeTheater);
        return "redirect:/hometheater/htList";
    }


}
