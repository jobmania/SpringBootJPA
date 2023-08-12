package hellojpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("앨범!") // dTYPE 이름 설정 가능!
public class Album extends Item {
    private String artist;
}
