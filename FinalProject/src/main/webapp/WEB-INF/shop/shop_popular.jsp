<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="<%=request.getContextPath()%>" />
<section class="breadcrumb-section">
	<div class="container">
		<div class="row">
			<div class="col-lg-12 text-center">
				<div class="breadcrumb__text">
					<h2>인기 작품</h2>
					<div class="breadcrumb__option">
						<a href="./index.html">Home</a> <span>인기 작품</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<section class="featured spad">
	<div class="container">
		<div class="row hot_list_all_box">
			<c:forEach var="a" items="${listpopul}" varStatus="status" begin="0" end="11">
 				<div class="col-lg-4 col-md-6 col-sm-6">
					<div class="featured__item">
						<a href="/shop/	content?num=${a.num}"  >
							<div class="featured__item__pic set-bg">
								<img src="/photo/${a.uploadfile1}" alt="" >
								<span class="shop_rank_number ank_number_${status.count}">${status.count}</span>
							</div>
							<div class="featured__item__text">
								<h6>${a.shopop}</h6>
								<h6>${a.title}</h6>
								<h5>${a.shopprice}원</h5>
							</div>
						</a>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</section>
