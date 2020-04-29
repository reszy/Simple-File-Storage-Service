package pl.reszy.filestorage.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.reszy.filestorage.service.dto.StatsDto;
import pl.reszy.filestorage.service.dto.StorageSizeDto;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final FileInfoRepository fileInfoRepository;

    public StatsDto getStats() {
        return StatsDto.builder()
            .filesStored(fileInfoRepository.count())
            .occupiedSpace(new StorageSizeDto(fileInfoRepository.sumByContentLength()))
            .build();
    }

}