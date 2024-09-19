package com.example.bookstore.service.serviceimpl;

import com.example.bookstore.model.Category;
import com.example.bookstore.payload.response.MessageResponse;
import com.example.bookstore.repository.CategoryRepository;
import com.example.bookstore.service.serviceinterface.ICategoryService;
import org.hibernate.ObjectDeletedException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> selectAllCategory() {
        try {
            List<Category> res = categoryRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Lỗi Server. Vui lòng thử lại sau!"));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> insertCategory(Category category) {
        try {
            categoryRepository.save(category);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Lỗi Server. Vui lòng thử lại sau!"));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> updateCategory(Category category, String id) {
        try {
            Category cate = categoryRepository.findById(UUID.fromString(id)).orElseThrow();
            cate.setCategoryName(category.getCategoryName());
            categoryRepository.save(cate);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Cập nhật thể loại thành công!"));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Lỗi Server. Vui lòng thử lại sau!"));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> deleteCategory(String id) {
        try {
            categoryRepository.deleteById(UUID.fromString(id));
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Xóa thành công!"));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Lỗi Server. Vui lòng thử lại sau!"));
        }
    }

}
