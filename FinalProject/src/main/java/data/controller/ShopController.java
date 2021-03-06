package data.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import data.dto.ShopBoardDto;
import data.dto.ShopHeartDto;
import data.dto.UserDto;
import data.mapper.ShopBoardMapper;
import data.service.ShopBoardService;
import data.service.UserProfileService;

@Controller
public class ShopController {

  @Autowired
  ShopBoardService service;

  @Autowired
  ShopBoardMapper mapper;

  @Autowired
  UserProfileService uservice;

  @Value("${file.upload.image}")
  String imagePath;

  @GetMapping("/shop/category")
  public ModelAndView getCategory(@RequestParam(value = "shopop", required = false) String shopop,
      @RequestParam(defaultValue = "1") int currentPage,
      @RequestParam(required = false) String key) {
    ModelAndView mview = new ModelAndView();
    int perPage = 12;
    int totalCount = service.getCaCount(shopop);
    int totalPage;
    int start;
    int perBlock = 5;
    int startPage;
    int endPage;
    totalPage = totalCount / perPage + (totalCount % perPage == 0 ? 0 : 1);
    startPage = (currentPage - 1) / perBlock * perBlock + 1;
    endPage = startPage + perBlock - 1;
    if (endPage > totalPage) {
      endPage = totalPage;
    }

    start = (currentPage - 1) * perPage;
    int no = totalCount - (currentPage - 1) * perPage;

    List<ShopBoardDto> listcate = service.getCategory(shopop, start, perPage);

    mview.addObject("listcate", listcate);
    mview.addObject("totalCount", totalCount);
    mview.addObject("startPage", startPage);
    mview.addObject("endPage", endPage);
    mview.addObject("totalPage", totalPage);
    mview.addObject("no", no);

    mview.addObject("shopop", shopop);
    mview.addObject("currentPage", currentPage);
    mview.setViewName("/5/shop/shop_category");
    return mview;
  }

  @GetMapping("/shop/content")
  public ModelAndView content(@RequestParam String num,
      @RequestParam(defaultValue = "1") int currentPage,
      @RequestParam(required = false) String key) {
    ModelAndView mview = new ModelAndView();

    if (key != null) {
      service.updateReadCount(num);
    }

    ShopBoardDto sdto = service.getData(num);
    UserDto udto = uservice.GetIdData(sdto.getMyid());
    int dotLoc = sdto.getUploadfile1().lastIndexOf('.');
    String ext = sdto.getUploadfile1().substring(dotLoc + 1);
    if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("gif") || ext.equalsIgnoreCase("png")) {
      mview.addObject("bupload", true);
    } else {
      mview.addObject("bupload", false);

    }

    int perPage = 4;
    int totalCount = service.getTotalCount();
    int totalPage;
    int start;
    int perBlock = 5;
    int startPage;
    int endPage;

    totalCount = service.getTotalCount();
    totalPage = totalCount / perPage + (totalCount % perPage == 0 ? 0 : 1);
    startPage = (currentPage - 1) / perBlock * perBlock + 1;
    endPage = startPage + perBlock - 1;

    if (endPage > totalPage) {
      endPage = totalPage;
    }

    start = (currentPage - 1) * perPage;

    List<ShopBoardDto> list = service.getList(start, perPage);
    ShopBoardDto dto = service.getData(num);

    List<ShopHeartDto> heartTrue = service.getTrue(num);

    int no = totalCount - (currentPage - 1) * perPage;
    mview.addObject("totalCount", totalCount);
    mview.addObject("list", list);
    mview.addObject("startPage", startPage);
    mview.addObject("endPage", endPage);
    mview.addObject("totalPage", totalPage);
    mview.addObject("no", no);
    mview.addObject("heartTrue", heartTrue);
    mview.addObject("sdto", sdto);
    mview.addObject("udto", udto);
    mview.addObject("currentPage", currentPage);
    mview.setViewName("/shop/shop_view");
    return mview;
  }

  @GetMapping("/shop/buy")
  public String buy() {
    return "/shop/shop_buy";
  }

  @GetMapping("/shop/hotlist")
  public String hotlist() {
    return "/shop/shop_hot_list";
  }

  @GetMapping("/mypage/shop/writeform")
  public String writform() {
    return "/1/author_mypage/shop_write_form";
  }

  @GetMapping(value = {"/shop/list"})
  public ModelAndView list(@RequestParam(defaultValue = "1") int currentPage,
      @RequestParam(required = false) String num) {
    ModelAndView mview = new ModelAndView();

    int perPage = 12;
    int totalCount = service.getTotalCount();
    int totalPage;
    int IdPage;
    int start;
    int perBlock = 5;
    int startPage;
    int endPage;

    totalCount = service.getTotalCount();
    totalPage = totalCount / perPage + (totalCount % perPage == 0 ? 0 : 1);
    startPage = (currentPage - 1) / perBlock * perBlock + 1;
    endPage = startPage + perBlock - 1;

    if (endPage > totalPage) {
      endPage = totalPage;
    }

    start = (currentPage - 1) * perPage;
    ShopBoardDto dto = service.getData(num);

    List<ShopHeartDto> heartTrue = service.getTrue(num);
    List<ShopBoardDto> list = service.getList(start, perPage);

    int no = totalCount - (currentPage - 1) * perPage;
    mview.addObject("totalCount", totalCount);
    mview.addObject("list", list);
    mview.addObject("startPage", startPage);
    mview.addObject("endPage", endPage);
    mview.addObject("totalPage", totalPage);
    mview.addObject("no", no);
    mview.addObject("heartTrue", heartTrue);
    mview.addObject("currentPage", currentPage);
    mview.setViewName("/5/shop/shop_list");

    return mview;
  }

  @PostMapping("/mypage/shop/insert")
  public String insert(HttpSession session, @ModelAttribute ShopBoardDto sdto) {

    String path = session.getServletContext().getRealPath("/photo");

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    if (sdto.getUpload1().getOriginalFilename().equals("")) {
      sdto.setUploadfile1("no");
    } else {
      String uploadfile1 = "f" + sdf.format(new Date()) + sdto.getUpload1().getOriginalFilename();
      sdto.setUploadfile1(uploadfile1);

      try {
        sdto.getUpload1().transferTo(new File(imagePath + "/" + uploadfile1));
      } catch (IllegalStateException | IOException e) {
        e.printStackTrace();
      }
    }

    if (sdto.getUpload2().getOriginalFilename().equals("")) {
      sdto.setUploadfile2("no");
    } else {
      String uploadfile2 = "f" + sdf.format(new Date()) + sdto.getUpload2().getOriginalFilename();
      sdto.setUploadfile2(uploadfile2);

      try {
        sdto.getUpload2().transferTo(new File(imagePath + "/" + uploadfile2));
      } catch (IllegalStateException | IOException e) {
        e.printStackTrace();
      }
    }

    if (sdto.getUpload3().getOriginalFilename().equals("")) {
      sdto.setUploadfile3("no");
    } else {
      String uploadfile3 = "f" + sdf.format(new Date()) + sdto.getUpload3().getOriginalFilename();
      sdto.setUploadfile3(uploadfile3);

      try {
        sdto.getUpload3().transferTo(new File(imagePath + "/" + uploadfile3));
      } catch (IllegalStateException | IOException e) {
        e.printStackTrace();
      }
    }

    if (sdto.getUpload4().getOriginalFilename().equals("")) {
      sdto.setUploadfile4("no");
    } else {
      String uploadfile4 = "f" + sdf.format(new Date()) + sdto.getUpload4().getOriginalFilename();
      sdto.setUploadfile4(uploadfile4);

      try {
        sdto.getUpload4().transferTo(new File(imagePath + "/" + uploadfile4));
      } catch (IllegalStateException | IOException e) {
        e.printStackTrace();
      }
    }

    if (sdto.getUpload5().getOriginalFilename().equals("")) {
      sdto.setUploadfile5("no");
    } else {
      String uploadfile5 = "f" + sdf.format(new Date()) + sdto.getUpload5().getOriginalFilename();
      sdto.setUploadfile5(uploadfile5);

      try {
        sdto.getUpload5().transferTo(new File(imagePath + "/" + uploadfile5));
      } catch (IllegalStateException | IOException e) {
        e.printStackTrace();
      }
    }

    service.insertBoard(sdto);
    return "redirect:content?num=" + service.getMaxNum();
  }

  @GetMapping("/shop/popul")
  public ModelAndView getPopular() {
    ModelAndView mview = new ModelAndView();
    List<ShopHeartDto> HotShop = mapper.getHotShop();
    List<ShopBoardDto> listpopul = mapper.getPopular();

    mview.addObject("HotShop", HotShop);
    mview.addObject("listpopul", listpopul);
    mview.setViewName("/shop/shop_popular");

    return mview;
  }

  @GetMapping("/shop/new_list")
  public ModelAndView getnewlist(@RequestParam(defaultValue = "1") int currentPage) {
    ModelAndView mview = new ModelAndView();
    List<ShopBoardDto> newlist = mapper.getPopular();

    int perPage = 4;
    int totalCount = service.getTotalCount();
    int totalPage;
    int start;
    int perBlock = 5;
    int startPage;
    int endPage;
    totalCount = service.getTotalCount();
    totalPage = totalCount / perPage + (totalCount % perPage == 0 ? 0 : 1);
    startPage = (currentPage - 1) / perBlock * perBlock + 1;
    endPage = startPage + perBlock - 1;

    if (endPage > totalPage) {
      endPage = totalPage;
    }

    start = (currentPage - 1) * perPage;

    List<ShopBoardDto> list = service.getList(start, perPage);

    for (ShopBoardDto d : list) {
      String name = "col";
      d.setName(name);
    }
    int no = totalCount - (currentPage - 1) * perPage;
    mview.addObject("totalCount", totalCount);
    mview.addObject("list", list);
    mview.addObject("startPage", startPage);
    mview.addObject("endPage", endPage);
    mview.addObject("totalPage", totalPage);
    mview.addObject("no", no);
    mview.addObject("currentPage", currentPage);
    mview.addObject("newlist", list);
    mview.setViewName("/shop/shop_new_list");
    return mview;
  }
}
