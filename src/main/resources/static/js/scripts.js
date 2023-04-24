String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

$('#post-reply').click(() => {
    var queryString = $(".submit-write textarea[name=replyContent]").serialize();
    var url = window.location.pathname + '/replies';
    var articleId = window.location.pathname.split('questions/')[1];

    $.ajax({
        type: 'POST',
        url: url,
        data: queryString,
        dataType: 'json',
    }).done((db) => {
        console.log(db);
        var answerTemplate = $("#answerTemplate").html();
        var template = answerTemplate.format(db.userId, db.replyTime, db.replyContent, articleId, db.id);

        $(".qna-comment-slipp-articles").append(template);
        $("textarea[name=replyContent]").val("");
        $(".delete-reply").last().on("click", deleteReply);

    }).fail((err) => {
        alert(JSON.stringify(err));
    });
});

$('.delete-reply').click(deleteReply)
function deleteReply(e) {
    var replyId = e.target.dataset['replyid'];
    var url = window.location.pathname + '/replies/' + replyId;

    $.ajax({
        type: 'DELETE',
        url: url,
        dataType: 'json',
    }).done((data) => {
        alert("okay");
        e.target.closest("article").remove();

    }).fail((err) => {
        alert("오류지만 삭제는 됨");
        console.log(JSON.stringify(err));
    })
}