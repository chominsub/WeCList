<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="root" value="<%=request.getContextPath()%>" />
<script type="text/javascript">
	/* 삭제하기 클릭시 팝업!!! */
	function showDelPopup(num) {
		$("#delnum").val(num);
		
		const popup2 = document.querySelector('#story_del_popup');
		popup2.classList.remove('hide');
		
		$("#btn_story_del").click(function(){
			var num=$("#delnum").val();
			location.href="/mypage/classdelete?num="+num;
		});
	}
	
	function closeDelPopup() {
		const popup2 = document.querySelector('#story_del_popup');
		popup2.classList.add('hide');
	}
</script>
<div class="mypage__story">
	<h2>작품/클래스 관리</h2>
	<ul class="tab__manu">
		<li><a href="/mypage/shop/list">작품 관리</a></li>
		<li class="on"><a href="/mypage/class/list">클래스 관리</a></li>
	</ul>
	<div class="mypage__story__list">
		<table class="story__table">
			<tr>
				<th>No</th>
				<th>썸네일</th>
				<th>제목</th>
				<th>작성일</th>
				<th>수정/삭제</th>
			</tr>
			<c:if test="${IdCount==0}">
				<tr>
					<td colspan="5" align="center">
						등록된 게시글이 없습니다.
					</td>
				</tr>
			</c:if>
			<c:if test="${IdCount>0}">
				<c:forEach var="cdto" items="${list}">
					<c:if test="${sessionScope.loginok!=null and sessionScope.id==cdto.myid}">
						<tr>
							<td align="center">${Idno}</td>
							<c:set var="Idno" value="${Idno-1}" />
							<td>
								<a href="${root}/class/view?num=${cdto.num}">
								<img src="/photo/${cdto.uploadfile}"></a>
							</td>
							<td>
								<a href="${root}/class/view?num=${cdto.num}">${cdto.title}</a>
							</td>
							<td>
								<fmt:formatDate value="${cdto.writeday}" pattern="yyyy-MM-dd" />
							</td>
							<td>
								<button type="button" class="story_mod_btn"
								onclick="location.href='/mypage/class/updateform?num=${cdto.num}'">수정</button>
								<button type="button" class="story_del_btn"
								onclick="location.href='/mypage/classdelete?num=${cdto.num}'">삭제</button>
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</c:if>
		</table>
		<div class="story__insert__bottom">
			<button type="submit" onclick="location.href='/mypage/class/addform'">등록하기</button>
		</div>
		<div class="product__pagination">
			<c:if test="${startPage>1}">
				<a href="list?currentPage=${startPage-1}">이전</a>
			</c:if>
			<c:forEach var="pp" begin="${startPage}" end="${endPage}">
				<c:if test="${currentPage==pp}">
					<li class="select"><a class="select2" href="list?currentPage=${pp}">${pp}</a></li>
				</c:if>
				<c:if test="${currentPage!=pp}">
					<li class="active"><a href="list?currentPage=${pp}">${pp}</a></li>
				</c:if>
			</c:forEach>
			<c:if test="${endPage<totalPage}">
				<a href="list?currentPage=${endPage+1}">다음</a>
			</c:if>
		</div>
	</div>
</div>
<!-- 삭제 클릭하면 나오는 팝업! -->
<div id="story_del_popup" class="hide">
	<div class="story_del_content">
		<p class="del_popup_title">삭제 확인</p>
		<span class="del_popup_text">
			해당 클래스를 삭제하시겠습니까?<br>
			삭제하시면 다시 원복하실 수 없습니다.<br>
			<b>※ 신중히 생각하시고 진행부탁드립니다.</b>	
		</span>
		<input type="hidden" id="delnum">
		<div class="popup_story_del_btn">
			<button type="button" id="btn_story_del">확인</button>
			<button type="button" id="btn_del_close" onclick="closeDelPopup()" >닫기</button>
		</div>
	</div>
</div>
