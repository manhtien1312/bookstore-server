package com.example.bookstore.service.serviceimpl;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.dto.mapper.BookDtoMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import com.example.bookstore.payload.response.MessageResponse;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CategoryRepository;
import com.example.bookstore.service.serviceinterface.IBookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookDtoMapper bookDtoMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ResponseEntity<?> getAllBook() {
        try {
            List<Book> books = bookRepository.findAll();
            if (books.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            List<BookDto> res = books.stream()
                    .map(bookDtoMapper)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(res.subList(0, 11));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Lỗi Server. Vui lòng thử lại sau!"));
        }
    }

    @Override
    public ResponseEntity<?> filterBooks(String category) {
        try {
            List<Book> books = bookRepository.findByCategory(category);
            if (books.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            List<BookDto> res = books.stream()
                    .map(bookDtoMapper)
                    .toList();
            return ResponseEntity.status(HttpStatus.OK).body(res.size());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Lỗi Server. Vui lòng thử lại sau!"));
        }
    }

    @Override
    public ResponseEntity<?> searchBooks(String searchText) {
        try {
            List<Book> books = bookRepository.search(searchText);
            if (books.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            List<BookDto> res = books.stream()
                    .map(bookDtoMapper)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Lỗi Server. Vui lòng thử lại sau!"));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> insertBook(String bookDtoStr, MultipartFile image) {
        try {
            BookDto bookDto = objectMapper.readValue(bookDtoStr, BookDto.class);

            if(bookDto.title().isBlank()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Tiêu đề sách không được để trống!"));
            }
            if(bookDto.author().isBlank()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Tác giả không được để trống!"));
            }
            if(bookDto.categories().isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Thể loại sách không được để trống!"));
            }

            List<Category> bookCategory = categoryRepository.findAll()
                    .stream()
                    .filter(category -> bookDto.categories().contains(category.getCategoryName()))
                    .toList();

            var product = Book.builder()
                    .title(bookDto.title())
                    .author(bookDto.author())
                    .description(bookDto.description())
                    .price(bookDto.price())
                    .inventory(bookDto.inventory())
                    .image(image.getBytes())
                    .categories(bookCategory)
                    .build();
            bookRepository.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Lỗi Server. Vui lòng thử lại sau!"));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> insertThroughFile(MultipartFile booksFile) throws IOException{
        try {
            List<Book> books = new ArrayList<>();
            XSSFWorkbook workbook = new XSSFWorkbook(booksFile.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            List<XSSFPictureData> coverImages = extractImage(workbook);
            for (int i=1; i<sheet.getPhysicalNumberOfRows(); i++){
                XSSFRow row = sheet.getRow(i);

                var book = Book.builder()
                        .title(row.getCell(0).getStringCellValue())
                        .author(row.getCell(1).getStringCellValue())
                        .description(row.getCell(2).getStringCellValue())
                        .price((int) row.getCell(3).getNumericCellValue())
                        .inventory((int) row.getCell(4).getNumericCellValue())
                        .image(coverImages.get(i-1).getData())
                        .categories(
                                categoryRepository.findAll().stream()
                                        .filter(category -> row.getCell(6).getStringCellValue().equals(category.getCategoryName()))
                                        .toList()
                        )
                        .build();

                books.add(book);
            }
            bookRepository.saveAll(books);
            workbook.close();
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Lỗi Server. Vui lòng thử lại sau!"));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> deleteBook(String id) {
        try {
            bookRepository.deleteById(UUID.fromString(id));
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Xóa thành công!"));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Lỗi Server. Vui lòng thử lại sau!"));
        }
    }

    private List<XSSFPictureData> extractImage(Workbook workbook) throws IOException {
        if (workbook instanceof XSSFWorkbook) {
            XSSFWorkbook xssfWorkbook = (XSSFWorkbook) workbook;
            List<XSSFPictureData> pictures = xssfWorkbook.getAllPictures();

            if (!pictures.isEmpty()) {
                return pictures;
            }
        }
        return null;
    }
}
