package Content.controller;

import Content.entity.ElectronicCharactsName;
import Content.entity.ElectronicCharactsValue;
import Content.entity.Pc;
import Content.entity.PcCharacts;
import Content.repository.ElectronicCharsNameRepository;
import Content.repository.ElectronicCharsValueRepository;
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
    final private ElectronicCharsNameRepository electronicCharsNameRepository;
    final private ElectronicCharsValueRepository electronicCharsValueRepository;

    public PcCharsController(PcRepository pcRepository,
                             PcCharactRepository pcCharactRepository,
                             ElectronicCharsNameRepository electronicCharsNameRepository,
                             ElectronicCharsValueRepository electronicCharsValueRepository) {
        this.pcRepository = pcRepository;
        this.pcCharactRepository = pcCharactRepository;
        this.electronicCharsNameRepository = electronicCharsNameRepository;
        this.electronicCharsValueRepository = electronicCharsValueRepository;
    }

    @GetMapping("pc/{id}/characts")
    public String pcCharList(@PathVariable Long id, Model model) throws Exception{

        Pc pc = pcRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        List<ElectronicCharactsName> elCharList = electronicCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elCharValueList = electronicCharsValueRepository.findAll();
        List<PcCharacts> pcChars = pc.getCharacts();
        model.addAttribute("model", pc.getModel());
        model.addAttribute("pcChars", pcChars);
        model.addAttribute("elCharList", elCharList);
        model.addAttribute("elCharValueList", elCharValueList);
        return "charList";
    }

    @PostMapping("pc/{id}/characts")
    public String addChar(@RequestParam Long id,
                          @RequestParam String name,
                          @RequestParam String value,
                          Model model) throws Exception {
        Pc pc = pcRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        PcCharacts pcCharacts = new PcCharacts(name, value, pc);
        pcCharactRepository.save(pcCharacts);
        List<PcCharacts> pcChars = pc.getCharacts();
        model.addAttribute("pcChars", pcChars);
        return "redirect:/pc/{id}/characts";
    }

    @GetMapping("/tv/charact/{pcChars}")
    public String editCharact(@PathVariable PcCharacts pcChars, Model model){
        model.addAttribute("pcChars", pcChars);
        return "editPcChar";
    }

    //HERE IS A PROBLEM
    @PostMapping("pc/characts/{id}/save") //OR HERE
    public String charSave(@RequestParam String name,
                           @RequestParam String value,
                           @RequestParam("id") PcCharacts pcChars,
                           Model model){
        pcChars.setName(name);
        pcChars.setValue(value);
        pcCharactRepository.save(pcChars);
        return "redirect:/pc/{id}/characts"; //HERE
    }

    @GetMapping("/charact/delete/{id}")
    public String deletePc(@PathVariable("id") PcCharacts pcChars, @PathVariable("pc_id") Long pc_id){
        pcCharactRepository.delete(pcChars);
        return "redirect:/pc/{pc_id}/characts";
    }


}
