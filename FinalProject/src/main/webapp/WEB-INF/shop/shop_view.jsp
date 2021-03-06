<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="root" value="<%=request.getContextPath()%>" />
<style>
.heart {
	cursor: pointer;
}
</style>
<script type="text/javascript">
	$(function(){
		loginok="${sessionScope.loginok}"; //로그인 여부
		loginid="${sessionScope.id}"; //로그인 아이디 가져옴
		
		//좋아요 이벤트!(추가)
		$(".fa-heart-o").click(function(){
			var shop_heart=loginid;
			var num=$(this).attr("num");
		         			
			if(loginok==""){
				alert("회원만 가능합니다!");
				location.href ="/users/login";
			}else{
				$.ajax({
					type:"post",
					dataType:"text",
					url:"/shop/shinsert",
					data:{"shop_heart":shop_heart,"num":num},
					success:function(data){
					alert("좋아요 되었습니다!");
					location.reload();
		         	}
		      });
		  	}
		});
		         		
		//좋아요 이벤트!(취소)
		$(".fa-heart").click(function(){
			var shop_heart=loginid;
			var num=$(this).attr("num");
		         			
				$.ajax({
					type:"post",
					dataType:"text",
					url:"/shop/shdelete",
					data:{"shop_heart":shop_heart,"num":num},
					success:function(data){
					alert("좋아요 취소되었습니다!");
					location.reload();
		     		}
		      });
		 });
	});
</script>
<section class="breadcrumb-section">
	<div class="container">
		<div class="row">
			<div class="col-lg-12 text-center">
				<div class="breadcrumb__text">
					<h2>작품</h2>
					<div class="breadcrumb__option">
						<a href="./index.html">Home</a> <span>상품 소개</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<style type="text/css">
.blind {
	position: absolute;
	clip: rect(0, 0, 0, 0);
	margin: -1px;
	width: 1px;
	height: 1px;
	overflow: hidden;
}
</style>
<section class="product-details spad">
	<div class="container">
		<div class="row">
			<div class="col-lg-6 col-md-6">
				<div class="product__details__pic">
					<div class="product__details__pic__item">
						<img class="product__details__pic__item--large"
							src="/photo/${sdto.uploadfile1}" alt="">
					</div>
					<div class="product__details__pic__slider owl-carousel">
						<img data-imgbigurl="/photo/${sdto.uploadfile1}"
							src="/photo/${sdto.uploadfile1}" alt=""> <img
							data-imgbigurl="/photo/${sdto.uploadfile2}"
							src="/photo/${sdto.uploadfile2}" alt=""> <img
							data-imgbigurl="/photo/${sdto.uploadfile3}"
							src="/photo/${sdto.uploadfile3}" alt=""> <img
							data-imgbigurl="/photo/${sdto.uploadfile4}"
							src="/photo/${sdto.uploadfile4}" alt=""> <img
							data-imgbigurl="/photo/${sdto.uploadfile5}"
							src="/photo/${sdto.uploadfile5}" alt="">
					</div>
				</div>
			</div>
			<div class="col-lg-6 col-md-6">
				<div class="product__details__text">
					<a onclick="location.href='/author/home?id=${sdto.myid}'">
						<span>
							<img src="${root}/photo/${udto.profileimg}">${udto.nickname}
							<i class="fa fa-angle-right"></i>
                  		</span>
					</a>
					<h3>${sdto.title}</h3>
					<div class="product__details__price">${sdto.shopprice}원</div>
					<p>${sdto.shopsub}</p>
					<form id="cart_form" method="post" action="/cart/insert2">
						<input type="hidden" name="shop_num" value="${sdto.num}">
						<input type="hidden" name="user_id" value="${sessionScope.id}">
						<ul class="view_option_select">
							<li class="view_option_select_su"><b>수량</b> <span>주문시 제작</span></li>
							<li><b>배송비</b> <span>2,500원 <samp>(모든 지역 동일)</samp></span></li>
							<li><b>배송시작</b> <span>평균 1일, 최대 30일 이내</span></li>
							<li>
								<b>옵션선택</b> 
								<select id="selectoption" name="shop_price">
									<option>--옵션선택--</option>
									<option value="${sdto.subprice1}">${sdto.suboption1}</option>
									<option value="${sdto.subprice2}">${sdto.suboption2}</option>
									<option value="${sdto.subprice3}">${sdto.suboption3}</option>
									<option value="${sdto.subprice4}">${sdto.suboption4}</option>
									<option value="${sdto.subprice5}">${sdto.suboption5}</option>
								</select>
							</li>
							<input type="hidden" id="shop_option" name="shop_option" value="">
						</ul>
						<div class="product__details__text__option__list">
							<p id="result">옵션</p>
							<div class="product__details__quantity">
								<div class="quantity">
									<div class="pro-qty">
										<span class="dec qtybtn">-</span>
										<input type="text" name="shop_qty" id="subnum" value="1">
										<span class="inc qtybtn">+</span>
									</div>
								</div>
							</div>
							<div class="product__details__quantity">
								<div class="quantity-del">
									<span id="quantity-del-dan"></span>
									<button>X</button>
								</div>
							</div>
						</div>
						<div class="product__details__price__total">
							<p>총 작품금액 :</p>
							<span id="total_price"></span>
							<p>원</p>
						</div>
						<%-- TODO: 아무것도 선택하지 않고 구매하기 눌렀을 경우 price:--선택안함-- 예외발생   --%>
						<button type="button" class="primary-btn btn_buy" onclick="buyItem()">구매하기</button>
						<button type="button" class="heart-btn btn_cart"	onclick="insertCart()">장바구니</button>
						<button type="button" class="list-btn"	onclick="location.href='list'">목록보기</button>
						<c:if test="${loginok eq 'yes'}">
							<c:choose>
								<c:when test="${fn:contains(heartTrue, sessionScope.id)}">
									<a class="heart"><i class="fa fa-heart" num="${sdto.num}">${sdto.shopheart}</i></a>
								</c:when>
								<c:otherwise>
									<a class="heart"><i class="fa fa-heart-o" num="${sdto.num}">${sdto.shopheart}</i></a>
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${loginok eq null}">
							<a class="heart"><i class="fa fa-heart-o" num="${sdto.num}">${sdto.shopheart}</i></a>
						</c:if>
					</form>
				</div>
			</div>
			<div class="col-lg-12">
				<div class="product__details__tab">
					<ul class="nav nav-tabs" role="tablist">
						<li class="nav-item"><a class="nav-link active"
							data-toggle="tab" href="#tabs-1" role="tab" aria-selected="true">
								상세정보</a></li>
						<li class="nav-item"><a class="nav-link" data-toggle="tab"
							href="#tabs-2" role="tab" aria-selected="false"> 배송/교환/환불정보</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="tabs-1" role="tabpanel">
							<div class="product__details__tab__desc">
								<p>${sdto.shopcontent}</p>
								<img src="/photo/${sdto.uploadfile1}" alt="">
							</div>
						</div>
						<div class="tab-pane" id="tabs-2" role="tabpanel">
							<div class="product__details__tab__desc">
								<p>${sdto.shopdelivery}</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<section class="related-product">
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<div class="section-title related__product__title">
					<h2>다른 작품 구경하기</h2>
				</div>
			</div>
		</div>
		<div class="row">
			<c:forEach var="a" items="${list}">
				<div class="col-lg-4 col-md-6 col-sm-6">
					<div class="product__item shop_view_list_box">
						<a href="content?num=${a.num}&currentPage=${currentPage}&key=list">
							<div class="product__item__pic">
								<img src="/photo/${a.uploadfile1}" alt="">
							</div>
							<div class="product__item__text">
								<h6>${a.shopop}</h6>
								<h6>${a.title}</h6>
								<h5>${a.shopprice}</h5>
							</div>
						</a>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</section>
<script type="text/javascript">
  $(function () {
    $("#selectoption").change(function () {
      let txt = $("#selectoption option:selected").text();
      let v = $("#selectoption option:selected").val();
	  let total =  $("#subnum").val() * (parseInt($("#selectoption").val()) + parseInt(${sdto.shopprice}));
	  $("#shop_option").val(txt);
      document.getElementById("result").innerText = "상품 종류  : " + txt + " (+" + v + "원)";
      document.getElementById("quantity-del-dan").innerText = $("#subnum").val() * $("#selectoption").val();
      document.getElementById("quantity-del-dan").innerText =total;
      document.getElementById("total_price").innerText =total;
    });

    // 막무가내 리스너 붙이기
    $(".pro-qty").on("change keyup paste input click", function () {
    	let total =  $("#subnum").val() * (parseInt($("#selectoption").val()) + parseInt(${sdto.shopprice}));
      document.getElementById("quantity-del-dan").innerText =total;
      document.getElementById("total_price").innerText =total;
    });
  });



  // 구매하기 onclick
  function buyItem(){
	  let v = $("#selectoption option:selected").val();
	  if(v == '--옵션선택--'){
		  alert("옵션을 선택해야합니다");
		  return false;
	  }else{
		$("#cart_form").submit();
	  }
  }

  // 장바구니 onclick
  function insertCart() {
	  let option = $("#selectoption option:selected");
	  if (${sessionScope.id == null}) {
      alert("로그인이 필요합니다");
      return false;
    } else if(option.val() == '--옵션선택--'){
		  alert("옵션을 선택해야합니다");
		  return false;
	}else{
      if (confirm("장바구니에 추가?")) {
        let u_id = "${sessionScope.id}";
        let s_num = ${sdto.num};
        let s_option = option.text();
        let s_qty = $("#subnum").val();
        let s_price = $("#selectoption").val();

        let data = {"user_id": u_id, "shop_num": s_num, "shop_option": s_option, "shop_qty": s_qty, "shop_price": s_price};

        $.ajax({
          type: "POST",
          url: "/cart/insert",
          data: JSON.stringify(data),
          contentType: "application/json",
          success: function (data) {
            if(confirm("장바구니확인?")){
              location.href = "/cart/list"
            }
          }
        }); // ajax
      } // if(confirm())
    }   // else
  } // function insertCart()
</script>