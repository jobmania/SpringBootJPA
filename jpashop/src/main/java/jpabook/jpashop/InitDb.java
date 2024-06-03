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
        initService.dbInit2();
        
       // 여기에 Service 코드작성하면 안되고 별도의 Bean을 등록해야된다.
    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        public void dbInit1(){

            Member member = createMember("userA","서울","1","5123");
            em.persist(member);

            Book book1 = createBook("JPA BOOK1",10000,100);
            em.persist(book1);

            Book book2 = createBook("JPA BOOK2", 20000, 100);
            em.persist(book2);


            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);


        }


        public void dbInit2(){

            Member member = createMember("userB","부산","4","9672");
            em.persist(member);

            Book book1 = createBook("Spring BOOK1",20000,220);
            em.persist(book1);

            Book book2 = createBook("Spring BOOK2", 40000, 300);
            em.persist(book2);


            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);


        }

        private static Book createBook(String name, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }

        private static Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private static Member createMember(String userName, String city, String street, String zipCode ) {
            Member member = new Member();
            member.setName(userName);
            member.setAddress(new Address(city,street,zipCode));
            return member;
        }



    }

}


