package pl.reszy.filestorage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.reszy.filestorage.service.StatsService;
import pl.reszy.filestorage.service.dto.StatsDto;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping
    public StatsDto getStatsDto() {
        return statsService.getStats();
    }
}