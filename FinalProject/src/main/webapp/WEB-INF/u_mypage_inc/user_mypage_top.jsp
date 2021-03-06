<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="<%=request.getContextPath()%>"/>
<section class="author-details-top">
	<div class="mypage__top">
		<div class="container">
			<div class="mypage__top__user">
				<div class="row">
					<div class="grade_user">
						<div class="profile_img" style="float:left;">
							<img src="/photo/${profileimg}">
						</div>
						<div class="profile_text_mypage_1" style="float:left;">
							<c:choose>
								<c:when test="${sessionScope.category == 2}">
									<span>작가</span><br>
								</c:when>
								<c:otherwise>
									<span>일반회원</span><br>
								</c:otherwise>
							</c:choose>
							<p class="mypage__name__top">${id}님</p>
						</div>
						<div class="profile_text_mypage_2">
							<c:choose>
								<c:when test="${sessionScope.nickname eq null}">
									<p class="mypage__nickname__top"><i class="fa fa-id-badge" aria-hidden="true"></i>&nbsp;&nbsp;닉네임 설정이 필요합니다.</p>
								</c:when>
								<c:otherwise>
									<p class="mypage__nickname__top"><i class="fa fa-id-badge" aria-hidden="true"></i>&nbsp;&nbsp;닉네임&nbsp;&nbsp;:&nbsp;&nbsp;${nickname}</p>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div class="grade_shopbox">
						<span>총 주문현황</span>
						<p class="shop_box_text">작품&nbsp;10개</p>
						<p>클래스&nbsp;20건</p>
					</div>
					<div class="grade_shopbox">
						<span>총 결제현황</span>
						<p class="shop_box_text">승인&nbsp;20건</p>
						<p>미승인&nbsp;30건</p>
					</div>
					
				</div>
			</div>
		</div>
	</div>
</section>
