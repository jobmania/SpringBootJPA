package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    // :: 좋은테스트는 db연결 없이 순수하게 단위테스트하는게 좋은 테스트이다.

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        //givne
        Member member = createMember();

        Book book = createBook("JPA 짱!", 10000, 10);

        //when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야한다.",1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량",1, 10000*orderCount,getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어들어야 된다!", 8, book.getStockQuantity());



    }


    @Test(expected = NotEnoughStockException.class )
    public void 상품주문_재고수량초과() throws Exception{
        //givne
        Member member = createMember();
        Item item = createBook("JPA 짱!", 10000, 10);

        int orderCount = 101; // 수량이 더많음

        //when
        orderService.order(member.id,  item.getId(), orderCount);

        //then
        fail("재고 수량 부족 예외가 발생해야 한다1!");

        // 조금더 좋은 단위테스트는 removeStock() 이라는 메서드에 대한 테스트를 하면 좋다.

    }

    @Test
    public void 주문취소() throws Exception{
        //givne
        Member member = createMember();
        Book item = createBook("jpa 북",10000,10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        // 재고 정상 복구 됫느지

        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("주문 취소시 상태는 CANCEL", OrderStatus.CANCEL,getOrder.getStatus());
        assertEquals("주문 취소된 상품은 원복!", 10,item.getStockQuantity());

    }





    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","도곡로","123-22"));
        em.persist(member);
        return member;
    }
}