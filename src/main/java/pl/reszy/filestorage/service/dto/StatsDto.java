package pl.reszy.filestorage.service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StatsDto {
    Long filesStored;
    StorageSizeDto occupiedSpace;
    StorageSizeDto freeSpace;
}