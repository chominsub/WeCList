package data.service;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import data.dto.ShopBoardDto;
import data.mapper.ShopBoardMapper;

@Service
public class ShopBoardService {

  @Autowired
  ShopBoardMapper mapper;
  
  
  public int getTotalCount() {
    
    
    return mapper.getTotalCount();
  }
  
  public void updateReadCount(String num) {
    mapper.updateReadCount(num);
  }
  
  public ShopBoardDto getData(String num) {
    return mapper.getData(num);
  }
  public int getMaxNum(){
    return mapper.getMaxNum();
  }
  
  public List<ShopBoardDto> getList(int start , int perpage){

  HashMap<String, Integer> map = new HashMap<String, Integer>();
  map.put("start", start);
  map.put("perpage", perpage);
  
  return mapper.getList(map);
  }
  
  public void insertBoard(ShopBoardDto dto) {
    mapper.insertBoard(dto);
  }
  
  public void updateBoard(ShopBoardDto dto) {
    mapper.updateBoard(dto);
  }
  
  public void deleteBoard(String num) {
    mapper.deleteBoard(num);

  }
}