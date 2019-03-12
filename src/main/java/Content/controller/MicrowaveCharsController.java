package Content.controller;

import Content.entity.ElectronicCharactsName;
import Content.entity.ElectronicCharactsValue;
import Content.entity.Microwave;
import Content.entity.MicrowaveChars;
import Content.repository.ElectronicCharsNameRepository;
import Content.repository.ElectronicCharsValueRepository;
import Content.repository.MicrowaveCharsRepository;
import Content.repository.MicrowaveRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MicrowaveCharsController {

    final private MicrowaveRepository mwRepository;
    final private MicrowaveCharsRepository mwCharsRepository;
    final private ElectronicCharsNameRepository electronicCharsNameRepository;
    final private ElectronicCharsValueRepository electronicCharsValueRepository;

    public MicrowaveCharsController(MicrowaveRepository tvRepository, MicrowaveCharsRepository tvCharsRepository,
                             ElectronicCharsNameRepository electronicCharsNameRepository,
                             ElectronicCharsValueRepository electronicCharsValueRepository) {
        this.mwRepository = tvRepository;
        this.mwCharsRepository = tvCharsRepository;
        this.electronicCharsNameRepository = electronicCharsNameRepository;
        this.electronicCharsValueRepository = electronicCharsValueRepository;
    }

    @GetMapping("microwave/{id}/characts")
    public String tvCharList(@PathVariable Long id, Model model) throws Exception{

        Microwave mw = mwRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        List<ElectronicCharactsName> elCharList = electronicCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elCharValueList = electronicCharsValueRepository.findAll();
        List<MicrowaveChars> mwChars = mw.getMicrowaveChars();
        model.addAttribute("model", mw.getModel());
        model.addAttribute("mwChars", mwChars);
        model.addAttribute("elCharList", elCharList);
        model.addAttribute("elCharValueList", elCharValueList);
        return "microwave/mwCharsList";
    }

    @PostMapping("microwave/{id}/characts")
    public String addChar(@RequestParam Long id,
                          @RequestParam String name,
                          @RequestParam String value,
                          Model model) throws Exception {
        Microwave mw = mwRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        MicrowaveChars mwCharas = new MicrowaveChars(name, value, mw);
        mwCharsRepository.save(mwCharas);
        List<MicrowaveChars> mwChars = mw.getMicrowaveChars();
        model.addAttribute("mwChars", mwChars);
        return "redirect:/microwave/{id}/characts";
    }

    @GetMapping("/microwave/charact/{mwChars}")
    public String editCharact(@PathVariable MicrowaveChars mwChars, Model model){
        List<ElectronicCharactsName> elcharsName = electronicCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elcharsValue = electronicCharsValueRepository.findAll();
        model.addAttribute("elcharsName", elcharsName);
        model.addAttribute("elcharsValue", elcharsValue);
        model.addAttribute("mwChars", mwChars);
        return "/microwave/editMwChar";
    }


    @PostMapping("microwave/characts/{id}/save")
    public String charSave(@RequestParam String name,
                           @RequestParam String value,
                           @RequestParam("id") MicrowaveChars mwChars,
                           Model model){
        mwChars.setName(name);
        mwChars.setValue(value);
        mwCharsRepository.save(mwChars);
        return "redirect:/microwave/{id}/characts";
    }

    @GetMapping("microwave/charact/delete/{id}")
    public String deletePc(@PathVariable("id") MicrowaveChars mwChars){
        mwCharsRepository.delete(mwChars);
        return "redirect:/microwave/"+ mwChars.getMicrowave().getId() +"/characts";
    }

}
