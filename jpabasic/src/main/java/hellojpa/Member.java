package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
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


    @ManyToOne(fetch = FetchType.LAZY) // 프록시 객체로 조회 ! << 멤버만 조회
    @JoinColumn(name = "TEAM_ID")  // 연관관계 주인!
//    @JoinColumn(name = "TEAM_ID", insertable = false,updatable = false)  // 연관관계 주인!
    private Team team;

    @OneToOne(fetch = FetchType.LAZY)
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


    /// 엔티티에 동일한 속성이 반복될때 @
//    private String createBy;
//    private LocalDateTime createdDate;
//    private String modifiedBy;
//    private String lastModifiedBy;
//    private LocalDateTime lastModifiedDate;


    @Embedded

    private Period period;
    @Embedded
    @AttributeOverrides({  @AttributeOverride(name = "city",
            column = @Column(name = "WORK_CITY")),
            @AttributeOverride(name = "street",
                    column = @Column(name = "WORK_STREET")),
            @AttributeOverride(name = "zipcode",
                    column = @Column(name = "WORK_ZIPCODE"))}
    )
    private Address workAddress;
    @Embedded
    private Address homeAddress;


    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns =
                @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();
    //    @ElementCollection
//    @CollectionTable(name = "ADDRESS" , joinColumns =
//                @JoinColumn(name = "MEMBER_ID"))
//    private List<Address> addressHistory = new ArrayList<>();
    // 값타입 컬렉션 -> 엔티티 변경
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();


    public Address getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(Address workAddress) {
        this.workAddress = workAddress;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }


    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }

    public List<MemberProduct> getMemberProductList() {
        return memberProductList;
    }

    public void setMemberProductList(List<MemberProduct> memberProductList) {
        this.memberProductList = memberProductList;
    }

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public List<AddressEntity> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<AddressEntity> addressHistory) {
        this.addressHistory = addressHistory;
    }
}
