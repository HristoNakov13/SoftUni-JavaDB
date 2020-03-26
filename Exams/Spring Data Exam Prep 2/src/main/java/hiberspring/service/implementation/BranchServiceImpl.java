package hiberspring.service.implementation;

import com.google.gson.Gson;
import hiberspring.common.Constants;
import hiberspring.domain.dtos.BranchSeedDto;
import hiberspring.domain.dtos.TownSeedDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Town;
import hiberspring.repository.BranchRepository;
import hiberspring.service.BranchService;
import hiberspring.service.TownService;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BranchServiceImpl implements BranchService {

    private static final String BRANCH_FILENAME = "branches.json";
    private BranchRepository branchRepository;
    private FileUtil fileUtil;
    private ModelMapper modelMapper;
    private TownService townService;
    private ValidationUtil validator;
    private Gson gson;

    public BranchServiceImpl(BranchRepository branchRepository, FileUtil fileUtil, ModelMapper modelMapper, TownService townService, ValidationUtil validator, Gson gson) {
        this.branchRepository = branchRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.townService = townService;
        this.validator = validator;
        this.gson = gson;

        this.init();
    }

    @Override
    public Boolean branchesAreImported() {
        return this.branchRepository.count() > 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return this.fileUtil.readFile(Constants.PATH_TO_FILES + BRANCH_FILENAME);
    }

    @Override
    public String importBranches(String branchesFileContent) {
        BranchSeedDto[] branches = this.gson.fromJson(branchesFileContent, BranchSeedDto[].class);
        StringBuilder result = new StringBuilder();

        List<Branch> branchEntities = Arrays.stream(branches)
                .filter(branch -> {
                    if (this.validator.isValid(branch) && this.townService.getTownByName(branch.getTown()) != null) {
                        result.append(
                                String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
                                        branch.getClass().getSimpleName(),
                                        branch.getName()))
                                .append(System.lineSeparator());

                        return true;
                    }
                    result.append(Constants.INCORRECT_DATA_MESSAGE)
                            .append(System.lineSeparator());

                    return false;})
                .map(branch -> this.modelMapper.map(branch, Branch.class))
                .collect(Collectors.toList());

        this.branchRepository.saveAll(branchEntities);

        return result.toString()
                .trim();
    }

    @Override
    public Branch getBranchByName(String branchName) {
        return this.branchRepository.findBranchByName(branchName);
    }

    private void init() {
        Converter<String, Town> townFetchConverter = new Converter<String, Town>() {
            @Override
            public Town convert(MappingContext<String, Town> mappingContext) {
                return townService.getTownByName(mappingContext.getSource());
            }
        };

        this.modelMapper.createTypeMap(BranchSeedDto.class, Branch.class)
                .addMappings(mapper -> mapper.using(townFetchConverter).map(BranchSeedDto::getTown, Branch::setTown));
    }
}
