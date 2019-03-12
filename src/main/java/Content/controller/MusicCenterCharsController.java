package Content.controller;

import Content.entity.ElectronicCharactsName;
import Content.entity.ElectronicCharactsValue;
import Content.entity.MusicCenter;
import Content.entity.MusicCenterChars;
import Content.repository.ElectronicCharsNameRepository;
import Content.repository.ElectronicCharsValueRepository;
import Content.repository.MusicCenterCharsRepository;
import Content.repository.MusicCenterRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MusicCenterCharsController {

    final private MusicCenterRepository mcRepository;
    final private MusicCenterCharsRepository mcCharsRepository;
    final private ElectronicCharsNameRepository electronicCharsNameRepository;
    final private ElectronicCharsValueRepository electronicCharsValueRepository;

    public MusicCenterCharsController(MusicCenterRepository mcRepository, MusicCenterCharsRepository mcCharsRepository,
                             ElectronicCharsNameRepository electronicCharsNameRepository,
                             ElectronicCharsValueRepository electronicCharsValueRepository) {
        this.mcRepository = mcRepository;
        this.mcCharsRepository = mcCharsRepository;
        this.electronicCharsNameRepository = electronicCharsNameRepository;
        this.electronicCharsValueRepository = electronicCharsValueRepository;
    }

    @GetMapping("mc/{id}/characts")
    public String mcCharList(@PathVariable Long id, Model model) throws Exception{

        MusicCenter mc = mcRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        List<ElectronicCharactsName> elCharList = electronicCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elCharValueList = electronicCharsValueRepository.findAll();
        List<MusicCenterChars> mcChars = mc.getMusicCenterChars();
        model.addAttribute("model", mc.getModel());
        model.addAttribute("mcChars", mcChars);
        model.addAttribute("elCharList", elCharList);
        model.addAttribute("elCharValueList", elCharValueList);
        return "mc/mcCharsList";
    }

    @PostMapping("mc/{id}/characts")
    public String addChar(@RequestParam Long id,
                          @RequestParam String name,
                          @RequestParam String value,
                          Model model) throws Exception {
        MusicCenter mc = mcRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        MusicCenterChars mcCharas = new MusicCenterChars(name, value, mc);
        mcCharsRepository.save(mcCharas);
        List<MusicCenterChars> mcChars = mc.getMusicCenterChars();
        model.addAttribute("mcChars", mcChars);
        return "redirect:/mc/{id}/characts";
    }

    @GetMapping("/mc/charact/{mcChars}")
    public String editCharact(@PathVariable MusicCenterChars mcChars, Model model){
        List<ElectronicCharactsName> elcharsName = electronicCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elcharsValue = electronicCharsValueRepository.findAll();
        model.addAttribute("elcharsName", elcharsName);
        model.addAttribute("elcharsValue", elcharsValue);
        model.addAttribute("mcChars", mcChars);
        return "mc/editMcChar";
    }

    //HERE IS A PROBLEM
    @PostMapping("mc/characts/{id}/save") //OR HERE
    public String charSave(@RequestParam String name,
                           @RequestParam String value,
                           @RequestParam("id") MusicCenterChars mcChars,
                           Model model){
        mcChars.setName(name);
        mcChars.setValue(value);
        mcCharsRepository.save(mcChars);
        return "redirect:/mc/{id}/characts";
    }

    @GetMapping("mc/charact/delete/{id}")
    public String deletePc(@PathVariable("id") MusicCenterChars mcChars){
        mcCharsRepository.delete(mcChars);
        return "redirect:/mc/"+ mcChars.getMusicCenter().getId() +"/characts";
    }

}
