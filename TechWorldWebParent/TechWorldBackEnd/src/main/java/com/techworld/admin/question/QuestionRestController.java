package com.techworld.admin.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionRestController {
    @Autowired private QuestionService questionService;

    @GetMapping("/questions/{id}/approval/{status}")
    public ResponseEntity<String> updateApprovalStatus(@PathVariable("id") Integer id,
                                                              @PathVariable("status") boolean approval){
        questionService.updateQuestionApprovalStatus(id, approval);
        String status = approval ? "approved" : "not approved";
        String message = "The question ID " + id + " has been " + status;
        return ResponseEntity.ok(message);
    }
}
