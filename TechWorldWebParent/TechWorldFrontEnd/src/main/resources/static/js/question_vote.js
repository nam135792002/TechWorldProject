$(document).ready(function () {
   $(".linkVoteQuestion").on("click",function (e) {
      e.preventDefault();
      voteQuestion($(this));
   });
});

function voteQuestion(currentLink) {
    requestURL = currentLink.attr("href");
    $.ajax({
        type: "POST",
        url: requestURL,
        beforeSend: function (xhr){
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        }

    }).done(function(voteResult){
        console.log(voteResult);
        if(!voteResult.successful){
            warning_noti(voteResult.message);
        }else{
            updateQuestionVoteCountAndIcons(currentLink, voteResult);
            anim5_noti(voteResult.message);
        }
    }).fail(function () {
        error_noti("Error while adding product to wishlist.");
    });
}

function updateQuestionVoteCountAndIcons(currentLink, voteResult){
    questionId = currentLink.attr("questionId");
    voteUpLink = $("#linkVoteupQuestion-"+questionId);
    voteDownLink = $("#linkVotedownQuestion-"+questionId);
    $("#voteCountQuestion-"+questionId).text(voteResult.voteCount + " Votes");

    message = voteResult.message;
    if(message.includes("successfully voted up")){
        highlightVoteUpIconQuestion(currentLink, voteDownLink);
    }else if(message.includes("successfully voted down")){
        highlightVoteDownIconQuestion(currentLink, voteUpLink);
    }else if(message.includes("unvoted down")){
        unHighlightVoteDownIconQuestion(voteDownLink);
    }else if(message.includes("unvoted up")){
        unHighlightVoteUpIconQuestion(voteUpLink);
    }
}

function highlightVoteUpIconQuestion(voteUpLink, voteDownLink) {
    voteUpLink.removeClass("far").addClass("fas");
    voteUpLink.attr("title","Undo vote up this question");
    voteDownLink.removeClass("fas").addClass("far");
}

function highlightVoteDownIconQuestion(voteDownLink, voteUpLink) {
    voteDownLink.removeClass("far").addClass("fas");
    voteDownLink.attr("title","Undo vote down this question");
    voteUpLink.removeClass("fas").addClass("far");
}

function unHighlightVoteDownIconQuestion(voteDownLink){
    voteDownLink.attr("title","Vote down this question");
    voteDownLink.removeClass("fas").addClass("far");
}

function unHighlightVoteUpIconQuestion(voteUpLink){
    voteUpLink.attr("title","Vote up this question");
    voteUpLink.removeClass("fas").addClass("far");
}