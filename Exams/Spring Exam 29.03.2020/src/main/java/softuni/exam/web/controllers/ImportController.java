package softuni.exam.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.exam.service.PictureService;
import softuni.exam.service.PlayerService;
import softuni.exam.service.TeamService;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping("/import")
public class ImportController extends BaseController {

    private final PictureService pictureService;
    private final TeamService teamService;
    private final PlayerService playerService;

    @Autowired
    public ImportController(PictureService pictureService, TeamService teamService, PlayerService playerService) {
        this.pictureService = pictureService;
        this.teamService = teamService;
        this.playerService = playerService;
    }

    @GetMapping("/json")
    public ModelAndView importJson() {
        boolean isImported = this.playerService.areImported();

        return super.view("json/import-json", "isImported", isImported);
    }

    @GetMapping("/xml")
    public ModelAndView importXml() {
        boolean[] areImported = new boolean[] {
                this.pictureService.areImported(),
                this.teamService.areImported()
        };

        return super.view("xml/import-xml", "areImported", areImported);
    }

    @GetMapping("/pictures")
    public ModelAndView importPictures() throws IOException {
        String picturesXmlFileContent = this.pictureService.readPicturesXmlFile();

        return super.view("xml/import-pictures", "pictures", picturesXmlFileContent);
    }

    @PostMapping("/pictures")
    public ModelAndView importPicturesConfirm(@ModelAttribute(name = "pictures") String picturesXml) throws JAXBException, IOException {
        System.out.println(this.pictureService.importPictures(picturesXml));

        return super.redirect("/import/xml");
    }

    @GetMapping("/teams")
    public ModelAndView importTeams() throws IOException {
        String teamsXmlFileContent = this.teamService.readTeamsXmlFile();

        return super.view("xml/import-teams", "teams", teamsXmlFileContent);
    }

    @PostMapping("/teams")
    public ModelAndView importTeamsConfirm(@ModelAttribute(name = "teams") String teamsXml) throws JAXBException, IOException {
        System.out.println(this.teamService.importTeams(teamsXml));

        return super.redirect("/import/xml");
    }

    @GetMapping("/players")
    public ModelAndView importPlayers() throws IOException {
        String playersXmlFileContent = this.playerService.readPlayersJsonFile();

        return super.view("json/import-players", "players", playersXmlFileContent);
    }

    @PostMapping("/players")
    public ModelAndView importPlayersConfirm(@ModelAttribute(name = "players") String playersJson) throws IOException {
        System.out.println(this.playerService.importPlayers(playersJson));

        return super.redirect("/import/json");
    }
}
