package org.innopolis.cookainno.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/calories")
@Tag(name = "Calories")
public interface CalorieAPI {

    @Operation(summary = "Calculate daily kilocalories to gain weight")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calories calculated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping("/gain-weight")
    ResponseEntity<Integer> calculateCaloriesToGainWeight(
            @RequestParam double weight, @RequestParam double height);

    @Operation(summary = "Calculate daily kilocalories to maintain weight")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calories calculated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping("/maintain-weight")
    ResponseEntity<Integer> calculateCaloriesToMaintainWeight(
            @RequestParam double weight, @RequestParam double height);

    @Operation(summary = "Calculate daily kilocalories to lose weight")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calories calculated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping("/lose-weight")
    ResponseEntity<Integer> calculateCaloriesToLoseWeight(
            @RequestParam double weight, @RequestParam double height);
}
