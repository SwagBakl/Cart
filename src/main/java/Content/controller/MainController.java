package Content.controller;

import Content.entity.Pc;
import Content.repository.PcRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class MainController {

    @Value("${upload.path}")
    private String uploadPath;

    final private PcRepository pcRepository;
    public MainController(PcRepository pcRepository){
        this.pcRepository = pcRepository;
    }

    @GetMapping("/")
    public String greeting(Model model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Model model){
        return "main";
    }

//    @PostMapping("/main")
//    public String addItem(@RequestParam String name,
//                          @RequestParam String desc,
//                          @RequestParam("file") MultipartFile file,
//                          Model model) throws IOException {
//        Catalog catalog = new Catalog(name, desc);
//
//        if (file != null && !file.getOriginalFilename().isEmpty()){
//            File uploadDir = new File(uploadPath);
//            if (!uploadDir.exists()){
//                uploadDir.mkdir();
//            }
//            String uuidFile = UUID.randomUUID().toString();
//            String resultFilename = uuidFile + "." + file.getOriginalFilename();
//            file.transferTo(new File(uploadPath + "/" + resultFilename));
//            catalog.setFilename(resultFilename);
//        }
//
//        catalogRepository.save(catalog);
//        Iterable<Catalog> catalogList = catalogRepository.findAll();
//        model.addAttribute("catalogList", catalogList);
//        return "main";
//    }


}
