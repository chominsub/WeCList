package data.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import data.dto.AuthorStoryDto;
import data.dto.ShopBoardDto;
import data.mapper.UserMapper;
import data.service.AuthorStoryService;
import data.service.ShopBoardService;

@Controller
public class AuthorShopController {

  @Autowired
  ShopBoardService service;
  
  @Autowired
  UserMapper mapper;
  @GetMapping("/mypage/shop/updateform")
  public ModelAndView updateForm(String num, String currentPage) {

    ModelAndView mview = new ModelAndView();

    ShopBoardDto dto = service.getData(num);

    mview.addObject("dto", dto);
    mview.addObject("currentPage", currentPage);
    mview.setViewName("/1/author_mypage/shop_update_form");
    return mview;
  }
  
  @PostMapping("/mypage/shop/update")
  public String update(HttpSession session, String currentPage, @ModelAttribute ShopBoardDto sdto) {

      //�뾽濡쒕뱶�븷 �뤃�뜑 ���옣
      String path = session.getServletContext().getRealPath("/photo");
      //�뾽濡쒕뱶�븷 �뙆�씪  紐�
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    
      if (sdto.getUpload1().getOriginalFilename().equals("")) {
          sdto.setUploadfile1("no");
      } else {
          String uploadfile1 = "f" + sdf.format(new Date()) + sdto.getUpload1().getOriginalFilename();
          sdto.setUploadfile1(uploadfile1);

          try {
              sdto.getUpload1().transferTo(new File(path + "/" + uploadfile1));
          } catch (IllegalStateException | IOException e) {
              // TODO: handle exception
              e.printStackTrace();
          }
      }

      if (sdto.getUpload2().getOriginalFilename().equals("")) {
          sdto.setUploadfile2("no");
      } else {
          String uploadfile2 = "f" + sdf.format(new Date()) + sdto.getUpload2().getOriginalFilename();
          sdto.setUploadfile2(uploadfile2);

          try {
              sdto.getUpload2().transferTo(new File(path + "/" + uploadfile2));
          } catch (IllegalStateException | IOException e) {
              // TODO: handle exception
              e.printStackTrace();
          }
      }

      if (sdto.getUpload3().getOriginalFilename().equals("")) {
          sdto.setUploadfile3("no");
      } else {
          String uploadfile3 = "f" + sdf.format(new Date()) + sdto.getUpload3().getOriginalFilename();
          sdto.setUploadfile3(uploadfile3);

          try {
              sdto.getUpload3().transferTo(new File(path + "/" + uploadfile3));
          } catch (IllegalStateException | IOException e) {
              // TODO: handle exception
              e.printStackTrace();
          }
      }

      if (sdto.getUpload4().getOriginalFilename().equals("")) {
          sdto.setUploadfile4("no");
      } else {
          String uploadfile4 = "f" + sdf.format(new Date()) + sdto.getUpload4().getOriginalFilename();
          sdto.setUploadfile4(uploadfile4);

          try {
              sdto.getUpload4().transferTo(new File(path + "/" + uploadfile4));
          } catch (IllegalStateException | IOException e) {
              // TODO: handle exception
              e.printStackTrace();
          }
      }

      if (sdto.getUpload5().getOriginalFilename().equals("")) {
          sdto.setUploadfile5("no");
      } else {
          String uploadfile5 = "f" + sdf.format(new Date()) + sdto.getUpload5().getOriginalFilename();
          sdto.setUploadfile5(uploadfile5);

          try {
              sdto.getUpload5().transferTo(new File(path + "/" + uploadfile5));
          } catch (IllegalStateException | IOException e) {
              // TODO: handle exception
              e.printStackTrace();
          }
      }
      //�꽭�뀡�뿉�꽌 �븘�씠�뵒瑜� �뼸�뼱�꽌 dto�뿉 ���옣
     
      service.updateBoard(sdto);
      return "redirect:content?num=" + service.getMaxNum();
  }
  @GetMapping("/mypage/shop/content")
  public ModelAndView content(@RequestParam String num,
                              @RequestParam(defaultValue = "1") int currentPage,
                              @RequestParam(required = false) String key) {
      ModelAndView mview = new ModelAndView();

      //紐⑸줉�뿉�꽌 key�뿉 list瑜� 蹂대궪 寃쎌슦�뿉 留� 議고쉶�닔 利앷�
      if (key != null) {
          service.updateReadCount(num);
      }

      ShopBoardDto sdto = service.getData(num);

      System.out.println(sdto.getMyid());
      String name = "as";
      sdto.setName(name);
      int dotLoc = sdto.getUploadfile1().lastIndexOf('.');
      String ext = sdto.getUploadfile1().substring(dotLoc + 1);
      if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("gif") || ext.equalsIgnoreCase("png")) {
          mview.addObject("bupload", true);
      } else {
          mview.addObject("bupload", false);

      }
      /////////////////////////////////////////////////////
      /////////////////////////////////////////////////////
      /////////////////////////////////////////////////////
      /////////////////////////////////////////////////////

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

      System.out.println(list.size());
      for (ShopBoardDto d : list) {

          d.setName(name);
      }
      int no = totalCount - (currentPage - 1) * perPage;
      mview.addObject("totalCount", totalCount);
      mview.addObject("list", list);
      mview.addObject("startPage", startPage);
      mview.addObject("endPage", endPage);
      mview.addObject("totalPage", totalPage);
      mview.addObject("no", no);


      /////////////////////////////////////////////////////
      /////////////////////////////////////////////////////
      /////////////////////////////////////////////////////
      /////////////////////////////////////////////////////
      /////////////////////////////////////////////////////

      mview.addObject("sdto", sdto);
      mview.addObject("currentPage", currentPage);
      mview.setViewName("/shop/shop_view");
      return mview;
  }
  
  @GetMapping("/mypage/shop/list")
  public ModelAndView list(@RequestParam(defaultValue = "1") int currentPage,
      HttpSession session, Model model
      ) {
    ModelAndView mview = new ModelAndView();
    String from_id = (String) session.getAttribute("id");

    String id = (String)session.getAttribute("id"); // 세션값 얻어오기
    String nickname = mapper.getNickName(id);
    model.addAttribute("nickname", nickname);
    
    int perPage = 8;//�븳�럹�씠吏��뿉 蹂댁뿬吏� 湲��쓽 媛��닔
    int IdCount = service.getIdCount(from_id);
    int IdPage;//珥앺럹�씠吏�
    int start;//媛곹럹�씠�젿�꽌 遺덈윭�삱 �떆�옉 踰덊샇
    int perBlock = 5;//紐뉕컻�쓽 �럹�씠吏� 踰덊샇�뵫 �몴�쁽�븷寃껋씤媛�
    int startPage;//媛곷툝�윮�뿉 �몴�떆�븷 �떆�옉�럹�씠吏�
    int endPage;//媛곷툝�윮�뿉 �몴�떆�븷 留덉�留됲럹�씠吏�

    IdCount = service.getIdCount(from_id);
    //�쁽�옱 �럹�씠吏� 踰덊샇 �씫湲�(�떒 null �씪 寃쎌슦 1�럹�씠吏�濡� �꽕�젙)

    IdPage = IdCount / perPage + (IdCount % perPage == 0 ? 0 : 1);
    //媛� 釉붾윮�쓽 �떆�옉�럹�씠吏�
    startPage = (currentPage - 1) / perBlock * perBlock + 1;

    endPage = startPage + perBlock - 1;
    if (endPage > IdPage) {
        endPage = IdPage;
    }
    //媛� �럹�씠吏��뿉�꽌 遺덈윭�삱 �떆�옉踰덊샇
    start = (currentPage - 1) * perPage;
    //媛곹럹�씠吏��뿉�꽌 �븘�슂�븳 寃뚯떆湲� 媛��졇�삤湲�
    List<ShopBoardDto> list = service.getList(start, perPage);

    int Idno = IdCount - (currentPage - 1) * perPage;
    mview.addObject("IdCount", IdCount);
    mview.addObject("list", list);
    mview.addObject("startPage", startPage);
    mview.addObject("endPage", endPage);
    mview.addObject("IdPage", IdPage);
    mview.addObject("Idno", Idno);
    mview.addObject("currentPage", currentPage);
    mview.setViewName("/1/author_mypage/m_shop_list");

    return mview;
}
  
  
  @GetMapping("/mypage/shopdelete")
  public String delete(@RequestParam(required = false) String num, 
      @RequestParam(defaultValue = "1") int currentPage,
      HttpSession session) {

    String path = session.getServletContext().getRealPath("/photo");
    MultipartFile uploadfile1 = service.getData(num).getUpload1();
    File file1 = new File(path + "/" + uploadfile1);

    MultipartFile uploadfile2 = service.getData(num).getUpload2();
    File file2 = new File(path + "/" + uploadfile2);

    MultipartFile uploadfile3 = service.getData(num).getUpload3();
    File file3 = new File(path + "/" + uploadfile3);

    MultipartFile uploadfile4 = service.getData(num).getUpload4();
    File file4 = new File(path + "/" + uploadfile4);

    MultipartFile uploadfile5 = service.getData(num).getUpload5();
    File file5 = new File(path + "/" + uploadfile5);

    //사진 삭제
    file1.delete();
    file2.delete();
    file3.delete();
    file4.delete();
    file5.delete();

    service.deleteBoard(Integer.parseInt(num));
    return "redirect:/mypage/shop/list?currentPage="+currentPage;
  }
}
