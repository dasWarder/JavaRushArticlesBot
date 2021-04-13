package com.warder.jrtb.javarushclient.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.*;
import static java.util.Objects.nonNull;

@Getter
@Builder
public class GroupRequestArgs {

    private final String query;
    private final MeGroupInfoType type;
    private final GroupFilter filter;
    private final Integer offset;
    private final Integer limit;

    public Map populateQueries() {
        Map queries = new HashMap<>();
        if (nonNull(query)) {
            queries.put("query", query);
        }
        if (nonNull(type)) {
            queries.put("type", type);
        }
        if(nonNull(filter)) {
            queries.put("filter", filter);
        }
        if(nonNull(offset)) {
            queries.put("offset", offset);
        }
        if(nonNull(limit)) {
            queries.put("limit", limit);
        }

        return queries;
    }
}
