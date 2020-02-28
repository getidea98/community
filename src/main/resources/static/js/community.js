function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId, 1, content);
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容~~~");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "parentType": type
        }),
        success: function (response) {
            if(response.code == 200){
                $("#comment_content").hide();
                window.location.reload();
            }else{
                if(response.code == 2001){
                    var isAccepted = confirm(response.message);
                    if(isAccepted){
                        window.localStorage.setItem("closable","true");
                        window.open("https://github.com/login/oauth/authorize?client_id=955241e3910b07ee628a&redirect_uri=http://localhost:8887/callback&scope=user&state=1")
                    }
                }
            }
        },
        dataType: "json"
    });
}