package Content.controller;

import Content.entity.MusicCenter;
import Content.repository.MusicCenterRepository;
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
public class MusicCenterController {

    @Value("${upload.path}")
    private String uploadPath;

    final private MusicCenterRepository mcRepository;

    public MusicCenterController(MusicCenterRepository mcRepository) {
        this.mcRepository = mcRepository;
    }

    @GetMapping("mc/mcList")
    public String mcList(@RequestParam(required = false, defaultValue = "") String filter, Model model,
                         @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable){
        Page<MusicCenter> mcList;
        if(filter != null && !filter.isEmpty()){
            mcList = mcRepository.findByModel(filter, pageable);

        }else {
            mcList = mcRepository.findAll(pageable);
        }
        model.addAttribute("url", "/mc/mcList");
        model.addAttribute("mcList", mcList);
        model.addAttribute("filter", filter);
        return "mc/mcList";
    }

    @PostMapping("mc/mcList")
    public String addMc(@Valid MusicCenter mc,
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
            return "/mc/addMc";
        }else {
            if (file != null && !file.getOriginalFilename().isEmpty()){
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()){
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFilename));
                mc.setFilename(resultFilename);
            }
            mcRepository.save(mc);}
        List<MusicCenter> mcList = mcRepository.findAll();
        model.addAttribute("mcList", mcList);
        return "redirect:/mc/mcList";
    }

    @GetMapping("mc/addMc")
    public String addMc(Model model){
        return "mc/addMc";
    }

    @GetMapping("mc/{mc}")
    public String editMc(@PathVariable MusicCenter mc, Model model){
        model.addAttribute("mc", mc);
        return "mc/editMc";
    }

    @PostMapping("mc/save")
    public String saveMc(@RequestParam String name,
                         @RequestParam Integer price,
                         @RequestParam Integer quantity,
                         @RequestParam String description,
                         @RequestParam("file") MultipartFile file,
                         @RequestParam("id") MusicCenter mc) throws IOException {
        mc.setModel(name);
        mc.setPrice(price);
        mc.setQuantity(quantity);
        mc.setDescription(description);
        if (file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            mc.setFilename(resultFilename);
        }
        mcRepository.save(mc);
        return "redirect:/mc/mcList";
    }

    @GetMapping("/mc/delete/{id}")
    public String deletePc(@PathVariable("id") MusicCenter mc){
        mcRepository.delete(mc);
        return "redirect:/mc/mcList";
    }

}
