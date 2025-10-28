package Service;

import java.util.List;
import java.util.Optional;

import DTO.CopyDTO;
import Exceptions.BusinessRuleException;
import Exceptions.DataAccessException;
import Exceptions.DataCreationException;
import Exceptions.FormatErrorException;
import Exceptions.ResourceNotFoundException;
import Model.BookCopy;
import Model.Enum.ItemStatus;
import Repository.Interface.BookCopyRepository;


// Operações remetente ao usuário de tipo Librarian
// Cadastrar cópias de um livro
// Edtiar cópias de um livro
// Remover cópias de um livro

// TO-DO 21/10/2025
// Create copies ✅
// Read copies ✅
// Update copies ✅
// Update copy status ✅
// Delete copies ✅

public class BookCopyService {
    
    private final BookCopyRepository copyRepository;

    public BookCopyService(BookCopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    public BookCopy createCopy(Integer id, String barcode, String locationCode) throws DataCreationException, BusinessRuleException {
        Optional<BookCopy> bookWithSameBarcode = copyRepository.findByBarCode(barcode);
        if (bookWithSameBarcode.isPresent()) {
            throw new BusinessRuleException("Barcodes must be unique");
        }

        try {
            isValidBarcode(barcode);
        } catch (FormatErrorException e) {
            throw new BusinessRuleException(e.getMessage());
        }

        BookCopy copy = new BookCopy(id, barcode, locationCode);
        copyRepository.create(copy);

        return copy;
    }

    public BookCopy copyByBarcode(String barcode) throws DataAccessException, ResourceNotFoundException {
        Optional<BookCopy> optionalCopy = copyRepository.findByBarCode(barcode);
        return optionalCopy.orElseThrow(() -> new ResourceNotFoundException("Couldn't find copy with barcode: " + barcode));
    }

    public List<BookCopy> readAllCopies() throws DataAccessException {
        return copyRepository.findAll();
    }

    public List<BookCopy> findCopiesByBook(Integer id) throws DataAccessException {
        System.out.print("Find copies foi chamado com ID: " + id);
        return copyRepository.findByBook(id);
    }

    public BookCopy findCopyById(Integer id) throws DataAccessException {
        Optional<BookCopy> optionalCopy = copyRepository.findById(id);
        return optionalCopy.orElseThrow(() -> new ResourceNotFoundException("Couldn't find copy with id: " + id));
    }

    public void updateCopy(Integer id, CopyDTO copyData) throws DataAccessException, ResourceNotFoundException, BusinessRuleException {
        BookCopy existingCopy = copyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Copy not found with ID: " + id + ", couldn't update data"));
        Optional<BookCopy> bookWithSameBarcode = copyRepository.findByBarCode(copyData.getBarcode());

        if (bookWithSameBarcode.isPresent() && !bookWithSameBarcode.get().getIdCopy().equals(id)) {
            throw new BusinessRuleException("The barcode: " + copyData.getBarcode() + " is already in use by another copy");
        }

        existingCopy.setBarcode(copyData.getBarcode());
        existingCopy.setLocationCode(copyData.getLocationCode());
        copyRepository.update(existingCopy);
    }

    public void updateCopyStatus(Integer id, ItemStatus status) throws DataAccessException, ResourceNotFoundException {
        BookCopy existingCopy = copyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Copy not found with ID: " + id + ", couldn't update status"));
        existingCopy.setStatus(status);
        copyRepository.update(existingCopy);
    }

    public void deleteCopy(Integer id) throws DataAccessException, ResourceNotFoundException {
        if (copyRepository.findById(id).isPresent()) {
            copyRepository.delete(id);
        } else {
            throw new ResourceNotFoundException("Copy not found with ID: " + id);
        }
        
    }

    private static boolean isValidBarcode(String barcode) throws FormatErrorException {
        if (barcode == null || barcode.trim().isEmpty()) {
            throw new FormatErrorException("Barcode format error: value is null or empty");
        }

        String trimmedCode = barcode.trim();
        boolean isValid = trimmedCode.matches("\\d+");

        if (!isValid) {
            throw new FormatErrorException("Barcode can only contain numbers");
        }

        return isValid;
    }

}
