package com.sparta.seoulmate.service;

import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.entity.UserInterest;
import com.sparta.seoulmate.entity.UserInterestEnum;
import com.sparta.seoulmate.repository.InterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;

    public void saveInterest(User user, List<String> list) {

        List<UserInterest> categories = list.stream()
                .map(categoryName -> UserInterest.builder().categories(UserInterestEnum.fromTitle(categoryName)).user(user).build())
                .collect(Collectors.toList());
        try{
            for(UserInterest lists: categories){
                interestRepository.save(lists);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
