package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;




/** 어플리케이션 실행 시점 Test Data 주입!
 * user A
 *  JPA BOOK 1
 *  JPA BOOK 2
 *
 * user B
 *  Spring1 Book
 *  Spring2 Book
 * */

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
       // 여기에 Service 코드작성하면 안되고 별도의 Bean을 등록해야된다.
    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        public void dbInit1(){

            Member member = new Member();
            member.setName("userA");
            member.setAddress(new Address("서울","1","23123"));
            em.persist(member);

            Book book1 = new Book();
            book1.setName("JPA BOOK1");
            book1.setPrice(10000);
            book1.setStockQuantity(100);
            em.persist(book1);


            Book book2 = new Book();
            book2.setName("JPA BOOK2");
            book2.setPrice(20000);
            book2.setStockQuantity(100);
            em.persist(book2);


            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);


        }

    }

}


