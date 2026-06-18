package online.comfyplace.chineserevision.controller;

import online.comfyplace.chineserevision.model.Word;
import online.comfyplace.chineserevision.repository.WordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RevisionController {
    @Autowired
    private WordsRepository wordsRepository;

    @GetMapping("/")
    public String home(Model model) {
        Word word = wordsRepository.getRandomWord();
        model.addAttribute("word", word);

        return "home";
    }
}
