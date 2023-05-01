document.addEventListener("DOMContentLoaded", function () {
    $(".comment-submit button[type='submit']").on("click", writeAjax);
    $(document).on("click", ".comment-delete button[type='submit']", deleteAjax);
    $(document).on("click", ".comment-edit button[type='submit']", editAjax);
});

function editAjax(e) {
    e.preventDefault();
    e.stopPropagation();

    const form = $(this).closest(".comment-edit");

    const commentId = form.data("comment-id");
    const newContent = form.find(".edit-content").val();

    updateAjax(commentId, newContent); // 수정 Ajax 요청 함수 호출
}

function writeAjax(e) {
    e.preventDefault();
    e.stopPropagation();
    const formSerializeArray = $('.comment-submit').serializeArray();
    const object = {};

    for (let i = 0; i < formSerializeArray.length; i++) {
        object[formSerializeArray[i]['name']] = formSerializeArray[i]['value'];
    }

    $.ajax({
        type: 'post',
        url: $(".comment-submit").attr("action"),
        data: JSON.stringify(object),
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        error: function (request, status, error) {
        },
        success: function (data, status) {
            $(".comments").append(commentTemplate(data));
            $("#content").val('');
        },
    });
}

function commentTemplate(data) {
    return `<div class="comment">
                <strong class="comment-writer">${data.writer}</strong>
                <pre class="comment-content">${data.content}</pre>
                <span class="comment-date">${data.createDate}</span>
                <form class="comment-delete" action="/posts/${data.postId}/comments/${data.commentId}">
                     <button class="btn btn-primary btn-sm" type="submit">삭제</button>
                </form>
            </div>`;
}

function updateAjax(commentId, newContent) {
    $.ajax({
        type: 'put',
        url: `/comments/${commentId}`,
        data: {content: newContent},
        dataType: 'json',
        success: function (data, status) {
            console.log('댓글이 업데이트되었습니다.');
            window.location.reload();
        },
        error: function (request, status, error) {
            alert("업데이트 실패");
        },
    });
}


function deleteAjax(e) {
    e.preventDefault();
    e.stopPropagation();

    const form = $(this).closest(".comment-delete");
    const comment = form.closest('.comment');

    $.ajax({
        type: 'delete',
        url: form.attr("action"),
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        error: function (request, status, error) {
            console.log('삭제 실패');
        },
        success: function (data, status) {
            comment.remove();
        },
        complete: function () {
            console.log('삭제 완료');
        }
    });
}

