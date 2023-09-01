package com.sparta.seoulmate.dto.interest;

import com.sparta.seoulmate.entity.UserInterestEnum;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class CategoryResponseDto {
    private Map<String, List<CategoryInfo>> categoriesWithInfo;

    public CategoryResponseDto() {
        // 모든 카테고리 가져오기
        UserInterestEnum[] allCategories = UserInterestEnum.values();

        // 카테고리와 타이틀/이름 매핑하기
        categoriesWithInfo = categorizeCategoriesWithInfo(allCategories);
    }

    private Map<String, List<CategoryInfo>> categorizeCategoriesWithInfo(UserInterestEnum[] allCategories) {
        return UserInterestEnum.ROOT.getChildCategories().stream()
                .collect(Collectors.toMap(
                        parent -> parent.getTitle(),
                        parent -> parent.getChildCategories().stream()
                                .map(child -> new CategoryInfo(child.getTitle(), child.name()))
                                .filter(info -> isInfoBelongsToParent(info, parent, allCategories))
                                .collect(Collectors.toList())
                ));
    }

    private boolean isInfoBelongsToParent(CategoryInfo info, UserInterestEnum parent, UserInterestEnum[] allCategories) {
        return contains(allCategories, parent, info);
    }

    private boolean contains(UserInterestEnum[] categories, UserInterestEnum parent, CategoryInfo targetInfo) {
        for (UserInterestEnum category : categories) {
            if (category.getTitle().equals(targetInfo.getTitle()) && category.name().equals(targetInfo.getName()) &&
                    category.getParentCategory().isPresent() && category.getParentCategory().get().equals(parent)) {
                return true;
            }
        }
        return false;
    }

    @Getter
    private static class CategoryInfo {
        private final String title;
        private final String name;

        public CategoryInfo(String title, String name) {
            this.title = title;
            this.name = name;
        }
    }
}
