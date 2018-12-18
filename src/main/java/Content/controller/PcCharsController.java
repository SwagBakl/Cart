package Content.controller;

import Content.entity.Pc;
import Content.entity.PcCharacts;
import Content.repository.PcCharactRepository;
import Content.repository.PcRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PcCharsController {

    final private PcRepository pcRepository;
    final private PcCharactRepository pcCharactRepository;

    public PcCharsController(PcRepository pcRepository, PcCharactRepository pcCharactRepository) {
        this.pcRepository = pcRepository;
        this.pcCharactRepository = pcCharactRepository;
    }

    @GetMapping("pc/characts/{id}/")
    public String pcCharList(@PathVariable Long id, Model model) throws Exception{

        Pc pc = pcRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        List<PcCharacts> pcChars = pc.getCharacts();
        model.addAttribute("model", pc.getModel());
        model.addAttribute("pcChars", pcChars);
        return "charList";
    }

    @PostMapping("pc/characts/{id}/")
    public String addChar(@RequestParam Long id,
                          @RequestParam String name,
                          @RequestParam String value,
                          Model model) throws Exception {
        Pc pc = pcRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        PcCharacts pcCharacts = new PcCharacts(name, value, pc);
        pcCharactRepository.save(pcCharacts);
        List<PcCharacts> pcChars = pc.getCharacts();
        model.addAttribute("pcChars", pcChars);
        return "redirect:";
    }

    @GetMapping("/charact/{pcChars}")
    public String editCharact(@PathVariable PcCharacts pcChars, Model model){
        model.addAttribute("pcChars", pcChars);
        return "editPcChar";
    }

    @PostMapping("charact/save")
    public String charSave(@RequestParam String name,
                           @RequestParam Long id,
                           @RequestParam String value,
                           @RequestParam("id") PcCharacts pcChars, Model model){
        pcChars.setName(name);
        pcChars.setValue(value);
        pcCharactRepository.save(pcChars);
        model.addAttribute("id", id);
        return "redirect:/pc/{id}/";
    }

    @GetMapping("/charact/delete/{id}")
    public String deletePc(@PathVariable("id") PcCharacts pcChars){
        pcCharactRepository.delete(pcChars);
        return "redirect:/pc/{id}/";
    }
}
