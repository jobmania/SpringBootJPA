package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId,String name, int price, int stockQunatinty){
        Item findItem = itemRepository.findOne(itemId); // 찾아 온 상태로 영속상태
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQunatinty);
        // 호출할 필요 x  없다~ 영속성컨텍스트에서 관리한다.
        // 병합은 모든 필드를 교체해버린다. !  병합 보다는 변경 감지로 사용하자.
    }


    public List<Item> findItems(){
        return  itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

}
