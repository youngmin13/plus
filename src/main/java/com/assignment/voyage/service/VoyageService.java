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

    public VoyageResponseDto getVoyageContent(Long id) {
        VoyagePost voyagePost = findVoyage(id);
        VoyageResponseDto voyageResponseDto = new VoyageResponseDto(voyagePost);
        return voyageResponseDto;
    }

    @Transactional
    public Long updateVoyage(Long id, VoyageRequestDto voyageRequestDto) {
        VoyagePost voyagePost = findVoyage(id);
        voyagePost.update(voyageRequestDto);
        return id;
    }

    public Long deleteVoyage(Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        VoyagePost voyagePost = findVoyage(id);

        // memo 삭제
        voyageRepository.delete(voyagePost);
        return id;
    }

    private VoyagePost findVoyage(Long id) {
        return voyageRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }

}
