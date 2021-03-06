package data.service;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import data.dto.ShopHeartDto;
import data.mapper.ShopHeartMapper;

@Service
public class ShopHeartService {

    @Autowired
    ShopHeartMapper mapper;

    public void insertHeart(ShopHeartDto dto) {
        mapper.insertHeart(dto);
    }

    public void deleteHeart(String shop_heart, int num) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("shop_heart", shop_heart);
        map.put("num", num);
        mapper.deleteHeart(map);
    }
    public int getMaxLikeCnt(int num) {
      return mapper.getMaxLikeCnt(num);
    }
    

    public void updateHeart(int num) {
      mapper.updateHeart(num);
    }
    public void downdateHeart(int num) {
      mapper.downdateHeart(num);
    }
}
