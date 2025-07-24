package gift.controller;

import gift.model.Options;
import gift.service.OptionsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class OptionsController {
    private final OptionsService optionsService;

    public OptionsController(OptionsService optionsService) {
        this.optionsService = optionsService;
    }

    @GetMapping("/{product_id}/options")
    public ResponseEntity<List<Options>> getOptions(@PathVariable Long product_id) {
        List<Options> optionsList = optionsService.getOptions(product_id);
        return ResponseEntity.status(HttpStatus.OK).body(optionsList);
    }

    @PostMapping("/{product_id}/options")
    public ResponseEntity<Options> newOptions(@Valid @RequestBody Options options, @PathVariable Long product_id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(optionsService.newOptions(product_id, options));
    }

}
