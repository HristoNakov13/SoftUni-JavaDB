package hiberspring.service.implementation;

import hiberspring.common.Constants;
import hiberspring.domain.dtos.EmployeeSeedDto;
import hiberspring.domain.dtos.EmployeesRootSeedDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Employee;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.EmployeeRepository;
import hiberspring.service.BranchService;
import hiberspring.service.EmployeeCardService;
import hiberspring.service.EmployeeService;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String EMPLOYEES_FILENAME = "employees.xml";

    private EmployeeRepository employeeRepository;
    private BranchService branchService;
    private EmployeeCardService employeeCardService;
    private XmlParser xmlParser;
    private ValidationUtil validator;
    private ModelMapper modelMapper;
    private FileUtil fileUtil;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, BranchService branchService, EmployeeCardService employeeCardService, XmlParser xmlParser, ValidationUtil validator, ModelMapper modelMapper, FileUtil fileUtil) {
        this.employeeRepository = employeeRepository;
        this.branchService = branchService;
        this.employeeCardService = employeeCardService;
        this.xmlParser = xmlParser;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;

        this.init();
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return this.fileUtil.readFile(Constants.PATH_TO_FILES + EMPLOYEES_FILENAME);
    }

    @Override
    public String importEmployees() throws JAXBException {
        List<EmployeeSeedDto> employees = this.xmlParser
                .parseXml(EmployeesRootSeedDto.class, Constants.PATH_TO_FILES + EMPLOYEES_FILENAME)
                .getEmployees();
        StringBuilder result = new StringBuilder();

        List<Employee> employeeEntities = employees.stream()
                .filter(employee -> {
                    if (this.validator.isValid(employee)
                            && this.employeeCardService.getCardByNumber(employee.getCard()) != null
                            && this.branchService.getBranchByName(employee.getBranch()) != null) {

                        result.append(
                                String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
                                        employee.getClass().getSimpleName(),
                                        String.format("%s %s", employee.getFirstName(), employee.getLastName())))
                                .append(System.lineSeparator());

                        return true;
                    }
                    result.append(Constants.INCORRECT_DATA_MESSAGE)
                            .append(System.lineSeparator());

                    return false;

                })
                .map(employee -> this.modelMapper.map(employee, Employee.class))
                .collect(Collectors.toList());

        this.employeeRepository.saveAll(employeeEntities);

        return result.toString()
                .trim();
    }

    @Override
    @Transactional
    public String exportProductiveEmployees() {
        String format = "Name: %s %s\r\nPosition: %s\r\nCard Number: %s";

        return this.employeeRepository
                .findEmployeesWorkingInBranchWithProducts()
                .stream()
                .map(employee ->
                        String.format(format,
                                employee.getFirstName(),
                                employee.getLastName(),
                                employee.getPosition(),
                                employee.getCard().getNumber()))
                .collect(Collectors.joining("\r\n-------------------------\r\n"));
    }

    private void init() {
        Converter<String, Branch> branchConverter = new Converter<String, Branch>() {
            @Override
            public Branch convert(MappingContext<String, Branch> mappingContext) {
                return branchService.getBranchByName(mappingContext.getSource());
            }
        };

        Converter<String, EmployeeCard> employeeCardConverter = new Converter<String, EmployeeCard>() {
            @Override
            public EmployeeCard convert(MappingContext<String, EmployeeCard> mappingContext) {
                return employeeCardService.getCardByNumber(mappingContext.getSource());
            }
        };

        this.modelMapper.createTypeMap(EmployeeSeedDto.class, Employee.class)
                .addMappings(mapper -> mapper.using(branchConverter).map(EmployeeSeedDto::getBranch, Employee::setBranch))
                .addMappings(mapper -> mapper.using(employeeCardConverter).map(EmployeeSeedDto::getCard, Employee::setCard));
    }
}
