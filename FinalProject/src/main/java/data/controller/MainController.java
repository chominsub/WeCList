package data.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import data.dto.ClassBoardDto;
import data.dto.ClassNewBoardDto;
import data.dto.InterDto;
import data.dto.ShopBoardDto;
import data.dto.StoryDto;
import data.mapper.MainMapper;
import data.mapper.ShopBoardMapper;
import data.mapper.UserMapper;
import data.service.MainService;
import data.service.ShopBoardService;

@Controller
public class MainController {

  @Autowired
  MainMapper mapper;

  @Autowired
  MainService service;

  @Autowired
  ShopBoardService shservice;

  @Autowired
  ShopBoardMapper shmapper;

  @Autowired
  UserMapper umapper;

  @GetMapping("/")
  public ModelAndView mainlist(@RequestParam(defaultValue = "1") int currentPage,
      HttpSession session, Model model) {
    String id = (String) session.getAttribute("id");
    String nickname = umapper.getNickName(id);
    model.addAttribute("nickname", nickname);

    ModelAndView mview = new ModelAndView();
    List<ShopBoardDto> listpopul = shmapper.getPopular();
    int perPage = 15;
    int totalCount = shservice.getTotalCount();

    int totalPage;
    int start;
    int perBlock = 5;
    int startPage;
    int endPage;

    totalCount = shservice.getTotalCount();

    totalPage = totalCount / perPage + (totalCount % perPage == 0 ? 0 : 1);

    startPage = (currentPage - 1) / perBlock * perBlock + 1;

    endPage = startPage + perBlock - 1;
    if (endPage > totalPage) {
      endPage = totalPage;
    }

    start = (currentPage - 1) * perPage;

    List<ShopBoardDto> list = shservice.getList(start, perPage);

    System.out.println(list.size());
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
    mview.addObject("listpopul", listpopul);
    mview.addObject("Newlist", list);
    mview.setViewName("/inc/main");

    List<StoryDto> MainStoryList = service.getMainStoryList();
    int StorytotalCount = service.getStoryTotalCount();

    mview.addObject("MainStoryList", MainStoryList);
    mview.addObject("StorytotalCount", StorytotalCount);

    return mview;
  }

  @GetMapping("/class")
  public ModelAndView getMain() {
    ModelAndView mview = new ModelAndView();
    List<ClassBoardDto> listMain = mapper.getAlllist();
    List<ClassBoardDto> listnewsMain = mapper.getAllnewlist();
    List<ClassBoardDto> listpopulMain = mapper.getPopular();
    List<InterDto> inter=mapper.getInter();
    List<ClassBoardDto> listseven=mapper.getSevendays();

    mview.addObject("listMain", listMain);
    mview.addObject("listnewsMain", listnewsMain);
    mview.addObject("listpopulMain", listpopulMain);
    mview.addObject("inter", inter);
    mview.addObject("listseven", listseven);
    mview.setViewName("/2/inc/class");
    return mview;
  }
  
  @GetMapping("/off/info")
  public String list() {
    return "/inc/offline_inf";
  }
  
  @GetMapping("/search")
  public ModelAndView shopSearch(
      @RequestParam(defaultValue = "1") int currentPage,
      @RequestParam(value = "keyword", required = false) String keyword, HttpSession session) {

    session.setAttribute("keyword", keyword);
    ModelAndView mview = new ModelAndView();

    // ����¡ ó���� �ʿ��� ��������
    int perPage = 9;// ���������� ������ ���� ����
    int totalPage;// �� ��������
    int start;// ������������ �ҷ��� db�� ���۹�ȣ
    int perBlock = 5;// ��� ��������ȣ�� ǥ���Ұ��ΰ�
    int startPage;// �� ���� ǥ���� ����������
    int endPage;// �� ���� ǥ���� ������������

    int totalCount=service.getSearchTotal();
	// �� ������ ���� ���ϱ�
    totalPage = totalCount / perPage + (totalCount % perPage == 0 ? 0 : 1);
    // �� ���� ����������
    startPage = (currentPage - 1) / perBlock * perBlock + 1;
    endPage = startPage + perBlock - 1;
    if (endPage > totalPage)
      endPage = totalPage;
    // �� ���������� �ҷ��� ���۹�ȣ
    start = (currentPage - 1) * perPage;
    List<ClassBoardDto> Search = service.getSearch(keyword, start, perPage);

    int no = totalCount - (currentPage - 1) * perPage;
    mview.addObject("Search", Search);
    mview.addObject("startPage", startPage);
    mview.addObject("endPage", endPage);
    mview.addObject("totalPage", totalPage);
    mview.addObject("no", no);
    mview.addObject("currentPage", currentPage);
    mview.addObject("totalCount", totalCount);
    mview.setViewName("/search");
    return mview;
  }
}

























