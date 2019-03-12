package Content.controller;

import Content.entity.Pc;
import Content.repository.PcCharactRepository;
import Content.repository.PcRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class PcController {

    @Value("${upload.path}")
    private String uploadPath;

    final private PcRepository pcRepository;
    final private PcCharactRepository pcCharactRepository;
    public PcController(PcRepository pcRepository, PcCharactRepository pcCharactRepository){
        this.pcRepository = pcRepository;
        this.pcCharactRepository = pcCharactRepository;
    }

    @GetMapping("/pcList")
    public String pcList(@RequestParam(required = false, defaultValue = "")String filter, Model model){
        Iterable<Pc> pcList = pcRepository.findAll();
        if(filter != null && !filter.isEmpty()){
            pcList = pcRepository.findByModel(filter);

        }else {
            pcList = pcRepository.findAll();
        }
        model.addAttribute("pcList", pcList);
        model.addAttribute("filter", filter);
        return "pc/pcList";
    }


    @PostMapping("/pcList")
    public String addPC(@RequestParam String modelName,
                        @RequestParam Integer price,
                        @RequestParam Integer quantity,
                        @RequestParam("file") MultipartFile file,
                        Model model) throws IOException {
        Pc pc = new Pc(modelName, price, quantity);
        if (file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            pc.setFilename(resultFilename);
        }
        pcRepository.save(pc);
        Iterable<Pc> pcList = pcRepository.findAll();
        model.addAttribute("pcList", pcList);
        return "pc/pcList";
    }

    @GetMapping("/pc/{pc}")
    public String editPc(@PathVariable Pc pc, Model model){
        model.addAttribute("pc", pc);
        return "pc/editPc";
    }

    @PostMapping("/pc/save")
    public String savePc(
            @RequestParam String name,
            @RequestParam Integer price,
            @RequestParam Integer quantity,
            @RequestParam("file") MultipartFile file,
            @RequestParam("id") Pc pc) throws IOException {
        pc.setModel(name);
        pc.setPrice(price);
        pc.setQuantity(quantity);
        if (file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            pc.setFilename(resultFilename);
        }
        pcRepository.save(pc);
        return "redirect:/pcList";
    }

    @GetMapping("/pc/delete/{id}")
    public String deletePc(@PathVariable("id") Pc pc){
        pcRepository.delete(pc);
        return "redirect:/pcList";
    }


}
