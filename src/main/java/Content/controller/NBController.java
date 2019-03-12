package Content.controller;

import Content.entity.NoteBook;
import Content.repository.NBCharsRepository;
import Content.repository.NBRepository;
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
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class NBController {

    @Value("${upload.path}")
    private String uploadPath;

    final private NBRepository nbRepository;
    final private NBCharsRepository nbCharsRepository;

    public NBController(NBRepository nbRepository, NBCharsRepository nbCharsRepository) {
        this.nbRepository = nbRepository;
        this.nbCharsRepository = nbCharsRepository;
    }

    @GetMapping("/notebook/nBList")
    public String nbList(@RequestParam(required = false, defaultValue = "")String filter, Model model,
                         @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable){
        Page<NoteBook> nbList;
        if(filter != null && !filter.isEmpty()){
            nbList = nbRepository.findByModel(filter, pageable);

        }else {
            nbList = nbRepository.findAll(pageable);
        }
        model.addAttribute("url", "/notebook/nBList");
        model.addAttribute("nbList", nbList);
        model.addAttribute("filter", filter);
        return "/notebook/nBList";
    }


    @PostMapping("/notebook/nBList")
    public String addPC(@Valid NoteBook noteBook,
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
            return "/notebook/addNB";
        }else {
        if (file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            noteBook.setFilename(resultFilename);
        }
        nbRepository.save(noteBook);}
        Iterable<NoteBook> nbList = nbRepository.findAll();
        model.addAttribute("nbList", nbList);
        return "redirect:/notebook/nBList";
    }

    @GetMapping("notebook/addNB")
    public String addTab(Model model){
        return "/notebook/addNB";
    }

    @GetMapping("/nb/{noteBook}")
    public String editPc(@PathVariable NoteBook noteBook, Model model){
        model.addAttribute("noteBook", noteBook);
        return "notebook/editNB";
    }

    @PostMapping("/nb/save")
    public String savePc(
            @RequestParam String name,
            @RequestParam Integer price,
            @RequestParam Integer quantity,
            @RequestParam String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam("id") NoteBook noteBook) throws IOException {
        noteBook.setModel(name);
        noteBook.setPrice(price);
        noteBook.setQuantity(quantity);
        noteBook.setDescription(description);
        if (file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            noteBook.setFilename(resultFilename);
        }
        nbRepository.save(noteBook);
        return "redirect:/notebook/nBList";
    }

    @GetMapping("/nb/delete/{id}")
    public String deletePc(@PathVariable("id") NoteBook noteBook){
        nbRepository.delete(noteBook);
        return "redirect:/notebook/nBList";
    }

}
