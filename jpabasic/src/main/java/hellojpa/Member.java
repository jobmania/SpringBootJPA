package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;

@Entity
//@SequenceGenerator(
//        name = "MEMBER_SEQ_GENERATOR",
//        sequenceName = "MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
//        initialValue = 1, allocationSize = 50) // 미리 땡겨쓰기!
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO  ) // DB 방언에 맞춰 자동생성
    @Column(name = "MEMBER_ID")
    private Long id;

//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE,
//            generator = "MEMBER_SEQ_GENERATOR")
//    private Long id;
    @Column(name = "username") /// 데이터베이스 컬럼 수정 및 제약조건 추가 가능.
    private String username;
//    @Column(name = "TEAM_ID")
//    private Long teamId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")  // 연관관계 주인!
//    @JoinColumn(name = "TEAM_ID", insertable = false,updatable = false)  // 연관관계 주인!
    private Team team;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProductList;

//    private Integer age;
//    @Enumerated(EnumType.STRING) // db에 enum 사용
//    private RoleType roleType;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdDate;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;
//    private LocalDate testLocal;
//    private LocalDateTime testLocalDateTime;
//    @Lob // 크기제약 x 큰컨텣느.
//    private String description;
//
//    @Transient // 디비가아닌 메모리에만 저장하고싶어!
//    private int temp;

    public Member() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public Team getTeam() {
//        return team;
//    }

    public void changeTeam(Team team) {
//        this.team = team;
        team.getMembers().add(this);
    }
}
