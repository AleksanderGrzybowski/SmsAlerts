package pl.kelog.smsalerts.ksdownloader;

import pl.kelog.smsalerts.ksparser.KsInfoEntryDto;

import java.util.List;

public interface KsDownloaderService {
    List<KsInfoEntryDto> downloadFirstPage();
}
