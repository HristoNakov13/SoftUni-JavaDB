package hiberspring.service.implementation;

import com.google.gson.Gson;
import hiberspring.common.Constants;
import hiberspring.domain.dtos.EmployeeCardSeedDto;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.service.EmployeeCardService;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class EmployeeCardServiceImpl implements EmployeeCardService {

    private static final String EMPLOYEE_CARDS_FILENAME = "employee-cards.json";

    private EmployeeCardRepository employeeCardRepository;
    private ModelMapper modelMapper;
    private FileUtil fileUtil;
    private ValidationUtil validator;
    private Gson gson;


    public EmployeeCardServiceImpl(EmployeeCardRepository employeeCardRepository, ModelMapper modelMapper, FileUtil fileUtil, ValidationUtil validator, Gson gson) {
        this.employeeCardRepository = employeeCardRepository;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.validator = validator;
        this.gson = gson;
    }

    @Override
    public Boolean employeeCardsAreImported() {
        return this.employeeCardRepository.count() > 0;
    }

    @Override
    public String readEmployeeCardsJsonFile() throws IOException {
        return this.fileUtil.readFile(Constants.PATH_TO_FILES + EMPLOYEE_CARDS_FILENAME);
    }

    @Override
    public String importEmployeeCards(String employeeCardsFileContent) {
        EmployeeCardSeedDto[] employeeCards = this.gson.fromJson(employeeCardsFileContent, EmployeeCardSeedDto[].class);
        StringBuilder result = new StringBuilder();

        Arrays.stream(employeeCards).forEach(card -> {
            if (this.validator.isValid(card) && this.isUniqueCardNumberInDb(card.getNumber())) {
                result.append(
                        String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
                                "Employee Card",
                                card.getNumber()))
                        .append(System.lineSeparator());

                EmployeeCard cardEntity = this.modelMapper.map(card, EmployeeCard.class);
                this.employeeCardRepository.saveAndFlush(cardEntity);

                return;
            }

            result.append(Constants.INCORRECT_DATA_MESSAGE)
                    .append(System.lineSeparator());

        });

        return result.toString()
                .trim();
    }

    @Override
    public EmployeeCard getCardByNumber(String number) {
        return this.employeeCardRepository.findEmployeeCardByNumber(number);
    }

    private boolean isUniqueCardNumberInDb(String cardNumber) {
        return this.employeeCardRepository.findEmployeeCardByNumber(cardNumber) == null;
    }
}
