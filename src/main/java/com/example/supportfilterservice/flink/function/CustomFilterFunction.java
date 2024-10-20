package com.example.supportfilterservice.flink.function;

import com.example.supportfilterservice.model.SupportRequest;
import org.apache.flink.api.common.functions.FilterFunction;

import java.util.List;
import java.util.regex.Pattern;

public class CustomFilterFunction implements FilterFunction<SupportRequest> {

    private final boolean regexEnabled;
    private final Pattern regexPattern;
    private final boolean excludeModeEnabled;
    private final boolean replaceModeEnabled;
    private final boolean removeModeEnabled;
    private final List<String> disabledEndpoints;

    public CustomFilterFunction(boolean regexEnabled, String regexPattern, boolean excludeModeEnabled,
                                boolean replaceModeEnabled, boolean removeModeEnabled, List<String> disabledEndpoints) {
        this.regexEnabled = regexEnabled;
        this.regexPattern = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);
        this.excludeModeEnabled = excludeModeEnabled;
        this.replaceModeEnabled = replaceModeEnabled;
        this.removeModeEnabled = removeModeEnabled;
        this.disabledEndpoints = disabledEndpoints;
    }

    @Override
    public boolean filter(SupportRequest request) {
        if (disabledEndpoints.contains(request.getEndpoint())) {
            return false;
        }

        boolean isSensitive = false;

        if (regexEnabled && regexPattern.matcher(request.getMessage()).find()) {
            isSensitive = true;
            if (excludeModeEnabled) {
                return false;
            } else {
                if (replaceModeEnabled) {
                    request.setMessage("***");
                }
                if (removeModeEnabled) {
                    request.setMessage(null);
                }
            }
        }

        return true;
    }
}
