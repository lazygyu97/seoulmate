package com.sparta.seoulmate.entity;

import java.util.*;
import java.util.stream.Collectors;

public enum UserInterestEnum {

    ROOT("카테고리", null),
    SPORT("체육시설", ROOT),//code=T100&dCode=
    SOCCER("축구장", SPORT),//code=T100&dCode=T107
    FUTSAL("풋살장", SPORT),//code=T100&dCode=T109
    FOOTVOLLEYBALL("족구장", SPORT),//code=T100&dCode=T106
    BASEBALL("야구장", SPORT),//code=T100&dCode=T105
    TENNIS("테니스장", SPORT),////code=T100&dCode=T108
    BASKETBALL("농구장", SPORT),//code=T100&dCode=T101
    VOLLEYBALL("배구장", SPORT),//code=T100&dCode=T103
    MULTIPURPOSEGYM("다목적경기장", SPORT),//code=T100&dCode=T102
    PLAYFIELD("운동장", SPORT),//code=T100&dCode=T125
    GYM("체육관", SPORT),//code=T100&dCode=T126
    BADMINTON("배드민턴장", SPORT),//code=T100&dCode=T104
    TABLETENNIS("탁구장", SPORT),//code=T100&dCode=T127
    EDUCATIOGYM("교육시설", SPORT),//code=T100&dCode=T116
    SWIMMING("수영장", SPORT),//code=T100&dCode=T117
    GOLF("골프장", SPORT),//code=T100&dCode=T115

    SPACE("공간시설", ROOT),//code=T500&dCode=
    CAMPING("캠핑장", SPACE),//code=T500&dCode=T502
    MULTIPURPOSEROOM("다목적실", SPACE),//code=T500&dCode=T506
    LECTURE("강의실", SPACE),//code=T500&dCode=T505
    AUDITORIUM("강당", SPACE),//code=T500&dCode=T504
    MEETING("회의실", SPACE),//code=T500&dCode=T509
    CITIZENSHARE("주민공유공간", SPACE),//code=T500&dCode=T508
    CONCERTHALL("공연장", SPACE),//code=T500&dCode=T511
    RECORDING("녹화장소", SPACE),//code=T500&dCode=T50
    EXHIBITION("전시실", SPACE),//code=T500&dCode=T507
    PLAZA("광장", SPACE),//code=T500&dCode=T513
    YOUTHSPACE("청년공간", SPACE),//code=T500&dCode=T514
    ETCSPACE("민원 등 기타", SPACE),//code=T500&dCode=T510

    CULTURE("문화체험", ROOT),//code=T200&dCode=
    EDUCATIONEX("교육체험", CULTURE),//code=T200&dCode=T205
    FARM("농장체험", CULTURE),//code=T200&dCode=T206
    CULTUREEVENT("문화행사", CULTURE),//code=T200&dCode=T204
    VIEWING("전시/관람", CULTURE),//code=T200&dCode=T203
    VOLUNTEER("단체봉사", CULTURE),//code=T200&dCode=T202
    PARKTOUR("공원탐방", CULTURE),//code=T200&dCode=T207
    KIDSCAFE("서울형키즈카페", CULTURE),//code=T200&dCode=T208
    FORESTLEISURE("산림여가", CULTURE),//code=T200&dCode=T209

    EDUCATION("교육강좌", ROOT),//code=T000&dCode=
    EDUSPORT("스포츠", EDUCATION),//code=T000&dCode=T014
    CRAFTS("공예/취미", EDUCATION),//code=T000&dCode=T025
    SCIENCE("자연/과학", EDUCATION),//code=T000&dCode=T021
    HISTORY("역사", EDUCATION),//code=T000&dCode=T019
    LANGUAGE("교양/어학", EDUCATION),//code=T000&dCode=T015
    ART("미술제작", EDUCATION),//code=T000&dCode=T017
    EDUTOOL("교육도구", EDUCATION),//code=T000&dCode=T016
    ICT("정보통신", EDUCATION),//code=T000&dCode=T023
    LICENSE("전문/자격증", EDUCATION),//code=T000&dCode=T022
    CITYFARM("도시농업", EDUCATION),//code=T000&dCode=T027
    YOUTHINFO("청년정보", EDUCATION),//code=T000&dCode=T028
    ETCEDU("기타", EDUCATION),//code=T000&dCode=T026

    MEDICAL("진료복지", ROOT),//code=T400&dCode=T026
    PUBLICHEALTH("보건소", MEDICAL),//code=T400&dCode=T404
    BUS("장애인버스", MEDICAL),//code=T400&dCode=T405
    FAMILYHOUSE("가족안심숙소", MEDICAL),//code=T400&dCode=T406
    EUNPYEONG("은평병원", MEDICAL),//code=T400&dCode=T401
    CHILDREN("어린이병원", MEDICAL),//code=T400&dCode=T402
    NORTHWEST("서북병원", MEDICAL);//code=T400&dCode=T403
    private final String title;

    // 부모 카테고리
    private final UserInterestEnum parentCategory;

    // 자식카테고리
    private final List<UserInterestEnum> childCategories;

    UserInterestEnum(String title, UserInterestEnum parentCategory) {
        this.childCategories = new ArrayList<>();
        this.title = title;
        this.parentCategory = parentCategory;
        if (Objects.nonNull(parentCategory)) {
            parentCategory.childCategories.add(this);
        }
    }
    public static UserInterestEnum fromTitle(String title) {
        return Arrays.stream(values())
                .filter(category -> category.title.equals(title))
                .findFirst()
                .orElse(null); // 또는 원하는 방식으로 처리
    }
    public String getTitle() {
        return title;
    }

    // 부모카테고리 Getter
    public Optional<UserInterestEnum> getParentCategory() {
        return Optional.ofNullable(parentCategory);
    }

    // 자식카테고리 Getter
    public List<UserInterestEnum> getChildCategories() {
        return Collections.unmodifiableList(childCategories);
    }

    // 마지막 카테고리인지 반환
    public boolean isLeafCategory() {
        return childCategories.isEmpty();
    }

    // 마지막 카테고리들 반환
    public List<UserInterestEnum> getLeafCategories() {
        return Arrays.stream(UserInterestEnum.values())
                .filter(category -> category.isLeafCategoryOf(this))
                .collect(Collectors.toList());
    }

    private boolean isLeafCategoryOf(UserInterestEnum category) {
        return this.isLeafCategory() && category.contains(this);
    }

    private boolean contains(UserInterestEnum category) {
        if (this.equals(category)) return true;

        return Objects.nonNull(category.parentCategory) &&
                this.contains(category.parentCategory);
    }

}
