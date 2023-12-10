$(document).ready(function () {
   $(".linkVoteReview").on("click",function (e) {
      e.preventDefault();
      voteReview($(this));
   });
});

function voteReview(currentLink) {
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
            updateVoteCountAndIcons(currentLink, voteResult);
            anim5_noti(voteResult.message);
        }
    }).fail(function () {
        error_noti("Error while adding product to wishlist.");
    });
}

function updateVoteCountAndIcons(currentLink, voteResult){
    reviewId = currentLink.attr("reviewId");
    voteUpLink = $("#linkVoteup-"+reviewId);
    voteDownLink = $("#linkVotedown-"+reviewId);
    $("#voteCount-"+reviewId).text(voteResult.voteCount + " Votes");

    message = voteResult.message;
    if(message.includes("successfully voted up")){
        highlightVoteUpIcon(currentLink, voteDownLink);
    }else if(message.includes("successfully voted down")){
        highlightVoteDownIcon(currentLink, voteUpLink);
    }else if(message.includes("unvoted down")){
        unHighlightVoteDownIcon(voteDownLink);
    }else if(message.includes("unvoted up")){
        unHighlightVoteUpIcon(voteUpLink);
    }
}

function highlightVoteUpIcon(voteUpLink, voteDownLink) {
    voteUpLink.removeClass("far").addClass("fas");
    voteUpLink.attr("title","Undo vote up this review");
    voteDownLink.removeClass("fas").addClass("far");
}

function highlightVoteDownIcon(voteDownLink, voteUpLink) {
    voteDownLink.removeClass("far").addClass("fas");
    voteDownLink.attr("title","Undo vote down this review");
    voteUpLink.removeClass("fas").addClass("far");
}

function unHighlightVoteDownIcon(voteDownLink){
    voteDownLink.attr("title","Vote down this review");
    voteDownLink.removeClass("fas").addClass("far");
}

function unHighlightVoteUpIcon(voteUpLink){
    voteUpLink.attr("title","Vote up this review");
    voteUpLink.removeClass("fas").addClass("far");
}