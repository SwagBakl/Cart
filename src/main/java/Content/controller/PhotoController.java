package Content.controller;

import Content.entity.Photo;
import Content.repository.PhotoRepository;
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
public class PhotoController {
    @Value("${upload.path}")
    private String uploadPath;

    final private PhotoRepository photoRepository;

    public PhotoController(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }


    @GetMapping("/photo/photoList")
    public String photosList(@RequestParam(required = false, defaultValue = "") String filter, Model model,
                             @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable){
        Page<Photo> photoList;
        if(filter != null && !filter.isEmpty()){
            photoList = photoRepository.findByModel(filter, pageable);

        }else {
            photoList = photoRepository.findAll(pageable);
        }
        model.addAttribute("photoList", photoList);
        model.addAttribute("url", "/photo/photoList");
        model.addAttribute("filter", filter);
        return "/photo/photoList";
    }

    @PostMapping("/photo/photoList")
    public String addTab(@Valid Photo photo,
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
            return "/photo/addPhoto";
        }else {
        if (file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            photo.setFilename(resultFilename);
        }
        photoRepository.save(photo);}
        List<Photo> photoList = photoRepository.findAll();
        model.addAttribute("photoList", photoList);
        return "redirect:/photo/photoList";
    }

    @GetMapping("/photo/{photo}")
    public String editTab(@PathVariable Photo photo, Model model){
        model.addAttribute("photo", photo);
        return "photo/editPhoto";
    }

    @GetMapping("/photo/addPhoto")
    public String addPhoto(Model model){
        return "/photo/addPhoto";
    }

    @PostMapping("/photo/save")
    public String savePc(
            @RequestParam String name,
            @RequestParam Double price,
            @RequestParam Integer quantity,
            @RequestParam String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam("id") Photo photo) throws IOException {
        photo.setModel(name);
        photo.setPrice(price);
        photo.setQuantity(quantity);
        photo.setDescription(description);
        if (file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            photo.setFilename(resultFilename);
        }
        photoRepository.save(photo);
        return "redirect:/photo/photoList";
    }

    @GetMapping("/photo/delete/{id}")
    public String deletePhoto(@PathVariable("id") Photo photo){
        photoRepository.delete(photo);
        return "redirect:/photo/photoList";
    }
}
