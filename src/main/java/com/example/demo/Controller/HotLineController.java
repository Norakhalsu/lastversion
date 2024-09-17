package com.example.demo.Controller;


import com.example.demo.Model.HotLine;
import com.example.demo.Service.HotLineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotline")
public class HotLineController {
    private final HotLineService hotLineService;

    @PostMapping("/add")//ALL
    public ResponseEntity addHotLine(@Valid @RequestBody HotLine hotLine) {
        hotLineService.addHotLineToSystem(hotLine);
        return ResponseEntity.status(200).body("HotLine added successfully");
    }

    @GetMapping("/get-all")//ADMIN
    public ResponseEntity getAllHotLine() {
        return ResponseEntity.status(200).body(hotLineService.getAllHotLines());
    }

    @PutMapping("/update/{hotlineId}")//ADMIN
    private ResponseEntity updateHotLine(@PathVariable Integer hotlineId, @Valid @RequestBody HotLine hotLine) {
        hotLineService.updateHotLine(hotlineId,hotLine);
        return ResponseEntity.status(200).body("HotLine updated successfully");
    }

    @DeleteMapping("/delete/{hotlineId}")//ADMIN
    public ResponseEntity deleteHotLine(@PathVariable Integer hotlineId) {
        hotLineService.deleteHotLineFromSystem(hotlineId);
        return ResponseEntity.status(200).body("HotLine deleted successfully");
    }
    // --------------------------- end point -----------
    @PutMapping("/set-accept/{requestId}")//HOTLINE
    public ResponseEntity setAcceptToRequest(@PathVariable Integer requestId) {
        hotLineService.statusAcceptRequest(requestId);
        return ResponseEntity.status(200).body("Request has been accepted");
    }



}
