package data.mapper;

import java.util.HashMap;
import org.apache.ibatis.annotations.Mapper;
import data.dto.ShopHeartDto;

@Mapper
public interface ShopHeartMapper {

	public void insertHeart(ShopHeartDto dto);
    public int getMaxLikeCnt(int num);
	public void deleteHeart(HashMap<String, Object> map);
    public void updateHeart(int num);
    public void downdateHeart(int num);
}
