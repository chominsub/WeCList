package data.controller;

import data.dto.CartDTO;
import data.service.CartService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CartController {

    @Autowired
    CartService cartService;

    private final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    @PostMapping("/cart/insert")
    public String insertCart(@RequestBody CartDTO cartDTO) {
        cartService.insertCart(cartDTO);
        LOGGER.info("POST:/cart/insert ");
        return "redirect:/cart/list";
    }


    @PostMapping("/cart/insert2")
    public String insertCart2(@ModelAttribute CartDTO cartDTO) {
        cartService.insertCart(cartDTO);
        LOGGER.info("POST:/cart/insert2 ");
        return "redirect:/cart/buy";
    }

    @GetMapping("/cart/list")
    public ModelAndView list(HttpSession session, ModelAndView mv) {
        // TODO: 로그인, 비로그인 처리
        String userId = "noid";
        if ((String) session.getAttribute("id") != null) {
            userId = (String) session.getAttribute("id");
        }
        List<CartDTO> list = cartService.listCart(userId);
        // TODO: 배송비,쿠폰, 할인, 돈 계산

        mv.addObject("list", list);
        mv.setViewName("/shop/shop_cart");
        LOGGER.info("GET:/cart/list w/" + userId);
        LOGGER.info(list.toString());
        return mv;
    }

    @GetMapping("/cart/buy")
    public ModelAndView buy(HttpSession session, ModelAndView mv) {
        // TODO: 로그인, 비로그인 처리
        String userId = "noid";
        if ((String) session.getAttribute("id") != null) {
            userId = (String) session.getAttribute("id");
        }
        List<CartDTO> list = cartService.listCart(userId);
        // TODO: 배송비,쿠폰, 할인, 돈 계산

        mv.addObject("list", list);
        mv.setViewName("/shop/shop_buy");
        LOGGER.info("GET:/cart/list w/" + userId);
        LOGGER.info(list.toString());
        return mv;
    }

    @ResponseBody
    @DeleteMapping("/cart/delete")
    public void delete(String user_id, int shop_num, String shop_option ) { cartService.deleteCart(user_id, shop_num, shop_option);  }
}
