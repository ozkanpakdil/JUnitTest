package fete.bird.fetebirdproduct.viewmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public record ProductViewModel(@JsonProperty("id") @Null String id,
        @JsonProperty("name") @NotBlank(message = "Name is mandatory")String name,
        @JsonProperty("price") @NotNull float price,
        @JsonProperty("description") @NotBlank(message = "Description is mandatory")String description) {
}
