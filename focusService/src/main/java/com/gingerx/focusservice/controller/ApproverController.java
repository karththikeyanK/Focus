package com.gingerx.focusservice.controller;

import com.gingerx.focusservice.dto.ApproverDto;
import com.gingerx.focusservice.dto.ApproverRequest;
import com.gingerx.focusservice.dto.ApproverResponse;
import com.gingerx.focusservice.facade.ApproverFacade;
import com.gingerx.focusservice.response.ApiResponse;
import com.gingerx.focusservice.service.ApproverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/approver")
@RequiredArgsConstructor
public class ApproverController {
    private final ApproverService approverService;
    private final ApproverFacade approverFacade;

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

    @PostMapping("/approve/{id}")
    public ResponseEntity<ApiResponse<ApproverResponse>> approve(@PathVariable Long id, @RequestBody ApproverDto approverDto) {
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS,"Approver Approved Successfully",approverFacade.addApprover(id,approverDto)));
    }

    @PostMapping("/confirm/{id}")
    public ResponseEntity<ApiResponse<ApproverResponse>> confirm(@PathVariable Long id, @RequestBody ApproverDto approverDto) {
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS,"Approver Confirmed Successfully",approverFacade.confirmApprover(id, approverDto.getVCode())));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<ApproverResponse>> get(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS,"Approver Retrieved Successfully",approverService.getApproverById(id)));
    }

    @GetMapping("/get-approver-requset-by-approver/{userId}")
    public ResponseEntity<ApiResponse<List<ApproverResponse>>> getByApproverId(@PathVariable Long userId) {
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS,"Approver Retrieved Successfully",approverService.getApproverRequestByApproverId(userId)));
    }


}
