package softuni.exam.service;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.entities.Team;
import softuni.exam.domain.models.binding.team.ListTeamCreateBindingModel;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.constants.FilePaths;
import softuni.exam.util.parser.XmlParser;


import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private PictureService pictureService;
    private ValidatorUtil validator;
    private ModelMapper modelMapper;
    private XmlParser xmlParser;

    public TeamServiceImpl(TeamRepository teamRepository, PictureService pictureService, ValidatorUtil validator, ModelMapper modelMapper, XmlParser xmlParser) {
        this.teamRepository = teamRepository;
        this.pictureService = pictureService;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public String importTeams() throws JAXBException, IOException {
        ListTeamCreateBindingModel listTeam = this.xmlParser.fromXmlToObject(FilePaths.TEAMS_XML, ListTeamCreateBindingModel.class);

       List<Team> teams = listTeam
               .getTeams()
               .stream()
               .filter(this.validator::isValid)
               .map(currentTeam -> {
                  Team team = this.modelMapper.map(currentTeam, Team.class);
                  team.setPicture(this.pictureService.getPictureByUrl(team.getPicture().getUrl()));

                  return team;
               }).filter(team -> team.getPicture() != null)
               .collect(Collectors.toList());

        this.teamRepository.saveAll(teams);

        return "";
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return Files.readString(Paths.get(FilePaths.TEAMS_XML));
    }

    @Override
    public Team getTeamByName(String teamName) {
        return this.teamRepository.findTeamByName(teamName);
    }
}
