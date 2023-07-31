package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본생성자를 다른 곳에서 생성하는 걸 막는것, 제약 )) -> 유지보수성 증가.
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "odrder_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;


    private int orderPrice; // 주문가격
    private int count; // 주문 수량

//    protected OrderItem(){
//        //// createOrderItem() 를 사용하기 때문에 기본생성자 제약// jpa는 기본생성자가 필요함.
//    }


    // == 생성 메서드 //
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }





    // === 비즈니스 로직 ===
    public void cancel() {
        getItem().addStock(count);// 주문수량만큼 재고수량 원복
    }

    /// == 조회 로직

    /**
     *  주문상품 전체 가격 조회
     * */

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
