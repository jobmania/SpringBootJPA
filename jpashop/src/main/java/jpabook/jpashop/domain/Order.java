package jpabook.jpashop.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue
    @Column(name ="order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id") // 연관관계의 주인을 정해줘야된다.
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne /// 오더를 조회빈도 높기때문에 여기에 외래키를 둘 수도 잇음
    @JoinColumn(name="delivery_id")
    private Delivery delivery;
    private LocalDateTime orderDate; // 주문시간
    private OrderStatus status; // 주문의 상태  [ ORDER, STATUS]
}
