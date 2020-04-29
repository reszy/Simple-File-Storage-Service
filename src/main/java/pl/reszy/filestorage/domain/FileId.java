package pl.reszy.filestorage.domain;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class FileId {
    private String fileId;

    public static FileId random() {
        return new FileId(WEBID.randomWEBID().toString());
    }

    @Override
    public String toString() {
        return fileId;
    }
}