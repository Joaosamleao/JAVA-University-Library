package Service;

import java.util.List;
import java.util.Optional;

import DTO.CopyUpdateDTO;
import Exceptions.BusinessRuleException;
import Exceptions.DataAccessException;
import Exceptions.DataCreationException;
import Exceptions.ResourceNotFoundException;
import Model.Book;
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

    public BookCopy createCopy(Book book, String barcode, ItemStatus status, String locationCode) throws DataCreationException, BusinessRuleException {
        Optional<BookCopy> bookWithSameBarcode = copyRepository.findByBarCode(barcode);
        if (bookWithSameBarcode.isPresent()) {
            throw new BusinessRuleException("ERROR: Barcodes must be unique");
        }

        BookCopy copy = new BookCopy(book, barcode, status, locationCode);
        copyRepository.create(copy);

        return copy;
    }

    public BookCopy copyByBarcode(String barcode) throws DataAccessException, ResourceNotFoundException {
        Optional<BookCopy> optionalCopy = copyRepository.findByBarCode(barcode);
        return optionalCopy.orElseThrow(() -> new ResourceNotFoundException("ERROR: Couldn't find copy with barcode: " + barcode));
    }

    public List<BookCopy> readAllCopies() throws DataAccessException {
        return copyRepository.findAll();
    }

    public void updateCopy(Integer id, CopyUpdateDTO copyData) throws DataAccessException, ResourceNotFoundException, BusinessRuleException {
        BookCopy existingCopy = copyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ERROR: Copy not found with ID: " + id + ", couldn't update data"));
        Optional<BookCopy> bookWithSameBarcode = copyRepository.findByBarCode(copyData.getBarcode());

        if (bookWithSameBarcode.isPresent() && !bookWithSameBarcode.get().getIdCopy().equals(id)) {
            throw new BusinessRuleException("ERROR: The barcode: " + copyData.getBarcode() + " is already in use by another copy");
        }

        existingCopy.setBarcode(copyData.getBarcode());
        existingCopy.setLocationCode(copyData.getLocationCode());
        copyRepository.update(existingCopy);
    }

    public void updateCopyStatus(Integer id, ItemStatus status) throws DataAccessException, ResourceNotFoundException {
        BookCopy existingCopy = copyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ERROR: Copy not found with ID: " + id + ", couldn't update status"));
        existingCopy.setStatus(status);
        copyRepository.update(existingCopy);
    }

}
