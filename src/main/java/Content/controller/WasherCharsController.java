package Content.controller;

import Content.entity.ElectronicCharactsName;
import Content.entity.ElectronicCharactsValue;
import Content.entity.Washer;
import Content.entity.WasherChars;
import Content.repository.ElectronicCharsNameRepository;
import Content.repository.ElectronicCharsValueRepository;
import Content.repository.WasherCharsRepository;
import Content.repository.WasherRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WasherCharsController {

    final private WasherRepository washerRepository;
    final private WasherCharsRepository washerCharsRepository;
    final private ElectronicCharsNameRepository electronicCharsNameRepository;
    final private ElectronicCharsValueRepository electronicCharsValueRepository;

    public WasherCharsController(WasherRepository washerRepository, WasherCharsRepository washerCharsRepository,
                             ElectronicCharsNameRepository electronicCharsNameRepository,
                             ElectronicCharsValueRepository electronicCharsValueRepository) {
        this.washerRepository = washerRepository;
        this.washerCharsRepository = washerCharsRepository;
        this.electronicCharsNameRepository = electronicCharsNameRepository;
        this.electronicCharsValueRepository = electronicCharsValueRepository;
    }

    @GetMapping("washer/{id}/characts")
    public String washerCharList(@PathVariable Long id, Model model) throws Exception{

        Washer washer = washerRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        List<ElectronicCharactsName> elCharList = electronicCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elCharValueList = electronicCharsValueRepository.findAll();
        List<WasherChars> washerChars = washer.getWasherChars();
        model.addAttribute("model", washer.getModel());
        model.addAttribute("washerChars", washerChars);
        model.addAttribute("elCharList", elCharList);
        model.addAttribute("elCharValueList", elCharValueList);
        return "washer/washerCharsList";
    }

    @PostMapping("washer/{id}/characts")
    public String addChar(@RequestParam Long id,
                          @RequestParam String name,
                          @RequestParam String value,
                          Model model) throws Exception {
        Washer washer = washerRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        WasherChars washerCharas = new WasherChars(name, value, washer);
        washerCharsRepository.save(washerCharas);
        List<WasherChars> washerChars = washer.getWasherChars();
        model.addAttribute("washerChars", washerChars);
        return "redirect:/washer/{id}/characts";
    }

    @GetMapping("/washer/charact/{washerChars}")
    public String editCharact(@PathVariable WasherChars washerChars, Model model){
        List<ElectronicCharactsName> elcharsName = electronicCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elcharsValue = electronicCharsValueRepository.findAll();
        model.addAttribute("elcharsName", elcharsName);
        model.addAttribute("elcharsValue", elcharsValue);
        model.addAttribute("washerChars", washerChars);
        return "washer/editWasherChar";
    }


    @PostMapping("washer/characts/{id}/save")
    public String charSave(@RequestParam String name,
                           @RequestParam String value,
                           @RequestParam("id") WasherChars washerChars,
                           Model model){
        washerChars.setName(name);
        washerChars.setValue(value);
        washerCharsRepository.save(washerChars);
        return "redirect:/washer/{id}/characts";
    }

    @GetMapping("washer/charact/delete/{id}")
    public String deletePc(@PathVariable("id") WasherChars washerChars){
        washerCharsRepository.delete(washerChars);
        return "redirect:/washer/"+ washerChars.getWasher().getId() +"/characts";
    }

}
