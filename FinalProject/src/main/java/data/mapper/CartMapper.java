package data.mapper;

import data.dto.CartDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper {
    void insertCart(CartDTO cartDTO);
    List<CartDTO> listCart(int userIdx);
    void deleteCart(CartDTO cartDTO);
    void deleteAllCart(int userIdx);
}