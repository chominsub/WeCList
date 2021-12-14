<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="<%=request.getContextPath()%>" />
<section class="breadcrumb-section">
	<div class="container">
		<div class="row">
			<div class="col-lg-12 text-center">
				<div class="breadcrumb__text">
					<h2>인기 클래스</h2>
					<div class="breadcrumb__option">
						<a href="./index.html">Home</a> <span>인기 클래스</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<section class="featured spad">
	<div class="container">
		<div class="row hot_list_all_box">
			<c:forEach var="idto" items="${inter}">
				<c:forEach var="hdto" items="${HotClass}">
					<c:if test="${hdto.num==idto.num && idto.inter_cnt>=1}">
						<c:forEach var="cdto" items="${listpopul}" varStatus="status">
							<c:if test="${hdto.num==cdto.num}">
								<div class="product__item">
									<a href="${root}/class/view?num=${cdto.num}">
										<div class="product__item__pic set-bg">
											<img src="/photo/${cdto.uploadfile}"
												style="width: 300px; height: 300px;"> <span
												class="shop_rank_number ank_number_${status.count}">${status.count}</span>
										</div>
										<div class="product__item__text">
											<h6>${cdto.title}</h6>
											<h6>${cdto.class_op_cate}</h6>
											<h5>${cdto.classprice}원</h5>
										</div>
									</a>
								</div>
							</c:if>
						</c:forEach>
					</c:if>
				</c:forEach>
			</c:forEach>
		</div>
	</div>
</section>
