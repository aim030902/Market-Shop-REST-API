package uz.aim.marketshop.service.project;

import uz.aim.marketshop.domains.Category;
import uz.aim.marketshop.domains.ImageCategory;
import uz.aim.marketshop.dtos.category.CategoryCreateDto;
import uz.aim.marketshop.dtos.category.CategoryDto;
import uz.aim.marketshop.dtos.category.CategoryUpdateDto;
import uz.aim.marketshop.exceptions.GenericNotFoundException;
import uz.aim.marketshop.mapper.CategoryMapper;
import uz.aim.marketshop.repository.project.CategoryRepository;
import uz.aim.marketshop.repository.project.ImageCategoryRepository;
import uz.aim.marketshop.service.auth.AuthUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private final AuthUserService userService;
    private final ImageCategoryRepository imageCategoryRepository;
    private final StorageService storageService;

    public Long create(@NonNull CategoryCreateDto dto) {
        return repository.save(Category.childBuilder()
                .name(dto.getName())
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .createdBy(userService.getCurrentAuthUser().getId())
                .build()).getId();
    }

    public CategoryDto get(@NonNull Long id) {
        Category category = repository.get(id).orElseThrow(() -> {
            throw new GenericNotFoundException("Category is not found!", 404);
        });
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .images(getImagesByCategoryId(category.getId()))
                .createdBy(category.getCreatedBy())
                .createdAt(category.getCreatedAt())
                .build();
    }

    public Category getCategory(@NonNull Long id) {
        return repository.get(id).orElseThrow(() -> {
            throw new GenericNotFoundException("Category not found!", 404);
        });
    }

    public List<CategoryDto> getAll() {
        return repository.getAll().stream().map(category ->
                get(category.getId())
        ).collect(Collectors.toList());
    }

    private String[] getImagesByCategoryId(@NonNull Long id) {
        List<ImageCategory> imageCategoryList = imageCategoryRepository.getImagesByCategoryId(id).orElseThrow(() -> {
            throw new GenericNotFoundException("Images not found!", 404);
        });
        String[] images = new String[imageCategoryList.size()];
        int counter = 0;
        for (ImageCategory imageCategory : imageCategoryList) {
            images[counter++] = imageCategory.getPath();
        }
        return images;
    }

    public Category update(@NonNull CategoryUpdateDto dto) {
        Category category = getCategory(dto.getId());
        mapper.update(dto, category);
        category.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        category.setUpdatedBy(userService.getCurrentAuthUser().getId());
        return repository.save(category);
    }

    public Long delete(@NonNull Long id) {
        Category category = getCategory(id);
        category.setDeleted(true);
        return repository.save(category).getId();
    }

    public void uploadCover(Long id, MultipartFile file) {
        imageCategoryRepository.save(storageService.uploadCoverCategory(file, repository.findById(id).orElseThrow(() -> {
            throw new GenericNotFoundException("Category not found!", 404);
        })));
    }
}
