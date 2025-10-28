package Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import DTO.FineDTO;
import Exceptions.BusinessRuleException;
import Exceptions.DataAccessException;
import Exceptions.DataCreationException;
import Exceptions.FormatErrorException;
import Exceptions.ResourceNotFoundException;
import Model.Fine;
import Repository.Interface.FineRepository;

public class FineService {
    
    private final FineRepository fineRepository;

    public FineService(FineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    public Fine createFine(Fine fineData) throws DataCreationException, BusinessRuleException {
        Optional<Fine> existingActiveFine = fineRepository.findActiveFineByLoanId(fineData.getLoan());
        if (existingActiveFine.isPresent()) {
            throw new BusinessRuleException("Cannot have multiples fines to the same loan");
        }

        Fine fine = new Fine(fineData.getLoan(), fineData.getIdUser(), fineData.getAmount());
        fineRepository.create(fine);

        return fine;
    }

    public List<Fine> readAllFines() throws DataAccessException {
        return fineRepository.findAll();
    }

    public List<Fine> findFinesByUserId(Integer id) throws DataAccessException {
        return fineRepository.findFineByUserId(id);
    }

    public void updateFine(Integer id, FineDTO fineData) throws DataAccessException, ResourceNotFoundException, BusinessRuleException {
        Fine existingFine = fineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Fine not found with ID: " + id));
        if (fineData.getPaymentDate().isBefore(existingFine.getIssueDate())) {
            throw new BusinessRuleException("WARNING: Invalid value for payment date");
        }
        existingFine.setPaymentDate(fineData.getPaymentDate());
        fineRepository.update(existingFine);
    }

    public boolean isCreateFine(LocalDate expectedDate, LocalDate actualReturn) {
        return actualReturn.isAfter(expectedDate);
    }

    public void deleteFine(Integer id) throws DataAccessException {
        fineRepository.delete(id);
    }

        public static LocalDate isValidDate(String dateString) throws FormatErrorException {
        if (dateString.isEmpty()) {
            throw new FormatErrorException("Invalid value for return date");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate convertedDate;

        try {
            convertedDate = LocalDate.parse(dateString, formatter);
            return convertedDate;
        } catch (DateTimeParseException e) {
            throw new FormatErrorException("Couldn't convert: " + dateString + " to a Date");
        }
    }
}
