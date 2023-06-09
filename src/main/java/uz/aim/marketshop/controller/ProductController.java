package uz.aim.marketshop.controller;

import uz.aim.marketshop.dtos.product.ProductCreateDto;
import uz.aim.marketshop.dtos.product.ProductDto;
import uz.aim.marketshop.dtos.product.ProductUpdateDto;
import uz.aim.marketshop.response.ApiResponse;
import uz.aim.marketshop.service.project.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController extends ApiController<ProductService> {

    protected ProductController(ProductService service) {
        super(service);
    }

    @PostMapping(PATH + "/product/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Long> create(@Valid @RequestBody ProductCreateDto dto) {
        return new ApiResponse<>(service.create(dto));
    }

    @GetMapping(PATH + "/search")
    public ApiResponse<List<ProductDto>> search(@RequestParam("search") String search) {
        return new ApiResponse<>(service.search(search));
    }

    @GetMapping(PATH + "/product/get/{id}")
    public ApiResponse<ProductDto> get(@PathVariable Long id) {
        return new ApiResponse<>(service.get(id));
    }

    @GetMapping(PATH + "/product/{categoryId}")
    public ApiResponse<List<ProductDto>> getAllByCategory(@PathVariable Long categoryId) {
        return new ApiResponse<>(service.getAllByCategory(categoryId));
    }

    @GetMapping(PATH + "/home")
    public ApiResponse<List<ProductDto>> getAll() {
        return new ApiResponse<>(service.getAll());
    }

    @PutMapping(PATH + "/product/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ProductDto> update(@Valid @RequestBody ProductUpdateDto dto) {
        return new ApiResponse<>(service.update(dto));
    }

    @DeleteMapping(PATH + "/product/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Long> delete(@PathVariable Long id) {
        return new ApiResponse<>(service.delete(id));
    }

    @PostMapping(value = PATH + "/product/uploadCover/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> uploadCover(@RequestBody MultipartFile file, @PathVariable Long id) {
        service.uploadCover(id, file);
        return new ApiResponse<>(200, true);
    }
}
