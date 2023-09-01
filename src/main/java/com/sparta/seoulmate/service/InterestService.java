package com.sparta.seoulmate.service;

import com.sparta.seoulmate.dto.interest.InterestListResponseDto;
import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.entity.UserInterest;
import com.sparta.seoulmate.entity.UserInterestEnum;
import com.sparta.seoulmate.repository.InterestRepository;
import com.sparta.seoulmate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;
    private final UserRepository userRepository;
    public void saveInterest(User user, List<String> list) {

        List<UserInterest> categories = list.stream()
                .map(categoryName -> UserInterest.builder().interests(UserInterestEnum.fromTitle(categoryName)).user(user).build())
                .collect(Collectors.toList());
        try{
            for(UserInterest lists: categories){
                interestRepository.save(lists);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public InterestListResponseDto getInterest(User user) {
        List<UserInterest> list =interestRepository.findByUser_id(user.getId());
        return InterestListResponseDto.of(list);
    }

    @Transactional
    public void updateInterest(User user, List<String> list) {
        List<UserInterest> newInterests = list.stream()
                .map(categoryName -> UserInterest.builder().interests(UserInterestEnum.fromTitle(categoryName)).user(user).build())
                .collect(Collectors.toList());

        for(UserInterest lists : newInterests){
            System.out.println(lists.getInterests());
        }

        User targetUser= userRepository.findByUsername(user.getUsername()).get();

        targetUser.getUserInterests().clear(); // 기존 관심사 모두 삭제
        targetUser.getUserInterests().addAll(newInterests);

    }

}
