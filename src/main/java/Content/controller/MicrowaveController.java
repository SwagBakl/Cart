package Content.controller;

import Content.entity.Microwave;
import Content.repository.MicrowaveRepository;
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
public class MicrowaveController {

    @Value("${upload.path}")
    private String uploadPath;

    final private MicrowaveRepository mwRepository;

    public MicrowaveController(MicrowaveRepository mwRepository) {
        this.mwRepository = mwRepository;
    }

    @GetMapping("microwave/mwList")
    public String tvList(@RequestParam(required = false, defaultValue = "") String filter, Model model,
                         @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable){
        Page<Microwave> mwList;
        if(filter != null && !filter.isEmpty()){
            mwList = mwRepository.findByModel(filter, pageable);

        }else {
            mwList = mwRepository.findAll(pageable);
        }
        model.addAttribute("url", "/microwave/mwList");
        model.addAttribute("mwList", mwList);
        model.addAttribute("filter", filter);
        return "microwave/mwList";
    }

    @PostMapping("microwave/mwList")
    public String addTv(@Valid Microwave mw,
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
            return "/microwave/addMw";
        }else {
            if (file != null && !file.getOriginalFilename().isEmpty()){
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()){
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFilename));
                mw.setFilename(resultFilename);
            }
            mwRepository.save(mw);}
        List<Microwave> mwList = mwRepository.findAll();
        model.addAttribute("mwList", mwList);
        return "redirect:/microwave/mwList";
    }

    @GetMapping("microwave/addMw")
    public String addTv(Model model){
        return "microwave/addMw";
    }

    @GetMapping("microwave/{mw}")
    public String editTv(@PathVariable Microwave mw, Model model){
        model.addAttribute("mw", mw);
        return "microwave/editMw";
    }

    @PostMapping("microwave/save")
    public String saveTv(@RequestParam String name,
                         @RequestParam Integer price,
                         @RequestParam Integer quantity,
                         @RequestParam String description,
                         @RequestParam("file") MultipartFile file,
                         @RequestParam("id") Microwave mw) throws IOException {
        mw.setModel(name);
        mw.setPrice(price);
        mw.setQuantity(quantity);
        mw.setDescription(description);
        if (file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            mw.setFilename(resultFilename);
        }
        mwRepository.save(mw);
        return "redirect:/microwave/mwList";
    }

    @GetMapping("/microwave/delete/{id}")
    public String deletePc(@PathVariable("id") Microwave mw){
        mwRepository.delete(mw);
        return "redirect:/microwave/mwList";
    }

}
