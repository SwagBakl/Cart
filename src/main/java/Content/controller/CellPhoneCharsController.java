package Content.controller;

import Content.entity.CellPhone;
import Content.entity.CellPhoneChars;
import Content.entity.ElectronicCharactsName;
import Content.entity.ElectronicCharactsValue;
import Content.repository.CellPhoneCharsRepository;
import Content.repository.CellPhoneRepository;
import Content.repository.ElectronicCharsNameRepository;
import Content.repository.ElectronicCharsValueRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CellPhoneCharsController {


    final private CellPhoneRepository cpRepository;
    final private CellPhoneCharsRepository cpCharsRepository;
    final private ElectronicCharsNameRepository elCharsNameRepository;
    final private ElectronicCharsValueRepository elCharsValueRepository;

    public CellPhoneCharsController(CellPhoneRepository cpRepository, CellPhoneCharsRepository cpCharsRepository,
                                    ElectronicCharsNameRepository elCharsNameRepository,
                                    ElectronicCharsValueRepository elCharsValueRepository) {
        this.cpRepository = cpRepository;
        this.cpCharsRepository = cpCharsRepository;
        this.elCharsNameRepository = elCharsNameRepository;
        this.elCharsValueRepository = elCharsValueRepository;
    }

    @GetMapping("cphone/{id}/characts")
    public String nbCharList(@PathVariable Long id, Model model) throws Exception{

        CellPhone cellPhone = cpRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        List<ElectronicCharactsName> elCharList = elCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elCharValueList = elCharsValueRepository.findAll();
        List<CellPhoneChars> cpChars = cellPhone.getCellPhoneChars();
        model.addAttribute("model", cellPhone.getModel());
        model.addAttribute("cpChars", cpChars);
        model.addAttribute("elCharList", elCharList);
        model.addAttribute("elCharValueList", elCharValueList);
        return "cphone/phoneCharsList";
    }

    @PostMapping("/cphone/{id}/characts")
    public String addChar(@RequestParam Long id,
                          @RequestParam String name,
                          @RequestParam String value,
                          Model model) throws Exception {
        CellPhone cellPhone = cpRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        CellPhoneChars cpCharas = new CellPhoneChars(name, value, cellPhone);
        cpCharsRepository.save(cpCharas);
        List<CellPhoneChars> cpChars = cellPhone.getCellPhoneChars();
        model.addAttribute("cpChars", cpChars);
        return "redirect:/cphone/{id}/characts";
    }

    @GetMapping("cphone/charact/{cpChars}")
    public String editCharact(@PathVariable CellPhoneChars cpChars, Model model){
        List<ElectronicCharactsName> elcharsName = elCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elcharsValue = elCharsValueRepository.findAll();
        model.addAttribute("elcharsName", elcharsName);
        model.addAttribute("elcharsValue", elcharsValue);
        model.addAttribute("cpChars", cpChars);
        return "cphone/editPhoneChar";
    }


    @PostMapping("/cphone/characts/{id}/save") //OR HERE
    public String charSave(@RequestParam String name,
                           @RequestParam String value,
                           @RequestParam("id") CellPhoneChars cpChars,
                           Model model){
        cpChars.setName(name);
        cpChars.setValue(value);
        cpCharsRepository.save(cpChars);
        return "redirect:/cphone/{id}/characts";
    }

    @GetMapping("/cphone/charact/delete/{id}")
    public String deletePc(@PathVariable("id") CellPhoneChars cpChars){
        cpCharsRepository.delete(cpChars);
        return "redirect:/cphone/"+ cpChars.getCellPhone().getId() +"/characts";
    }
}
