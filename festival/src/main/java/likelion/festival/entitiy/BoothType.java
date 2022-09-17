package likelion.festival.entitiy;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BoothType {
    PUB("주점"),
    FOODTRUCK("푸드트럭"),
    BOOTH("부스");

    private String korean;

    BoothType(String korean) {this.korean = korean;}

    public String getKorean() {return korean;}
}
