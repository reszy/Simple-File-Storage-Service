package pl.reszy.filestorage.service.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.Value;

@Value
public class StorageSizeDto {

    private static final BigDecimal divisor = BigDecimal.valueOf(1024L);

    Long bytes;
    BigDecimal kilobytes;
    BigDecimal megabytes;
    BigDecimal gigabytes;

    public StorageSizeDto(Long bytes) {
        this.bytes = bytes;
        kilobytes = divideBy1024(bytes);
        megabytes = divideBy1024(kilobytes);
        gigabytes = divideBy1024(megabytes);
    }

    private static BigDecimal divideBy1024(BigDecimal number) {
        return number.divide(divisor, 4, RoundingMode.HALF_UP);
    }

    private static BigDecimal divideBy1024(Long number) {
        return BigDecimal.valueOf(number).divide(divisor, 4, RoundingMode.HALF_UP);
    }
}