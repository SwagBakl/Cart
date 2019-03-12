package Content.controller;

import Content.entity.ElectronicCharactsName;
import Content.entity.ElectronicCharactsValue;
import Content.entity.NoteBook;
import Content.entity.NoteBookChars;
import Content.repository.ElectronicCharsNameRepository;
import Content.repository.ElectronicCharsValueRepository;
import Content.repository.NBCharsRepository;
import Content.repository.NBRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class NBCharsController {

    final private NBRepository nbRepository;
    final private NBCharsRepository nbCharsRepository;
    final private ElectronicCharsNameRepository elCharsNameRepository;
    final private ElectronicCharsValueRepository elCharsValueRepository;

    public NBCharsController(NBRepository nbRepository, NBCharsRepository nbCharsRepository,
                        ElectronicCharsNameRepository elCharsNameRepository,
                        ElectronicCharsValueRepository elCharsValueRepository) {
        this.nbRepository = nbRepository;
        this.nbCharsRepository = nbCharsRepository;
        this.elCharsNameRepository = elCharsNameRepository;
        this.elCharsValueRepository = elCharsValueRepository;
    }

    @GetMapping("notebook/{id}/characts")
    public String nbCharList(@PathVariable Long id, Model model) throws Exception{

        NoteBook noteBook = nbRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        List<ElectronicCharactsName> elCharList = elCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elCharValueList = elCharsValueRepository.findAll();
        List<NoteBookChars> nbChars = noteBook.getNoteBookChars();
        model.addAttribute("model", noteBook.getModel());
        model.addAttribute("nbChars", nbChars);
        model.addAttribute("elCharList", elCharList);
        model.addAttribute("elCharValueList", elCharValueList);
        return "notebook/nBCharsList";
    }

    @PostMapping("/notebook/{id}/characts")
    public String addChar(@RequestParam Long id,
                          @RequestParam String name,
                          @RequestParam String value,
                          Model model) throws Exception {
        NoteBook noteBook = nbRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        NoteBookChars nbCharas = new NoteBookChars(name, value, noteBook);
        nbCharsRepository.save(nbCharas);
        List<NoteBookChars> nbChars = noteBook.getNoteBookChars();
        model.addAttribute("nbChars", nbChars);
        return "redirect:/notebook/{id}/characts";
    }

    @GetMapping("nb/charact/{nbChars}")
    public String editCharact(@PathVariable NoteBookChars nbChars, Model model){
        List<ElectronicCharactsName> elcharsName = elCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elcharsValue = elCharsValueRepository.findAll();
        model.addAttribute("elcharsName", elcharsName);
        model.addAttribute("elcharsValue", elcharsValue);
        model.addAttribute("nbChars", nbChars);
        return "notebook/editNBChar";
    }


    @PostMapping("/notebook/characts/{id}/save") //OR HERE
    public String charSave(@RequestParam String name,
                           @RequestParam String value,
                           @RequestParam("id") NoteBookChars nbChars,
                           Model model){
        nbChars.setName(name);
        nbChars.setValue(value);
        nbCharsRepository.save(nbChars);
        return "redirect:/notebook/{id}/characts";
    }

    @GetMapping("notebook/charact/delete/{id}")
    public String deletePc(@PathVariable("id") NoteBookChars nbChars){
        nbCharsRepository.delete(nbChars);
        return "redirect:/notebook/"+ nbChars.getNoteBook().getId() +"/characts";
    }
}
