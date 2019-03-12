package Content.controller;

import Content.entity.ElectronicCharactsName;
import Content.entity.ElectronicCharactsValue;
import Content.entity.TV;
import Content.entity.TvChars;
import Content.repository.ElectronicCharsNameRepository;
import Content.repository.ElectronicCharsValueRepository;
import Content.repository.TvCharsRepository;
import Content.repository.TvRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TvCharsController {

    final private TvRepository tvRepository;
    final private TvCharsRepository tvCharsRepository;
    final private ElectronicCharsNameRepository electronicCharsNameRepository;
    final private ElectronicCharsValueRepository electronicCharsValueRepository;

    public TvCharsController(TvRepository tvRepository, TvCharsRepository tvCharsRepository,
                             ElectronicCharsNameRepository electronicCharsNameRepository,
                             ElectronicCharsValueRepository electronicCharsValueRepository) {
        this.tvRepository = tvRepository;
        this.tvCharsRepository = tvCharsRepository;
        this.electronicCharsNameRepository = electronicCharsNameRepository;
        this.electronicCharsValueRepository = electronicCharsValueRepository;
    }

    @GetMapping("tv/{id}/characts")
    public String tvCharList(@PathVariable Long id, Model model) throws Exception{

        TV tv = tvRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        List<ElectronicCharactsName> elCharList = electronicCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elCharValueList = electronicCharsValueRepository.findAll();
        List<TvChars> tvChars = tv.getCharacts();
        model.addAttribute("model", tv.getModel());
        model.addAttribute("tvChars", tvChars);
        model.addAttribute("elCharList", elCharList);
        model.addAttribute("elCharValueList", elCharValueList);
        return "tv/tvCharsList";
    }

    @PostMapping("tv/{id}/characts")
    public String addChar(@RequestParam Long id,
                          @RequestParam String name,
                          @RequestParam String value,
                          Model model) throws Exception {
        TV tv = tvRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        TvChars tvCharas = new TvChars(name, value, tv);
        tvCharsRepository.save(tvCharas);
        List<TvChars> tvChars = tv.getCharacts();
        model.addAttribute("tvChars", tvChars);
        return "redirect:/tv/{id}/characts";
    }

    @GetMapping("/charact/{tvChars}")
    public String editCharact(@PathVariable TvChars tvChars, Model model){
        List<ElectronicCharactsName> elcharsName = electronicCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elcharsValue = electronicCharsValueRepository.findAll();
        model.addAttribute("elcharsName", elcharsName);
        model.addAttribute("elcharsValue", elcharsValue);
        model.addAttribute("tvChars", tvChars);
        return "tv/editTvChar";
    }

    //HERE IS A PROBLEM
    @PostMapping("tv/characts/{id}/save")
    public String charSave(@RequestParam String name,
                           @RequestParam String value,
                           @RequestParam("id") TvChars tvChars,
                           Model model){
        tvChars.setName(name);
        tvChars.setValue(value);
        tvCharsRepository.save(tvChars);
        return "redirect:/tv/{id}/characts";
    }

    @GetMapping("tv/charact/delete/{id}")
    public String deletePc(@PathVariable("id") TvChars tvChars){
        tvCharsRepository.delete(tvChars);
        return "redirect:/tv/"+ tvChars.getTv().getId() +"/characts";
    }
}
