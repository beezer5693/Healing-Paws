package org.brandon.healingpaws.auditing;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class ApplicationAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.empty();
    }
}
