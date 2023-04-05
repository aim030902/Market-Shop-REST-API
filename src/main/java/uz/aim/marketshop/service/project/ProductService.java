package uz.aim.marketshop.service.project;

import uz.aim.marketshop.domains.ImageProduct;
import uz.aim.marketshop.domains.Product;
import uz.aim.marketshop.dtos.product.ProductCreateDto;
import uz.aim.marketshop.dtos.product.ProductDto;
import uz.aim.marketshop.dtos.product.ProductUpdateDto;
import uz.aim.marketshop.exceptions.GenericNotFoundException;
import uz.aim.marketshop.mapper.ProductMapper;
import uz.aim.marketshop.repository.project.ImageProductRepository;
import uz.aim.marketshop.repository.project.ProductRepository;
import uz.aim.marketshop.service.auth.AuthUserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final StorageService storageService;
    private final CategoryService categoryService;

    private final ProductMapper mapper;
    private final AuthUserService userService;
    private final ImageProductRepository imageProductRepository;

    public Long create(@NonNull ProductCreateDto dto) {
        return repository.save(Product.childBuilder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .companyName(dto.getCompanyName())
                .expiry(dto.getExpiry())
                .category(categoryService.getCategory(dto.getCategoryId()))
                .createdBy(userService.getCurrentAuthUser().getId())
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build()).getId();
    }

    public ProductDto get(@NonNull Long id) {
        Product product = repository.get(id).orElseThrow(() -> {
            throw new GenericNotFoundException("Product not found!", 404);
        });
        ProductDto dto = mapper.toDto(product);
        dto.setImages(getImagesByProductId(product.getId()));
        dto.setCategoryId(categoryService.get(product.getCategory().getId()).getId());
        return dto;
    }

    private String[] getImagesByProductId(@NonNull Long id) {
        List<ImageProduct> imageProductList = imageProductRepository.getImagesByProductId(id).orElseThrow(() -> {
            throw new GenericNotFoundException("Images not found!", 404);
        });
        String[] images = new String[imageProductList.size()];
        int counter = 0;
        for (ImageProduct imageProduct : imageProductList) {
            images[counter++] = imageProduct.getPath();
        }
        return images;
    }

    Product getOne(@NonNull Long id) {
        return repository.get(id).orElseThrow(() -> {
            throw new GenericNotFoundException("Product not found!", 404);
        });
    }

    public List<ProductDto> getAll() {
        List<ProductDto> productDtos = new ArrayList<>();
        repository.getAll().orElseThrow(() -> {
            throw new GenericNotFoundException("Product not found!", 404);
        }).forEach(product -> productDtos.add(this.get(product.getId())));
        return productDtos;
    }

    public ProductDto update(@NonNull ProductUpdateDto dto) {
        Product product = getOne(dto.getId());
        mapper.update(dto, product);
        product.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        product.setUpdatedBy(userService.getCurrentAuthUser().getId());
        product.setCategory(categoryService.getCategory(dto.getCategoryId()));
        repository.save(product);
        return this.get(product.getId());
    }

    public Long delete(@NonNull Long id) {
        Product product = getOne(id);
        product.setDeleted(true);
        return repository.save(product).getId();
    }

    public List<ProductDto> getAllByCategory(@NonNull Long categoryId) {
        List<ProductDto> productDtos = new ArrayList<>();
        repository.getAllByCategory(categoryId).orElseThrow(() -> {
            throw new GenericNotFoundException("Product not found!", 404);
        }).forEach(product -> productDtos.add(this.get(product.getId())));
        return productDtos;
    }

    public void uploadCover(@NonNull Long id, @NonNull MultipartFile file) {
        imageProductRepository.save(storageService.uploadCoverProduct(file, this.getOne(id)));
    }

    public List<ProductDto> search(@NonNull String search) {
        return repository.search(search).orElse(new ArrayList<>())
                .stream().map(product -> get(product.getId())).collect(Collectors.toList());
    }
}
