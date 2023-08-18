<sec:authentication property="user.id" var="currentid" /> <!-- 현재 로그인user의 id -->
<c:choose>
    <c:when test="${page_id == currentid}">
        <div class="fix">
            <a href="update/${user.id}" class="btn btn-default">프로필 수정</a>
        </div>
    </c:when>
    <c:otherwise>
        <div class="fix">
            <c:choose>
                <c:when test="${follow == true}"> <!-- 0이 아니므로 팔로우 되어있음 -->
                    <form action="/unfollow" name="form" method="post"> <!-- 언팔로우 버튼 -->
                        <input type="hidden" value="${currentid}" name="user_id"> <!-- 현재로그인id -->
                        <input type="hidden" value="${page_id}" name="page_id"> <!-- 현재페이지id -->
                        <button class="btn btn-default" type="submit">언팔로잉</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <form action="/follow" name="form" method="post">
                        <input type="hidden" value="${currentid}" name="user_id">
                        <input type="hidden" value="${page_id}" name="page_id">
                        <button class="btn btn-default">팔로잉</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </c:otherwise>
</c:choose>