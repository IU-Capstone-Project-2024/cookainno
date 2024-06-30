package org.innopolis.cookainno.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalorieController implements CalorieAPI {

    @Override
    public ResponseEntity<Integer> calculateCaloriesToGainWeight(double weight, double height) {
        int calories = calculateDailyCalories(weight, height, 500);
        return ResponseEntity.ok(calories);
    }

    @Override
    public ResponseEntity<Integer> calculateCaloriesToMaintainWeight(double weight, double height) {
        int calories = calculateDailyCalories(weight, height, 0);
        return ResponseEntity.ok(calories);
    }

    @Override
    public ResponseEntity<Integer> calculateCaloriesToLoseWeight(double weight, double height) {
        int calories = calculateDailyCalories(weight, height, -500);
        return ResponseEntity.ok(calories);
    }

    private int calculateDailyCalories(double weight, double height, int calorieAdjustment) {
        // Simple BMR calculation using the Harris-Benedict equation
        double bmr = 10 * weight + 6.25 * height - 5 * 25 + 5; // Assuming age 25 and male gender
        return (int) (bmr + calorieAdjustment);
    }
}