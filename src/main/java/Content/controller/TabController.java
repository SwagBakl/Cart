package Content.controller;

import Content.entity.Tab;
import Content.repository.TabRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
public class TabController {
    @Value("${upload.path}")
    private String uploadPath;

    final private TabRepository tabRepository;

    public TabController(TabRepository tabRepository) {
        this.tabRepository = tabRepository;
    }


    @GetMapping("/tab/tabList")
    public String tabsList(@RequestParam(required = false, defaultValue = "") String filter, Model model,
                           @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable){
        Page<Tab> tabList;
        if(filter != null && !filter.isEmpty()){
            tabList = tabRepository.findByModel(filter, pageable);

        }else {
            tabList = tabRepository.findAll(pageable);
        }
        model.addAttribute("tabList", tabList);
        model.addAttribute("url", "/tab/tabList");
        model.addAttribute("filter", filter);
        return "tab/tabList";
    }

    @PostMapping("/tab/tabList")
    public String addTab(
                         @Valid Tab tab,
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
            model.addAttribute("tabu", tab);
            String error = "Файл не выбран";
            model.addAttribute("error", error);
            return "/tab/addTab";
        }else {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFilename));
                tab.setFilename(resultFilename);
            }
        tabRepository.save(tab);}
        List<Tab> tabList = tabRepository.findAll();
        System.out.println(tab.getPrice());
        model.addAttribute("tabList", tabList);

        return "redirect:/tab/tabList";
    }

    @GetMapping("tab/addTab")
    public String addTab(Model model){
        return "tab/addTab";
    }

    @GetMapping("/tab/{tab}")
    public String editTab(@PathVariable Tab tab, Model model){
        model.addAttribute("tab", tab);
        return "tab/editTab";
    }

    @PostMapping("/tab/save")
    public String savePc(
            @RequestParam String name,
            @RequestParam Double price,
            @RequestParam Integer quantity,
            @RequestParam String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam("id") Tab tab) throws IOException {
        tab.setModel(name);
        tab.setPrice(price);
        tab.setQuantity(quantity);
        tab.setDescription(description);
        if (file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            tab.setFilename(resultFilename);
        }
        tabRepository.save(tab);
        return "redirect:/tab/tabList";
    }

    @GetMapping("/tab/delete/{id}")
    public String deletePc(@PathVariable("id") Tab tab){
        tabRepository.delete(tab);
        return "redirect:/tab/tabList";
    }

}
