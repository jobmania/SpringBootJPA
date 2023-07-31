package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")//
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name ="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items") //  컬럼 추가를 못함
    List<Category> categories = new ArrayList<>();


    // 비즈니스 로직 -- 객체지향적으로 데이터를 가지고있는 쪽에 비즈니스로직을... 응집성 높게 ..
    /**
    * 재고 수량 증가!
    * */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;

    }

    /**
     * 재고수량 감소!
     * */
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock<0){
            throw new NotEnoughStockException("need more stock");

        }
        this.stockQuantity = restStock;
    }
}
