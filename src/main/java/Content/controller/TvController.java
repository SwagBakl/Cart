package Content.controller;

import Content.entity.TV;
import Content.repository.TvRepository;
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
public class TvController {

    @Value("${upload.path}")
    private String uploadPath;

    final private TvRepository tvRepository;

    public TvController(TvRepository tvRepository) {
        this.tvRepository = tvRepository;
    }

    @GetMapping("tv/tvList")
    public String tvList(@RequestParam(required = false, defaultValue = "") String filter, Model model,
                         @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable){
        Page<TV> tvList;
        if(filter != null && !filter.isEmpty()){
            tvList = tvRepository.findByModel(filter, pageable);

        }else {
            tvList = tvRepository.findAll(pageable);
        }
        model.addAttribute("url", "/tv/tvList");
        model.addAttribute("tvList", tvList);
        model.addAttribute("filter", filter);
        return "tv/tvList";
    }

    @PostMapping("tv/tvList")
    public String addTv(@Valid TV tv,
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
            return "/tv/addTv";
        }else {
        if (file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            tv.setFilename(resultFilename);
        }
        tvRepository.save(tv);}
        List<TV> tvList = tvRepository.findAll();
        model.addAttribute("tvList", tvList);
        return "redirect:/tv/tvList";
    }

    @GetMapping("tv/addTv")
    public String addTv(Model model){
        return "tv/addTv";
    }

    @GetMapping("tv/{tv}")
    public String editTv(@PathVariable TV tv, Model model){
        model.addAttribute("tv", tv);
        return "tv/editTv";
    }

    @PostMapping("tv/save")
    public String saveTv(@RequestParam String name,
                         @RequestParam Integer price,
                         @RequestParam Integer quantity,
                         @RequestParam String description,
                         @RequestParam("file") MultipartFile file,
                         @RequestParam("id") TV tv) throws IOException {
        tv.setModel(name);
        tv.setPrice(price);
        tv.setQuantity(quantity);
        tv.setDescription(description);
        if (file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            tv.setFilename(resultFilename);
        }
        tvRepository.save(tv);
        return "redirect:/tv/tvList";
    }

    @GetMapping("/tv/delete/{id}")
    public String deletePc(@PathVariable("id") TV tv){
        tvRepository.delete(tv);
        return "redirect:/tv/tvList";
    }

}
