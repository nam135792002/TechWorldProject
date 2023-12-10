package com.techworld.question.vote;

import com.techworld.Utility;
import com.techworld.common.entity.Customer;
import com.techworld.customer.CustomerNotFoundException;
import com.techworld.customer.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoteQuestionRestController {

    @Autowired private QuestionVoteService questionVoteService;
    @Autowired private CustomerService customerService;

    private Customer getAuthenticatsCustomer(HttpServletRequest request) throws CustomerNotFoundException {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        return customerService.getCustomerByEmail(email);
    }

    @PostMapping("/vote_question/{id}/{type}")
    public VoteResult voteQuestion(@PathVariable(name = "id") Integer questionId, @PathVariable(name = "type") String type, HttpServletRequest request) throws CustomerNotFoundException {
        Customer customer = getAuthenticatsCustomer(request);

        if(customer == null){
            return VoteResult.fail("You must login to vote the review");
        }

        VoteType voteType = VoteType.valueOf(type.toUpperCase());
        return questionVoteService.doVote(questionId, customer, voteType);
    }
}
