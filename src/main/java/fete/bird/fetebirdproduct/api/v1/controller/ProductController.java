package fete.bird.fetebirdproduct.api.v1.controller;

import fete.bird.fetebirdproduct.service.consumer.ProductTopics;
import fete.bird.fetebirdproduct.service.producer.IProductProducer;
import fete.bird.fetebirdproduct.viewmodels.ProductViewModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Tag(name = "Product Version-1", description = "Add/Update/Delete/Search - Product")
@RestController("ProductV1Controller")
@RequestMapping(path = "api/v1/product")
@SecurityRequirement(name = "security_auth")
public record ProductController(IProductProducer _productProducer) {
    @Operation(summary = "Find product by Id")
    @ApiResponses(value = {@ApiResponse(content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductViewModel.class))})})
    @GetMapping("/{id}")
    ProductViewModel Product(@PathVariable String id) throws InterruptedException, ExecutionException, TimeoutException{
        return _productProducer.GetProduct(id);
    }

    @Operation(summary = "Find all products")
    @ApiResponses(value = {@ApiResponse(content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductViewModel.class))})})
    @GetMapping()
    List<ProductViewModel> Products() throws InterruptedException, ExecutionException, TimeoutException {
        return _productProducer.GetProducts();
    }

    @Operation(summary = "Add new product")
    @ApiResponses(value = {@ApiResponse(content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductViewModel.class))})})
    @PostMapping()
    void AddProduct(@Valid @RequestBody ProductViewModel product) {
        _productProducer.AddProduct(product);
    }

    @Operation(summary = "Update a product by Id")
    @ApiResponses(value = {@ApiResponse(content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductViewModel.class))})})
    @PutMapping("{id}")
    ProductViewModel UpdateProduct(@Valid @RequestBody ProductViewModel Product, @PathVariable String id) {
        _productProducer.UpdateProduct(Product, id);
        return new ProductViewModel("","",3,"");
    }

    @Operation(summary = "Update a product by Id using patch method")
    @ApiResponses(value = {@ApiResponse(content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductViewModel.class))})})
    @PatchMapping("{id}")
    ProductViewModel PatchProduct(@RequestBody ProductViewModel Product, @PathVariable String id) {
        _productProducer.UpdateProduct(Product, id);
        return new ProductViewModel("","",1,"");
    }

    @Operation(summary = "Delete a product by Id")
    @ApiResponses(value = {@ApiResponse(content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductViewModel.class))})})
    @DeleteMapping("{id}")
    void DeleteProduct(@PathVariable String id) {
        _productProducer.DeleteProduct(id);
    }
}

