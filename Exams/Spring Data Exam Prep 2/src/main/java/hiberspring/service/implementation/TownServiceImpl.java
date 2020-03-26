package hiberspring.service.implementation;

import com.google.gson.Gson;
import hiberspring.common.Constants;
import hiberspring.domain.dtos.TownSeedDto;
import hiberspring.domain.entities.Town;
import hiberspring.repository.TownRepository;
import hiberspring.service.TownService;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static hiberspring.common.Constants.PATH_TO_FILES;

@Service
public class TownServiceImpl implements TownService {

    private static final String TOWNS_FILENAME = "towns.json";

    private TownRepository townRepository;
    private ModelMapper modelMapper;
    private FileUtil fileUtil;
    private ValidationUtil validator;
    private Gson gson;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, FileUtil fileUtil, ValidationUtil validator, Gson gson) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.validator = validator;
        this.gson = gson;
    }

    @Override
    public Boolean townsAreImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return this.fileUtil.readFile(PATH_TO_FILES + TOWNS_FILENAME);
    }

    @Override
    public String importTowns(String townsFileContent) {
        TownSeedDto[] towns = this.gson.fromJson(townsFileContent, TownSeedDto[].class);
        StringBuilder result = new StringBuilder();

        List<Town> townEntities = Arrays.stream(towns)
                .filter(town -> {
                    if (this.validator.isValid(town)) {
                        result.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
                                        town.getClass().getSimpleName(),
                                        town.getName()))
                                .append(System.lineSeparator());

                        return true;
                    }
                    result.append(Constants.INCORRECT_DATA_MESSAGE)
                            .append(System.lineSeparator());

                    return false;
                })
                .map(town -> this.modelMapper.map(town, Town.class))
                .collect(Collectors.toList());

        this.townRepository.saveAll(townEntities);

        return result.toString()
                .trim();
    }

    @Override
    public Town getTownByName(String townName) {
        return this.townRepository
                .findByName(townName)
                .orElse(null);
    }
}
