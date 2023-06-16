package com.assignment.voyage.service;

import com.assignment.voyage.dto.VoyageRequestDto;
import com.assignment.voyage.dto.VoyageResponseDto;
import com.assignment.voyage.entity.VoyagePost;
import com.assignment.voyage.repository.VoyageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VoyageService {
    private final VoyageRepository voyageRepository;

    public VoyageService(VoyageRepository voyageRepository) {
        this.voyageRepository = voyageRepository;
    }

    public VoyageResponseDto createVoyage(VoyageRequestDto voyageRequestDto) {
        // RequestDto -> Entity
        VoyagePost voyagePost = new VoyagePost(voyageRequestDto);
        //DB 저장
        VoyagePost saveVoyage = voyageRepository.save(voyagePost);
        // Entity -> ResponseDto
        VoyageResponseDto voyageResponseDto = new VoyageResponseDto(saveVoyage);

        return voyageResponseDto;
    }

    public List<VoyageResponseDto> getVoyages() {
        // DB 조회
        return voyageRepository.findAllByOrderByCreatedAtDesc().stream().map(VoyageResponseDto::new).toList();
    }

    public VoyageResponseDto getVoyageContent(String title) {
        VoyagePost voyagePost = findVoyageByTitle(title);
        VoyageResponseDto voyageResponseDto = new VoyageResponseDto(voyagePost);
        return voyageResponseDto;
    }

    @Transactional
    public VoyageResponseDto updateVoyage(String title, VoyageRequestDto voyageRequestDto) throws Exception {
        VoyagePost voyagePost = findVoyageByTitle(title);
        VoyageResponseDto voyageResponseDto;
        if (isPasswordMatching(voyagePost, voyageRequestDto.getPassword())) {
            voyagePost.update(voyageRequestDto);
            voyageResponseDto = new VoyageResponseDto(voyagePost);
        }
        else throw new Exception("비밀번호가 일치하지 않습니다!!");
        // 수정된 게시글을 반환해야 한다.
        return voyageResponseDto;
    }

    public String deleteVoyage(String title, VoyageRequestDto voyageRequestDto) throws Exception {
        // 해당 메모가 DB에 존재하는지 확인
        VoyagePost voyagePost = findVoyageByTitle(title);

        if (isPasswordMatching(voyagePost, voyageRequestDto.getPassword())) {
            voyageRepository.delete(voyagePost);
        }
        else throw new Exception("비밀번호가 일치하지 않습니다!!");
        return "success";
    }

    private VoyagePost findVoyage(Long id) {
        return voyageRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }

    private VoyagePost findVoyageByTitle(String title) {
        return voyageRepository.findByTitle(title).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }

    private boolean isPasswordMatching(VoyagePost voyagePost, String password) {
        if (voyagePost.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
