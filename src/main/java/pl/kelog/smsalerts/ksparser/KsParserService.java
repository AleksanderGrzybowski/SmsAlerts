package pl.kelog.smsalerts.ksparser;

import java.util.List;

public interface KsParserService {
    List<KsInfoEntryDto> parse(String content);
}
