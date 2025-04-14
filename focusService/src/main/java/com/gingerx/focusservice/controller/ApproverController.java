package com.gingerx.focusservice.controller;

import com.gingerx.focusservice.dto.ApproverRequest;
import com.gingerx.focusservice.dto.ApproverResponse;
import com.gingerx.focusservice.response.ApiResponse;
import com.gingerx.focusservice.service.ApproverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/approver")
@RequiredArgsConstructor
public class ApproverController {
    private final ApproverService approverService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ApproverResponse>> create(@RequestBody ApproverRequest approverRequest) {
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS,"Approver Created Successfully",approverService.create(approverRequest)));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ApproverResponse>> update(@RequestBody ApproverRequest approverRequest, @PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS,"Approver Updated Successfully",approverService.update(id,approverRequest)));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<ApproverResponse>> delete(@RequestParam Long id) {
        approverService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS,"Approver Deleted Successfully",null));
    }
}
