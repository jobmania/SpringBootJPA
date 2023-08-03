package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId()==null){ // 처음 저장시에는 id는 null 임.
            em.persist(item);
        }else {
            Item mergeItem = em.merge(item);// update한다.
            // 다만 item은 영속성 컨텍스트에 관리가 되지 않는다.
            // mergeItem은 영속성에서 관리함 ㅇㅇ.
        }
    }


    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }




}
