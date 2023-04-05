package uz.aim.marketshop.controller;

import uz.aim.marketshop.dtos.basket.OrderCreateDto;
import uz.aim.marketshop.dtos.basket.OrderDto;
import uz.aim.marketshop.response.ApiResponse;
import uz.aim.marketshop.service.project.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class OrderController extends ApiController<OrderService> {

    protected OrderController(OrderService service) {
        super(service);
    }

    @PostMapping(PATH + "/order/book")
    @PreAuthorize(value = "isAuthenticated()")
    public ApiResponse<Void> order(@Valid @RequestBody OrderCreateDto dto) {
        service.order(dto);
        return new ApiResponse<>(200, true);
    }

    @GetMapping(PATH + "/order/getAll")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<List<OrderDto>> getAll() {
        return new ApiResponse<>(service.getAll(), 200);
    }

    @GetMapping(PATH + "/order/get/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<OrderDto> get(@PathVariable Long id) {
        return new ApiResponse<>(service.get(id));
    }

    @GetMapping(PATH + "/order/basket")
    @PreAuthorize(value = "isAuthenticated()")
    public ApiResponse<OrderDto> basket() {
        return new ApiResponse<>(service.basket());
    }

    @PostMapping(PATH + "/order/addToBasket/{productId}")
    @PreAuthorize(value = "isAuthenticated()")
    public ApiResponse<Void> addToBasket(@PathVariable Long productId) {
        service.addToBasket(productId);
        return new ApiResponse<>(200, true);
    }

    @PostMapping(PATH + "/order/mark/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<Void> mark(@PathVariable Long id) {
        service.mark(id);
        return new ApiResponse<>(200, true);
    }

    @DeleteMapping(PATH + "/order/delete/{orderItemId}")
    @PreAuthorize(value = "isAuthenticated()")
    public ApiResponse<OrderDto> delete(@PathVariable Long orderItemId) {
        return new ApiResponse<>(service.deleteOrderItem(orderItemId));
    }
}
